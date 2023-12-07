package com.project.stickhero;

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
import javafx.scene.shape.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Homepage {

    public static void openHomepage() {
        FXMLLoader loader = new FXMLLoader(Homepage.class.getResource("homepage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        StickHero.getStage().setTitle("Stick Hero");
        StickHero.getStage().setScene(scene);
        StickHero.getStage().setFullScreen(true);
        StickHero.getStage().show();

    }


}
