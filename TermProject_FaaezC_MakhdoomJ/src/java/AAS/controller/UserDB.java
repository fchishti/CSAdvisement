/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.FacultyUser;
import AAS.model.StudentUser;
import AAS.model.User;
import AAS.utility.Sha256;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
                    "insert into GROUPTABLE (groupname, user_id, username) "
                    + "values ('studentgroup', (select ID from USERTABLE where EMAIL = (?)),?)"
            );

            PreparedStatement ps3 = conn.prepareStatement(
                    "insert into STUDENTTABLE (user_id, student_id, major_code) "
                    + "values ((select ID from USERTABLE where EMAIL = (?)),?,?)"
            );

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            Sha256 password = new Sha256(user.getPassword());
            ps.setString(3, password.getCipher());
            ps.setString(4, user.getEmail());

            ps2.setString(1, user.getEmail());
            ps2.setString(2, user.getEmail());

            ps3.setString(1, user.getEmail());
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

    public List<User> read() throws SQLException {

        List<User> list = new ArrayList<>();

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
                User u = new StudentUser();
                u.setFirstname(result.getString("FIRSTNAME"));
                u.setLastname(result.getString("LASTNAME"));
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
                    "update USERTABLE set FIRSTNAME = (?), LASTNAME = (?), EMAIL = (?) where ID = (?)"
            );

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getUserId());

            int result = ps.executeUpdate();

            if (user instanceof StudentUser) {
                StudentUser student = (StudentUser) user;

                PreparedStatement ps2 = conn.prepareStatement(
                        "update STUDENTTABLE set STUDENT_ID = (?), MAJOR_CODE = (?) where USER_ID = (?)"
                );

                ps2.setInt(1, student.getStudentId());
                ps2.setInt(2, student.getMajorCode());
                ps2.setInt(3, user.getUserId());

                int result2 = ps2.executeUpdate();

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

//<editor-fold defaultstate="collapsed" desc="Misc Methods">
    public List<User> readFaculty() throws SQLException {

        List<User> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select USERTABLE.ID, USERTABLE.FIRSTNAME, USERTABLE.LASTNAME, USERTABLE.EMAIL, GROUPTABLE.GROUPNAME from USERTABLE join GROUPTABLE "
                    + "on USERTABLE.ID = GROUPTABLE.ID where GROUPTABLE.GROUPNAME = 'facultygroup'"
            );

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                FacultyUser u = new FacultyUser();
                u.setUserId(result.getInt("ID"));
                u.setFirstname(result.getString("FIRSTNAME"));
                u.setLastname(result.getString("LASTNAME"));
                u.setEmail(result.getString("EMAIL"));
                u.setGroup(result.getString("GROUPNAME"));
                list.add(u);
            }

        } finally {
            conn.close();
        }
        return list;
    }

    public List<User> readStudent() throws SQLException {

        List<User> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select USERTABLE.FIRSTNAME, USERTABLE.LASTNAME, USERTABLE.EMAIL, GROUPTABLE.GROUPNAME from USERTABLE join GROUPTABLE "
                    + "on USERTABLE.ID = GROUPTABLE.ID where GROUPTABLE.GROUPNAME = 'studentgroup'"
            );

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                StudentUser u = new StudentUser();
                u.setUserId(result.getInt("ID"));
                u.setFirstname(result.getString("FIRSTNAME"));
                u.setLastname(result.getString("LASTNAME"));
                u.setEmail(result.getString("EMAIL"));
                u.setGroup(result.getString("GROUPNAME"));
                list.add(u);
            }

        } finally {
            conn.close();
        }
        return list;
    }

    /**
     * Returns current logged in user
     *
     * @param username
     * @param role
     * @exception SQLException
     * @return User
     */
    public User readUser(String username, String role) throws SQLException {

        StudentUser su = new StudentUser();
        FacultyUser fu = new FacultyUser();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps;
            if (role.equals("faculty")) {
                ps = conn.prepareStatement(
                        "select ID, FIRSTNAME, LASTNAME, EMAIL from USERTABLE where EMAIL = (?)"
                );
            } else if (role.equals("student")) {
                ps = conn.prepareStatement(
                        "select a.ID, a.FIRSTNAME, a.LASTNAME, a.EMAIL, b.STUDENT_ID, b.MAJOR_CODE "
                        + " from USERTABLE a join STUDENTTABLE b on a.ID = b.USER_ID where a.EMAIL = ?"
                );
            } else {
                ps = conn.prepareStatement(
                        "select ID, FIRSTNAME, LASTNAME, EMAIL from USERTABLE where EMAIL = (?)"
                );
            }

            ps.setString(1, username);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                if (role.equals("student")) {
                    su.setUserId(result.getInt("ID"));
                    su.setFirstname(result.getString("FIRSTNAME"));
                    su.setLastname(result.getString("LASTNAME"));
                    su.setEmail(result.getString("EMAIL"));
                    su.setMajorCode(result.getInt("MAJOR_CODE"));
                    su.setStudentId(result.getInt("STUDENT_ID"));
                } else {
                    fu = new FacultyUser();
                    fu.setUserId(result.getInt("ID"));
                    fu.setFirstname(result.getString("FIRSTNAME"));
                    fu.setLastname(result.getString("LASTNAME"));
                    fu.setEmail(result.getString("EMAIL"));
                }
            }

        } finally {
            conn.close();
        }

        if (role.equals("student")) {
            return su;
        } else {
            return fu;
        }
    }
//</editor-fold>
}
