/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.FacultyAppointmnetDB;
import AAS.model.Appointment;
import java.io.Serializable;
import java.util.List;
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
    
    private List<Appointment> list;
    private Appointment appointment;
    
    @PostConstruct
    public void init(){
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<Appointment> getList() {
        return list;
    }
    

}
