package com.example.javaproject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


public class HomeController  implements Initializable{
    public static int idVol;
    @FXML
    private ImageView avatar;

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView dashboard_icon;

    @FXML
    private TableView<Vol> dashboard_table;

    @FXML
    private Label dashboard_welcome;
    @FXML
    private Label dashboard_user_name;
    @FXML
    private ImageView deconnect_icon;
    @FXML
    private TableColumn<Vol,Integer> ID_vol;
    @FXML
    private TableColumn<Vol, String> depart;
    @FXML
    private TableColumn<Vol, String> arrive;
    @FXML
    private TableColumn<Vol,Integer> id_avion;
    @FXML
    private TableColumn<Vol,Integer> id_pilote;
    @FXML
    private TableColumn<Vol,String> jdep;
    @FXML
    private TableColumn<Vol,String> jarr;
    @FXML
    private TableColumn<Vol,String> detailsColumn;

    @FXML
    private AnchorPane navbar;

    @FXML
    private ImageView pilot_icon;

    @FXML
    private ImageView plane_icon;

    @FXML
    private ImageView team_icon;



    @FXML
    private ImageView vol_icon;


    @FXML
    private WebView map;
    @FXML
    private Label vols_number;

    @FXML
    private Label passengers_number;
    @FXML
    private Label price;
    @FXML
    private Label destinations_number;


    @FXML
    private ComboBox<String> combo_box;

    @FXML
    private ImageView place1;

    @FXML
    private ImageView place2;

    @FXML
    private ImageView place3;

    @FXML
    private Label nameplace1;

    @FXML
    private Label nameplace2;

    @FXML
    private Label nameplace3;

    public void initialize (URL Location, ResourceBundle resources) {
        dashboard_welcome.setText("Welcome  " + AdminDAO.user_name_login);
        dashboard_user_name.setText(AdminDAO.user_fullname_login);
/*
        ArrayList<String> places=HomeDAO.get_top_places();
        Iterator<String> iterator = places.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            place1.setImage(new Image("C:\\JavaProject\\src\\main\\resources\\images\\" + element));
            place2.setImage(new Image("C:\\JavaProject\\src\\main\\resources\\images\\" + element));
            place3.setImage(new Image("C:\\JavaProject\\src\\main\\resources\\images\\" + places.get(3)));
        }


 */
        combo_box.getItems().addAll("This Week", "This Month");
        combo_box.setValue("This Week");
        vols_number.setText(String.valueOf(HomeDAO.get_vols_number(HomeDAO.week)));
        passengers_number.setText(String.valueOf(HomeDAO.get_passengers_number(HomeDAO.week)));
        price.setText("$"+String.format("%.0f",HomeDAO.get_revenue(HomeDAO.week)));
        destinations_number.setText(String.valueOf(HomeDAO.get_destinations_number(HomeDAO.week)));
        combo_box.setOnAction(e -> {

            if (combo_box.getValue() == "This Week") {
                vols_number.setText(String.valueOf(HomeDAO.get_vols_number(HomeDAO.week)));
                passengers_number.setText(String.valueOf(HomeDAO.get_passengers_number(HomeDAO.week)));
                price.setText("$"+String.format("%.0f",HomeDAO.get_revenue(HomeDAO.week)));
                destinations_number.setText(String.valueOf(HomeDAO.get_destinations_number(HomeDAO.week)));
            } else {
                vols_number.setText(String.valueOf(HomeDAO.get_vols_number(HomeDAO.month)));
                passengers_number.setText(String.valueOf(HomeDAO.get_passengers_number(HomeDAO.month)));
                price.setText("$"+String.format("%.0f",HomeDAO.get_revenue(HomeDAO.month)));
                destinations_number.setText(String.valueOf(HomeDAO.get_destinations_number(HomeDAO.month)));
            }
        });

        // vols_number.setText(String.valueOf(new VolDAO().get_vols_number(Integer.parseInt(AdminDAO.user_login))));
        ID_vol.setCellValueFactory(new PropertyValueFactory<>("ID_vol"));
        depart.setCellValueFactory(new PropertyValueFactory<>("depart"));
        arrive.setCellValueFactory(new PropertyValueFactory<>("arrive"));
       // id_avion.setCellValueFactory(new PropertyValueFactory<>("ID_avion"));
       // id_pilote.setCellValueFactory(new PropertyValueFactory<>("ID_pilote"));
        jdep.setCellValueFactory(new PropertyValueFactory<>("jdep"));
        jarr.setCellValueFactory(new PropertyValueFactory<>("jarr"));

        try {
            dashboard_table.setItems( new HomeDAO().getall());
        } catch (SQLException e) {
            e.printStackTrace();
        }



// Set the cell factory to create a cell with the value "details"
        detailsColumn.setCellFactory(new Callback<TableColumn<Vol, String>, TableCell<Vol, String>>() {
            @Override
            public TableCell<Vol, String> call(TableColumn<Vol, String> column) {
                return new TableCell<Vol, String>() {
                    private final Hyperlink detailsLink = new Hyperlink();

                    {
                        detailsLink.setOnMouseClicked(event -> {
                            // Handle the click event here
                            idVol = getTableView().getItems().get(getIndex()).getID_vol();
                            System.out.println("linked page to vol "+ idVol);
                            LoadScene.load(dashboard_table, "signup.fxml","sign up",event);
                            System.out.println("Details link clicked for row " + getIndex());
                        });
                    }
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            detailsLink.setText("details");
                            setGraphic(detailsLink);
                        }
                    }
                };
            }
        });




        String apiKey = "YOUR_API_KEY";
        String origin = "Los Angeles, CA";
        String destination = "San Francisco, CA";
        String url = "https://www.google.com/maps/embed/v1/directions?key=" + apiKey + "&origin=" + origin + "&destination=" + destination;
        String MAPS_URL = "https://www.google.com/maps/dir/Tunisia/Rome,+Metropolitan+City+of+Rome+Capital,+Italy/@38.1294285,9.3464021,6z/data=!4m14!4m13!1m5!1m1!1s0x125595448316a4e1:0x3a84333aaa019bef!2m2!1d9.537499!2d33.886917!1m5!1m1!1s0x132f6196f9928ebb:0xb90f770693656e38!2m2!1d12.4963655!2d41.9027835!3e4";
       //String MAPS_URL="https://cmoreira.net/visited-countries-map/?vcstitle=&inacolor=%23e0e0e0&actcolor=%236699cc&";
        WebEngine webEngine = map.getEngine();
        webEngine.load(MAPS_URL);

    }



}
