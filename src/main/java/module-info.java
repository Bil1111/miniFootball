module com.example.kr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kr to javafx.fxml;
    exports com.example.kr;
}