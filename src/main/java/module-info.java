module com.example.cumil {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;


    opens com.example.cumil to javafx.fxml;
    exports com.example.cumil;
}