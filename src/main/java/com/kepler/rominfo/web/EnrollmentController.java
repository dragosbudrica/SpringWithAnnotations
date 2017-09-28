package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.logic.BusinessMessageAndCode;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.Student;
import com.kepler.rominfo.domain.vo.User;
import com.kepler.rominfo.service.CourseService;
import com.kepler.rominfo.service.EnrollmentService;
import com.kepler.rominfo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Api(value = "Enrollment Api")
@Controller
public class EnrollmentController {

    private UserService userService;
    private EnrollmentService enrollmentService;
    private CourseService courseService;
    private MessageSource messageSource;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEnrollmentService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @ApiOperation(value = "Get Enrolled Students At Specified Course Admin")
    @PostMapping(value = "/getEnrolledStudentsAdmin")
    public @ResponseBody
    BusinessMessageAndCode getEnrolledStudentsAdmin(@RequestBody Map<String, Object> params, Locale locale) {
        List<User> enrolledStudents=null;
        String courseCode = (String) params.get("courseCode");
        int code = 0;
        String message = null;
        try {
            enrolledStudents = userService.getEnrolledStudents(Long.parseLong(courseCode));
            if(enrolledStudents.size() == 0) {
                message = messageSource.getMessage("global.warning2", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                boolean isEverythingEvaluated = true;
                for (User student: enrolledStudents) {
                    if(student.getResult() == null) {
                        message = messageSource.getMessage("validation.warning3", null, locale);
                        code = BusinessMessageAndCode.ERROR;
                        isEverythingEvaluated = false;
                        break;
                    }
                }

                if(isEverythingEvaluated) {
                    message = "Everything is evaluated!";
                    code = BusinessMessageAndCode.SUCCESS;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(enrolledStudents, message, code);
    }

    @ApiOperation(value = "Get Enrolled Students At Specified Course Professor")
    @PostMapping(value = "/getEnrolledStudentsProfessor")
    public @ResponseBody
    BusinessMessageAndCode getEnrolledStudentsProfessor(@RequestBody Map<String, Object> params, Locale locale) {
        List<User> enrolledStudents=null;
        String courseCode = (String) params.get("courseCode");
        int code = 0;
        String message = null;
        try {
            enrolledStudents = userService.getEnrolledStudents(Long.parseLong(courseCode));
            if(enrolledStudents.size() == 0) {
                message = messageSource.getMessage("global.warning2", null, locale);
                code = BusinessMessageAndCode.ERROR;
            } else {
                message = "Get enrolled students at this course!";
                code = BusinessMessageAndCode.SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(enrolledStudents, message, code);
    }

    @ApiOperation(value = "Enrollment Operation")
    @PostMapping(value = "/enroll")
    public @ResponseBody
    BusinessMessageAndCode enroll(@RequestBody Map<String, Object> params, HttpSession session, Locale locale) {
        String enrollmentResult = null;
        String courseCode = (String) params.get("courseCode");
        User user = (User) session.getAttribute("user");
        Student student = userService.findStudent(user.getEmail());
        Course course = courseService.getCourseByCode(Long.parseLong(courseCode));
        int code;
        try {
            if(!enrollmentService.alreadyEnrolled(student, course)) {
                enrollmentService.enroll(student, course);
                enrollmentResult = messageSource.getMessage("enrollment.success", null, locale);
                code = BusinessMessageAndCode.SUCCESS;
            }
            else {
                enrollmentResult =  messageSource.getMessage("enrollment.error", null, locale);
                code = BusinessMessageAndCode.ERROR;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            enrollmentResult = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }
        return new BusinessMessageAndCode(enrollmentResult, code);
    }
}
