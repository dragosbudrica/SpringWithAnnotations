package com.kepler.rominfo.service;

import com.kepler.rominfo.dao.LectureDao;
import com.kepler.rominfo.domain.vo.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    private LectureDao lectureDao;

    @Autowired
    public LectureService(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public List<Lecture> getLectures(long courseCode) {
        return lectureDao.getLectures(courseCode);
    }

    public void uploadFile(byte[] file, long lectureId) {
        try {
            if (file != null) {
                lectureDao.uploadPDF(file, lectureId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Lecture getLectureByNameAndCourseCode(long courseCode, String lectureName) {
            return lectureDao.getLectureByNameAndCourseCode(courseCode, lectureName);
    }

    public Lecture getLectureById(long lectureId) {
        return lectureDao.getLectureById(lectureId);
    }

    public HttpStatus removeLectureAttachment(long lectureId) {
        try {
            lectureDao.removeLectureAttachment(lectureId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }
}
