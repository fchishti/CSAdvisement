/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import AAS.model.ImageFile;
import AAS.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.sql.DataSource;

/**
 *
 * @author fchishti-sw
 */
public class ImageFileDB {

    private DataSource db;

    public ImageFileDB(DataSource ds) {
        this.db = ds;
    }

    public List<ImageFile> loadFileList() throws SQLException {

        List<ImageFile> files = new ArrayList<>();
        Connection conn = db.getConnection();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(
                    "SELECT ID, USER_ID, FILE_NAME, FILE_TYPE, FILE_SIZE FROM IMAGETABLE"
            );
            while (result.next()) {
                ImageFile file = new ImageFile();
                file.setId(result.getInt("ID"));
                file.setName(result.getString("FILE_NAME"));
                file.setType(result.getString("FILE_TYPE"));
                file.setSize(result.getLong("FILE_SIZE"));
                file.setUserId(result.getInt("USER_ID"));
                files.add(file);
            }
        } finally {
            conn.close();
        }
        return files;
    }

    public void upload(Part part, User user) throws IOException, SQLException {
        Connection conn = db.getConnection();
        boolean exists = false;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(
                    "SELECT USER_ID FROM IMAGETABLE"
            );
            while (result.next()) {
                if (result.getInt("USER_ID") == user.getUserId()) {
                    update(part, user);
                    exists = true;
                }
            }

            if (!exists) {
                uploadFile(part, user);
            }
        } finally {
            conn.close();
        }
    }

    private void uploadFile(Part part, User user) throws IOException, SQLException {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        Connection conn = db.getConnection();

        InputStream inputStream;
        inputStream = null;
        try {
            inputStream = part.getInputStream();
            PreparedStatement insertQuery = conn.prepareStatement(
                    "INSERT INTO IMAGETABLE (FILE_NAME, FILE_TYPE, FILE_SIZE, FILE_CONTENTS, USER_ID) "
                    + "VALUES (?,?,?,?,?)");
            insertQuery.setString(1, part.getSubmittedFileName());
            insertQuery.setString(2, part.getContentType());
            insertQuery.setLong(3, part.getSize());
            insertQuery.setBinaryStream(4, inputStream);
            insertQuery.setInt(5, user.getUserId());

            int result = insertQuery.executeUpdate();
            if (result == 1) {
                facesContext.addMessage("uploadForm:upload",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                part.getSubmittedFileName()
                                + ": uploaded successfuly !!", null));
            } else {
                // if not 1, it must be an error.
                facesContext.addMessage("uploadForm:upload",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                result + " file uploaded", null));
            }
        } catch (IOException e) {
            facesContext.addMessage("uploadForm:upload",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "File upload failed !!", null));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (conn != null) {
                conn.close();
            }
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
                    "delete from IMAGETABLE where USER_ID = (?)"
            );

            ps.setLong(1, user.getUserId());

            int result = ps.executeUpdate();

        } finally {
            conn.close();
        }
    }

    private void update(Part part, User user) throws IOException, SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        Connection conn = db.getConnection();

        InputStream inputStream;
        inputStream = null;
        try {
            inputStream = part.getInputStream();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE IMAGETABLE SET FILE_NAME = (?), FILE_TYPE = (?), FILE_SIZE = (?), FILE_CONTENTS = (?) WHERE USER_ID = (?) ");
            ps.setString(1, part.getSubmittedFileName());
            ps.setString(2, part.getContentType());
            ps.setLong(3, part.getSize());
            ps.setBinaryStream(4, inputStream);
            ps.setInt(5, user.getUserId());

            int result = ps.executeUpdate();
            if (result == 1) {
                facesContext.addMessage("uploadForm:upload",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                part.getSubmittedFileName()
                                + ": uploaded successfuly !!", null));
            } else {
                // if not 1, it must be an error.
                facesContext.addMessage("uploadForm:upload",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                result + " file uploaded", null));
            }
        } catch (IOException e) {
            facesContext.addMessage("uploadForm:upload",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "File upload failed !!", null));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
