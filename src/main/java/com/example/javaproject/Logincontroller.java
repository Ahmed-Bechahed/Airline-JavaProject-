package com.example.javaproject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class Logincontroller {

    @FXML
    private TextField login_id;

    @FXML
    private TextField login_password;

    @FXML
    private Button signin_button;

    @FXML
    private Button signup_button;


    public void signin_action(ActionEvent event) throws SQLException {
        AdminDAO dao=new AdminDAO();
        boolean check= dao.authentification(login_id.getText(),login_password.getText());
        if (check) {
            dao.name_user(Integer.parseInt(login_id.getText()));
            LoadScene.load(signup_button, "home.fxml","Home",event);
            ObservableList<Vol> vollist = new HomeDAO().getall();
            for (Vol vol : vollist) {
                System.out.println(vol.arrive+vol.jarr+vol.jdep);
            }
        }



    }

    public void signup_action(ActionEvent event) throws IOException {
      LoadScene.load(signup_button, "signup.fxml","sign up",event);
    }

}

