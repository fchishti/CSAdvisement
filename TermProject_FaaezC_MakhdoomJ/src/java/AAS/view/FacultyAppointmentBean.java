/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.FacultyAppointmentDB;
import AAS.model.Appointment;
import AAS.model.StudentAppointment;
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
 * @author fchishti-sw
 */
@Named(value = "facultyAppointmentBean")
@SessionScoped
public class FacultyAppointmentBean implements Serializable{
    
    @Resource(name="jdbc/ds_wsp")
    private DataSource ds;
    
    private List<StudentAppointment> list;
    private Appointment appointment;
    private FacultyAppointmentDB dataBase;
    private CurrentUser user;
    
    @PostConstruct
    public void init(){
        dataBase = new FacultyAppointmentDB(ds);
        user = new CurrentUser(ds);
        read();
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<StudentAppointment> getList() {
        return list;
    }

    public void setList(List<StudentAppointment> list) {
        this.list = list;
    }
    
    
    public String read() {
        try {
            list = dataBase.read(user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
