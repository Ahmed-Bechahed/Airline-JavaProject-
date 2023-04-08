package com.example.javaproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Addpassengercontroller {
    @FXML
    private TextField idpass;
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

    @FXML
    public void undooo(MouseEvent event){
        Stage stage = (Stage) annulerbt.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void ajout_passenger(MouseEvent event) throws SQLException {
        String idpassenger=idpass.getText();
        String  numdepass=numpass.getText();
        String name=nom.getText();
        String prname=prenom.getText();
        if(idpassenger.isEmpty() || numdepass.isEmpty() || name.isEmpty() || prname.isEmpty( )){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        Connection connection=Myconnection.connect();
        //INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`) VALUES
        String query ="INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`) VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(idpassenger));
            statement.setString(2, name);
            statement.setString(3, prname);
            statement.setInt(4, Integer.parseInt(numdepass));
            statement.executeUpdate();
            Detailscontroller dt=new Detailscontroller();
            Stage stage = (Stage) validebt.getScene().getWindow();
            stage.close();
            //dt.here();
            LoadScene.load(this.validebt, "details.fxml","DETAILS",event);
        }
        }


}
