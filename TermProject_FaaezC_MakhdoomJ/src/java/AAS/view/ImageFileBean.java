/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.view;

import AAS.controller.ImageFileDB;
import AAS.model.ImageFile;
import AAS.utility.CurrentUser;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.sql.DataSource;

/**
 *
 * @author fchishti-sw
 */
@Named(value = "imageFileBean")
@SessionScoped
public class ImageFileBean implements Serializable {

    private Part part;
    private List<ImageFile> list;

    @Resource(name = "jdbc/ds_wsp")
    private DataSource ds;

    private ImageFileDB imageFileDB;
    private CurrentUser currentUser;

    @PostConstruct
    public void init() {
        imageFileDB = new ImageFileDB(ds);
        currentUser = new CurrentUser(ds);
    }

    public void updateList() throws SQLException {
        try {
            list = imageFileDB.loadFileList();
        } catch (SQLException ex) {
            Logger.getLogger(ImageFileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String upload() throws SQLException {
        try {
            imageFileDB.upload(part, currentUser.getUser());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ImageFileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String delete() throws SQLException {
        try {
            imageFileDB.delete(currentUser.getUser());
        } catch (SQLException ex) {
            Logger.getLogger(ImageFileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Select a file to upload", null));
        }
        Part file = (Part) value;
        long size = file.getSize();
        if (size <= 0) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "the file is empty", null));
        }
        if (size > 1024 * 1024 * 10) { // 10 MB limit
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            size + "bytes: file too big (limit 10MB)", null));
        }
    }

    public List<ImageFile> getList() throws SQLException {
        return list;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
