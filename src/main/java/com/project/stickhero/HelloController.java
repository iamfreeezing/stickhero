package com.project.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button playbutton;

    @FXML
    private VBox randiom;

    @FXML
    void onPlayButtonClick(ActionEvent event) {

        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameOver.fxml"));
            Parent root = loader.load();

            HelloApplication settingScene = new HelloApplication();

            settingScene.setScene1(settingScene.currStage, new Scene(root));

            settingScene.currStage.show();

            // Show the new stage
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

}
