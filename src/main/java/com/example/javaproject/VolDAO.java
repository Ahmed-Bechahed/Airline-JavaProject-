package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VolDAO {

    public ObservableList<Vol> getall() throws SQLException {

        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM vol WHERE STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') >= CURRENT_DATE() AND STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') < DATE_ADD(DATE(jdep), INTERVAL (6 - DAYOFWEEK(jdep)) DAY) + INTERVAL 1 DAY";
        PreparedStatement st= connection.prepareStatement(sql);
        System.out.println("success d'executer la requette select all ");
        ResultSet rs = st.executeQuery() ;
        ObservableList <Vol> listPr = FXCollections.observableArrayList();
        while(rs.next()) {
            System.out.println(rs.getInt(1));
            listPr.add(new Vol(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
        return listPr;

    }

}
