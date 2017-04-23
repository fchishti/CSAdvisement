/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.AppointmentDB;
import AAS.controller.UserDB;
import AAS.model.Appointment;
import AAS.model.FacultyUser;
import AAS.model.User;
import AAS.utility.AppointmentAdder;
import AAS.utility.AppointmentGenerator;
import AAS.utility.CurrentUser;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author fchishti-sw
 */
@Named(value = "appointmentBean")
@SessionScoped
public class AppointmentBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private AppointmentDB dataBase;
    private UserDB userDB;
    private List<Appointment> list;
    private List<User> facultyList;
    private CurrentUser user;
    private User facultyUser;
    private AppointmentAdder appointmentAdder;
    private AppointmentGenerator appointmentGenerator;

    @PostConstruct
    public void init() {
        dataBase = new AppointmentDB(ds);
        userDB = new UserDB(ds);
        user = new CurrentUser(ds);
        facultyUser = new FacultyUser();
        appointmentAdder = new AppointmentAdder();
        appointmentGenerator = new AppointmentGenerator();
        readFaculty();
        readAppointments();
    }

    public String readFaculty() {
        try {
            this.facultyList = userDB.readFaculty();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String read() {
// ---- Debug code ----- //
        
        Logger logger = Logger.getLogger(getClass().getName());
        logger.severe("severe");
        logger.info(facultyUser.getLastname());
        logger.fine("fine");
        
        /*facultyUser.setUserId(2);
        facultyUser.setEmail("hsung@uco.edu");
        facultyUser.setFirstname("Hong");
        facultyUser.setLastname("Sung");*/
        
// ---- Debug code ----- //
        
        try {
            list = dataBase.read(facultyUser);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String delete(Appointment appointment) {
        try {
            dataBase.delete(appointment);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        readAppointments();
        return null;
    }

    public String create(Appointment appointment) {
        try {
            dataBase.create(user.getUser(), appointment);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/facultyFolder/appointments";
    }

    public User getFacultyUser() {
        return facultyUser;
    }
    
    public void setFacultyUser(User facultyUser) {
        this.facultyUser = facultyUser;
        Logger logger = Logger.getLogger(getClass().getName());
        logger.severe("severe");
        logger.info(facultyUser.toString());
        
        logger.fine("fine");
    }

    public List<Appointment> getList() {
        return list;
    }

    public List<User> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<User> facultyList) {
        this.facultyList = facultyList;
    }

    public AppointmentAdder getAppointmentAdder() {
        return appointmentAdder;
    }

    public void setAppointmentAdder(AppointmentAdder appointmentAdder) {
        this.appointmentAdder = appointmentAdder;
    }
    
    public String createAppointment(){
        Appointment appt = new Appointment();
        appt.setDateTime(this.appointmentAdder.getDateTime());
        appt.setNotes("Be on time in room MCS 116");
        this.create(appt);
        readAppointments();
        return "/facultyFolder/appointments";
    }

    public AppointmentGenerator getAppointmentGenerator() {
        return appointmentGenerator;
    }

    public void setAppointmentGenerator(AppointmentGenerator appointmentGenerator) {
        this.appointmentGenerator = appointmentGenerator;
    }
    
    public String generateAppointments(){
        List<Appointment> tempList = new ArrayList<>();
        tempList = appointmentGenerator.getAppointmentList();
        
        for(Appointment a : tempList){
            this.create(a);
        }
        readAppointments();
        return "/facultyFolder/appointments";
    }
    
    public String readAppointments(){
        try {
            list = dataBase.read(user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(AppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
