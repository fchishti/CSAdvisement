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
}
