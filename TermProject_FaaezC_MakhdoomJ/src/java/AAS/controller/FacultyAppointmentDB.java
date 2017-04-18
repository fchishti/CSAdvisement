/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.Appointment;
import AAS.model.StudentAppointment;
import AAS.model.StudentUser;
import AAS.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.joda.time.DateTime;

/**
 *
 * @author fchishti-sw
 */
public class FacultyAppointmentDB {

    private DataSource db;

    public FacultyAppointmentDB(DataSource db) {
        this.db = db;
    }

    public List<StudentAppointment> read(User user) throws SQLException {

        List<StudentAppointment> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select u.ID, u.FIRSTNAME, u.LASTNAME, a.DATE, a.TIME, a.ID as 'APPOINTMENTID', a.NOTES from USERTABLE u "
                    + "join STUDENTAPPOINTMENTTABLE s on u.id = s.USER_ID "
                    + "join APPOINTMENTTABLE a on s.APPOINTMENT_ID = a.ID "
                    + "where a.USER_ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                StudentAppointment sa = new StudentAppointment();

                Appointment a = new Appointment();
                a.setAppointmentId(result.getInt("APPOINTMENTID"));

                Date date = result.getDate("DATE");
                Time time = result.getTime("TIME");
                DateTime dateTime = new DateTime(date.toString() + "T" + time.toString());

                a.setDateTime(dateTime);
                a.setNotes(result.getString("NOTES"));

                User u = new StudentUser();
                u.setUserId(result.getInt("ID"));
                u.setFirstname(result.getString("FIRSTNAME"));
                u.setLastname(result.getString("LASTNAME"));

                sa.setAppointment(a);
                sa.setUser(u);

                list.add(sa);
            }

        } finally {
            conn.close();
        }

        return list;
    }

    public List<Appointment> readAll(User user) throws SQLException {

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
                    "select ID, DATE, TIME, NOTES from APPOINTMENTTABLE where USER_ID = (?) "
            );

            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                Appointment a = new Appointment();
                a.setAppointmentId(result.getInt("ID"));

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
                    + "values (?,?,?,(select ID from USERTBALE where ID = (?)),?,?)"
            );

            String dateStr = appointment.getDateTime().year().getAsString()
                    + appointment.getDateTime().monthOfYear().getAsString()
                    + appointment.getDateTime().dayOfMonth().getAsString();
            
            String timeStr = appointment.getDateTime().hourOfDay().getAsString()
                    + appointment.getDateTime().minuteOfHour().getAsString()
                    + appointment.getDateTime().secondOfMinute().getAsString();

            Date date = Date.valueOf(dateStr);
            Time time = Time.valueOf(timeStr);

            ps.setDate(1, date);
            ps.setTime(2, time);
            ps.setString(3, appointment.getNotes());
            ps.setInt(4, user.getUserId());
            ps.setString(5, user.getFirstname());
            ps.setString(6, user.getLastname());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
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
