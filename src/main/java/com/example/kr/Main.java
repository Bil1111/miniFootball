package com.example.kr;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private MicroObjectManager microObjectManager;
    private MacroObjectManager macroObjectManager;
    private Canvas canvas;
    public static Scene mainScene;
    public static AnchorPane mainAnchorPane;
    public static ScrollPane mainScrollPane;

    public static ScrollPane getMainScrollPane() {
        return mainScrollPane;
    }

    public static void setMainScrollPane(ScrollPane mainScrollPane) {
        Main.mainScrollPane = mainScrollPane;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void setMainScene(Scene mainScene) {
        Main.mainScene = mainScene;
    }

    @Override
    public void start(Stage primaryStage) {
        mainScrollPane=new ScrollPane();
        mainAnchorPane=new AnchorPane();
        mainAnchorPane.setPrefWidth(6000);
        mainAnchorPane.setPrefHeight(2700);
        mainScrollPane.setContent(mainAnchorPane);
        mainScene = new Scene(mainScrollPane);
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Mini Football");
        primaryStage.show();

        canvas = new Canvas(mainScene.getWidth(), mainScene.getHeight());
        mainAnchorPane.getChildren().add(canvas);

        microObjectManager = MicroObjectManager.getInstance();
        microObjectManager.createMicroObjectDialog();
        macroObjectManager = MacroObjectManager.getInstance();

        mainScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.INSERT) {
                microObjectManager.createMicroObjectDialog();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                microObjectManager.deactivateAllMicroObjects();
            }
            if (event.getCode() == KeyCode.DELETE) {
                microObjectManager.removeActiveMicroObjects();
            }
            if(event.getCode() == KeyCode.NUM_LOCK) {
                microObjectManager.changeActiveParam();
            }
            if(event.getCode() == KeyCode.O) {
                microObjectManager.printActiveMicroObjects();
            }
            handleKeyInput(event.getCode());
        });

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0 / 60), event -> {
                    render();
                    update();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                microObjectManager.activateMicroObject(event.getX(), event.getY());
            }else{
                microObjectManager.createMicroObjectDialog();
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleKeyInput(KeyCode keyCode) {
        microObjectManager.moveActiveMicroObjects(keyCode);
    }

    private void update() {
        microObjectManager.updateAllMicroObjects();
    }

    private void render() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        macroObjectManager.drawAllMacroObjects(canvas.getGraphicsContext2D());
        microObjectManager.drawAllMicroObjects(canvas.getGraphicsContext2D());
    }
}