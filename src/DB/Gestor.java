/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Gestor {

    protected Connection connection = null;
    protected PreparedStatement prepare;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    protected String database;
    protected String user;
    protected String password;
    protected String host;
    protected String puerto;
    protected String url;

    public Gestor(String database,String puerto, String user, String password, String host, String url) {
        this.database = database;
        this.user = user;
        this.password = password;
        this.host = host;
        this.puerto=puerto;
        this.url=url;
    }

    public ResultSet execute(String query) {
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public int update(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    abstract protected void getConnection() throws Exception;

}