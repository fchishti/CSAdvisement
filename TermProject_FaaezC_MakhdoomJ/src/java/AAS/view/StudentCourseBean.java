/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.CourseDB;
import AAS.controller.StudentCourseDB;
import AAS.model.Course;
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
@Named(value = "studentCourseBean")
@SessionScoped
public class StudentCourseBean implements Serializable{
    
    @Resource(name="jdbc/ds_wsp")
    private DataSource ds;
    
    private CourseDB coursesDB;
    private StudentCourseDB studentCoursesDB;
    private CurrentUser user;
    private List<Course> list;
    
    @PostConstruct
    public void init() {
        coursesDB = new CourseDB(ds);
        studentCoursesDB = new StudentCourseDB(ds);
        user = new CurrentUser(ds);
        read();
    }
    
    public String read(){
        try{
            this.list = studentCoursesDB.read(user.getUser());
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String insert(Course course){
        try{
            studentCoursesDB.create(user.getUser(),course);
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
        return null;
    }
    
    public String delete(Course course){
        try{
            studentCoursesDB.delete(user.getUser(),course);
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        read();
        return null;
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }
    
    
    
}
