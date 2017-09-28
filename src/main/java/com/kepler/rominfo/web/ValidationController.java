package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.dto.CourseDto;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.Student;
import com.kepler.rominfo.service.CourseService;
import com.kepler.rominfo.service.EnrollmentService;
import com.kepler.rominfo.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class ValidationController {

    private EnrollmentService enrollmentService;


    @Autowired
    public void setEnrollmentService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @ApiOperation(value = "Get Validation Page")
    @GetMapping(value = "/validation")
    public String validation() {
        return "validation";
    }

    @ApiOperation(value = "Validate Grades")
    @PutMapping(value = "/validate")
    public ResponseEntity validate(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        List userIds = (List) params.get("userIds");
        return new ResponseEntity(enrollmentService.validateStudentsGrades(Long.parseLong(courseCode), userIds));
    }

    @ApiOperation(value = "Invalidate Grades")
    @PutMapping(value = "/invalidate")
    public ResponseEntity invalidate(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        List userIds = (List) params.get("userIds");
        return new ResponseEntity(enrollmentService.invalidateStudentsGrades(Long.parseLong(courseCode), userIds));
    }
}
