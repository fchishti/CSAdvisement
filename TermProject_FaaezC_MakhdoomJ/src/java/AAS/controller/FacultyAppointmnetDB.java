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
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.joda.time.DateTime;

/**
 *
 * @author fchishti-sw
 */
public class FacultyAppointmnetDB {
    
    private DataSource db;

    public FacultyAppointmnetDB(DataSource db) {
        this.db = db;
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
                    "select APPOINTMENTTABLE.ID, FACULTYFIRSTNAME, FACULTYLASTNAME, APPOINTMENTTABLE.USER_ID, DATE, TIME, NOTES from APPOINTMENTTABLE " +
                            "join STUDENTAPPOINTMENTTABLE on STUDENTAPPOINTMENTTABLE.APPOINTMENT_ID = APPOINTMENTTABLE.ID where APPOINTMENTTABLE.USER_ID = (?) "
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
                DateTime dateTime = new DateTime(date.toString() +"T" + time.toString());

                a.setDateTime(dateTime);
                a.setNotes(result.getString("NOTES"));
                list.add(a);
            }

        } finally {
            conn.close();
        }

        return list;
    }
}
