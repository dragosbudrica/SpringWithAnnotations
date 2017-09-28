package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Lecture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureDao {

    void createLecture(@Param("courseCode") long courseCode, @Param("name") String name);

    List<Lecture> getLectures(@Param("courseCode") long courseCode);

    void uploadPDF(@Param("file") byte[] file, @Param("lectureId") long lectureId);

    Lecture getLectureByNameAndCourseCode(@Param("courseCode") long courseCode, @Param("lectureName") String lectureName);

    Lecture getLectureById(long lectureId);

    void removeLectureAttachment(@Param("lectureId") long lectureId);
}
