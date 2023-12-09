package com.project.stickhero;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.text.Text;
import java.io.File;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Stick {
    private static String perfect = "perfectLenghtSound.mp3";
    private static final AudioClip perfectLength = new AudioClip(new File(perfect).toURI().toString());

        public static void rotateStickM() {
            Rotate rotate = new Rotate();
            StickHero.getStick().getTransforms().add(rotate);
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    rotate.setPivotX(StickHero.getStick().getX());
                    rotate.setPivotY(StickHero.getStick().getY() + StickHero.getStick().getHeight());

                    rotate.setAngle(rotate.getAngle() + 3);

                    if (rotate.getAngle()>=90) {

                        //increase 20 to make it easier to get perfect length
                        if (Math.abs(StickHero.getStick().getHeight() - (StickHero.getSecondPillar().getLayoutX() - StickHero.getFirstPillar().getWidth() + StickHero.getSecondPillar().getWidth() / 2)) <= 20) {
                            Data.heartScore = Data.heartScore + 1;
                            Data.setpermanentHeartScore(Data.getpermanentHeartScore() + 1);
                            StickHero.showScore.setText(String.valueOf(Data.heartScore));

                            perfectLength.play();
                            Text popUp= new Text("Perfect!");
                            popUp.setFont(new Font("Arial",48));
                            StickHero.getGameRoot().getChildren().add(popUp);
                            popUp.setFill(Color.WHITE);
                            popUp.setLayoutX(950);
                            popUp.setLayoutY(200);
                            FadeTransition FadeINpopUp= new FadeTransition(Duration.seconds(0.5),popUp);
                            FadeTransition FadeoutpopUp= new FadeTransition(Duration.seconds(0.5),popUp);
                            FadeINpopUp.setFromValue(0.0);
                            FadeINpopUp.setToValue(1.0);
                            FadeoutpopUp.setFromValue(1.0);
                            FadeoutpopUp.setToValue(0.0);

                            TranslateTransition textUP = new TranslateTransition(Duration.seconds(1.5), popUp);
                            textUP.setByY(-100);
                            textUP.setCycleCount(1);
                            textUP.setAutoReverse(false);
                            FadeINpopUp.play();
                            FadeINpopUp.setOnFinished(eventi->{
                                textUP.play();
                                FadeoutpopUp.play();
                            });
                            FadeoutpopUp.setOnFinished(event2->{
                                StickHero.getGameRoot().getChildren().remove(popUp);
                            });


                        }
                        stop();

                    }
                }
            };
            timer.start();
        }
}


