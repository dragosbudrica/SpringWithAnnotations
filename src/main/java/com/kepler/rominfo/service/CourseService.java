package com.kepler.rominfo.service;


import com.kepler.rominfo.dao.CategoryDao;
import com.kepler.rominfo.dao.CourseDao;
import com.kepler.rominfo.dao.LectureDao;
import com.kepler.rominfo.dao.UserDao;
import com.kepler.rominfo.domain.dto.CourseDto;
import com.kepler.rominfo.domain.vo.Category;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.Professor;
import com.kepler.rominfo.domain.vo.Student;
import com.kepler.rominfo.exception.CourseAlreadyExistsException;
import com.kepler.rominfo.exception.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private UserDao userDao;
    private LectureDao lectureDao;

    private static final String BEGINNING_OF_SCHOOL = "2016-10-01";
    private static final String END_OF_SCHOOL = "2017-07-01";

    @Autowired
    public CourseService(UserDao userDao, CourseDao courseDao, LectureDao lectureDao, CategoryDao categoryDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
        this.lectureDao = lectureDao;
        this.categoryDao = categoryDao;
    }


    public List<Course> getAllCourses() {
        List<Course> allCourses = null;
        try {
            allCourses = courseDao.getAllCourses();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return allCourses;
    }

    public List<Course> getStudentCourses(String email) {
        Student student = userDao.findStudentByEmail(email);
        return courseDao.getStudentCourses(student.getStudentId());
    }

    public List<Course> getStudentCoursesWithGrades(String email) {
        Student student = userDao.findStudentByEmail(email);
        return courseDao.getStudentCoursesWithGrades(student.getStudentId());
    }

    public List<Course> getProfessorCourses(String email) {
        Professor professor = userDao.findProfessorByEmail(email);
        return courseDao.getProfessorCourses(professor.getProfessorId());
    }

    public Course getCourseByName(String courseName) {
        return courseDao.getCourseByName(courseName);
    }

    public Course getCourseByCode(long courseCode) {
        return courseDao.getCourseByCode(courseCode);
    }

    public void setTime(String courseName, Date startTime) {
        courseDao.updateCourseSchedule(courseName, startTime);
    }

    public void addCourse(String courseName, String category, int numberOfLectures, String description, String email) throws CourseAlreadyExistsException {
        Professor professor = userDao.findProfessorByEmail(email);
        Course course = new Course();
        Category cat = categoryDao.getCategoryByName(category);
        course.setCourseName(courseName);
        course.setCategory(cat);
        course.setCategoryId(cat.getCategoryId());
        course.setNumberOfLectures(numberOfLectures);
        course.setDescription(description);
        course.setProfessorId(professor.getProfessorId());
        List<Course> allCourses = getAllCourses();
        for (Course currentCourse : allCourses) {
            if (currentCourse.getCourseName().equals(courseName)) {
                throw new CourseAlreadyExistsException("Course already exists!");
            }
        }
        courseDao.addCourse(course);
        for (int i = 0; i < numberOfLectures; i++) {
            lectureDao.createLecture(course.getCourseCode(), "Lecture " + (i + 1));
        }
    }

    public List<CourseDto> getOnlyCoursesWithDates() {

        List<Course> allCourses = courseDao.getOnlyCoursesWithDates();
        List<CourseDto> courses = new ArrayList<CourseDto>();
        Calendar cal = Calendar.getInstance(); // creates calendar

        for (Course currentCourse : allCourses) {
            CourseDto courseDto = new CourseDto();
            courseDto.setCourseName(currentCourse.getCourseName());
            courseDto.setStartTime(currentCourse.getStartTime());
            cal.setTime(currentCourse.getStartTime()); // sets start time
            cal.add(Calendar.HOUR_OF_DAY, 2); // adds two hours
            courseDto.setEndTime(cal.getTime());
            courses.add(courseDto);
        }
        return courses;
    }

    public List<CourseDto> getAllRecurrentCourses(CourseDto course) {
        List<CourseDto> events = new ArrayList<CourseDto>();
        CourseDto recurringEvent;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(course.getStartTime());
        Date newStartTime;
        Date newEndTime;

        try {
            Date beg = sdf.parse(BEGINNING_OF_SCHOOL);
            Date end = sdf.parse(END_OF_SCHOOL);
            while (c.getTime().compareTo(beg) > 0) {
                newStartTime = c.getTime();
                newEndTime = getEndTime(c, newStartTime, 2);
                recurringEvent = new CourseDto(course.getCourseName(), newStartTime, newEndTime);
                events.add(recurringEvent);
                setupNewRecurringEvent(c, newStartTime, -7);
            }
            setupNewRecurringEvent(c, course.getStartTime(), 7);
            while (c.getTime().compareTo(end) < 0) {
                newStartTime = c.getTime();
                newEndTime = getEndTime(c, newStartTime, 2);
                recurringEvent = new CourseDto(course.getCourseName(), newStartTime, newEndTime);
                events.add(recurringEvent);
                setupNewRecurringEvent(c, newStartTime, 7);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    private Date getEndTime(Calendar c, Date newStartTime, int amount) {
        c.setTime(newStartTime);
        c.add(Calendar.HOUR_OF_DAY, amount);
        return c.getTime();
    }

    private void setupNewRecurringEvent(Calendar c, Date newStartTime, int amount) {
        c.setTime(newStartTime);
        c.add(Calendar.DAY_OF_YEAR, amount);
    }

    public List<String> getAllCourseTitles() {

        List<Course> myCourses = courseDao.getAllCourses();
        List<String> courses = new ArrayList<String>();

        for (Course currentCourse : myCourses) {
            String courseName = currentCourse.getCourseName();
            courses.add(courseName);
        }
        return courses;
    }

   public void updateEvent(String courseName, String startTime) throws ParseException {
        Date formattedStartDate = formatStartTime(startTime);
        courseDao.updateCourseSchedule(courseName, formattedStartDate);
    }

    private Date formatStartTime(String startTime) throws ParseException {
        String[] tokens = startTime.split("T");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(tokens[0] + " " + tokens[1] + ":00.000");
    }

     public CourseDto getUpdatedCourseDto(Course course) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        CourseDto courseDto = new CourseDto();
        courseDto.setCourseName(course.getCourseName());
        courseDto.setStartTime(course.getStartTime());
        cal.setTime(course.getStartTime()); // sets start time
        cal.add(Calendar.HOUR_OF_DAY, 2); // adds two hours
        courseDto.setEndTime(cal.getTime());
        return courseDto;
    }

    public HttpStatus editCourseName(long courseCode, String courseName, String category) {
        try {
            Category cat = categoryDao.getCategoryByName(category);
            courseDao.editCourse(courseCode, courseName, cat.getCategoryId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    public HttpStatus deleteCourse(long courseCode) {
        try {
            courseDao.deleteCourse(courseCode);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    public List<Course> getProfessorCoursesWithEnrolledStudents(String email) {
        Professor professor = userDao.findProfessorByEmail(email);
        return courseDao.getCoursesWithEnrolledStudents(professor.getProfessorId());
    }

    public CourseDto putCourseDtoProperties(Course course) {
        CourseDto courseDto = new CourseDto();
        try {
            courseDto.setCourseCode(course.getCourseCode());
            courseDto.setCourseName(course.getCourseName());
            courseDto.setCategory(course.getCategory().getCategoryName());
            courseDto.setProfessor(course.getUser().getFullName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return courseDto;
    }

    public boolean isValid(String startTime) throws InvalidDateException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date universityStartDate = null;
        Date univeristyEndDate = null;
        Date date = null;
        try {
            universityStartDate = sdf.parse(BEGINNING_OF_SCHOOL);
            univeristyEndDate = sdf.parse(END_OF_SCHOOL);
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        /* dayOfWeek=7 => SATURDAY
         dayOfWeek=1 => SUNDAY*/
        return !date.before(universityStartDate) && !date.after(univeristyEndDate) && dayOfWeek != 1 && dayOfWeek != 7;
    }
}
