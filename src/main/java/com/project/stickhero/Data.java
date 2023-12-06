package com.project.stickhero;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Data {

    private static int highScore=0;



    private static int heartScore=0;

    public static int heartCounter=0;

    public int getHighScore() {
        return highScore;
    }

    public static ImageView generateHeart(){



            StickHero.setHeart(new ImageView(new Image("file:./heart.png")));;
            StickHero.getHeart().setLayoutX((StickHero.getSecondPillar().getLayoutX()+StickHero.getFirstPillar().getWidth())/2 - 20);
            StickHero.getHeart().setLayoutY(StickHero.getFirstPillar().getLayoutY()+5);
            StickHero.getHeart().setFitHeight(70);
            StickHero.getHeart().setFitWidth(70);
            StickHero.getHeart().toFront();
            TranslateTransition heartMove = new TranslateTransition(Duration.seconds(0.5), StickHero.getHeart());
            heartMove.setByY(-10);
            heartMove.setCycleCount(Transition.INDEFINITE);
            heartMove.setAutoReverse(true);
            heartMove.play();
            return StickHero.getHeart();


    }

    public static void setHighScore(int highScore) {
        highScore = highScore;
    }


    public static void setPermanentCherryCount(int permanentCherryCount) {
        permanentCherryCount = permanentCherryCount;
    }

    public static void purchaseCherries() {

    }

    public static int getHeartScore() {
        return heartScore;
    }

    public static void setHeartScore(int heartScore) {
        heartScore = heartScore;
    }

//    public static int getHeartCounter() {
//        return heartCounter;
//    }
//
//    public static void setHeartCounter(int heartCounter) {
//       heartCounter = heartCounter;
//    }


}
