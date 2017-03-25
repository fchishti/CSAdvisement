/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.model;

import java.util.List;

/**
 *
 * @author cece
 */
public class FacultyUser extends User{
    
    public List<Appointment> slots;

    public List<Appointment> getSlots() {
        return slots;
    }

    public void setSlots(List<Appointment> slots) {
        this.slots = slots;
    }
}
