package com.example.javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Myconnection {
    public static Connection connect() {
        //chargement du drive
        String nomDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(nomDriver);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("La classe " + nomDriver + " n'a pas été trouvée");
            cnfe.printStackTrace();
        }

        // connexion ala base de donnee
        String URL = "jdbc:mysql://localhost/vol";
        String login = "root";
        String password = "";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, login, password);
            System.out.println("connected");
            //interaction avec la base
        } catch (
                SQLException sqle) {
            //cf. Comment gérer les erreurs ?
        }
        return connection ;
    }

    public static void disconnect(Connection connection){
        try {
            connection.close();
            System.out.println("disconnected");

        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
