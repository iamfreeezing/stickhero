package com.project.stickhero;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Homepage {

    public static void openHomepage() {
        Label permanentHeartCount=new Label(String.valueOf(Data.getpermanentHeartScore()));
        FXMLLoader loader = new FXMLLoader(Homepage.class.getResource("homepage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pane mainRoot= new Pane();
        Rectangle blackScreen= new Rectangle();
        blackScreen.setHeight(1080);
        blackScreen.setWidth(1920);
        blackScreen.setFill(Color.BLACK);
        mainRoot.getChildren().add(blackScreen);

        Text intro= new Text("This is a game of life and death.");
        intro.setFont(new Font("Arial",18));
        intro.setFill(Color.WHITE);
        intro.setLayoutX(825);
        intro.setLayoutY(480);
        intro.setOpacity(0);


        Text intro2 = new Text("Only those who struggle will ever win.");
        intro2.setFont(new Font("Arial",18));
        intro2.setFill(Color.WHITE);
        intro2.setLayoutX(820);
        intro2.setLayoutY(480);
        intro2.setOpacity(0);

        Text intro3 = new Text("Please change your resolution to 1920x1080 (100%) for the game to work properly.");
        intro3.setFont(new Font("Arial",18));
        intro3.setFill(Color.WHITE);
        intro3.setLayoutX(650);
        intro3.setLayoutY(900);
        intro3.setOpacity(0);
        Parent finalRoot=root;
        mainRoot.getChildren().add(finalRoot);


        FadeTransition fadeInIntro1= new FadeTransition(Duration.seconds(2),intro);
        FadeTransition fadeOutIntro1= new FadeTransition(Duration.seconds(1),intro);
        FadeTransition fadeInIntro2= new FadeTransition(Duration.seconds(2),intro2);
        FadeTransition fadeOutIntro2= new FadeTransition(Duration.seconds(1),intro2);
        FadeTransition fadeInIntro3= new FadeTransition(Duration.seconds(2),intro3);
        FadeTransition fadeOutIntro3= new FadeTransition(Duration.seconds(1),intro3);

        FadeTransition fadeBlackScreen = new FadeTransition(Duration.seconds(1),blackScreen);
        PauseTransition pauseBlackScreen= new PauseTransition(Duration.seconds(3));
        PauseTransition pause0= new PauseTransition(Duration.seconds(1.5));
        PauseTransition pause1= new PauseTransition(Duration.seconds(2));
        PauseTransition pause2= new PauseTransition(Duration.seconds(2));
        PauseTransition pause3= new PauseTransition(Duration.seconds(2));



        fadeBlackScreen.setFromValue(1.0);
        fadeBlackScreen.setToValue(0.0);

        fadeOutIntro1.setFromValue(1.0);
        fadeOutIntro1.setToValue(0.0);
        fadeInIntro1.setFromValue(0.0);
        fadeInIntro1.setToValue(1.0);

        fadeOutIntro2.setFromValue(1.0);
        fadeOutIntro2.setToValue(0.0);
        fadeInIntro2.setFromValue(0.0);
        fadeInIntro2.setToValue(1.0);

        fadeOutIntro3.setFromValue(1.0);
        fadeOutIntro3.setToValue(0.0);
        fadeInIntro3.setFromValue(0.0);
        fadeInIntro3.setToValue(1.0);

        pause0.play();

        pause0.setOnFinished(event0->{
            mainRoot.getChildren().add(intro);
            intro.toFront();
            fadeInIntro1.play();

        });

        fadeInIntro1.setOnFinished(event->{
            pause1.play();
        });

        pause1.setOnFinished(event2->{

            fadeOutIntro1.play();

        });

        fadeOutIntro1.setOnFinished(event3->{
            mainRoot.getChildren().add(intro2);
            mainRoot.getChildren().add(intro3);
            intro2.toFront();
            intro3.toFront();
            fadeInIntro3.play();
            fadeInIntro2.play();
        });


        fadeInIntro3.setOnFinished(event7->{
            pause3.play();
        });

        pause3.setOnFinished(event8->{
            fadeOutIntro3.play();
            fadeOutIntro2.play();
        });

        fadeOutIntro3.setOnFinished(event9->{
            fadeBlackScreen.play();
            finalRoot.toFront();
            mainRoot.getChildren().add(permanentHeartCount);
            permanentHeartCount.toFront();
        });



        permanentHeartCount.setLayoutX(1550);
        permanentHeartCount.setLayoutY(936);
        permanentHeartCount.setTextFill(Color.WHITE);
        permanentHeartCount.setFont(new Font("Arial",50));
        Scene scene = new Scene(mainRoot);
        blackScreen.toFront();

        StickHero.getStage().setTitle("Stick Hero");
        StickHero.getStage().setScene(scene);
        StickHero.getStage().setFullScreenExitHint("");
        StickHero.getStage().setFullScreen(true);
        StickHero.getStage().show();


    }


}
