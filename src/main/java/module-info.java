module com.example.mailapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.mail;


    opens com.example.mailapp to javafx.fxml;
    exports com.example.mailapp;
}