package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EnrollmentDao {
    void addEnrollment(@Param("studentId") long studentId, @Param("courseCode") long courseCode);
    List<Course> getCoursesOfStudent(@Param("studentId") long studentId);
    void editResult(@Param("courseCode") long courseCode, @Param("studentId") long studentId, @Param("result") String result);
    void removeResult(@Param("courseCode") long courseCode, @Param("studentId") long studentId);
    void validate(@Param("courseCode") long courseCode, @Param("studentId") long studentId);
    void invalidate(@Param("courseCode") long courseCode, @Param("studentId") long studentId);
}
