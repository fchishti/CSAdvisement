/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.model;

import java.util.List;

/**
 *
 * @author faaez
 */
public class StudentUser extends User{
    
    public int studentId;
    public int majorCode;
    public List<Course> courses;

    public StudentUser(){
        super();
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
}
