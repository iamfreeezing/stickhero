package com.project.stickhero;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

//main controller file

public class StickHero extends Application {
    private static Stage stage;
    public static Scene scene;
    private static Parent gamePlayingFXML;
    private static ImageView gamePlayingBackground;

    private static Pane appRoot;          //these roots are only for gamePlaying page
    private static Pane gameRoot;
    private static Pane uiRoot;
    private static boolean isSpacePressed = false;

    @FXML
    private static Rectangle firstPillar;
    private static Rectangle endPillar;
    private static Rectangle secondPillar;
    public static Rectangle redRectangle;

    public static AtomicBoolean isTranslating = new AtomicBoolean(false);

    static Player player= Player.getInstance();
    private static ImageView slime = player.getPlayer_ImageView();

    @FXML
    //private static ImageView slime;
    private static ImageView slimeFriend;
    private static Rectangle stick;
    private static Double idealStickLength;
    private static Double distanceToTravel;
    private static boolean translateDone;
    public static boolean redVisibility;
    private static Random random = new Random();

    public static Label showScore;

    private static ImageView Heart;
    private static boolean collectedHeart=false;
    private static Pane alternateRoot = new Pane();
    public static boolean isHeartAdded = false;
    private static boolean isOnFirstPillar = false;
    private static boolean isOnSecondPillar = false;
    private static boolean wrongOrientation = false;
    static AtomicBoolean isSPressed = new AtomicBoolean(false);
    private static boolean bgSoundOn = false;
    public static boolean isPerfectStick = false;

    //homepage buttons
    @FXML
    private Button Ads;

    @FXML
    private Button buyCherry;

    @FXML
    private Button playbutton;

    @FXML
    private Button sound;


    @FXML
    void buyCherryButton(ActionEvent event) {

    }

    @FXML

    void soundButton(ActionEvent event) {

        if (bgSoundOn) {stopBackgroundSound();}
        else {startBackgroundSound();}

    }

    //homepage buttons over


    private static String bgsound = "Sakura-Girl-Peach-chosic.com_.mp3";
    private static final AudioClip backgroundSound = new AudioClip(new File(bgsound).toURI().toString());

    private static String rewardfile = "mixkit-game-treasure-coin-2038.wav";
    private static final AudioClip rewardSound = new AudioClip(new File(rewardfile).toURI().toString());

    public static AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {

            checkCollision(slime, Heart);

        }
    };

    public static BoundingBox editBounds(ImageView object, Double reduction) {
        Bounds initialBounds = object.getBoundsInParent();
        return new BoundingBox(initialBounds.getMinX() - 2.0, initialBounds.getMinY() + reduction, initialBounds.getMaxX() - initialBounds.getMinX() - 4.0, initialBounds.getMaxY() - 2*reduction - initialBounds.getMinY());
    }

    public static void checkCollision(ImageView player, ImageView target){
        if (isHeartAdded) {
            Bounds playerNewBounds = editBounds(player, 5.0);
            Bounds targetNewBounds = editBounds(target, 5.0);
            if (playerNewBounds.intersects(targetNewBounds)) {
                collisionTimer.stop();
                collectedHeart = true;
                Heart.setVisible(false);
                gameRoot.getChildren().remove(Heart);
                isHeartAdded = false;
//                if (Data.heartScore == Data.prevRoundScore) {
                    Data.setHeartScore(Data.getHeartScore()+1);
                    Data.setpermanentHeartScore(Data.getpermanentHeartScore() + 1);
                    StickHero.showScore.setText(String.valueOf(Data.getHeartScore()));
                    Text message= new Text("You caught my heart!");
                    message.setFill(Color.WHITE);
                    message.setFont(new Font("Arial",30));
                    message.setLayoutX(1920-1920/8.5-100);
                    message.setLayoutY(300);
                    message.setOpacity(0);
                    gameRoot.getChildren().add(message);
                    message.toFront();
                    FadeTransition FadeINpopUp= new FadeTransition(Duration.seconds(0.5),message);
                    FadeTransition FadeoutpopUp= new FadeTransition(Duration.seconds(0.5),message);
                    FadeINpopUp.setFromValue(0.0);
                    FadeINpopUp.setToValue(1.0);
                    FadeoutpopUp.setFromValue(1.0);
                    FadeoutpopUp.setToValue(0.0);

                    TranslateTransition textUP = new TranslateTransition(Duration.seconds(1), message);
                    textUP.setByY(-100);
                    textUP.setCycleCount(1);
                    textUP.setAutoReverse(false);
                    FadeINpopUp.play();
                    FadeINpopUp.setOnFinished(eventi->{
                        textUP.play();
                        FadeoutpopUp.play();
                    });
                    FadeoutpopUp.setOnFinished(event2->{
                        gameRoot.getChildren().remove(message);
                    });
                }
//                }
            }
        }


    public static void initialize() throws IOException {

        gamePlayingFXML = FXMLLoader.load(StickHero.class.getResource("gamePlaying.fxml"));

        Image backgroundImage = new Image("file:./background_2.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(1080);
        backgroundImageView.setFitWidth(1920);
        gamePlayingBackground = backgroundImageView;

        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();

        uiRoot.getChildren().add(gamePlayingFXML);
        appRoot.getChildren().add(gamePlayingBackground);
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(uiRoot);
        startBackgroundSound();

        Data.setpermanentHeartScore(readFromFile("cherrySaveFile.txt"));
        Data.setHighScore(readFromFile("scoreSaveFile.txt"));

    }

    public static void runGame(boolean revive) throws IOException {

        isSpacePressed = false;
        Data.setHeartCounter(0);
        Heart = null;
        Player.setPrevHeartNull();
        collectedHeart = false;
        isHeartAdded = false;

        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();

        uiRoot.getChildren().add(gamePlayingFXML);
        appRoot.getChildren().add(gamePlayingBackground);
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(uiRoot);

        if (revive) {
            Data.setPermanentHeartScore(Data.getPermanentHeartScore()-10);
        }

        else {

            Data.setHeartScore(0);
            Data.setPrevRoundScore(0);
            showScore = new Label();
            showScore.setFont(new Font("Arial", 46));
            showScore.setLayoutY(20);
            showScore.setLayoutX(105);
            showScore.setTextFill(Color.WHITE);
            showScore.setText(String.valueOf(0));

        }

        gameRoot.getChildren().add(showScore);
        Data.setPrevRoundScore(Data.getHeartScore());

        scene = new Scene(appRoot, 1920, 1080);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        gameRoot.requestFocus();


        appRoot.prefWidthProperty().bind(scene.widthProperty());
        appRoot.prefHeightProperty().bind(scene.heightProperty());

        firstPillar = new Rectangle();
        firstPillar.setHeight(1080/2);
        firstPillar.setWidth(1920/8);
        firstPillar.setFill(Color.BLACK);
        gameRoot.getChildren().add(firstPillar);
        firstPillar.setLayoutX(0);
        firstPillar.setLayoutY(gamePlayingBackground.getFitHeight()-firstPillar.getHeight());


        redRectangle = new Rectangle();
        redRectangle.setFill(Color.DARKGRAY);
        redRectangle.setWidth(20);
        redRectangle.setHeight(6);
        redRectangle.setVisible(false);
        redVisibility = false;

        gameRoot.getChildren().add(Pillar.generateSecondPillar());
        gameRoot.getChildren().add(redRectangle);

        endPillar= new Rectangle();
        endPillar.setHeight(1080/2);
        endPillar.setWidth(1920/8.5);
        endPillar.setFill(Color.BLACK);
        gameRoot.getChildren().add(endPillar);
        endPillar.setLayoutX(1920-1920/8.5);
        endPillar.setLayoutY(gamePlayingBackground.getFitHeight()-firstPillar.getHeight());

        Text text= new Text("Press the space bar to stretch out the stick");
        text.setFont(Font.font("Arial", 36));
        text.setFill(Color.WHITE);
        text.setLayoutY(120);
        text.setLayoutX(620);
        FadeTransition fadeout= new FadeTransition(Duration.seconds(1.5),text);
        fadeout.setToValue(0.0);


        Image characterImage = new Image("file:./character_green.png");

        slime = new ImageView(characterImage);
        slime.setFitHeight(50);
        slime.setFitWidth(50);
        slime.setLayoutX(firstPillar.getWidth()-slime.getFitWidth());
        slime.setLayoutY(firstPillar.getLayoutY()-slime.getFitHeight()+9);          //scaling factor
        gameRoot.getChildren().add(slime);

        slimeFriend=new ImageView(new Image("file:./character_pink.png"));
        slimeFriend.setFitWidth(50);
        slimeFriend.setFitHeight(50);


        slimeFriend.setLayoutX(endPillar.getLayoutX()+endPillar.getWidth()/2);
        slimeFriend.setLayoutY(endPillar.getLayoutY()-40);


        gameRoot.getChildren().add(slimeFriend);


        gameRoot.getChildren().add(text);

        TranslateTransition slimeFriendjump = new TranslateTransition(Duration.seconds(0.5), slimeFriend);
        slimeFriendjump.setByY(-50);
        slimeFriendjump.setCycleCount(Transition.INDEFINITE);
        slimeFriendjump.setAutoReverse(true);
        slimeFriendjump.play();




        stick = new Rectangle();
        stick.setWidth(5);
        stick.setHeight(0);
        stick.setFill(Color.BLACK);
        stick.setLayoutX(firstPillar.getLayoutX()+firstPillar.getWidth()-stick.getWidth());
        gameRoot.getChildren().add(stick);
        stick.toFront();
        stick.setLayoutY(firstPillar.getLayoutY());
        AtomicReference<Integer> changingHeight = new AtomicReference<>(0); // Because can't change normal variables inside a lambda
        AtomicReference<Integer> changingY = new AtomicReference<>((int)firstPillar.getLayoutY());
        AtomicBoolean tempSpacePressed = new AtomicBoolean(false);

        gameRoot.requestFocus();
        gameRoot.setOnKeyPressed(eventMain -> {
            if (eventMain.getCode() == KeyCode.SPACE && !isSpacePressed) {

                fadeout.play();
                tempSpacePressed.set(true);
                stick.setLayoutX(firstPillar.getWidth());
                changingHeight.updateAndGet(height -> height + 12);
                stick.setHeight(changingHeight.get());
                changingY.updateAndGet(y -> y - 12);
                stick.setLayoutY(changingY.get());
                stick.toFront();

            }
            if (eventMain.getCode() == KeyCode.S && !isSPressed.get() && isTranslating.get()) {
                isSPressed.set(true);
                StickHero.getSlime().setLayoutY(StickHero.getSlime().getLayoutY() + 2 * StickHero.getSlime().getFitHeight() - 13);
                StickHero.getSlime().getTransforms().add(new Scale(1, -1));
            }

        });

        gameRoot.setOnKeyReleased(eventMain -> {

            if (eventMain.getCode() == KeyCode.SPACE && tempSpacePressed.get()) {
                isSpacePressed = true;
                tempSpacePressed.set(false);
                changingHeight.updateAndGet(height -> 0);
                changingY.updateAndGet(y -> (int)firstPillar.getLayoutY());
                idealStickLength = secondPillar.getLayoutX() - firstPillar.getWidth();
                distanceToTravel = idealStickLength + secondPillar.getWidth();

                Stick.rotateStickM();

                if (stick.getHeight() >= idealStickLength && stick.getHeight() <= secondPillar.getLayoutX() - firstPillar.getWidth() + secondPillar.getWidth()) {
                    Player.translateSlimeM(distanceToTravel, true);
                }
                else {
                    distanceToTravel = stick.getHeight();
                    Player.translateSlimeM(distanceToTravel+slime.getFitWidth(), false);
                }
            }

            if (eventMain.getCode() == KeyCode.S && isSPressed.get() && isTranslating.get()) {
                isSPressed.set(false);
                wrongOrientation = false;
                StickHero.getSlime().setLayoutY(StickHero.getSlime().getLayoutY() - (2 * StickHero.getSlime().getFitHeight() - 13));
                StickHero.getSlime().getTransforms().removeIf(transform -> transform instanceof Scale);
            }


        });


    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        runGame(false);


    }

    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        StickHero.stage = stage;
        Homepage.openHomepage();

    }

    public static void main(String[] args) {
        launch();
    }


    public static Rectangle getSecondPillar() {
        return secondPillar;}

    public static Pane getGameRoot() {
        return gameRoot;}

    public static void setFirstPillar(Rectangle fp) {
        firstPillar = fp;
    }
    public static void setSecondPillar(Rectangle sp) {
        secondPillar = sp;
    }

    public static Random getRandom() {
        return random;
    }

    public static Rectangle getFirstPillar() {
        return firstPillar;
    }


    public static ImageView getSlime() {return slime;}

    public static boolean getTranslateDone() {
        return translateDone;
    }

    public static Rectangle getStick() {
        return stick;
    }

    public static Double getDistanceToTravel() {
        return distanceToTravel;
    }

    public static boolean getIsSpacePressed() {
        return isSpacePressed;
    }

    public static void setIsSpacePressed(boolean a) {
        isSpacePressed = a;
    }

    public static ImageView getHeart() {
        return Heart;
    }

    public static void setHeart(ImageView heart) {
        Heart = heart;
    }

    public static boolean isCollectedHeart() {
        return collectedHeart;
    }

    public static void setCollectedHeart(boolean collectedHeart) {
        StickHero.collectedHeart = collectedHeart;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void startBackgroundSound(){
        backgroundSound.play();
        bgSoundOn = true;
    }
    public static void stopBackgroundSound() {
        backgroundSound.stop();
        bgSoundOn = false;}
    public static void stopRewardSound(){
        rewardSound.stop();
    }
    public static void startRewardSound(){
        rewardSound.play();
    }


        public static int readFromFile(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                if (line != null) {
                    return Integer.parseInt(line);
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
            return 0;
        }

        public static void writeToFile(int value, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(String.valueOf(value));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @FXML
    void adsButton(ActionEvent event) {
        Rectangle noads= new Rectangle();
        Text text= new Text("No ADs since you're using premium!");
        text.setFont(new Font("Arial",18));
        text.setLayoutX(790);
        text.setLayoutY(450);
        text.setFill(Color.BLACK);
        noads.setStyle("-fx-fill: #ffffff;");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(21.0);
        dropShadow.setHeight(21.0);
        dropShadow.setRadius(10.0);

        noads.setEffect(dropShadow);

        noads.setWidth(480);
        noads.setHeight(170);
        noads.setLayoutX(692);
        noads.setLayoutY(372);
        Homepage.getMainRoot().getChildren().add(noads);
        Homepage.getMainRoot().getChildren().add(text);
        noads.toFront();
        text.toFront();


        FadeTransition fadeInRectangle= new FadeTransition(Duration.seconds(0.5),noads);
        FadeTransition fadeInText= new FadeTransition(Duration.seconds(0.5),text);

        FadeTransition fadeOutRectangle= new FadeTransition(Duration.seconds(0.5),noads);
        FadeTransition fadeOutText= new FadeTransition(Duration.seconds(0.5),text);


        PauseTransition pause0= new PauseTransition(Duration.seconds(3));
        fadeInText.setFromValue(0.0);
        fadeInText.setToValue(1.0);
        fadeInRectangle.setFromValue(0.0);
        fadeInRectangle.setToValue(1.0);


        fadeOutText.setToValue(0.0);
        fadeOutText.setFromValue(1.0);
        fadeOutRectangle.setToValue(0.0);
        fadeOutRectangle.setFromValue(1.0);

        fadeInRectangle.play();
        fadeInText.play();


        fadeInText.setOnFinished(event1->{
            pause0.play();
        });

        pause0.setOnFinished(event2->{
            fadeOutRectangle.play();
            fadeOutText.play();
            Homepage.getMainRoot().getChildren().remove(text);
            Homepage.getMainRoot().getChildren().remove(noads);
        });
    }


}