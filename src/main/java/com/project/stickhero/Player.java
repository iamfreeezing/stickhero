package com.project.stickhero;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Player {
public static void translateSlimeM(Double distance, boolean success) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), StickHero.getSlime());
            translateTransition.setByX(distance);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.play();

            translateTransition.setOnFinished(event -> {
                if (success) {
                    StickHero.getStick().getTransforms().clear();
                    StickHero.onSlimeTranslationDone();
                    StickHero.setIsSpacePressed(false);
                }
                else {
                    StickHero.getSlime().toFront();
                    TranslateTransition translateTransitionFall = new TranslateTransition(Duration.seconds(1), StickHero.getSlime());
                    translateTransitionFall.setByY(150);
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




