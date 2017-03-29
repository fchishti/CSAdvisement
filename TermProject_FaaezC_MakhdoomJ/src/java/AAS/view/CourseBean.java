/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.CourseDB;
import AAS.model.Course;
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
@Named(value = "courseBean")
@SessionScoped
public class CourseBean implements Serializable{
    @Resource(name="jdbc/ds_wsp")
    private DataSource ds;
    
    private CourseDB dataBase;
    private List<Course> list;
    
    @PostConstruct
    public void init(){
        dataBase = new CourseDB(ds);
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

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }
    
    
}
