/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.UserDB;
import AAS.model.StudentUser;
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
 * @author cece
 */
@Named(value = "crudBean")
@SessionScoped
public class CrudBean implements Serializable{
    
    @Resource(name="jdbc/ds")
    private DataSource ds;
    
    private UserDB dataBase;
    private StudentUser user;
    private List<StudentUser> list;
    
    @PostConstruct
    public void init() {
        dataBase = new UserDB(ds);
        user = new StudentUser();
        read();
    }
    
    public String read(){
        try{
            this.list = dataBase.read();
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String create(){
        try{
            dataBase.create(user);
            return "/login";
        } catch (SQLException ex){
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public StudentUser getUser() {
        return user;
    }

    public void setUser(StudentUser user) {
        this.user = user;
    }

    public List<StudentUser> getList() {
        return list;
    }

    public void setList(List<StudentUser> list) {
        this.list = list;
    }
}
