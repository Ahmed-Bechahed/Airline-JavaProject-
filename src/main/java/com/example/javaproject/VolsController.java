package com.example.javaproject;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
//import javafx.scene.control.DateTimePicker;
public class VolsController implements Initializable  {


    @FXML
    private TextField villedepvol_textfield;
    // private TextField searchField;
    @FXML
    private TextField villearvol_textfield;
    @FXML
    private TextField datedepvol_textfield;

    @FXML
    private TextField datearvol_textfield;

    @FXML
    private Button recherche_button;
    @FXML
    private ImageView addvol_icon;
    @FXML
    private ImageView backbtn;
    @FXML
    private TableView<Vol> volsTable;

    @FXML
    private TableColumn<Vol, Integer> idVolColumn;

    @FXML
    private TableColumn<Vol, String> departColumn;

    @FXML
    private TableColumn<Vol, String> arriveColumn;

    @FXML
    private TableColumn<Vol, Integer> idAvionColumn;

    @FXML
    private TableColumn<Vol, Integer> idPiloteColumn;

    @FXML
    private TableColumn<Vol, String> jDepColumn;

    @FXML
    private TableColumn<Vol, String> jArrColumn;

    @FXML
    private TableColumn<Vol, Integer> idAdminColumn;
    private ObservableList<Vol> volList = FXCollections.observableArrayList();


    private VolDAO volDAO;

    public void initialize(URL location, ResourceBundle resources) {
        volDAO = new VolDAO();
        idVolColumn.setCellValueFactory(new PropertyValueFactory<>("ID_vol"));
        departColumn.setCellValueFactory(new PropertyValueFactory<>("depart"));
        arriveColumn.setCellValueFactory(new PropertyValueFactory<>("arrive"));
        idAvionColumn.setCellValueFactory(new PropertyValueFactory<>("ID_avion"));
        idPiloteColumn.setCellValueFactory(new PropertyValueFactory<>("ID_pilote"));
        jDepColumn.setCellValueFactory(new PropertyValueFactory<>("jdep"));
        jArrColumn.setCellValueFactory(new PropertyValueFactory<>("jarr"));
        TableColumn<Vol, Void> editCol = new TableColumn<>("Modifier");
        TableColumn<Vol, Void> deleteCol = new TableColumn<>("Supprimer");
        /*editCol.setCellFactory(param -> new TableCell<Vol, Void>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Vol vol = getTableView().getItems().get(getIndex());
                    // Code pour ouvrir la fenêtre de modification du vol
                });
            }
            */


        try {
            //volDAO.init();
            volsTable.setItems(new VolDAO().all());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void load_home(MouseEvent event){
        LoadScene.load(backbtn,"home.fxml","login",event);
    }


    @FXML
    public void search() {
        String depart = villedepvol_textfield.getText();
        String arrivee = villearvol_textfield.getText();
        String dateDepart = datedepvol_textfield.getText();
        String dateArrivee = datearvol_textfield.getText();
        ObservableList<Vol> volsFiltres = volList
                .filtered(v -> v.getDepart().equals(depart))
                .filtered(v -> v.getArrive().equals(arrivee))
                .filtered(v -> v.getJdep().equals(dateDepart))
                .filtered(v -> v.getJarr().equals(dateArrivee));
        volsTable.setItems(volsFiltres);
    }
    @FXML
    public void addv(MouseEvent event) {
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        // Charger le fichier FXML de la nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addvolwindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Créer une nouvelle instance de Stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        // Afficher la nouvelle fenêtre et attendre que l'utilisateur la ferme
        stage.showAndWait();

        // Le code reprend ici une fois que la nouvelle fenêtre est fermée
        System.out.println("La nouvelle fenêtre est fermée");

    }





}

