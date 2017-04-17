/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.UserDB;
import AAS.model.StudentUser;
import AAS.model.User;
import AAS.utility.Email;
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
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private UserDB dataBase;
    private StudentUser user;
    private List<User> list;
    private Email email;

    @PostConstruct
    public void init() {
        dataBase = new UserDB(ds);
        user = new StudentUser();
        email = new Email(ds);
        read();
    }

    public String read() {
        try {
            this.list = dataBase.read();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String create() {
        try {
            dataBase.create(user);
            email.sendAuthCode(user);
            return "/login";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String delete(User user) {
        try {
            dataBase.delete(user);
            return "/login";
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String update(User user) {
        try {
            dataBase.delete(user);
            return "/login";
        } catch (SQLException ex) {
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

    public List<User> getList() {
        return list;
    }

//<editor-fold defaultstate="collapsed" desc="Misc Methods">
    public String readFaculty() {
        try {
            this.list = dataBase.readFaculty();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String readStudent() {
        try {
            this.list = dataBase.readStudent();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//</editor-fold>
}
