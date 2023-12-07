package com.project.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.concurrent.atomic.AtomicBoolean;



public class Player implements Runnable{

    public static double slimeXValue;

    public static boolean isSPressed = false;

    public static void onSlimeTranslationDone() {
        if(StickHero.isCollectedHeart()){
            Data.heartScore=Data.heartScore+1;
            StickHero.setCollectedHeart(false);
        }
        System.out.println(Data.heartScore);
        StickHero.getSlime().getTransforms().clear();
        StickHero.getSlime().setLayoutY(1080-StickHero.getFirstPillar().getHeight()-StickHero.getSlime().getFitHeight());
        StickHero.getSecondPillar().setTranslateX(-StickHero.getSecondPillar().getLayoutX());
        StickHero.getFirstPillar().setVisible(false);
        StickHero.setFirstPillar(StickHero.getSecondPillar());
        StickHero.getStick().setHeight(0);
        StickHero.getSlime().setTranslateX(-StickHero.getSlime().getLayoutX()+StickHero.getSecondPillar().getWidth()-StickHero.getSlime().getFitWidth());
        StickHero.getGameRoot().getChildren().add(Pillar.generateSecondPillar());
        if(Data.heartCounter%2==0){
            StickHero.getGameRoot().getChildren().add(Data.generateHeart());
        }
        StickHero.collisionTimer.start();

    }
public static <AnimationTimer> void translateSlimeM(Double distance, boolean success) {
            Runnable r= new Player();
            Thread t1= new Thread(r);
            //t1.start();

            StickHero.getSlime().toFront();
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), StickHero.getSlime());
            translateTransition.setByX(distance);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.play();
            StickHero.isTranslating.set(true);


            translateTransition.setOnFinished(event -> {
                StickHero.isTranslating.set(false);
                if (success) {
                    StickHero.getStick().getTransforms().clear();
                    onSlimeTranslationDone();
                    StickHero.setIsSpacePressed(false);
                }
                else {
                    StickHero.getSlime().toFront();
                    TranslateTransition translateTransitionFall = new TranslateTransition(Duration.seconds(0.5), StickHero.getSlime());
                    translateTransitionFall.setByY(StickHero.getFirstPillar().getLayoutY());
                    translateTransitionFall.setCycleCount(1);
                    translateTransitionFall.setAutoReverse(false);
                    translateTransitionFall.play();

                    translateTransitionFall.setOnFinished(eventNew -> {
                        StickHero.getSlime().setVisible(false);

                    });
                }
            });
            }


    public static Double slimeX() {
        return (StickHero.getSlime().getLayoutX() + StickHero.getSlime().getFitWidth()) / 2;        //slime's middle part's X
    }

    public static boolean sImportant = false;

    @Override
    public void run() {
        while(true){
            slimeXValue=StickHero.getSlime().getLayoutX();
            System.out.println(slimeXValue);
        }

    }
}




