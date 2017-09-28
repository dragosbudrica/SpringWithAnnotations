package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Professor;
import com.kepler.rominfo.domain.vo.Student;
import com.kepler.rominfo.domain.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    List<User> getAllUsers();

    User findByEmail(@Param("email") String email);

    void addUser(User user);

    void addStudent(@Param("userId") long userId);

    void addProfessor(@Param("userId") long userId);

    List<User> getEnrolledStudents(@Param("courseCode") long courseCode);

    Student findStudentByEmail(@Param("email") String email);

    Professor findProfessorByEmail(@Param("email") String email);

    Student findStudentByUserId(@Param("userId") long userId);
}
