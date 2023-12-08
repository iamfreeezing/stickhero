module com.project.stickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.project.stickhero to javafx.fxml;
    exports com.project.stickhero;
}