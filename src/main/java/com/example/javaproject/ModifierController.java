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
    private void handleEnregistrer(MouseEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");//jdep.getValue().atStartOfDay().format(formatter).isEmpty()
        if (jarr.getValue()==null ||jdep.getValue()==null || idpil.getText().isEmpty() || idav.getText().isEmpty() ||arr.getValue().isEmpty() || dep.getValue().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
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

    @FXML
    private void handleAnnuler(MouseEvent event) {
        // Fermer la fenêtre de modification
        Stage stage = (Stage) idav.getScene().getWindow();
        stage.close();
    }



}
