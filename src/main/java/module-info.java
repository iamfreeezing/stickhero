module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.testng;
    requires junit;


    opens com.project.stickhero to javafx.fxml;
    exports com.project.stickhero;
}