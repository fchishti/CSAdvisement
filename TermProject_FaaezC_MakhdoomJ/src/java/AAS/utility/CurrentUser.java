/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.utility;

import AAS.controller.UserDB;
import AAS.model.User;
import AAS.view.SessionBean;
import java.security.Principal;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author faaez
 */
public class CurrentUser {

    private UserDB database;
    public User user;
    private DataSource ds;
    private String role;

    public CurrentUser(DataSource ds) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Principal p = fc.getExternalContext().getUserPrincipal();
        this.ds = ds;
        loadRole(fc);
        loadUser(p.getName());
    }

    private void loadRole(FacesContext fc) {
        if (fc.getExternalContext().isUserInRole("studentrole")) {
            this.role = "student";
        } else if (fc.getExternalContext().isUserInRole("facultyrole")) {
            this.role = "faculty";
        } else if (fc.getExternalContext().isUserInRole("adminrole")) {
            this.role = "admin";
        }
    }

    private void loadUser(String username) {
        database = new UserDB(ds);

        try {
            user = database.readUser(username, role);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUser() {
        return this.user;
    }
}
