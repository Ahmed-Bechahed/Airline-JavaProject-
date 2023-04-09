package com.example.javaproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Addpassengercontroller implements Initializable {
    @FXML
    private TextField idpass;
    @FXML
    private TextField nom;
    @FXML
    private TextField numpass,numvol;
    @FXML
    private TextField prenom;
    @FXML
    private Button validebt;
    @FXML
    private Button annulerbt;
    DashboardController d = new DashboardController();
    int a = d.idVol;
    public void initialize(URL location, ResourceBundle resources) {
        numvol.setText(Integer.toString(a));

    }

    @FXML
    public void undooo(MouseEvent event){
        Stage stage = (Stage) annulerbt.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void ajout_passenger(MouseEvent event) throws SQLException {

        String idpassenger=idpass.getText();
        String  numdepass=numpass.getText();
        String name=nom.getText();
        String prname=prenom.getText();
        String nvol=numvol.getText();
        if(idpassenger.isEmpty() || numdepass.isEmpty() || name.isEmpty() || prname.isEmpty( )|| nvol.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
       if( ! AvaiblePassenger(Integer.parseInt(idpassenger),a)){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Passenger !!!!!!!!!!!");
           alert.setHeaderText(null);
           alert.setContentText("Passenger already  exist try to change The Id Please.");
           alert.showAndWait();
           return;
       }
        if( ! Avaiblevol(Integer.parseInt(nvol))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vol !!!!!!!!!!!");
            alert.setHeaderText(null);
            alert.setContentText("Passenger doesn't exist try to change The Id Please.");
            alert.showAndWait();
            return;
        }

        Connection connection=Myconnection.connect();
        //INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`) VALUES
        String query ="INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`,`ID_vol`) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(idpassenger));
            statement.setString(2, name);
            statement.setString(3, prname);
            statement.setInt(4, Integer.parseInt(numdepass));
            statement.setInt(5, Integer.parseInt(nvol));
            statement.executeUpdate();
            Detailscontroller dt=new Detailscontroller();
            Stage stage = (Stage) validebt.getScene().getWindow();
            stage.close();
            //dt.here();

           // LoadScene.load(this.validebt, "details.fxml","DETAILS",event);
        }
        }
    public static boolean AvaiblePassenger(int idpass,int a) throws SQLException {
        boolean available = true;
        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM passenger WHERE ID_passenger = "+idpass+" AND ID_vol ="+ a;
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);

        if (rs.next()) {
            available = false; // Flight ID is already taken
        }
        return available;
    }
    public static boolean Avaiblevol(int idv) throws SQLException {
        boolean available = false;
        Connection connection=Myconnection.connect();
        String sql = "SELECT * FROM vol WHERE ID_vol = "+idv;
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        rs = stmt.executeQuery(sql);

        if (rs.next()) {
            available = true; // Flight ID is already taken
        }
        return available;
    }



}
