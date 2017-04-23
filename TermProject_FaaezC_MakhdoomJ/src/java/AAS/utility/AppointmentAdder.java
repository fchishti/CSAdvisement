/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.utility;

import org.joda.time.DateTime;

/**
 *
 * @author faaez
 */
public class AppointmentAdder {
    private String date;
    private String hours;
    private String minutes;
    private String amPm;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }
    
    public DateTime getDateTime(){
        //this.date = normalizeDate();
        if(amPm.toLowerCase().equals("pm")){
            hours = String.valueOf((Integer.parseInt(hours)) + 12);
        }
        DateTime temp = new DateTime(this.date + "T" + hours + ":" + minutes);
        return temp;
    }
    
    private String normalizeDate(){
        String day = this.date.substring(0, 2);
        String month = this.date.substring(3, 5);
        String year = this.date.substring(6);
        
        return year + "/" + month + "/" + day;
    }
    
    @Override
    public String toString(){
        DateTime temp = this.getDateTime();
        return temp.toString();
    }
}
