/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.StudentCourseDB;
import AAS.controller.UserDB;
import AAS.model.Course;
import AAS.model.FacultyUser;
import AAS.model.StudentUser;
import AAS.utility.CurrentUser;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author faaez
 */
@Named(value = "FacultyBean")
@SessionScoped
public class FacultyBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private UserDB dataBase;
    private StudentCourseDB studentCoursesDB;

    private CurrentUser currentUser;
    private FacultyUser facultyUser;
    private StudentUser studentUser;
    private List<Course> studentCourses;

    @PostConstruct
    public void init() {
        dataBase = new UserDB(ds);
        studentCoursesDB = new StudentCourseDB(ds);
        currentUser = new CurrentUser(ds);
        facultyUser = (FacultyUser) currentUser.getUser();
        studentUser = new StudentUser();
    }

    public FacultyUser getFacultyUser() {
        return facultyUser;
    }

    public void setFacultyUser(FacultyUser facultyUser) {
        this.facultyUser = facultyUser;
    }

    public String update() {
        try {
            dataBase.update(facultyUser);
            return "/facultyFolder/index";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public String readStudent(int userId) {
        try {
            studentUser = (StudentUser) dataBase.readUserFromId(userId, "student");
            readStudentCourses(userId);
            return "/facultyFolder/student";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    private void readStudentCourses(int userId) {
        try {
            this.studentCourses = studentCoursesDB.readFromId(userId);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
