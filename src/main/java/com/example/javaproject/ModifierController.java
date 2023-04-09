package com.example.javaproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifierController implements Initializable {


    @FXML
    private ComboBox<String> dep ,arr;
    @FXML
    private TextField idav,idpil;
    @FXML
    private DatePicker jdep,jarr;
    private Vol vol;

    public void setVol(Vol vol) {
        this.vol = vol;
        dep.setValue(vol.getDepart());
        arr.setValue(vol.getArrive());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
       // jdep.setValue(LocalDate.parse(vol.getJdep(),formatter));
        LocalDate date = LocalDate.of(2023, 4, 8);
        //jdep.setValue(date);
        //varr.setValue(date);
       // varr.setValue(LocalDate.parse(vol.getJarr(),formatter));
        idav.setText(Integer.toString(vol.getID_avion()));
        idpil.setText(Integer.toString(vol.getID_pilote()));
    }



    public void initialize(URL location, ResourceBundle resources) {
        dep.getItems().addAll("Tokyo ", "Cairo","London","Paris");

        arr.getItems().addAll("Tokyo ", "Cairo","London","Paris");
    }

    @FXML
    private void handleEnregistrer(MouseEvent event) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");//jdep.getValue().atStartOfDay().format(formatter).isEmpty()
        if (jarr.getValue()==null ||jdep.getValue()==null || idpil.getText().isEmpty() || idav.getText().isEmpty() ||arr.getValue().isEmpty() || dep.getValue().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        if( ! Avaiblepil(Integer.parseInt(idpil.getText()))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Pilpote !!!!!!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Pilote doesn't  exist try to change The Id Please.");
            alert.showAndWait();
            return;
        }
        if( ! Avaibleav(Integer.parseInt(idav.getText()))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Plane !!!!!!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Plane doesn't   exist try to change The Id Please.");
            alert.showAndWait();
            return;
        }
        vol.setArrive(arr.getValue());
        vol.setDepart(dep.getValue());
        vol.setID_avion(Integer.parseInt(idav.getText()));
        vol.setID_pilote(Integer.parseInt(idpil.getText()));
        vol.setJarr(jarr.getValue().atStartOfDay().format(formatter));
        vol.setJdep(jdep.getValue().atStartOfDay().format(formatter));


        VolDAO volDAO = new VolDAO();
        volDAO.update(vol);

        // Fermer la fenêtre de modification
        Stage stage = (Stage) idav.getScene().getWindow();
        stage.close();
    }

    public static boolean Avaiblepil(int idpilote) throws SQLException {
        boolean available = false;
        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM pilote WHERE ID_pilote = "+ idpilote;
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);

        if (rs.next()) {
            available = true;
        }
        return available;

    }

    public static boolean Avaibleav(int aa) throws SQLException {
        boolean available = false;
        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM avion WHERE ID_avion = "+ aa;
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);

        if (rs.next()) {
            available = true;
        }
        return available;
    }

    @FXML
    private void handleAnnuler(MouseEvent event) {
        // Fermer la fenêtre de modification
        Stage stage = (Stage) idav.getScene().getWindow();
        stage.close();
    }



}
