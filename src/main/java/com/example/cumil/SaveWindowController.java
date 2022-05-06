package com.example.cumil;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveWindowController{
    private Stage stage;
    @FXML
    TextField fileNameField;
    String initFilePath;
    Image imageToSave;
    DirectoryChooser directoryChooser = new DirectoryChooser();

    public void init(String initFilePath, Image imageToSave){
        this.initFilePath = initFilePath;
        this.imageToSave = imageToSave;
    }

    public void saveBtnClick(ActionEvent event){
        String path = directoryChooser.showDialog(stage).getAbsolutePath() + "\\" + fileNameField.getText()+".png";
        File outputFile = new File(path);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageToSave, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}
