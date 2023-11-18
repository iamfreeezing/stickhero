package com.project.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public Stage currStage;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        this.currStage = stage;
        stage.show();
    }

    public void setScene1(Stage stage, Scene scene) {
        stage.setScene(scene);
        this.currStage = stage;
    }


    public static void main(String[] args) {
        launch();
    }
}