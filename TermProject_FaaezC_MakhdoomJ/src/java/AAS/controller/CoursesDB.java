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
public class CoursesDB {
    private DataSource db;

    public CoursesDB(DataSource db) {
        this.db = db;
    }
}
