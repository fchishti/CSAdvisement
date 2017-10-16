/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.model;

import org.joda.time.DateTime;

/**
 *
 * @author faaez
 */
public class Appointment {

    public int appointmentId;
    public int facultyId;
    public String facultyFirstname;
    public String facultyLastname;
    public DateTime dateTime;
    public String notes;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFacultyFirstname() {
        return facultyFirstname;
    }

    public void setFacultyFirstname(String facultyFirstname) {
        this.facultyFirstname = facultyFirstname;
    }

    public String getFacultyLastname() {
        return facultyLastname;
    }

    public void setFacultyLastname(String facultyLastname) {
        this.facultyLastname = facultyLastname;
    }

}
