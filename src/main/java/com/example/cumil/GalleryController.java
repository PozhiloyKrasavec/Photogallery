package com.example.cumil;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.awt.image.BufferedImage;

public class GalleryController implements Initializable {
    private Stage stage;
    private Scene scene;
    private List<Filter> filters = Arrays.asList(
            new Filter("Invert", color -> color.invert()),
            new Filter("Black and White", color -> color.grayscale()),
            new Filter("Blue", color -> Color.color(color.getRed(),color.getGreen(),1.0)),
            new Filter("Green",color -> Color.color(color.getRed(),1.0,color.getBlue())),
            new Filter("Red",color -> Color.color(1.0,color.getGreen(),color.getBlue())),
            new Filter("Return",color -> Color.color(color.getRed(), color.getGreen(), color.getBlue()))
    );
    private List<Image> imageList = new ArrayList<>();
    List<File> temp;
    String imagePath;
    File imageDirectory;
    FileChooser fileChooser = new FileChooser();
    @FXML
    ImageView imageView;
    @FXML
    BorderPane borderPane;
    @FXML
    MenuBar menuBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setTitle("Выберите изображение");
        fileChooser.setInitialDirectory(new File("C:\\Users\\user\\Downloads"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
        filters.forEach(filter -> {
            MenuItem item = new MenuItem(filter.getName());
            item.setOnAction(event -> {
                imageView.setImage(filter.apply(new Image(imagePath)));
            });
            menuBar.getMenus().get(1).getItems().add(item);
        });
    }
    private static class Filter implements Function<Image, Image> {
        private String name;
        private Function<Color,Color> colorMap;

        public Filter(String name,Function<Color,Color> colorMap) {
            this.name = name;
            this.colorMap = colorMap;
        }

        @Override
        public Image apply(Image source) {
            int w = (int)source.getWidth();
            int h = (int) source.getHeight();

            WritableImage image = new WritableImage(w,h);
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Color c1 = source.getPixelReader().getColor(x,y);
                    Color c2 = colorMap.apply(c1);
                    image.getPixelWriter().setColor(x,y,c2);
                }
            }
            return image;
        }

        public String getName() {
            return name;
        }
    }
    public void imagePathInit(String imagePath){
        this.imagePath = imagePath;
        imageDirectory = new File(new File(imagePath).getParent());
        imageView.setImage(new Image(imagePath));
        temp = new ArrayList<>(Arrays.asList(Objects.requireNonNull(imageDirectory.listFiles((dir, name) -> {
            if (name.endsWith("jpg")) return name.endsWith("jpg");
            else if (name.endsWith("png")) return name.endsWith("png");
            else if (name.endsWith("gif")) return name.endsWith("gif");
            return false;
        }))));
        temp.forEach(file -> {
            imageList.add(new Image(file.getAbsolutePath()));
        });
        menuBar.getMenus().get(0).getItems().forEach(item ->{
            item.setOnAction(this::changeImageBack);
        });
        menuBar.getMenus().get(2).getItems().forEach(item -> {
            if (item.getText().equals("Выбрать...")){
                    item.setOnAction(this::changeImagePathChooser);
            }
            else if (item.getText().equals("Вперед")){
                    item.setOnAction(this::changeImageNext);
            }
        });
        menuBar.getMenus().get(3).getItems().forEach(item -> {
            if (item.getText().equals("Сохранить")){
                item.setOnAction(event -> {
                    try {
                        saveToFile(imageView.getImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (item.getText().equals("Сохранить как")){
                item.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("savewindow.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stageNew = new Stage();
                    Scene sceneNew = new Scene(root);
                    stageNew.setScene(sceneNew);
                    SaveWindowController saveWindowController = loader.getController();
                    saveWindowController.init(new File(imagePath).getParent(),imageView.getImage());
                    stageNew.show();
                });
            }
            if(item.getText().equals("Печать")){
                item.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("print.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stageNew = new Stage();
                    Scene sceneNew = new Scene(root);
                    stageNew.setScene(sceneNew);
                    PrintController printController = loader.getController();
                    printController.init(imageView.getImage());
                    stageNew.show();
                });
            }
        });
    }
    public  void saveToFile(Image image) {
        File outputFile = new File(imagePath);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public void changeImagePathChooser(ActionEvent event){
        try {
            imagePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
            imageView.setImage(new Image(imagePath));
        } catch (Exception e) {
            Alert alert = createAlert(Alert.AlertType.ERROR);
            alert.setContentText("Следующее изображение не может быть выбрано");
            if (alert.showAndWait().get() == ButtonType.OK) alert.close();
        }
    }
    public void changeImageNext(ActionEvent event) {
        try {
            imagePath = temp.get(temp.indexOf(new File(imagePath))+1).getAbsolutePath();
            imageView.setImage(new Image(imagePath));
        } catch (Exception e) {
            Alert alert = createAlert(Alert.AlertType.ERROR);
            alert.setContentText("Следующее изображение отсутствует");
            if (alert.showAndWait().get() == ButtonType.OK) alert.close();
        }
    }
    public void changeImageBack(ActionEvent event) {
        try {
            imagePath = temp.get(temp.indexOf(new File(imagePath))-1).getAbsolutePath();
            imageView.setImage(new Image(imagePath));
        } catch (Exception e) {
            Alert alert = createAlert(Alert.AlertType.ERROR);
            alert.setContentText("Предыдущее изображение отсутствует");
            if (alert.showAndWait().get() == ButtonType.OK) alert.close();
        }
    }
}
