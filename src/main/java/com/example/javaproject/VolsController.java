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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
//import javafx.scene.control.DateTimePicker;
public class VolsController implements Initializable  {


    @FXML
    private ComboBox<String> villedepvol_textfield,villearvol_textfield;
    @FXML
    private AnchorPane container;
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
    private TableColumn<Vol, Void> actionColumn;

    @FXML
    private TableColumn<Vol, Integer> idAdminColumn;
    private ObservableList<Vol> volList = FXCollections.observableArrayList();

    String pattern = "yyyy-MM-dd";
    private VolDAO volDAO;

    public void initialize(URL location, ResourceBundle resources) {
        //DatePicker datePicker = localDateTimePicker();
        //datedepvol_textfield.setValue(datePicker.getValue());
        villedepvol_textfield.getItems().addAll("Tokyo ", "Cairo","London","Paris");
        villedepvol_textfield.setValue("Paris");
        villearvol_textfield.getItems().addAll("Tokyo ", "Cairo","London","Paris");
        villearvol_textfield.setValue("Paris");
        volDAO = new VolDAO();
        idVolColumn.setCellValueFactory(new PropertyValueFactory<>("ID_vol"));
        departColumn.setCellValueFactory(new PropertyValueFactory<>("depart"));
        arriveColumn.setCellValueFactory(new PropertyValueFactory<>("arrive"));
        idAvionColumn.setCellValueFactory(new PropertyValueFactory<>("ID_avion"));
        idPiloteColumn.setCellValueFactory(new PropertyValueFactory<>("ID_pilote"));
        jDepColumn.setCellValueFactory(new PropertyValueFactory<>("jdep"));
        jArrColumn.setCellValueFactory(new PropertyValueFactory<>("jarr"));
        actionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vol, Void>, ObservableValue<Void>>() {
            @Override
            public ObservableValue<Void> call(TableColumn.CellDataFeatures<Vol, Void> features) {
                return new SimpleObjectProperty<>(null);
            }
        });
        actionColumn.setCellFactory(column -> {
            return new TableCell<Vol, Void>() {
                private final Button editButton = new Button("Modifier");
                private final Button deleteButton = new Button("Supprimer");
                private final Button moreButton = new Button("more.....");

                {
                    editButton.setOnAction(event -> {
                        Vol vol = getTableView().getItems().get(getIndex());
                        try {
                            modifierVol(vol);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        volsTable.refresh();
                    });
                    deleteButton.setOnAction(event -> {
                        Vol vol = getTableView().getItems().get(getIndex());
                        volDAO.delete(vol);
                        volsTable.getItems().remove(vol);
                    });
                    moreButton.setOnAction(event -> {

                        Vol vol = getTableView().getItems().get(getIndex());
                        DashboardController.idVol=vol.getID_vol();

                            LoadScene.load_pane(container,"details.fxml");
                           // modifierVol(vol);
                       // volsTable.refresh();
                    });

                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        HBox hbox = new HBox(editButton, deleteButton,moreButton);
                        hbox.setSpacing(5);
                        setGraphic(hbox);
                    } else {
                        setGraphic(null);
                    }
                }
            };
        });


        try {

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
    public void modifierVol(Vol vol) throws IOException {
        if (vol != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier.fxml"));
            Parent root = loader.load();
            ModifierController modifierController = loader.getController();
            modifierController.setVol(vol);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }


    @FXML
    public void search() throws SQLException {
        String depart = villedepvol_textfield.getValue();
        String arrivee = villearvol_textfield.getValue();
        ObservableList<Vol> flights = searchFlights(depart, arrivee);
        volsTable.getItems().setAll(flights);
    }

    public ObservableList<Vol> searchFlights(String depart, String arrivee) throws SQLException {
        Connection connection=Myconnection.connect();
        String query = "SELECT * FROM `vol` WHERE depart = ? AND arrive = ?";
        ObservableList<Vol> flights = FXCollections.observableArrayList();
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(query);
        stmt.setString(1, depart);
        stmt.setString(2, arrivee);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Vol flight = new Vol( rs.getInt("ID_vol"),rs.getString("depart"), rs.getString("arrive"),
                    rs.getInt("ID_avion"), rs.getInt("ID_pilote"), rs.getString("jdep"),
                    rs.getString("jarr")
            );
            flights.add(flight);
        }
        return flights;
    }




    @FXML
    public void addv(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Addvolwindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        // Charger le fichier FXML de la nouvelle fenêtre
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("Addvolwindow.fxml"));
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
        */
        System.out.println("La nouvelle fenêtre est fermée");
        volsTable.refresh();
        volsTable.setItems(new VolDAO().all());

    }





}

