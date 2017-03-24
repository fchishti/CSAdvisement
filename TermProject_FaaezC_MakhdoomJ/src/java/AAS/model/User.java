/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.model;

/**
 *
 * @author faaez
 */
public class User {
    public String firstname;
    public String lasename;
    public String email;
    public String password;
    public int studentId;
    public int majorCode;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLasename() {
        return lasename;
    }

    public void setLasename(String lasename) {
        this.lasename = lasename;
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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(int majorCode) {
        this.majorCode = majorCode;
    }
    
    
}
