package com.project.stickhero;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import java.io.File;



public class Player implements Runnable{

    private static Player player;
    private ImageView Player_ImageView;
    private static Pane mainRoot= new Pane();
    public static Player getInstance(){
        if(player==null){
            player=new Player(new ImageView(new Image("file:./character_green.png")));
        }
        return player;
    }
    private Player(ImageView iv){
        this.Player_ImageView=iv;
    }

    public static double slimeXValue;

    public static boolean isSPressed = false;

    private static ImageView prevHeart;

    public static ImageView getPrevHeart() {
        return prevHeart;
    }
    public static void setPrevHeartNull() {
        prevHeart = null;
    }

    public static void whenDead() throws IOException {
        if (Data.heartScore > Data.getHighScore()) {
            Data.setHighScore(Data.heartScore);
            System.out.println("hs:" + Data.getHighScore());
        }


        Rectangle blackScreen= new Rectangle();
        blackScreen.setHeight(1080);
        blackScreen.setWidth(1920);
        blackScreen.setFill(Color.BLACK);
//        blackScreen.setOpacity(0);
        mainRoot.getChildren().add(blackScreen);

        Text text= new Text("Oops......"+"\n"+"\n"+"\n"+"You'll get there, soon.");
        text.setFont(new Font("Arial",18));
        text.setFill(Color.WHITE);
        text.setLayoutX(830);
        text.setLayoutY(440);
        text.setOpacity(0);
        mainRoot.getChildren().add(text);

        //FadeTransition fadeInBG= new FadeTransition(Duration.seconds(2),blackScreen);
        FadeTransition fadeInText= new FadeTransition(Duration.seconds(1),text);
        FadeTransition fadeOutText= new FadeTransition(Duration.seconds(0.5),text);
        FadeTransition fadeBG= new FadeTransition(Duration.seconds(1),blackScreen);

        PauseTransition pauseBlackScreen= new PauseTransition(Duration.seconds(2));
        PauseTransition pause1= new PauseTransition(Duration.seconds(0.5));

        fadeOutText.setFromValue(1.0);
        fadeOutText.setToValue(0.0);
        fadeInText.setFromValue(0.0);
        fadeInText.setToValue(1.0);

        fadeBG.setFromValue(1.0);
        fadeBG.setToValue(0.0);
//        fadeInBG.setFromValue(0.0);
//        fadeInBG.setToValue(1.0);

        pause1.play();

        pause1.setOnFinished(event->{
            fadeInText.play();

        });

        fadeInText.setOnFinished(event2->{
            pauseBlackScreen.play();
        });
        pauseBlackScreen.setOnFinished(event3->{
            fadeOutText.play();
            fadeBG.play();
            mainRoot.getChildren().remove(blackScreen);
        });

        Label highScore = new Label(String.valueOf(Data.getHighScore()));
        Label currentScore = new Label(String.valueOf(Data.heartScore));
        highScore.setFont(new Font("Arial",30));
        highScore.setTextFill(Color.WHITE);
        currentScore.setFont(new Font("Arial",30));
        currentScore.setTextFill(Color.WHITE);

        StickHero.getSlime().setVisible(false);
        Parent FXMLRoot = FXMLLoader.load(Objects.requireNonNull(Player.class.getResource("gameOver.fxml")));
        mainRoot.getChildren().add(FXMLRoot);
        mainRoot.getChildren().add(highScore);
        mainRoot.getChildren().add(currentScore);
        blackScreen.toFront();
        text.toFront();
        System.out.println("2");
//        GameOver.getHomebutton().toFront();
//        GameOver.getExitGameButton().toFront();
//        GameOver.getPlayagainbutton().toFront();
        System.out.println("2");


        highScore.setLayoutX(929);
        highScore.setLayoutY(571);
        currentScore.setLayoutX(929);
        currentScore.setLayoutY(444);


        Scene gameOver = new Scene(mainRoot);
        StickHero.getStage().setScene(gameOver);
        StickHero.getStage().setFullScreen(true);
        StickHero.getStage().show();


    }

    public static void onSlimeTranslationDone() {

        if(StickHero.isCollectedHeart()){
            Data.prevRoundScore=Data.prevRoundScore+1;
            StickHero.setCollectedHeart(false);

        }
        else {
            StickHero.getGameRoot().getChildren().remove(StickHero.getHeart());
            StickHero.getGameRoot().getChildren().remove(prevHeart);
            prevHeart = null;
        }
        System.out.println(Data.heartScore);
        StickHero.getSlime().getTransforms().clear();
        StickHero.getSlime().setLayoutY(1080-StickHero.getFirstPillar().getHeight()-StickHero.getSlime().getFitHeight()+10);
        StickHero.getSecondPillar().setTranslateX(-StickHero.getSecondPillar().getLayoutX());
        StickHero.getFirstPillar().setVisible(false);
        StickHero.setFirstPillar(StickHero.getSecondPillar());
        StickHero.getStick().setHeight(0);
        StickHero.getSlime().setTranslateX(-StickHero.getSlime().getLayoutX()+StickHero.getSecondPillar().getWidth()-StickHero.getSlime().getFitWidth());
        StickHero.getGameRoot().getChildren().add(Pillar.generateSecondPillar());
//        StickHero.getGameRoot().getChildren().add(StickHero.redRectangle);
        StickHero.redRectangle.toFront();
        if(Data.heartCounter%2==0){
            prevHeart = Data.generateHeart();
            StickHero.getGameRoot().getChildren().add(prevHeart);
            StickHero.isHeartAdded = true;
        }
        StickHero.collisionTimer.start();


    }
public static <AnimationTimer> void translateSlimeM(Double distance, boolean success) {

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
                        try {
                            whenDead();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
    public ImageView getPlayer_ImageView() {
        return Player_ImageView;
    }

    public void setPlayer_ImageView(ImageView player_ImageView) {
        Player_ImageView = player_ImageView;
    }

    public static Pane getMainRoot() {
        return mainRoot;
    }

    public static void setMainRoot(Pane mainRoot) {
        Player.mainRoot = mainRoot;
    }
}




