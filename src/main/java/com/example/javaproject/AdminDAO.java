package com.example.javaproject;

import com.example.javaproject.Myconnection;
import javafx.scene.control.Alert;

import java.sql.*;

public class AdminDAO {
    //Connection connection;
    static Connection connection;
    Statement statement =null;
    static String user_name_login;
    static String user_fullname_login;
    static int user_id;
    int id;
    AdminDAO(){
        connection= Myconnection.connect();
        if(connection!=null){
            try {
                statement=connection.createStatement();
                System.out.println("Statement created");
            }catch(SQLException e){
                System.out.println("erreur statment");
            }
        }
    }
    public boolean authentification(String id, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE ID_admin=? and password=?";
        PreparedStatement st=connection.prepareStatement(sql);
        st.setString(1, id);
        st.setString(2,password);
        ResultSet res = st.executeQuery();
        int n=0;
        while(res.next()) {
            n++;
            String nom = res.getString(2);
            String prenom = res.getString(3);
            System.out.println(nom + prenom);
        }
        if(n==1) {
            System.out.println("user verified");
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ID ou password incorrect ");
            alert.show();
        }
        return (n==1);
    }
    public int getlastid() throws SQLException {
        if (connection!=null) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM admin ORDER BY ID_admin DESC LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("ID_admin");
            }
        }
        return id;
    }
    public void insert_admin(String b, String c,String d) throws SQLException {
        if (connection!=null){
            String req= "insert into admin (ID_admin,nom,prenom,password) values(?,?,?,?)";
            try{
                PreparedStatement ps=connection.prepareStatement(req);
                int id=getlastid();
                id++;
                System.out.println("voici "+id);
                ps.setString(1,String.valueOf(id));
                ps.setString(2,b);
                ps.setString(3,c);
                ps.setString(4,d);
                int res=ps.executeUpdate();
            } catch (SQLException e){
                System.out.println("erreur d'ajout");
            }
        }
    }
    public void name_user(int id) throws SQLException {
        user_id=id;
        String req="select nom,prenom from admin where ID_admin=?";
        PreparedStatement st=connection.prepareStatement(req);
        st.setString(1, String.valueOf(id));
        ResultSet res=st.executeQuery();
        while (res.next()){
            user_name_login=res.getString(1);
            user_fullname_login=res.getString(2)+user_name_login;
        }
    }
}