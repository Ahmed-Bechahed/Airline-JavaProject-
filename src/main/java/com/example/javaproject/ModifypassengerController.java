package com.example.javaproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifypassengerController implements Initializable {
    private PassengerDAO passengerDAO;
    @FXML
    private TextField nom;
    @FXML
    private TextField numpass;
    @FXML
    private TextField prenom;
    @FXML
    private Button validebt;
    @FXML
    private Button annulerbt;
    Addpassengercontroller add=new Addpassengercontroller();
    private  Passenger passenger;
    public void setpassenger(Passenger passenger) {
        this.passenger=passenger;
    }
    Detailscontroller tarkii7 =new Detailscontroller();

    @FXML
    public void undooo(MouseEvent event){
        Stage stage = (Stage) annulerbt.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL location, ResourceBundle resources) {

        if (tarkii7.Pass != null) {
            nom.setText(tarkii7.Pass.getNom());
            prenom.setText(tarkii7.Pass.getPrenom());
            numpass.setText(Integer.toString(tarkii7.Pass.getNum_passeport()));
            setpassenger(tarkii7.Pass);
        }
    }
    @FXML
    public void ajout_passenger(MouseEvent event) throws SQLException {

        String  numdepass=numpass.getText();
        String name=nom.getText();
        String prname=prenom.getText();
        if( numdepass.isEmpty() || name.isEmpty() || prname.isEmpty( )){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        passengerDAO = new PassengerDAO();
        passenger.setNom(name);
        passenger.setPrenom(prname);
        passenger.setNum_passeport((Integer.parseInt(numdepass)));
        try {
            passengerDAO.update(passenger);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) numpass.getScene().getWindow();
        stage.close();


    }


}
