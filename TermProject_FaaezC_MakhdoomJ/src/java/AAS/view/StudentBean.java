/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.UserDB;
import AAS.model.StudentUser;
import AAS.model.User;
import AAS.utility.CurrentUser;
import java.io.Serializable;
import java.sql.SQLException;
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
@Named(value = "StudentBean")
@SessionScoped
public class StudentBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private CurrentUser currentUser;

    private StudentUser user;
    
    private UserDB dataBase;

    @PostConstruct
    public void init() {
        currentUser = new CurrentUser(ds);
        user = (StudentUser) currentUser.getUser();
        dataBase = new UserDB(ds);
    }

    public StudentUser getUser() {
        return user;
    }

    public void setUser(StudentUser user) {
        this.user = user;
    }
    
    public String update(){
        try{
            setUser(user);
            dataBase.update(user);
            return "/studentFolder/index";
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
