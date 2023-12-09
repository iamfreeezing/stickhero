package com.project.stickhero;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Pillar {
    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static Rectangle generateSecondPillar() {
        Data.heartCounter = Data.heartCounter + 1;

        double maxSize = 2 * (1980 / 8);
        Rectangle a = new Rectangle();
        StickHero.setSecondPillar(a);
        StickHero.getSecondPillar().setFill(Color.BLACK);
        StickHero.getSecondPillar().setHeight(StickHero.getFirstPillar().getHeight());
        StickHero.getSecondPillar().setWidth(StickHero.getRandom().nextDouble() * (maxSize - 150) + 150);  //nextDouble returns a number between 0.0 and 1.0 (exclusive)
        StickHero.getSecondPillar().setLayoutX(StickHero.getFirstPillar().getWidth() + StickHero.getRandom().nextDouble() * 250 + 100);
        StickHero.getSecondPillar().setLayoutY(StickHero.getFirstPillar().getLayoutY());
        StickHero.getSecondPillar().setFill(Color.BLACK);

        

        return StickHero.getSecondPillar();
    }

}
