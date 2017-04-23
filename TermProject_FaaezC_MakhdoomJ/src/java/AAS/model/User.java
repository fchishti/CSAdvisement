/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.model;

import javax.validation.constraints.NotNull;

/**
 *
 * @author faaez
 */
public abstract class User {

    @NotNull(message="Please enter something")
    public String firstname;
    
    @NotNull(message="Please enter something")
    public String lastname;
    
    @NotNull(message="Please enter something")
    public String email;
    
    @NotNull(message="Please enter something")
    public String password;
    
    public int userId;
    public String group;

    public User() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return this.firstname + " "
                + this.lastname + " "
                + this.email + " "
                + this.group;
    }

}
