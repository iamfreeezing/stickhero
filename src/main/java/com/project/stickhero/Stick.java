package com.project.stickhero;

import javafx.animation.AnimationTimer;
import javafx.scene.transform.Rotate;

public class Stick {
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
                        if (Math.abs(StickHero.getStick().getHeight() - (StickHero.getSecondPillar().getLayoutX() - StickHero.getFirstPillar().getWidth() + StickHero.getSecondPillar().getWidth() / 2)) <= 20) {
                            Data.heartScore = Data.heartScore + 1;
                            Data.setpermanentHeartScore(Data.getpermanentHeartScore() + 1);
                            StickHero.showScore.setText(String.valueOf(Data.heartScore));

                            //add sound

                        }
                        stop();

                    }
                }
            };
            timer.start();
        }
}


