/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.UserDB;
import AAS.model.FacultyUser;
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
@Named(value = "FacultyBean")
@SessionScoped
public class FacultyBean implements Serializable{
    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private UserDB dataBase;
    
    private CurrentUser currentUser;

    private FacultyUser user;

    @PostConstruct
    public void init() {
        dataBase = new UserDB(ds);
        currentUser = new CurrentUser(ds);
        user = (FacultyUser) currentUser.getUser();
    }

    public FacultyUser getUser() {
        return user;
    }

    public void setUser(FacultyUser user) {
        this.user = user;
    }
    
    public String update(){
        try{
            dataBase.update(user);
            return "/facultyFolder/index";
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
