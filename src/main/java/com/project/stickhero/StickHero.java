package com.project.stickhero;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class StickHero extends Application {

    private Stage stage;
    private static Scene scene;
    private static Parent root;

    @FXML
    private Button Ads;

    @FXML
    private Button buyCherry;

    @FXML
    private Button playbutton;

    @FXML
    private Rectangle pillar;

    @FXML
    void adsButton(ActionEvent event) {

    }

    @FXML
    void buyCherryButton(ActionEvent event) {

    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("gamePlaying.fxml"));      //Parent is a type of root node
        scene = new Scene(root);   //need to pass a root node while creating any scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        Rectangle stick = new Rectangle(5, 0, Color.BLACK);
        ((Pane) root).getChildren().add(stick);

        stick.setLayoutX(100);
        stick.setLayoutY(100);

        final double[] stickHeight = {0.0};
        AtomicReference<Double> yCoordinateStick = new AtomicReference<>((double) 200);

        scene.setOnKeyPressed(event1 -> {
            if (event1.getCode() == KeyCode.UP) {
                stickHeight[0] +=1;
                yCoordinateStick.updateAndGet(v -> new Double((double) (v - 1)));
                stick.setLayoutY(yCoordinateStick.get());
                stick.setHeight(stickHeight[0]);
            }
        });



    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();

    }

    public void showNewStage (Stage stage) {


    }

    public static void main(String[] args) {
        launch();


    }
}