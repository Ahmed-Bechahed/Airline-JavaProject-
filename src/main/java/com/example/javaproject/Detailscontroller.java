package com.example.javaproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Detailscontroller implements Initializable {
    @FXML
    private ImageView addpassenger_icon;
    @FXML
    private TableView<Passenger> passengersTable;

    @FXML
    private TableColumn<Passenger, Integer> id_passengercl;

    @FXML
    private TableColumn<Passenger, String> nouncl;

    @FXML
    private TableColumn<Passenger, String> prnouncl;

    @FXML
    private TableColumn<Passenger, Integer> numpassscl;
    @FXML
    private TableColumn<Passenger, Void> editcl;

    @FXML
    private TableColumn<Passenger, Void> deletecl;

    private ObservableList<Passenger> passengerList = FXCollections.observableArrayList();


    private PassengerDAO passengerDAO;
    @FXML
    private TableColumn<Passenger, Void> actionColumn;

    public void initialize(URL location, ResourceBundle resources) {
        passengerDAO = new PassengerDAO();
        id_passengercl.setCellValueFactory(new PropertyValueFactory<>("ID_passenger"));
        nouncl.setCellValueFactory(new  PropertyValueFactory<>("nom"));
        prnouncl.setCellValueFactory(new  PropertyValueFactory<>("prenom"));
        numpassscl.setCellValueFactory(new  PropertyValueFactory<>("num_passeport"));
        actionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Passenger, Void>, ObservableValue<Void>>() {
            @Override
            public ObservableValue<Void> call(TableColumn.CellDataFeatures<Passenger, Void> features) {
                return new SimpleObjectProperty<>(null);
            }
        });

        // Définir une usine de cellules personnalisée pour les boutons d'action
        actionColumn.setCellFactory(column -> {
            return new TableCell<Passenger, Void>() {
                private final Button editButton = new Button("Modifier");
                private final Button deleteButton = new Button("Supprimer");

                {
                    editButton.setOnAction(event -> {
                        Passenger passenger = getTableView().getItems().get(getIndex());
                        // Code pour modifier le passager sélectionné
                        Stage editStage = new Stage();
                        editStage.initModality(Modality.APPLICATION_MODAL);

                        // Créer les champs de saisie pour les informations du passager
                        TextField nomTextField = new TextField(passenger.getNom());
                        TextField prenomTextField = new TextField(passenger.getPrenom());
                        TextField numPasseportTextField = new TextField( String.valueOf(passenger.getNum_passeport()));

                        // Créer le formulaire pour modifier les informations du passager
                        GridPane editForm = new GridPane();
                        editForm.addRow(0, new Label("Nom :"), nomTextField);
                        editForm.addRow(1, new Label("Prénom :"), prenomTextField);
                        editForm.addRow(2, new Label("Numéro de passeport :"), numPasseportTextField);

                        // Créer les boutons pour enregistrer ou annuler les modifications
                        Button saveButton = new Button("Enregistrer");
                        Button cancelButton = new Button("Annuler");

                        // Ajouter les gestionnaires d'événements pour les boutons
                        saveButton.setOnAction(saveEvent -> {
                            // Enregistrer les modifications dans la base de données
                            passenger.setNom(nomTextField.getText());
                            passenger.setPrenom(prenomTextField.getText());
                            passenger.setNum_passeport((Integer.parseInt(numPasseportTextField.getText())));
                            try {
                                passengerDAO.update(passenger);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            // Fermer la fenêtre de modification
                            editStage.close();
                        });

                        cancelButton.setOnAction(cancelEvent -> {
                            // Annuler les modifications et fermer la fenêtre de modification
                            editStage.close();
                        });

                        // Ajouter les éléments au formulaire de modification
                        editForm.add(saveButton, 0, 3);
                        editForm.add(cancelButton, 1, 3);

                        // Afficher la fenêtre de modification
                        Scene editScene = new Scene(editForm);
                        editStage.setScene(editScene);
                        editStage.showAndWait();

                        // Mettre à jour la TableView avec les nouvelles informations du passager
                        passengersTable.refresh();
                    });
                    deleteButton.setOnAction(event -> {
                        Passenger passenger = getTableView().getItems().get(getIndex());
                        passengerDAO.delete(passenger);
                        passengersTable.getItems().remove(passenger);
                    });



                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        HBox hbox = new HBox(editButton, deleteButton);
                        hbox.setSpacing(5);
                        setGraphic(hbox);
                    } else {
                        setGraphic(null);
                    }
                }
            };
        });
        try {
            passengersTable.setItems(new PassengerDAO().all());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void addp(MouseEvent event) {

        // Charger le fichier FXML de la nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addpassengerwindow.fxml"));
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

