package com.project.stickhero;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class GamePlaying {
    private Player player;

    @FXML
    private AnchorPane cherry;

    private Rectangle stick;

    private double stickLength = 0;



    public void startGame () {      //creates new player and starts game

    }

    public void createStick() {

        stick = new Rectangle(0, 2, Color.BLACK);
        stick.setWidth(stickLength);

        Homepage.gamePlayingScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    stickLength+=1;
                    stick.setWidth(stickLength);
                    break;
                default:
                    break;
            }
        });


    }








}
