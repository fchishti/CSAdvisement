/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.AppointmentDB;
import AAS.controller.FacultyAppointmentDB;
import AAS.model.Appointment;
import AAS.model.StudentAppointment;
import AAS.utility.CurrentUser;
import AAS.utility.Email;
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
 * @author fchishti-sw
 */
@Named(value = "facultyAppointmentBean")
@SessionScoped
public class FacultyAppointmentBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private List<StudentAppointment> bookedAppointmentList;
    private List<Appointment> appointmentList;
    private Appointment appointment;
    private FacultyAppointmentDB bookedAppointmentDB;
    private AppointmentDB appointmentDB;
    private CurrentUser user;

    @PostConstruct
    public void init() {
        bookedAppointmentDB = new FacultyAppointmentDB(ds);
        appointmentDB = new AppointmentDB(ds);
        user = new CurrentUser(ds);
        readBookedAppointments();
        readAppointments();
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<StudentAppointment> getBookedAppointmentList() {
        return bookedAppointmentList;
    }

    public void setBookedAppointmentList(List<StudentAppointment> list) {
        this.bookedAppointmentList = list;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public String readBookedAppointments() {
        try {
            bookedAppointmentList = bookedAppointmentDB.read(user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String readAppointments() {
        try {
            appointmentList = appointmentDB.read(user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String cancelAppointment(StudentAppointment studentAppointment){
        try {
            bookedAppointmentDB.delete(studentAppointment);
            Email email = new Email(ds);
            email.sendDeleteConfirm(studentAppointment);
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
