package com.project.stickhero;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class GameOver {

    @FXML
    private Button exitGameButton;

    @FXML
    private Button homebutton;

    @FXML
    private Button playagainbutton;

    @FXML
    void onClickHome(ActionEvent event) {
        Homepage.openHomepage();

    }

    @FXML
    void onClickPlayAgain(ActionEvent event) throws IOException {
        StickHero.runGame(false);

    }
    @FXML
    void onClickingExit(ActionEvent event) throws IOException {
        StickHero.getStage().close();
    }

    @FXML
    void onClickingRevive(ActionEvent event) throws IOException {
        if (Data.getpermanentHeartScore() >= 10) {
            Data.setpermanentHeartScore(Data.getpermanentHeartScore() - 10);
            StickHero.runGame(true);
        }
        else {
            
        }


    }

}
