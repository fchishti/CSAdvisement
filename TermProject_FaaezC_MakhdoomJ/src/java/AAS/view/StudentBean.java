/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.StudentAppointmentDB;
import AAS.controller.UserDB;
import AAS.model.Appointment;
import AAS.model.FacultyUser;
import AAS.model.StudentUser;
import AAS.model.User;
import AAS.utility.CurrentUser;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author faaez
 */
@Named(value = "StudentBean")
@SessionScoped
public class StudentBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private CurrentUser currentUser;

    private StudentUser studentUser;

    private FacultyUser facultyUser;

    private UserDB dataBase;
    
    private List<Appointment> appointments;
    
    private StudentAppointmentDB studentAppointmentDB;

    @PostConstruct
    public void init() {
        currentUser = new CurrentUser(ds);
        studentUser = (StudentUser) currentUser.getUser();
        dataBase = new UserDB(ds);
        studentAppointmentDB = new StudentAppointmentDB(ds);
        facultyUser = new FacultyUser();
    }

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public String update() {
        try {
            setStudentUser(studentUser);
            dataBase.update(studentUser);
            return "/studentFolder/index";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public FacultyUser getFacultyUser() {
        return facultyUser;
    }

    public void setFacultyUser(FacultyUser facultyUser) {
        this.facultyUser = facultyUser;
    }

    public String readFaculty(int userId) {

        try {
            facultyUser = (FacultyUser) dataBase.readUserFromId(userId, "faculty");
            readFacultyAppointments(userId);
            return "/studentFolder/faculty";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    private void readFacultyAppointments(int facultyId){
        try {
            appointments = studentAppointmentDB.readFacultyAppointment(currentUser.getUser(), facultyId);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
