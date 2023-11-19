package com.project.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StickHero extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StickHero.class.getResource("homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Stick Hero!");
        stage.setScene(scene);
        stage.show();
    }

    public void setCurrentScene(Scene scene) {

    }

    public void showNewStage (Stage stage) {


    }


    public static void main(String[] args) {
        launch();
    }
}