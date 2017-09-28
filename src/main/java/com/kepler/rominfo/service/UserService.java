package com.kepler.rominfo.service;

import com.kepler.rominfo.dao.RoleDao;
import com.kepler.rominfo.dao.UserDao;
import com.kepler.rominfo.domain.vo.Professor;
import com.kepler.rominfo.domain.vo.Student;
import com.kepler.rominfo.domain.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }


    @Transactional
    public void addUser(String firstName, String lastName, long ssn, String email, String password, String role) {
        User user = new User();
        long roleId = roleDao.getRoleByName(role).getRoleId();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSsn(ssn);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(roleId);
        userDao.addUser(user);
        if(role.equals("Student")) {
            User student = userDao.findByEmail(email);
            userDao.addStudent(student.getUserId());
        } else {
            User professor = userDao.findByEmail(email);
            userDao.addProfessor(professor.getUserId());
        }
    }

    public User findUser(String email) {
        return userDao.findByEmail(email);
    }

    public Student findStudent(String email) {
        return userDao.findStudentByEmail(email);
    }

    public Professor findProfessor(String email) { return userDao.findProfessorByEmail(email); }

    public boolean checkCredentials(User user, String email, String password) {
        return (user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public List<User> getEnrolledStudents(long courseCode) {
        return userDao.getEnrolledStudents(courseCode);
    }
}

