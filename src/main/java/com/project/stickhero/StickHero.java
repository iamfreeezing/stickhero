package com.project.stickhero;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.io.IOException;
import javafx.scene.image.ImageView;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.animation.*;
import javafx.scene.control.Label;

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
    public static AtomicBoolean isTranslating = new AtomicBoolean(false);

    @FXML
    private static ImageView slime;
    private static ImageView slimeFriend;
    private static Rectangle stick;
    private static Double idealStickLength;
    private static Double distanceToTravel;
    private static boolean translateDone;
    private static Random random = new Random();

    public static Label showScore;


    private static ImageView Heart;
    private static boolean collectedHeart=false;
    private static Pane alternateRoot = new Pane();
    public static boolean isHeartAdded = false;

    //homepage buttons
    @FXML
    private Button Ads;
    @FXML
    private Button buyCherry;
    @FXML
    private Button playbutton;
    @FXML
    void adsButton(ActionEvent event) {
    }
    @FXML
    void buyCherryButton(ActionEvent event) {
    }
    //homepage buttons over

    static AnimationTimer collisionTimer= new AnimationTimer() {
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
                collectedHeart = true;
                Heart.setVisible(false);
                gameRoot.getChildren().remove(Heart);
                isHeartAdded = false;
                if (Data.heartScore == Data.prevRoundScore) {
                    Data.heartScore = Data.heartScore + 1;
                    StickHero.showScore.setText(String.valueOf(Data.heartScore));
                }
            }
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

    }

    public static void runGame() throws IOException {

        isSpacePressed = false;
        Data.heartScore = 0;
        Data.prevRoundScore = 0;
        Data.heartCounter = 0;
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

        System.out.println(1);

        scene = new Scene(appRoot, 1920, 1080);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        gameRoot.requestFocus();

        System.out.println(2);

        appRoot.prefWidthProperty().bind(scene.widthProperty());
        appRoot.prefHeightProperty().bind(scene.heightProperty());

        firstPillar = new Rectangle();
        firstPillar.setHeight(1080/2);
        firstPillar.setWidth(1920/8);
        firstPillar.setFill(Color.BLACK);
        gameRoot.getChildren().add(firstPillar);
        firstPillar.setLayoutX(0);
        firstPillar.setLayoutY(gamePlayingBackground.getFitHeight()-firstPillar.getHeight());

        gameRoot.getChildren().add(Pillar.generateSecondPillar());

        endPillar= new Rectangle();
        endPillar.setHeight(1080/2);
        endPillar.setWidth(1920/8.5);
        endPillar.setFill(Color.BLACK);
        gameRoot.getChildren().add(endPillar);
        endPillar.setLayoutX(1920-1920/8.5);
        endPillar.setLayoutY(gamePlayingBackground.getFitHeight()-firstPillar.getHeight());

        System.out.println(3);


        Text text= new Text("Press the space bar to stretch out the stick");
        text.setFont(Font.font("Arial", 36));
        text.setFill(Color.WHITE);
        text.setLayoutY(120);
        text.setLayoutX(620);
        FadeTransition fadeout= new FadeTransition(Duration.seconds(1.5),text);
        fadeout.setToValue(0.0);

        showScore= new Label();
        showScore.setText(String.valueOf(0));
        showScore.setFont(new Font("Arial", 46));
        showScore.setLayoutY(20);
        showScore.setLayoutX(110);
        showScore.setTextFill(Color.WHITE);
        gameRoot.getChildren().add(showScore);


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
        slimeFriend.setLayoutY(endPillar.getLayoutY()-slimeFriend.getFitHeight()+9);
        slimeFriend.setLayoutX(endPillar.getLayoutX()+endPillar.getWidth()/2);
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
        AtomicReference<Integer> changingY = new AtomicReference<>((int)firstPillar.getLayoutY()); // Because can't change normal variables inside a lambda
        AtomicBoolean tempSpacePressed = new AtomicBoolean(false);
        AtomicBoolean isSPressed = new AtomicBoolean(false);
        System.out.println(4);

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
                System.out.println(5);

            }
            if (eventMain.getCode() == KeyCode.S && !isSPressed.get() && isTranslating.get()) {
                isSPressed.set(true);
                StickHero.getSlime().setLayoutY(StickHero.getSlime().getLayoutY() + 2 * StickHero.getSlime().getFitHeight() - 10);
                StickHero.getSlime().getTransforms().add(new Scale(1, -1)); // Adjust for your slime object
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

                if (stick.getHeight()>= idealStickLength) {
                    Player.translateSlimeM(distanceToTravel, true);
                }
                else {
                    distanceToTravel = stick.getHeight();
                    Player.translateSlimeM(distanceToTravel+slime.getFitWidth(), false);
                }
            }

            if (eventMain.getCode() == KeyCode.S && isSPressed.get() && isTranslating.get()) {
                isSPressed.set(false);
                StickHero.getSlime().setLayoutY(StickHero.getSlime().getLayoutY() - (2 * StickHero.getSlime().getFitHeight() - 10));
                StickHero.getSlime().getTransforms().removeIf(transform -> transform instanceof Scale);
            }


        });

        System.out.println(6);

    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        runGame();


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


}