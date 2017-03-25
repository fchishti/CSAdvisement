/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.Course;
import AAS.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author faaez
 */
public class StudentCoursesDB {

    private DataSource db;

    public StudentCoursesDB(DataSource db) {
        this.db = db;
    }

    public void create(User user, Course course) throws SQLException {

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into STUDENTCOURSESTABLE(user_id, course_id)"
                    + "values(?,?)"
            );

            ps.setInt(1, user.getUserId());

            ps.setInt(2, course.getId());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public List<Course> read(User user) throws SQLException {

        List<Course> list = new ArrayList<>();

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                "select a.TITLE, a.COURSEPREFIX, a.CODE from COURSETABLE a" +
                    "inner join STUDENTCOURSESTABLE b on a.ID = b.ID" +
                    "where b.USER_ID = (?)"
            );
            
            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {

                Course c = new Course();
                c.setId(result.getInt("ID"));
                c.setTitle(result.getString("TITLE"));
                c.setPrefix(result.getString("COURSEPREFIX"));
                c.setCode(result.getInt("CODE"));
                list.add(c);
            }

        } finally {
            conn.close();
        }

        return list;
    }

    public void delete(User user, Course course) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "delete from STUDENTCOURSESTABLE where USER_ID = (?) AND COURSD_ID = (?)"
            );

            ps.setInt(1, user.getUserId());
            ps.setInt(2, course.getId());

            int result = ps.executeUpdate();

        } finally {
            conn.close();
        }
    }
}
