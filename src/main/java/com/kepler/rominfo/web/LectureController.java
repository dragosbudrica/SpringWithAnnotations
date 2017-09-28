package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.vo.Lecture;
import com.kepler.rominfo.service.LectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Api(value = "Lecture Api")
@Controller
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @ApiOperation(value = "Get All Lectures For Specified Course")
    @PostMapping(value = "/getLectures")
    public @ResponseBody
    List<Lecture> getLectures(@RequestBody Map<String, Object> params) {
        String courseCode = (String) params.get("courseCode");
        List<Lecture> lectures = null;
        try {
            lectures = lectureService.getLectures(Long.parseLong(courseCode));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lectures;
    }

    @ApiOperation(value = "Download Lecture")
    @GetMapping(value = "/download")
    public @ResponseBody
    String doDownload(@RequestParam("lectureId") String lectureId, HttpServletResponse response) {
        String result = null;

        Lecture lecture = lectureService.getLectureById(Long.parseLong(lectureId));
        InputStream is = new ByteArrayInputStream(lecture.getAttachment());
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename="+lecture.getName()+".pdf");
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            result = "The file was successfully downloaded!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "The download process failed! Reason: " + e.getMessage();
        }

        return result;
    }

    @ApiOperation(value = "Upload Lecture")
    @PostMapping(value = "/upload")
    public @ResponseBody
    String doUpload(MultipartHttpServletRequest request) {
        String result = null;

        MultipartFile multipartFile =  request.getFile("file");
        String lectureId = request.getParameter("lectureId");

        InputStream stream = null;
        try {
            stream = multipartFile.getInputStream();
            byte[] file = IOUtils.toByteArray(stream);
            lectureService.uploadFile(file, Long.parseLong(lectureId));
            result = "The file was successfully uploaded!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "The upload process failed! Reason: " + e.getMessage();
        }

        return result;
    }

    @ApiOperation(value = "Remove Lecture Attachment")
    @DeleteMapping(value = "/removeLectureAttachment")
    public ResponseEntity removeLectureAttachment(@RequestBody Map<String, Object> params) {
        String lectureId = (String) params.get("lectureId");
        return new ResponseEntity(lectureService.removeLectureAttachment(Long.parseLong(lectureId)));
    }
}
