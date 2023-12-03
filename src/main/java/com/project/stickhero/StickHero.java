package com.project.stickhero;
import javafx.animation.AnimationTimer;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.io.IOException;
import javafx.scene.image.ImageView;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    public static void translateSlimeM() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), StickHero.getSlime());
        translateTransition.setByX(StickHero.getDistanceToTravel()); // Translate by 200 pixels in the X direction
        translateTransition.setCycleCount(1); // Play animation once
        translateTransition.setAutoReverse(false); // Don't reverse the animation

        // Play the animation
        translateTransition.play();
    }
}

public class StickHero extends Application {
    private Stage stage;
    private static Scene scene;

    private Pane appRoot = new Pane();          //these roots are only for gamePlaying page
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();

    @FXML
    private Rectangle firstPillar;
    private Rectangle secondPillar;
    @FXML
    private static ImageView slime;
    private static Rectangle stick;

    private Double idealStickLength;
    private static Double distanceToTravel;

    public static ImageView getSlime() {
        return slime;
    }
    public static Rectangle getStick() {
        return stick;
    }
    public static Double getDistanceToTravel() {
        return distanceToTravel;
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


    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {

        Paint black = Color.BLACK;
        Paint red = Color.RED;

        Parent FXMLRoot = FXMLLoader.load(getClass().getResource("gamePlaying.fxml"));

        //FXMLRoot is 600x400

        appRoot.getChildren().add(FXMLRoot);
        appRoot.getChildren().add(gameRoot);
        appRoot.getChildren().add(uiRoot);

        scene = new Scene(appRoot, 600, 400);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        firstPillar = new Rectangle();
        firstPillar.setFill(black);
        firstPillar.setHeight(150);
        firstPillar.setWidth(150);
        gameRoot.getChildren().add(firstPillar);
        firstPillar.setLayoutX(0);          //LayoutX manages the position within a pane. Top left corner's X.
        firstPillar.setLayoutY(250);

        Random random = new Random();

        secondPillar = new Rectangle();
        secondPillar.setFill(black);
        secondPillar.setHeight(150);
        secondPillar.setWidth(random.nextDouble()*300 + 100);  //nextDouble returns a number between 0.0 and 1.0 (exclusive)
        gameRoot.getChildren().add(secondPillar);
        secondPillar.setLayoutX(200);
        secondPillar.setLayoutY(250);

        Image characterImage = new Image("file:./character_pink.png");
        slime = new ImageView(characterImage);
        slime.setFitHeight(50);
        slime.setFitWidth(50);
        slime.setLayoutX(firstPillar.getWidth()-slime.getFitWidth());
        slime.setLayoutY(210);
        gameRoot.getChildren().add(slime);

        stick = new Rectangle();
        stick.setWidth(5);
        stick.setHeight(0);
        stick.setFill(red);
        gameRoot.getChildren().add(stick);
        stick.setLayoutX(firstPillar.getLayoutX()+firstPillar.getWidth());
        stick.setLayoutY(250-stick.getHeight());
        AtomicReference<Integer> changingHeight = new AtomicReference<>(0); // Because can't change normal variables inside a lambda
        AtomicReference<Integer> changingY = new AtomicReference<>(250); // Because can't change normal variables inside a lambda

        scene.setOnKeyPressed(eventMain -> {
            if (eventMain.getCode() == KeyCode.SPACE) {
                keys.put(KeyCode.SPACE, true);
                changingHeight.updateAndGet(height -> height + 2);
                stick.setHeight(changingHeight.get());
                changingY.updateAndGet(y -> y - 2);
                stick.setLayoutY(changingY.get());
            }
        });

        scene.setOnKeyReleased(eventMain -> {
            if (eventMain.getCode() == KeyCode.SPACE && keys.get(KeyCode.SPACE)) {
                keys.put(KeyCode.SPACE, false);
                idealStickLength = secondPillar.getLayoutX() - firstPillar.getWidth();
                distanceToTravel = idealStickLength + secondPillar.getWidth();

                rotateStick.rotateStickM();
                    if (stick.getHeight()>= idealStickLength) {
                        translateSlime.translateSlimeM();
                        System.out.println("true");
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

    public void showNewStage (Stage stage) {



    }

    public static void main(String[] args) {
        launch();


    }
}