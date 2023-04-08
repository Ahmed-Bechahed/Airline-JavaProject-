package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    AdminDAO dao= new AdminDAO();
    @FXML
    private Label signin_button;
    @FXML
    private Button signup_button;

    @FXML
    private Label signup_id;

    @FXML
    private TextField signup_nom;

    @FXML
    private TextField signup_password;

    @FXML
    private TextField signup_prenom;

    @FXML
    private TextField signup_confirm;

    @FXML
    private CheckBox signup_check;



   public void initialize (URL Location, ResourceBundle resources) {
      System.out.println("done");
      int newid;
       try {
             newid = dao.getlastid();
           newid++;
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       signup_id.setText(String.valueOf(newid));
    }




    public void insert_action(ActionEvent event) throws SQLException {
      System.out.println("done");
        System.out.println ("name:"+signup_nom.getText().trim().isEmpty());
        if  ((signup_nom.getText().trim().isEmpty() || signup_prenom.getText().trim().isEmpty() || signup_password.getText().trim().isEmpty()|| signup_confirm.getText().trim().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Requirement");
            alert.setContentText("all attributes are required ");
            alert.show();
            System.out.println("error");

        } else if (!signup_confirm.getText().equals(signup_password.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("password Incorrect ");
            alert.show();
            System.out.println("error");

        } else if (!signup_check.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Terms not confirmed ");
            alert.show();
            System.out.println("Terms not confirmed");
        } else {
            dao.insert_admin(signup_nom.getText(),signup_prenom.getText(),signup_password.getText());
            LoadScene.load(signup_button,"login.fxml","Home",event);
        }

    }
    public void load_signin_page(MouseEvent event){
       LoadScene.load(signin_button,"login.fxml","login",event);
    }
}
