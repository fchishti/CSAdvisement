/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.model.FacultyUser;
import AAS.utility.CurrentUser;
import java.io.Serializable;
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

    private CurrentUser currentUser;

    private FacultyUser user;

    @PostConstruct
    public void init() {
        currentUser = new CurrentUser(ds);
        user = (FacultyUser) currentUser.getUser();
    }

    public FacultyUser getUser() {
        return user;
    }

    public void setUser(FacultyUser user) {
        this.user = user;
    }
}
