package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerDAO {
    public ObservableList<Passenger> all(int a) throws SQLException {

        Connection connection=Myconnection.connect();
        // "SELECT * FROM passenger"where id_vol=id .................
        //"select * from passsenger
        //where ID_passenger in (select ID_passenger from reservation
        //where ID_vol="+a+")";
        String sql = "SELECT * FROM passenger where id_vol="+a;
        PreparedStatement st= connection.prepareStatement(sql);
        System.out.println("success d'executer la requette select all ");
        ResultSet rs = st.executeQuery() ;
        ObservableList <Passenger> listPr = FXCollections.observableArrayList();
        while(rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            listPr.add(new Passenger(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        return listPr;
    }

    public void update(Passenger passenger) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Connection connection=Myconnection.connect();
            String sql = "UPDATE passenger SET nom=?, prenom=?, num_passeport=? WHERE ID_passenger=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, passenger.getNom());
            stmt.setString(2, passenger.getPrenom());
            stmt.setInt(3, passenger.getNum_passeport());
            stmt.setInt(4, passenger.getID_passenger());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void delete(Passenger passenger) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Connection connection=Myconnection.connect();
            String sql = "DELETE FROM passenger WHERE ID_passenger = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, passenger.getID_passenger());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
