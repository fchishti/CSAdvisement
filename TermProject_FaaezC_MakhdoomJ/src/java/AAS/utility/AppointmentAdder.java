/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.utility;

import java.util.HashMap;
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

    private static HashMap<String, Integer> months;
    private static HashMap<String, String> zones;

    public AppointmentAdder() {
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

    public DateTime getDateTime() {
        
        String timeZone = zones.get(this.date.substring(20, 23));
        
        this.date = normalizeDate();
        if (amPm.toLowerCase().equals("pm")) {
            hours = String.valueOf(((int) (Double.parseDouble(hours))) + 12);
        }

        hours = String.valueOf((int) (Double.parseDouble(hours)));
        minutes = String.valueOf((int) (Double.parseDouble(minutes)));

        if (((int) (Double.parseDouble(hours))) < 10) {
            hours = "0" + hours;
        }

        if (((int) (Double.parseDouble(minutes))) < 10) {
            minutes = "0" + minutes;
        }
        
        DateTime temp = new DateTime(this.date + "T" + hours + ":" + minutes + ":" + "00" + timeZone);
        
        return temp;
    }

    private String normalizeDate() {
        String day = this.date.substring(8, 10);
        String month = String.valueOf(months.get(this.date.substring(4, 7).toUpperCase()));
        String year = this.date.substring(24, 28);

        return year + "-" + month + "-" + day;
    }

    @Override
    public String toString() {
        DateTime temp = this.getDateTime();
        return temp.toString();
    }
}
