package com.jarvinen.jyri.ss;

import java.sql.*;
/**
 * Performs queries to a MySQL database.
 */
public class DataBaseReader {
    String dbURL;

    String user;
    String pass;

    boolean connected;

    Connection connection;
    Statement statement;

    public DataBaseReader(){

        dbURL = "jdbc:mysql://82.181.221.24:3306/shootingstars";
        user = "user";
        pass = "ShootingStars55";

        connected = false;
    }

    public ResultSet getHighScores() {
            connection = openConnection();
            if(connection == null){
                return null;
            }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql =    "select namecol, scorecol" +
                            " from tblname" +
                            " inner join tblscore" +
                            " on idname=name_id;";

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

        //return null;
    }

    public void updateQuery(String sql) {
        if(!sql.startsWith("update")){
            System.out.println("DataBaseReader.updateQuery: Parameter does not start with \"update\"!");
        }
        try {
            connection = openConnection();

            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(String sql) {
        if(!sql.startsWith("delete")){
            System.out.println("DataBaseReader.updateQuery: Parameter does not start with \"delete\"!");
        }
        try {
            connection = openConnection();

            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertQuery(String sql) {
        if(!sql.startsWith("insert")){
            System.out.println("DataBaseReader.updateQuery: Parameter does not start with \"insert\"!");
        }
        try {
            connection = openConnection();

            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectQuery(String sql) {
        if(!sql.startsWith("select")){
            System.out.println("DataBaseReader.updateQuery: Parameter does not start with \"select\"!");
        }
        try {
            connection = openConnection();

            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection openConnection(){
        connected = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(dbURL, user, pass);
            connected = true;
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
