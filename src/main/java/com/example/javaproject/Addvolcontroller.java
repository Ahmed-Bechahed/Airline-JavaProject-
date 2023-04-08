package com.example.javaproject;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Addvolcontroller {
    @FXML
    private Button validebtn;
    @FXML
    private Button annulerbtn;
    @FXML
    private TextField idvol_txtfld;
    @FXML
    private TextField dep_txtfld;
    @FXML
    private TextField arr_textfld;
    @FXML
    private TextField idav;
    @FXML
    private TextField idpil;
    @FXML
    private TextField jarr;
    @FXML
    private TextField jdep;
    private VolsController volsController;

    public void setVolsController(VolsController volsController) {
        this.volsController = volsController;
    }
    public void undooo(MouseEvent event){
        Stage stage = (Stage) annulerbtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void ajout_vol(MouseEvent event) {
        // Récupération des données saisies dans les champs
        String idvol=idvol_txtfld.getText();
        String depart= dep_txtfld.getText();
        String arrive= arr_textfld.getText();
        String idavion= idav.getText();
        String idpilote= idpil.getText();
        String datearr= jarr.getText();
        String datedep=jdep.getText();

        // Vérification que tous les champs sont remplis
        if (idvol.isEmpty() || arrive.isEmpty() || depart.isEmpty() || idavion.isEmpty() ||datedep.isEmpty()
                || datearr.isEmpty()|| idpilote.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Insertion des données dans la base de données
        Connection connection=Myconnection.connect();
        String query = "INSERT INTO `vol` (`ID_vol`, `depart`, `arrive`, `ID_avion`, `ID_pilote`, `jdep`, `jarr`) VALUES(?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(idvol));

            statement.setString(2, depart);
            statement.setString(3, arrive);
            statement.setInt(4, Integer.parseInt(idavion));
            statement.setInt(5, Integer.parseInt(idpilote));

            //String dateString = "2022-04-07 14:30:00";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //  Date date = dateFormat.parse(dateString);

            statement.setString(6,  datedep);
            //
            // statement.setString(6, "2022-04-07 16:30:00");
            //statement.setString(7, datearr);
            statement.setString(7,  datearr);

            statement.executeUpdate();
            // this(this.volsController);
            // volsControlle.initialize(null,null);
            LoadScene.load(this.validebtn, "vols.fxml","Home",event);
            // this.volsController.initialize(null,"vols.fxml");

           /* FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            try {
                Parent root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            VolsController volsController = loader.getController();

            volsController.initialize(null, null);
            */

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Ajout des données dans la TableView



        // Fermeture de la fenêtre
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
