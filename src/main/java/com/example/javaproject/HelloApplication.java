package com.example.javaproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("details.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        //stage.getIcons().add(new Image("C:\\JavaProject\\src\\main\\resources\\images\\travel.png"));
        stage.setTitle("Travel!");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) throws SQLException {
    launch();
    }

}