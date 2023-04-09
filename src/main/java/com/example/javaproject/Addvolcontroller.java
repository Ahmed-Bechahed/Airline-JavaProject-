package com.example.javaproject;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class Addvolcontroller implements Initializable {
    @FXML
    private Button validebtn;
    @FXML
    private Button annulerbtn;
    @FXML
    private TextField idvol_txtfld;
    @FXML
    private ComboBox<String> dep_txtfld ,arr_textfld;
    @FXML
    private TextField idav;
    @FXML
    private TextField idpil;
    @FXML
    private DatePicker jarr,jdep;

    private VolsController volsController;

    public void setVolsController(VolsController volsController) {
        this.volsController = volsController;
    }
    public void undooo(MouseEvent event){
        Stage stage = (Stage) annulerbtn.getScene().getWindow();
        stage.close();
    }
    public void initialize(URL location, ResourceBundle resources) {
        dep_txtfld.getItems().addAll("Tokyo ", "Cairo","London","Paris");
        dep_txtfld.setValue("Paris");
        arr_textfld.getItems().addAll("Tokyo ", "Cairo","London","Paris");
        arr_textfld.setValue("Paris");
    }
    @FXML
    public void ajout_vol(MouseEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String idvol=idvol_txtfld.getText();
        String depart= dep_txtfld.getValue();
        String arrive= arr_textfld.getValue();
        String idavion= idav.getText();
        String idpilote= idpil.getText();
        // Vérification que tous les champs sont remplis
        if (idvol.isEmpty() || arrive.isEmpty() || depart.isEmpty() || idavion.isEmpty() ||jdep.getValue()==null
                || jarr.getValue()==null|| idpilote.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        // Récupération des données saisies dans les champs

        String datearr= jarr.getValue().atStartOfDay().format(formatter);
        String datedep=jdep.getValue().atStartOfDay().format(formatter);
        // Insertion des données dans la base de données
        Connection connection=Myconnection.connect();
        String query = "INSERT INTO `vol` (`ID_vol`, `depart`, `arrive`, `ID_avion`, `ID_pilote`, `jdep`, `jarr`) VALUES(?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(idvol));
            statement.setString(2, depart);
            statement.setString(3, arrive);
            statement.setInt(4, Integer.parseInt(idavion));
            statement.setInt(5, Integer.parseInt(idpilote));
            statement.setString(6,  datedep);
            statement.setString(7,  datearr);
            statement.executeUpdate();

            // this(this.volsController);
            // volsControlle.initialize(null,null);
            //volsController.initialize(null, null);
           //LoadScene.load(this.validebtn, "vols.fxml","Home",event);


        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
