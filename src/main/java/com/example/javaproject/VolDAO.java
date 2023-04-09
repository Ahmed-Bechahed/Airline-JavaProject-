package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.*;

public class VolDAO {
    ObservableList<Vol> data;
    TableView<Vol> vols;
    Connection conn=null;
    Statement st=null;
    public VolDAO(){
        try {
            conn=Myconnection.connect();
            if (conn!=null){
                st= conn.createStatement();
            }
        }catch (SQLException e){
            System.out.println("error sql ."+e.getMessage());
        }
    }

    public ObservableList<Vol> all() throws SQLException {

        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM vol";
        PreparedStatement st= connection.prepareStatement(sql);
        System.out.println("success d'executer la requette select all ");
        ResultSet rs = st.executeQuery() ;
        ObservableList <Vol> listPr = FXCollections.observableArrayList();
        while(rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            listPr.add(new Vol(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7)));
        }
        return listPr;
    }
    public void supprimer(int id){
        String req="DELETE FROM `vol` WHERE ID_vol ="+id;

        try {
            if (st != null) {
                st.executeUpdate(req);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }


    public int update( String dep, String arr, int id_av, int id_pil, String jdep, String jarr, int id_ad,int id_vol ) {
        String req = "UPDATE vol  SET depart = ?, arrive = ?, ID_avion = ?, ID_pilote = ?, jdep = ?, jarr = ?, ID_admin = ? WHERE ID_vol = ?";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(req);
                ps.setString(1, dep);
                ps.setString(2, arr);
                ps.setInt(3, id_av);
                ps.setInt(4, id_pil);
                ps.setString(5, jdep);
                ps.setString(6, jarr);
                ps.setInt(7, id_ad);
                ps.setInt(8, id_vol);
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }



    public void delete(Vol vol) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Connection connection=Myconnection.connect();
            String sql = "DELETE FROM vol WHERE ID_vol = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, vol.ID_vol);
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

    public int update(Vol vol) {
        Connection connection=Myconnection.connect();
        String req = "UPDATE vol  SET depart = ?, arrive = ?, ID_avion = ?, ID_pilote = ?, jdep = ?, jarr = ? WHERE ID_vol = ?";
        try {
            if (conn != null) {
                PreparedStatement ps = connection.prepareStatement(req);
                ps.setString(1, vol.getDepart());
                ps.setString(2, vol.getArrive());
                ps.setInt(3, vol.getID_avion());
                ps.setInt(4, vol.getID_pilote());
                ps.setString(5, vol.getJdep());
                ps.setString(6, vol.getJarr());
                ps.setInt(7, vol.getID_vol());

                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;

    }
    public ObservableList<Vol> getall() throws SQLException {

        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM vol WHERE STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') >= CURRENT_DATE() AND STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') < DATE_ADD(DATE(jdep), INTERVAL (6 - DAYOFWEEK(jdep)) DAY) + INTERVAL 1 DAY";
        PreparedStatement st= connection.prepareStatement(sql);
        System.out.println("success d'executer la requette select all ");
        ResultSet rs = st.executeQuery() ;
        ObservableList <Vol> listPr = FXCollections.observableArrayList();
        while(rs.next()) {
            System.out.println(rs.getInt(1));
            listPr.add(new Vol(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7)));
        }
        return listPr;

    }


}
