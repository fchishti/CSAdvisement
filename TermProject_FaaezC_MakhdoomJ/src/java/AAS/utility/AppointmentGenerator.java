/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.utility;

import AAS.model.Appointment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author faaez
 */
public class AppointmentGenerator {
    
    private String date;
    private String from;
    private String to;
    private String interval;
    private String notes;
    
    private static HashMap<String, Integer> months;
    private static HashMap<String, String> zones;
    
    public AppointmentGenerator() {
        months = new HashMap<>();
        months.put("JAN", 1);
        months.put("FEB", 2);
        months.put("MAR", 3);
        months.put("APR", 4);
        months.put("MAY", 5);
        months.put("JUN", 6);
        months.put("JUL", 7);
        months.put("AUG", 8);
        months.put("SEP", 9);
        months.put("OCT", 10);
        months.put("NOV", 11);
        months.put("DEC", 12);
        
        zones = new HashMap<>();
        zones.put("HAST", "-10:00");
        zones.put("HADT", "-09:00");
        zones.put("AKDT", "-08:00");
        zones.put("PDT", "-07:00");
        zones.put("MST", "-07:00");
        zones.put("MDT", "-06:00");
        zones.put("CDT", "-05:00");
        zones.put("EDT", "-04:00");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public List<Appointment> getAppointmentList(){
        List<Appointment> appointmentList = new ArrayList<>();
        
        String timeZone = zones.get(this.date.substring(20, 23));
        this.date = normalizeDate();
        
        int fromTemp = ((int)Double.parseDouble(this.from)) * 60;
        int toTemp = ((int)Double.parseDouble(this.to)) * 60;
        int intervalTemp = (int)Double.parseDouble(this.interval);
        
        int slots = (toTemp - fromTemp) / intervalTemp;
        
        for(int i =  0; i < slots; i++){
            fromTemp += intervalTemp;
            double div = fromTemp / 60;
            int hours = (int) div;
            int minutes = ((fromTemp) - (hours * 60));
            DateTime temp = new DateTime(date + "T" + hours + ":" + minutes + ":00" + timeZone);
            
            Appointment a = new Appointment();
            a.setDateTime(temp);
            a.setNotes(notes);
            
            appointmentList.add(a);
        }
        
        return appointmentList;
    }
    
    private String normalizeDate() {
        String day = this.date.substring(8, 10);
        String month = String.valueOf(months.get(this.date.substring(4, 7).toUpperCase()));
        String year = this.date.substring(24, 28);

        return year + "-" + month + "-" + day;
    }
}
