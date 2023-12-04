package com.project.stickhero;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.io.IOException;
import javafx.scene.image.ImageView;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.layout.*;
import javafx.animation.*;

//main controller file

class rotateStick {
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
                    stop();

                }
            }
        };
        timer.start();
    }
}

class translateSlime {

    public static void translateSlimeM(Double distance, boolean success) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), StickHero.getSlime());
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

public class StickHero extends Application {
    private Stage stage;
    private static Scene scene;

    private Pane appRoot = new Pane();          //these roots are only for gamePlaying page
    private static Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private static boolean isSpacePressed = false;

    @FXML
    private static Rectangle firstPillar;
    private static Rectangle secondPillar;
    @FXML
    private static ImageView slime;
    private static Rectangle stick;

    private Double idealStickLength;
    private static Double distanceToTravel;
    private static boolean translateDone;
    private static Random random = new Random();

    private static Image pillarBackground = new Image("file:./rockbg_7.jpg");

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

    public static void onSlimeTranslationDone() {
        secondPillar.setTranslateX(-secondPillar.getLayoutX());
        firstPillar.setVisible(false);
        firstPillar = secondPillar;
        stick.setHeight(0);
        slime.setTranslateX(-slime.getLayoutX()+secondPillar.getWidth()-slime.getFitWidth());
        gameRoot.getChildren().add(generateSecondPillar());
    }

    public static Rectangle generateSecondPillar() {

        double maxSize = 2*(1980/8);

        secondPillar = new Rectangle();
        secondPillar.setFill(Color.BLACK);
        secondPillar.setHeight(firstPillar.getHeight());
        secondPillar.setWidth(random.nextDouble()*(maxSize-150) + 150);  //nextDouble returns a number between 0.0 and 1.0 (exclusive)
        secondPillar.setLayoutX(firstPillar.getWidth() + random.nextDouble()*250 + 100);
        secondPillar.setLayoutY(firstPillar.getLayoutY());
        secondPillar.setFill(Color.BLACK);

        return secondPillar;
    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        Paint black = Color.BLACK;
        Paint red = Color.RED;

        Parent FXMLRoot = FXMLLoader.load(getClass().getResource("gamePlaying.fxml"));

        //FXMLRoot is 600x400

        Image backgroundImage = new Image("file:./background_2.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(1080);
        backgroundImageView.setFitWidth(1920);

        appRoot.getChildren().add(backgroundImageView);
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(uiRoot);

        scene = new Scene(appRoot, 1920, 1080);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        appRoot.prefWidthProperty().bind(scene.widthProperty());
        appRoot.prefHeightProperty().bind(scene.heightProperty());

        firstPillar = new Rectangle();
        firstPillar.setFill(black);
        firstPillar.setHeight(1080/2);
        firstPillar.setWidth(1920/8);
        firstPillar.setFill(Color.BLACK);
        gameRoot.getChildren().add(firstPillar);
        firstPillar.setLayoutX(0);
        firstPillar.setLayoutY(backgroundImageView.getFitHeight()-firstPillar.getHeight());

        gameRoot.getChildren().add(generateSecondPillar());

        Image characterImage = new Image("file:./character_pink.png");
        slime = new ImageView(characterImage);
        slime.setFitHeight(50);
        slime.setFitWidth(50);
        slime.setLayoutX(firstPillar.getWidth()-slime.getFitWidth());
        slime.setLayoutY(firstPillar.getLayoutY()-slime.getFitHeight()+9);          //scaling factor
        gameRoot.getChildren().add(slime);

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
        scene.setOnKeyPressed(eventMain -> {
            if (eventMain.getCode() == KeyCode.SPACE && !isSpacePressed) {
                tempSpacePressed.set(true);
                stick.setLayoutX(firstPillar.getWidth());
                changingHeight.updateAndGet(height -> height + 6);
                stick.setHeight(changingHeight.get());
                changingY.updateAndGet(y -> y - 6);
                stick.setLayoutY(changingY.get());
                stick.toFront();
            }
        });

        scene.setOnKeyReleased(eventMain -> {
            if (eventMain.getCode() == KeyCode.SPACE && tempSpacePressed.get()) {
                isSpacePressed = true;
                tempSpacePressed.set(false);
                changingHeight.updateAndGet(height -> 0);
                changingY.updateAndGet(y -> (int)firstPillar.getLayoutY());
                idealStickLength = secondPillar.getLayoutX() - firstPillar.getWidth();
                distanceToTravel = idealStickLength + secondPillar.getWidth();

                rotateStick.rotateStickM();
                    if (stick.getHeight()>= idealStickLength) {
                        translateSlime.translateSlimeM(getDistanceToTravel(), true);
                    }
                    else {
                        distanceToTravel = stick.getHeight();
                        translateSlime.translateSlimeM(distanceToTravel+slime.getFitWidth(), false);
                    }
            }
        });


    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}