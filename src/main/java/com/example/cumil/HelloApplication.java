package com.example.cumil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Фотогалерея");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("photo.png"))));
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            exitButtonOn(stage);
        });
    }

    public static void main(String[] args) {
        launch();
    }
    public void exitButtonOn(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выход");
        alert.setHeaderText("Вы выходите");
        alert.setContentText("Вы точно хотите выйти?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }
}