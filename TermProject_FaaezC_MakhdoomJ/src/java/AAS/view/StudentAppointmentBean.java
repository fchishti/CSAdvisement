/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.StudentAppointmentDB;
import AAS.model.Appointment;
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
@Named(value = "studentAppointmentBean")
@SessionScoped
public class StudentAppointmentBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private StudentAppointmentDB dataBase;
    private List<Appointment> list;
    private CurrentUser user;

    @PostConstruct
    public void init() {
        dataBase = new StudentAppointmentDB(ds);
        user = new CurrentUser(ds);
        read();
    }

    public String create(Appointment appointment) {
        try {
            dataBase.create(appointment, user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
        return null;
    }

    public String read() {
        try {
            list = dataBase.read(user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String delete(Appointment appointment) {
        try {
            dataBase.delete(appointment, user.getUser());
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StudentAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
        return null;
    }

    public List<Appointment> getList() {
        return list;
    }
 
}
