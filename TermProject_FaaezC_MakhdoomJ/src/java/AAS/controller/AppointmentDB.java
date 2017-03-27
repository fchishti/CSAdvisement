/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AAS.controller;

import javax.sql.DataSource;

/**
 *
 * @author cece
 */
public class AppointmentDB {
    private DataSource db;

    public AppointmentDB(DataSource db) {
        this.db = db;
    }
}
