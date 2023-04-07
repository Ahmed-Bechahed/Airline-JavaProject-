package com.example.javaproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;


public class HomeController  implements Initializable{

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


    public void initialize (URL Location, ResourceBundle resources) {
       dashboard_welcome.setText("Welcome  "+AdminDAO.user_login);
       ID_vol.setCellValueFactory(new PropertyValueFactory<>("ID_vol"));
        depart.setCellValueFactory(new PropertyValueFactory<>("depart"));
        arrive.setCellValueFactory(new PropertyValueFactory<>("arrive"));
       // id_avion.setCellValueFactory(new PropertyValueFactory<>("ID_avion"));
       // id_pilote.setCellValueFactory(new PropertyValueFactory<>("ID_pilote"));
        jdep.setCellValueFactory(new PropertyValueFactory<>("jdep"));
        jarr.setCellValueFactory(new PropertyValueFactory<>("jarr"));


        try {
            dashboard_table.setItems( new VolDAO().getall());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String apiKey = "YOUR_API_KEY";
        String origin = "Los Angeles, CA";
        String destination = "San Francisco, CA";
        String url = "https://www.google.com/maps/embed/v1/directions?key=" + apiKey + "&origin=" + origin + "&destination=" + destination;
        //String MAPS_URL = "https://www.google.com/maps/dir/Tunisia/Rome,+Metropolitan+City+of+Rome+Capital,+Italy/@38.1294285,9.3464021,6z/data=!4m14!4m13!1m5!1m1!1s0x125595448316a4e1:0x3a84333aaa019bef!2m2!1d9.537499!2d33.886917!1m5!1m1!1s0x132f6196f9928ebb:0xb90f770693656e38!2m2!1d12.4963655!2d41.9027835!3e4";
       String MAPS_URL="https://www.google.com/maps/@46.3337537,-6.811227,3.47z";
        WebEngine webEngine = map.getEngine();
        webEngine.load(MAPS_URL);

    }



}
