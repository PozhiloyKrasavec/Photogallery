package com.example.cumil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PrintController implements Initializable {
    @FXML
    ChoiceBox<String> printerChoiceBox;
    @FXML
    ChoiceBox<String> sideChoiceBox;
    @FXML
    ChoiceBox<String> copyChoiceBox;
    @FXML
    ChoiceBox<String> orientationChoiceBox;
    @FXML
    ChoiceBox<String> formatChoiceBox;
    @FXML
    ChoiceBox<String> bordersChoiceBox;
    @FXML
    ChoiceBox<String> pagesChoiceBox;
    @FXML
    Spinner<Integer> copiesNumSpinner;
    @FXML
    ImageView imageView;

    public void init(Image image){
        imageView.setImage(image);
    }
    public void onPrintBtnClick(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText("Принтер");
        alert.setHeaderText("Настройте принтер");
        if (alert.showAndWait().get() == ButtonType.OK){
            alert.close();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        printerChoiceBox.getItems().addAll(
                "FAX",
                "MS PRINT TO PDF",
                "MS XPS DOCUMENT DRIVER"
        );
        sideChoiceBox.getItems().addAll(
                "Односторонняя печать",
                "Вручную с обеих сторон"
        );
        copyChoiceBox.getItems().addAll(
                "Разобрать по копиям",
                "Не разбирать по копиям"
        );
        orientationChoiceBox.getItems().addAll(
                "Книжная ориентация",
                "Альбомная ориентация"
        );
        formatChoiceBox.getItems().addAll(
                "A3",
                "A4"
        );
        bordersChoiceBox.getItems().addAll(
                "Обычные",
                "Узкие"
        );
        pagesChoiceBox.getItems().addAll(
                "1 на листе",
                "2 на листе"
        );
        copiesNumSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1));
    }
}
