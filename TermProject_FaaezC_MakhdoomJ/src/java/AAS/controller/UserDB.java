/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.StudentUser;
import AAS.model.User;
import AAS.utility.Sha256;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author cece
 */
public class UserDB {

    private DataSource db;

    public UserDB(DataSource db) {
        this.db = db;
    }

    public void create(StudentUser user) throws SQLException {

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into USERTABLE(firstname, lastname, password, email)"
                    + "values(?,?,?,?)"
            );

            PreparedStatement ps2 = conn.prepareStatement(
                    "insert into GROUPTABLE (groupname, user_id) values ('studentgroup', (?))"
            );

            PreparedStatement ps3 = conn.prepareStatement(
                    "insert into STUDENTTABLE (user_id, student_id, major_code) values (?,?,?)"
            );

            ps.setString(1, user.getFirstname());

            ps.setString(2, user.getLastname());

            Sha256 password = new Sha256(user.getPassword());

            ps.setString(2, password.getCipher());

            ps.setString(3, user.getEmail());

            ps2.setInt(1, user.getUserId());

            ps3.setInt(1, user.getUserId());

            ps3.setInt(2, user.getStudentId());

            ps3.setInt(3, user.getMajorCode());

            int result = ps.executeUpdate();

            int result2 = ps2.executeUpdate();

            int result3 = ps3.executeUpdate();

            if (result != 1 && result2 != 1 && result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public List<StudentUser> read() throws SQLException {

        List<StudentUser> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select USERTABLE.FIRSTNAME, USERTABLE.LASTNAME, USERTABLE.EMAIL, GROUPTABLE.GROUPNAME from USERTABLE join GROUPTABLE on USERTABLE.ID = GROUPTABLE.ID"
            );

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                StudentUser u = new StudentUser();
                u.setFirstname(result.getString("FIRSTNAME"));
                u.setFirstname(result.getString("LASTNAME"));
                u.setEmail(result.getString("EMAIL"));
                u.setGroup(result.getString("GROUPNAME"));
                list.add(u);
            }

        } finally {
            conn.close();
        }

        return list;
    }

    public void update(User user) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {

            PreparedStatement ps = conn.prepareStatement(
                    "update USERTABLE set FIRSTNAME = (?), LASTNAME = (?), EMAIL= (?), PASSWORD = (?)"
                    + "where ID = (?)"
            );

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());

            Sha256 password = new Sha256(user.getPassword());

            ps.setString(4, password.getCipher());

            ps.setInt(5, user.getUserId());

            int result = ps.executeUpdate();

            int result2 = 0;

            if (user instanceof StudentUser) {
                StudentUser student = (StudentUser) user;

                PreparedStatement ps2 = conn.prepareStatement(
                        "update STUDENTTABLE set STUDENT_ID = (?), MAJOR_CODE = (?)"
                        + "where USER_ID = (?)"
                );

                ps2.setInt(1, student.getStudentId());
                ps2.setInt(2, student.getMajorCode());
                ps2.setInt(3, user.getUserId());

                result2 = ps2.executeUpdate();

                if (result2 != 1) {
                    throw new SQLException();
                }
            }

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public void delete(User user) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "delete from USERTABLE where ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            int result = ps.executeUpdate();

        } finally {
            conn.close();
        }
    }
}
