/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.AppointmentDB;
import AAS.model.Appointment;
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
@Named(value = "appointmentBean")
@SessionScoped
public class AppointmentBean implements Serializable{
    @Resource(name="jdbc/ds_wsp")
    private DataSource ds;
    
    private AppointmentDB dataBase;
    private List<Appointment> list;
    
    @PostConstruct
    public void init(){
        dataBase = new AppointmentDB(ds);
        read();
    }
    
    public String read(){
        try{
            list = dataBase.read();
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String delete(Appointment appointment){
        try{
            dataBase.delete(appointment);
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
        return null;
    }

    public List<Appointment> getList() {
        return list;
    }

    public void setList(List<Appointment> list) {
        this.list = list;
    }
        
}
