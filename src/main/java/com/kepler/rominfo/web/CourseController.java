package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.dto.CourseDto;
import com.kepler.rominfo.domain.logic.BusinessMessageAndCode;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.User;
import com.kepler.rominfo.exception.CourseAlreadyExistsException;
import com.kepler.rominfo.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Api(value = "Course Api")
@Controller
public class CourseController {

    private CourseService courseService;
    private MessageSource messageSource;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ApiOperation(value = "Get Course Titles")
    @GetMapping(value = "/getCourseTitles")
    public @ResponseBody
    List<String> courseTitles() {
        return courseService.getAllCourseTitles();
    }

    @ApiOperation(value = "Redirect to All Courses View")
    @GetMapping(value = "/allCourses")
    public String allCourses() {
        return "allCourses";
    }

    @ApiOperation(value = "Redirect to Student Courses View")
    @GetMapping(value = "/studentCourses")
    public String studentCourses() {
        return "studentCourses";
    }

    @ApiOperation(value = "Redirect to Professor Courses View")
    @GetMapping(value = "/professorCourses")
    public String professorCourses() {
        return "professorCourses";
    }

    @ApiOperation(value = "Redirect to New Course View")
    @RequestMapping(value = "/newCourse", method = RequestMethod.GET)
    public String newCourse() {
        return "newCourse";
    }

    @ApiOperation(value = "Get All Courses")
    @GetMapping(value = "/getAllCourses")
    public @ResponseBody
    BusinessMessageAndCode getAllCourses(Locale locale) {
        List<CourseDto> allCourses = new ArrayList<>();
        List<Course> courses = courseService.getAllCourses();
        int code;
        String message;

        try {
            for (Course currentCourse : courses) {
                CourseDto courseDto = courseService.putCourseDtoProperties(currentCourse);
                allCourses.add(courseDto);
            }
            if(allCourses.size() == 0) {
                message = messageSource.getMessage("global.warning1", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                message = "All courses have been taken successfully";
                code = BusinessMessageAndCode.SUCCESS;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(allCourses, message, code);
    }

    @ApiOperation(value = "Get Student Courses")
    @GetMapping(value = "/getStudentCourses")
    public @ResponseBody
    BusinessMessageAndCode getStudentCourses(HttpSession session, Locale locale) {
        List<CourseDto> studentCourses = new ArrayList<>();
        String message;
        int code;

        try {
            User user = (User) session.getAttribute("user");
            List<Course> courses = courseService.getStudentCourses(user.getEmail());

            for (Course currentCourse : courses) {
                CourseDto courseDto = courseService.putCourseDtoProperties(currentCourse);
                studentCourses.add(courseDto);
            }
            if(studentCourses.size() == 0) {
                message = messageSource.getMessage("course.warning2", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                message = "The student courses have been taken successfully";
                code = BusinessMessageAndCode.SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }
        return new BusinessMessageAndCode(studentCourses, message, code);
    }

    @ApiOperation(value = "Get Professor Courses")
    @GetMapping(value = "/getProfessorCourses")
    public @ResponseBody
    BusinessMessageAndCode getProfessorCourses(HttpSession session, Locale locale) {
        List<CourseDto> professorCourses = new ArrayList<>();
        String message;
        int code;

        try {
            User user = (User) session.getAttribute("user");
            List<Course> courses = courseService.getProfessorCourses(user.getEmail());

            for (Course currentCourse : courses) {
                CourseDto courseDto = courseService.putCourseDtoProperties(currentCourse);
                professorCourses.add(courseDto);
            }
            if(professorCourses.size() == 0) {
                message = messageSource.getMessage("course.warning1", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                message = "The professor courses have been taken successfully";
                code = BusinessMessageAndCode.SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }
        return new BusinessMessageAndCode(professorCourses, message, code);
    }

    @ApiOperation(value = "Edit An Existing Course")
    @PutMapping(value = "/editCourse")
    public ResponseEntity editCourse(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        String courseName = (String) params.get("courseName");
        String category = (String) params.get("category");
        return new ResponseEntity(courseService.editCourseName(Long.parseLong(courseCode), courseName, category));
    }

    @ApiOperation(value = "Remove An Existing Course")
    @DeleteMapping(value = "/deleteCourse")
    public ResponseEntity deleteCourse(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        return new ResponseEntity(courseService.deleteCourse(Long.parseLong(courseCode)));
    }

    @ApiOperation(value = "Add New Course")
    @PostMapping(value = "/addNewCourse")
    public @ResponseBody
    BusinessMessageAndCode addCourse(@RequestBody Map<String, Object> params, HttpSession session, Locale locale) {
        String message;
        int code;
        String courseName = (String) params.get("courseName");
        String category = (String) params.get("category");
        String numberOfLectures = (String) params.get("numberOfLectures");
        String description = (String) params.get("description");
        try {
            User user = (User) session.getAttribute("user");
            courseService.addCourse(courseName, category, Integer.parseInt(numberOfLectures), description, user.getEmail());
            message = messageSource.getMessage("newCourse.success", null, locale);
            code = BusinessMessageAndCode.SUCCESS;
        } catch (CourseAlreadyExistsException ex) {
            message = messageSource.getMessage("newCourse.error", null, locale);
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(message, code);
    }
}
