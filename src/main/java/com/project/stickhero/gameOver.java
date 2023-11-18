package com.project.stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class gameOver {

    @FXML
    private Button PlayAgainButton;

    @FXML
    private Button homebutton;

    public void homeButtonM () {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            HelloApplication settingScene = new HelloApplication();

            settingScene.setScene1(settingScene.currStage, new Scene(root));

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

}
