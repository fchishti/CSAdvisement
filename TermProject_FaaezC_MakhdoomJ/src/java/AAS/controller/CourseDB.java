/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.Course;
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
public class CourseDB {

    private DataSource db;

    public CourseDB(DataSource db) {
        this.db = db;
    }

    public void create(Course course) throws SQLException {

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into COURSETABLE(tite, courseprefix, code)"
                    + "values(?,?,?)"
            );

            ps.setString(1, course.getTitle());

            ps.setString(2, course.getPrefix());

            ps.setInt(3, course.getCode());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public List<Course> read() throws SQLException {

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
                    "select ID, TITLE, COURSEPREFIX, CODE from COURSETABLE"
            );

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

    public void update(Course course) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {

            PreparedStatement ps = conn.prepareStatement(
                    "update COURSETABLE set TITLE = (?), COURSEPREFIX = (?), CODE = (?)"
                    + "where ID = (?)"
            );

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getPrefix());
            ps.setInt(3, course.getCode());
            ps.setInt(4, course.getId());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public void delete(Course course) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "delete from COURSETABLE where ID = (?)"
            );

            ps.setInt(1, course.getId());

            int result = ps.executeUpdate();

        } finally {
            conn.close();
        }
    }
}
