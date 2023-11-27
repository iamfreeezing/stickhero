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
import javafx.stage.Stage;

import java.io.IOException;

public class StickHero extends Application {

    @FXML
    private Button Ads;

    @FXML
    private Button Sound;

    @FXML
    private Button buyCherry;

    @FXML
    private Button playbutton;

    @FXML
    void adsButton(ActionEvent event) {

    }

    @FXML
    void buyCherryButton(ActionEvent event) {

    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("gamePlaying.fxml"));      //Parent is a type of root node
        Scene scene = new Scene(root);                                          //need to pass a root node while creating any scene
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    void soundButton(ActionEvent event) {

    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StickHero.class.getResource("homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Stick Hero");
        Image icon = new Image("C:\\Users\\Nish\\IdeaProjects\\StickHero\\src\\main\\resources\\com\\project\\stickhero\\icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }


    public void showNewStage (Stage stage) {


    }


    public static void main(String[] args) {
        launch();
    }
}