package com.project.stickhero;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Collection;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class GameOver {

    @FXML
    private static Button exitGameButton;

    @FXML
    private static Button homebutton;

    @FXML
    private static Button playagainbutton;

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
            Rectangle cantRevivePopUp= new Rectangle();
            Text cantRevive= new Text("cant revive");
            cantRevivePopUp.setFill(Color.WHITE);
            cantRevive.setFill(Color.BLACK);
            cantRevivePopUp.setWidth(480);
            cantRevivePopUp.setHeight(264);
            cantRevivePopUp.setLayoutX(692);
            cantRevivePopUp.setLayoutY(372);
            cantRevive.setFont(new Font("Arial",36));
            cantRevive.setLayoutX(850);
            cantRevive.setLayoutY(550);
            Player.getMainRoot().getChildren().add(cantRevivePopUp);
            Player.getMainRoot().getChildren().add(cantRevive);
            cantRevivePopUp.toFront();
            cantRevive.toFront();
            FadeTransition fadeInRectangle= new FadeTransition(Duration.seconds(0.5),cantRevivePopUp);
            FadeTransition fadeInText= new FadeTransition(Duration.seconds(0.5),cantRevive);
            FadeTransition fadeOutRectangle= new FadeTransition(Duration.seconds(0.5),cantRevivePopUp);
            FadeTransition fadeOutText= new FadeTransition(Duration.seconds(0.5),cantRevive);
            PauseTransition pause0= new PauseTransition(Duration.seconds(3));
            fadeInText.setFromValue(0.0);
            fadeInText.setToValue(1.0);
            fadeInRectangle.setFromValue(0.0);
            fadeInRectangle.setToValue(1.0);

            fadeOutText.setToValue(0.0);
            fadeOutText.setFromValue(1.0);
            fadeOutRectangle.setToValue(0.0);
            fadeOutRectangle.setFromValue(1.0);

            fadeInRectangle.play();
            fadeInText.play();

            fadeInText.setOnFinished(event1->{
                pause0.play();
            });

            pause0.setOnFinished(event2->{
                fadeOutRectangle.play();
                fadeOutText.play();
                Player.getMainRoot().getChildren().remove(cantRevive);
                Player.getMainRoot().getChildren().remove(cantRevivePopUp);
            });


        }


    }

    public static Button getExitGameButton() {
        return exitGameButton;
    }

    public static void setExitGameButton(Button exitGameButton) {
        GameOver.exitGameButton = exitGameButton;
    }

    public static Button getHomebutton() {
        return homebutton;
    }

    public static void setHomebutton(Button homebutton) {
        GameOver.homebutton = homebutton;
    }

    public static Button getPlayagainbutton() {
        return playagainbutton;
    }

    public static void setPlayagainbutton(Button playagainbutton) {
        GameOver.playagainbutton = playagainbutton;
    }
}
