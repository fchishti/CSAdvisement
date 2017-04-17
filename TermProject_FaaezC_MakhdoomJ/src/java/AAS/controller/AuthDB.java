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
import javax.sql.DataSource;

/**
 *
 * @author faaez
 */
public class AuthDB {

    private DataSource db;

    public AuthDB(DataSource db) {
        this.db = db;
    }

    public void create(User user, int code) throws SQLException {

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into CODETABLE(USER_ID, CODE)"
                    + "values((SELECT ID from USERTABLE where EMAIL = (?)),?)"
            );

            ps.setString(1, user.getEmail());

            ps.setInt(2, code);

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }

    public boolean isAuthroized(User user) throws SQLException {

        boolean isAuthroized = false;

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select AUTHORIZED from STUDENTTABLE where USER_ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                isAuthroized = result.getBoolean("AUTHORIZED");
            }

        } finally {
            conn.close();
        }

        return isAuthroized;
    }

    public int readCode(User user) throws SQLException {

        int code = 0;

        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select CODE from CODETABLE where USER_ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                code = result.getInt("CODE");
            }

        } finally {
            conn.close();
        }

        return code;
    }

    public void authorize(User user) throws SQLException {
        if (db == null) {
            throw new SQLException("db is null; Can't get data source");
        }

        Connection conn = db.getConnection();

        if (conn == null) {
            throw new SQLException("conn is null; Can't get db connection");
        }

        try {

            PreparedStatement ps = conn.prepareStatement(
                    "update STUDENTTABLE set AUTHORIZED = true where USER_ID = (?)"
            );

            ps.setInt(1, user.getUserId());

            int result = ps.executeUpdate();

            if (result != 1) {
                throw new SQLException();
            }

        } finally {
            conn.close();
        }
    }
}
