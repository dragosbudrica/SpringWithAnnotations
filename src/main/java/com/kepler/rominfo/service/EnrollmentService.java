package com.kepler.rominfo.service;

import com.kepler.rominfo.dao.EnrollmentDao;
import com.kepler.rominfo.dao.UserDao;
import com.kepler.rominfo.domain.vo.Course;
import com.kepler.rominfo.domain.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;


@Service
public class EnrollmentService {
    private EnrollmentDao enrollmentDao;
    private UserDao userDao;

    @Autowired
    public EnrollmentService(EnrollmentDao enrollmentDao, UserDao userDao) {
        this.enrollmentDao = enrollmentDao;
        this.userDao = userDao;
    }

    @Transactional
    public void enroll(Student student, Course course) {
        student.getCourses().add(course);
        course.getStudents().add(student);
        enrollmentDao.addEnrollment(student.getStudentId(), course.getCourseCode());
    }

    public boolean alreadyEnrolled(Student student, Course course) {
        for (Course currentCourse : enrollmentDao.getCoursesOfStudent(student.getStudentId())) {
            if (currentCourse.getCourseCode() == course.getCourseCode())
                return true;
        }
        return false;
    }

    public HttpStatus editResult(long courseCode, long userId, String result) {
        try {
            Student student = userDao.findStudentByUserId(userId);
            enrollmentDao.editResult(courseCode, student.getStudentId(), result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    public HttpStatus removeResult(long courseCode, long userId) {
        try {
            Student student = userDao.findStudentByUserId(userId);
            enrollmentDao.removeResult(courseCode, student.getStudentId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }

    public HttpStatus validateStudentsGrades(long courseCode, List userIds) {
        Student student = null;
        try {
            for (Object userId : userIds) {
                student = userDao.findStudentByUserId(Long.parseLong((String) userId));
                enrollmentDao.validate(courseCode, student.getStudentId());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }


        return HttpStatus.OK;
    }

    public HttpStatus invalidateStudentsGrades(long courseCode, List userIds) {
        Student student = null;
        try {
            for (Object userId : userIds) {
                student = userDao.findStudentByUserId(Long.parseLong((String) userId));
                enrollmentDao.invalidate(courseCode, student.getStudentId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.OK;
    }
}
