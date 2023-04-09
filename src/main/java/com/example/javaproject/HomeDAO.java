package com.example.javaproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class HomeDAO {

    static String week0=" STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') >= CURRENT_DATE() AND STR_TO_DATE(jdep, '%Y-%m-%d %H:%i:%s') < DATE_ADD(DATE(jdep), INTERVAL (6 - DAYOFWEEK(jdep)) DAY) + INTERVAL 1 DAY";
   static String week=" DATE(jdep) >= CURDATE() AND DATE(jdep) < DATE_ADD(DATE(jdep), INTERVAL (6 - DAYOFWEEK(jdep)) DAY) + INTERVAL 1 DAY;";
    static String month=" jdep >= CURRENT_DATE() AND jdep <= LAST_DAY(CURRENT_DATE())";

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





    public static int get_number(String sql)  {
        int nbr=0;
        Connection connection=Myconnection.connect();
        PreparedStatement st= null;
        try {
            st = connection.prepareStatement(sql);
            System.out.println("success d'executer le get vols number");
            ResultSet rs = st.executeQuery() ;
            while(rs.next()){
                nbr=rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nbr;
    }


    public static int get_vols_number(String period)  {
        String sql = "SELECT count(*) FROM vol WHERE"+period;
        PreparedStatement st= null;
       return(get_number(sql));
    }

    public static int get_passengers_number(String period)  {
        String sql = "SELECT count(*) FROM vol v , reservation r  WHERE v.ID_vol=r.ID_admin and "+period;
        return(get_number(sql));
    }

    public static double get_revenue(String period)  {
        String sql = "SELECT sum(price) FROM reservation r , vol v where r.ID_vol=v.ID_vol and " + period;
        return(get_number(sql));
    }

    public static int get_destinations_number(String period){
        String sql= "select count(distinct(arrive)) from vol where" +period;
        return(get_number(sql));
    }


    public static ArrayList<String> get_top_places(){
        ArrayList<String> places = new ArrayList<>();
        String sql="SELECT arrive, COUNT(*) AS count FROM vol GROUP BY arrive ORDER BY count DESC LIMIT 3;";
        Connection connection=Myconnection.connect();
        PreparedStatement st= null;
        try {
            st = connection.prepareStatement(sql);
            System.out.println("success d'executer le get vols number");
            ResultSet rs = st.executeQuery() ;
            while(rs.next()){
                places.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return places;
    }


}
