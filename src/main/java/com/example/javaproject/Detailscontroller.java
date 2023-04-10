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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @FXML
    private AnchorPane container;
    private PassengerDAO passengerDAO;
    @FXML
    private TableColumn<Passenger, Void> actionColumn;
    @FXML
    private TextField volnum_textfield,villedepart_textfield,villearrive_textfield,datedepart_textfield;
    @FXML
    private TextField datearrive_textfield,idpilote_textfield,idavion_textfield;
    DashboardController d = new DashboardController();
    int a = d.idVol;
    static Passenger Pass;
    public  void initialize(URL location, ResourceBundle resources) {

        Connection connection = Myconnection.connect();
        String sql = "SELECT * FROM vol WHERE ID_vol = " + a;
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (rs.next()) {
                volnum_textfield.setText(Integer.toString(rs.getInt(1)));
                villedepart_textfield.setText(rs.getString(2));
                villearrive_textfield.setText(rs.getString(3));
                idavion_textfield.setText(Integer.toString(rs.getInt(4)));
                idpilote_textfield.setText(Integer.toString(rs.getInt(5)));
                datedepart_textfield.setText(rs.getString(6));
                datearrive_textfield.setText(rs.getString(7));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        passengerDAO = new PassengerDAO();
        id_passengercl.setCellValueFactory(new PropertyValueFactory<>("ID_passenger"));
        nouncl.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prnouncl.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        numpassscl.setCellValueFactory(new PropertyValueFactory<>("num_passeport"));
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
                        try {
                            modifierpass(passenger);
                            System.out.println(" a zeuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
            passengersTable.setItems(new PassengerDAO().all(a));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
        @FXML
        public void modifierpass(Passenger passenger) throws IOException {
            if (passenger != null) {
                this.Pass=passenger;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("passengermodification.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            }
        }
    @FXML
    private void dashboard_load(MouseEvent event){
        LoadScene.load_pane(container,"vols.fxml");
    }
    @FXML
    private void addp(MouseEvent event) throws SQLException {

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
        System.out.println("La nouvelle fenêtre est fermée");
        passengersTable.refresh();
        passengersTable.setItems(new PassengerDAO().all(a));
    }
}

