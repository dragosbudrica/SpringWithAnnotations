package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface CourseDao {
    List<Course> getAllCourses();

    Course getCourseByCode(long courseCode);

    List<Course> getStudentCourses(@Param("studentId") long studentId);

    List<Course> getStudentCoursesWithDates(@Param("studentId") long studentId);

    List<Course> getProfessorCourses(@Param("professorId") long professorId);

    List<Course> getCoursesWithEnrolledStudents(@Param("professorId") long professorId);

    List<Course> getOnlyCoursesWithDates();

    void updateCourseSchedule(@Param("courseName") String courseName, @Param("startTime") Date startTime);

    void addCourse(@Param("course") Course course);

    Course getCourseByName(@Param("courseName") String courseName);

    void editCourse(@Param("courseCode") long courseCode, @Param("courseName") String courseName, @Param("categoryId") long categoryId);

    void deleteCourse(@Param("courseCode") long courseCode);

    List<Course> getStudentCoursesWithGrades(@Param("studentId") long studentId);
}
