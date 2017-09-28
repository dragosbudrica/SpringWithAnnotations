package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.dto.CourseDto;
import com.kepler.rominfo.domain.logic.BusinessMessageAndCode;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Api(value = "Timetable Api")
@Controller
public class TimetableController {

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

    @ApiOperation(value = "Get timetable page")
    @GetMapping(value = "/timetable")
    public String timetable() {
        return "timetable";
    }

    @ApiOperation(value = "Get course scheduling page")
    @GetMapping(value = "/courseScheduling")
    public String courseScheduling() {
        return "courseScheduling";
    }

    @ApiOperation(value = "Get Timetable Events")
    @GetMapping(value = "/getEvents")
    public @ResponseBody
    BusinessMessageAndCode getEvents(Locale locale) {
        List<CourseDto> events = new ArrayList<CourseDto>();
        int code;
        boolean timetableUnderConstruction = false;
        for (Course course : courseService.getAllCourses()) {
            if (course.getStartTime() == null) {
                timetableUnderConstruction = true;
                break;
            }
        }

        if (!timetableUnderConstruction) {
            for (CourseDto currentCourseDto : courseService.getOnlyCoursesWithDates()) {
                List<CourseDto> reccurentCourses = courseService.getAllRecurrentCourses(currentCourseDto);
                events.addAll(reccurentCourses);
            }
            code = BusinessMessageAndCode.SUCCESS;
        } else {
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(events, locale, code);
    }

    @ApiOperation(value = "Get Course Events")
    @GetMapping("/getCourseEvents")
    public @ResponseBody
    List<CourseDto> events() {
        List<CourseDto> events = new ArrayList<CourseDto>();
        try {
            for (CourseDto currentCourseDto : courseService.getOnlyCoursesWithDates()) {
                List<CourseDto> reccurentCourses = courseService.getAllRecurrentCourses(currentCourseDto);
                events.addAll(reccurentCourses);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return events;
    }

    @ApiOperation(value = "Edit event")
    @PutMapping(value = "/schedule")
    public @ResponseBody
    BusinessMessageAndCode schedule(@RequestBody Map<String, Object> params, Locale locale) {
        List<CourseDto> events = new ArrayList<CourseDto>();
        String courseName = (String) params.get("courseName");
        String startTime = (String) params.get("startTime");

        int code;
        String message;

        try {
            if (courseService.isValid(startTime)) {
                courseService.updateEvent(courseName, startTime);
                for (CourseDto currentCourseDto : courseService.getOnlyCoursesWithDates()) {
                    List<CourseDto> reccurentCourses = courseService.getAllRecurrentCourses(currentCourseDto);
                    events.addAll(reccurentCourses);
                }
                message = messageSource.getMessage("scheduling.success", null, locale);
                code = BusinessMessageAndCode.SUCCESS;
            } else {
                message = messageSource.getMessage("scheduling.error", null, locale);
                code = BusinessMessageAndCode.ERROR;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(events, message, code);
    }
}
