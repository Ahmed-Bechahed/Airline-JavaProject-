package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AboutDAO {
    public List<Pilote> getAllPilotes() throws SQLException {
        // charger les pilotes depuis la base de données
        List<Pilote> pilotes = new ArrayList<>();

        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM pilote ";
        PreparedStatement st= connection.prepareStatement(sql);
       // System.out.println("success d'executer la requette select all ");
        ResultSet rs = st.executeQuery() ;
       // ObservableList<Passenger> listPr = FXCollections.observableArrayList();
        while (rs.next()) {
            int idp = rs.getInt(1);
            String nom = rs.getString(2);
            String prenom = rs.getString(3);

            Pilote pilote = new Pilote(idp,nom,prenom);
            pilotes.add(pilote);
        }
        return pilotes;
    }
    public void delete(Pilote pilote) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Connection connection=Myconnection.connect();
            String sql = "DELETE FROM pilote WHERE ID_pilote = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pilote.getID_pilote());
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
    public void deleteav(Avion avion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Connection connection=Myconnection.connect();
            String sql = "DELETE FROM avion WHERE ID_avion = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, avion.getID_avion());
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

    public void update(Pilote pilote) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Connection connection=Myconnection.connect();
            String sql = "UPDATE pilote SET nom=?, prenom=? WHERE ID_pilote=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, pilote.getNom());
            stmt.setString(2, pilote.getPrenom());
            stmt.setInt(3, pilote.getID_pilote());
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
    public void updateav(Avion avion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Connection connection=Myconnection.connect();
            String sql = "UPDATE avion SET type=?, capacite=? WHERE ID_avion=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, avion.getType());
            stmt.setInt(2, avion.getCapacite());
            stmt.setInt(3, avion.getID_avion());
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


    public List<Avion> getAllavions() throws SQLException {
        List<Avion> avions = new ArrayList<>();

        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM avion ";
        PreparedStatement st= connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery() ;
        while (rs.next()) {
            int idav = rs.getInt(1);
            String type = rs.getString(2);
            int cap = rs.getInt(3);

            Avion avion = new Avion(idav,type,cap);
            avions.add(avion);
        }
        return avions;
    }


}
