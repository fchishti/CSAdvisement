/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.Appointment;
import AAS.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.joda.time.DateTime;

/**
 *
 * @author cece
 */
public class AppointmentDB {

    private DataSource db;

    public AppointmentDB(DataSource db) {
        this.db = db;
    }

    public void create(User user, Appointment appointment) throws SQLException {

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into APPOINTMENTTABLE (DATE, TIME, NOTES, USER_ID, FACULTYFIRSTNAME, FACULTYLASTNAME)"
                    + "values (?,?,?,(select ID from USERTABLE where ID = (?)),(select FIRSTNAME from USERTABLE where ID = (?)),(select LASTNAME from USERTABLE where ID = (?)))"
            );

            String dateStr = appointment.getDateTime().year().getAsString()
                    + "-" + appointment.getDateTime().monthOfYear().getAsString()
                    + "-" +appointment.getDateTime().dayOfMonth().getAsString();

            String timeStr = appointment.getDateTime().hourOfDay().getAsString()
                    + ":" +appointment.getDateTime().minuteOfHour().getAsString()
                    + ":"  +appointment.getDateTime().secondOfMinute().getAsString();

            Date date = Date.valueOf(dateStr);
            Time time = Time.valueOf(timeStr);

            ps.setDate(1, date);
            ps.setTime(2, time);
            ps.setString(3, appointment.getNotes());
            ps.setInt(4, user.getUserId());
            ps.setInt(5, user.getUserId());
            ps.setInt(6, user.getUserId());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public List<Appointment> read(User user) throws SQLException {

        List<Appointment> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    /* "select ID, USER_ID, EXTRACT(DAY from DATE), EXTRACT(MONTH from DATE), EXTRACT(YEAR from DATE), "
                    + "EXTRACT(HOUR from TIME), EXTRACT(MINUTE from TIME), EXTRACT(SECOND from TIME), NOTES from APPOINTMENTTABLE"*/
                    "select ID, FACULTYFIRSTNAME, FACULTYLASTNAME, USER_ID, DATE, TIME, NOTES from APPOINTMENTTABLE where USER_ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                Appointment a = new Appointment();
                a.setAppointmentId(result.getInt("ID"));
                a.setFacultyId(result.getInt("USER_ID"));
                a.setFacultyFirstname(result.getString("FACULTYFIRSTNAME"));
                a.setFacultyLastname(result.getString("FACULTYLASTNAME"));

                Date date = result.getDate("DATE");
                Time time = result.getTime("TIME");
                DateTime dateTime = new DateTime(date.toString() + "T" + time.toString());

                a.setDateTime(dateTime);
                a.setNotes(result.getString("NOTES"));
                list.add(a);
            }

        } finally {
            conn.close();
        }

        return list;
    }

    public void delete(Appointment appointment) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "delete from APPOINTMENTTABLE where ID = (?)"
            );

            ps.setInt(1, appointment.getAppointmentId());

            int result = ps.executeUpdate();

        } finally {
            conn.close();
        }
    }
}
