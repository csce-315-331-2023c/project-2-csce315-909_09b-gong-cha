module com.example.gongchapos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gongchapos to javafx.fxml;
    exports com.example.gongchapos;
}