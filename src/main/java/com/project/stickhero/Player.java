package com.project.stickhero;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.nio.channels.Pipe;

public class Player {

    public static void onSlimeTranslationDone() {
        StickHero.getSecondPillar().setTranslateX(-StickHero.getSecondPillar().getLayoutX());
        StickHero.getFirstPillar().setVisible(false);
        StickHero.setFirstPillar(StickHero.getSecondPillar());
        StickHero.getStick().setHeight(0);
        StickHero.getSlime().setTranslateX(-StickHero.getSlime().getLayoutX()+StickHero.getSecondPillar().getWidth()-StickHero.getSlime().getFitWidth());
        StickHero.getGameRoot().getChildren().add(Pillar.generateSecondPillar());

        if(Data.heartCounter%2==0){
            StickHero.getGameRoot().getChildren().add(Data.generateHeart());
        }

    }
public static void translateSlimeM(Double distance, boolean success) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), StickHero.getSlime());
            translateTransition.setByX(distance);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.play();

            translateTransition.setOnFinished(event -> {
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
}




