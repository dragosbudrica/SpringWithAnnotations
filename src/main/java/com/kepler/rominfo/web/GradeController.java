package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.dto.CourseDto;
import com.kepler.rominfo.domain.logic.BusinessMessage;
import com.kepler.rominfo.domain.logic.BusinessMessageAndCode;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.User;
import com.kepler.rominfo.service.CourseService;
import com.kepler.rominfo.service.EnrollmentService;
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

@Api(value = "Grade Api")
@Controller
public class GradeController {

    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private MessageSource messageSource;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setEnrollmentService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ApiOperation(value = "Get Professor Grades Page")
    @GetMapping(value = "/grades")
    public String grades() {
        return "grades";
    }

    @ApiOperation(value = "Get Student Grades Page")
    @GetMapping(value = "/myGrades")
    public String myGrades() {
        return "myGrades";
    }

    @ApiOperation(value = "Get Student Grades")
    @GetMapping(value = "/getMyCoursesWithGrades")
    public @ResponseBody
    List<CourseDto> getMyCoursesWithGrades(HttpSession session) {
        List<CourseDto> studentCoursesWithGrades = new ArrayList<>();
        try {
            User user = (User) session.getAttribute("user");
            List<Course> courses = courseService.getStudentCoursesWithGrades(user.getEmail());

            for (Course currentCourse : courses) {
                CourseDto courseDto = courseService.putCourseDtoProperties(currentCourse);
                courseDto.setResult(currentCourse.getResult());
                courseDto.setValidated(currentCourse.isValidated());
                studentCoursesWithGrades.add(courseDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentCoursesWithGrades;
    }

    @ApiOperation(value = "Get Courses With Enrolled Students")
    @GetMapping(value = "/getCoursesWithEnrolledStudents")
    public @ResponseBody
    BusinessMessageAndCode getProfessorCourses(HttpSession session, Locale locale) {
        List<CourseDto> professorCoursesWithEnrolledStudents = new ArrayList<>();
        String message;
        int code;

        try {
            User user = (User) session.getAttribute("user");
            List<Course> courses = courseService.getProfessorCoursesWithEnrolledStudents(user.getEmail());

            for (Course currentCourse : courses) {
                CourseDto courseDto = courseService.putCourseDtoProperties(currentCourse);
                courseDto.setNumberOfEnrolledStudents(currentCourse.getNumberOfEnrolledStudents());
                professorCoursesWithEnrolledStudents.add(courseDto);
            }

            if(professorCoursesWithEnrolledStudents.size() == 0) {
                message = messageSource.getMessage("course.warning1", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                message = "The professor courses have been taken successfully!";
                code = BusinessMessageAndCode.SUCCESS;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }
        return new BusinessMessageAndCode(professorCoursesWithEnrolledStudents, message, code);
    }

    @PutMapping(value = "/editResult")
    public ResponseEntity editResult(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        String userId = (String) params.get("userId");
        String result = (String) params.get("result");
        return new ResponseEntity(enrollmentService.editResult(Long.parseLong(courseCode), Long.parseLong(userId), result));
    }

    @DeleteMapping(value = "/removeResult")
    public ResponseEntity removeResult(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        String userId = (String) params.get("userId");
        return new ResponseEntity(enrollmentService.removeResult(Long.parseLong(courseCode), Long.parseLong(userId)));
    }
}
