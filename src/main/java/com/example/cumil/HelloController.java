package com.example.cumil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private Stage stage;
    private Scene scene;
    private FileChooser fileChooser = new FileChooser();
    String imageFilePath;
    @FXML
    public void openBtnClick(ActionEvent event) throws IOException {
        try {
            imageFilePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
        } catch (Exception e) {
            Alert alert = createAlert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            if (alert.showAndWait().get() == ButtonType.OK) alert.close();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gallery.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        GalleryController galleryController = loader.getController();
        galleryController.imagePathInit(imageFilePath);
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setTitle("Выберите изображение");
        fileChooser.setInitialDirectory(new File("C:\\Users\\user\\Downloads"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
    }
    public Alert createAlert(Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        if (alertType.equals(Alert.AlertType.ERROR)){
            alert.setTitle("Ошибка");
            alert.setHeaderText("Исправьте ошибку");
        }
        else if (alertType.equals(Alert.AlertType.WARNING)){
            alert.setTitle("Внимание");
            alert.setHeaderText("Обратите внимание");
        }
        else if (alertType.equals(Alert.AlertType.CONFIRMATION)){
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Подтвердите действие");
        }
        return alert;
    }
}