module com.example.triovisioniseprtp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.triovisioniseprtp to javafx.fxml;
    exports com.example.triovisioniseprtp;
    exports com.example.triovisioniseprtp.classes;
    opens com.example.triovisioniseprtp.classes to javafx.fxml;
}