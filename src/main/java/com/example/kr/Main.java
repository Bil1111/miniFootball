package com.example.kr;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    private MicroObjectManager microObjectManager;
    private MacroObjectManager macroObjectManager;
    private Canvas canvas;
    private Canvas miniMap;
    public final static int FPS = 120;
    private double initialOffsetX;
    private double initialOffsetY;
    public static Scene mainScene;
    public static AnchorPane mainAnchorPane;
    public static ScrollPane mainScrollPane;


    @Override
    public void start(Stage primaryStage) {
        mainScrollPane = new ScrollPane();
        mainAnchorPane = new AnchorPane();
        mainAnchorPane.setPrefWidth(4096);
        mainAnchorPane.setMaxWidth(4096);
        mainAnchorPane.setPrefHeight(3072);
        mainAnchorPane.setMaxHeight(3072);
        mainAnchorPane.setMinSize(4096, 3072);
        mainScrollPane.setContent(mainAnchorPane);
        StackPane stackPane = new StackPane(mainScrollPane);
        mainScene = new Scene(stackPane, 1024, 768);
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Mini Football");
        primaryStage.show();

        canvas = new Canvas(mainAnchorPane.getWidth(), mainAnchorPane.getHeight());
        miniMap = new Canvas(200, 200);
        Rectangle miniMapBorder = new Rectangle(210, 210, Color.TRANSPARENT);
        // miniMapBorder.setStroke(Paint.valueOf("/com/example/kr/MiniMapBorder1.png"));
        miniMapBorder.setStroke(Color.BLACK);
        mainAnchorPane.getChildren().add(canvas);
        stackPane.getChildren().addAll(miniMapBorder, miniMap);
        StackPane.setAlignment(miniMapBorder, Pos.TOP_RIGHT);
        StackPane.setMargin(miniMapBorder, new Insets(10));
        StackPane.setAlignment(miniMap, Pos.TOP_RIGHT);
        StackPane.setMargin(miniMap, new Insets(15));

        microObjectManager = MicroObjectManager.getInstance();
        microObjectManager.createMicroObjectDialog();
        macroObjectManager = MacroObjectManager.getInstance();

        mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case INSERT:
                    microObjectManager.createMicroObjectDialog();
                    break;
                case O:
                    MicroObjectManager.getInstance().printActiveMicroObjects();
                    break;
                case ESCAPE:
                    microObjectManager.deactivateAllMicroObjects();
                    break;
                case DELETE:
                    microObjectManager.removeActiveMicroObjects();
                    break;
                case NUM_LOCK:
                    microObjectManager.changeActiveParam();
                    break;
                case CAPS:
                    microObjectManager.requests();//запити
                    break;
                case X:
                    microObjectManager.method();//взаємодія мікро з макро
                    break;
                case C:
                    List<Fan> fansToAdd = new ArrayList<>();
                    for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                        if (fan.isActive()) {
                            try {
                                Fan clonedFan = (Fan) fan.clone();
                                fansToAdd.add(clonedFan);
                            } catch (CloneNotSupportedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    MicroObjectManager.getInstance().getFans().addAll(fansToAdd);
                    break;
                case E:
                    Serialize.saveGame(MacroObjectManager.getMacroObjects(), microObjectManager.getArray(), "location.ser", "fans");
                    File fansFolder = new File("fans");
                    if (fansFolder.exists() && fansFolder.isDirectory()) {
                        for (File file : Objects.requireNonNull(fansFolder.listFiles())) {
                            if (file.isFile() && file.getName().endsWith(".ser")) file.delete();
                        }
                    }

                    for (Fan fan : microObjectManager.getFans()) {
                        Serialize.saveFan(fan, "fans/" + fan.getUniqueID() + ".ser");
                    }
                    break;
                case L:
                    ArrayList<Location> loadedLocation = Serialize.loadGame("location.ser", "fans");
                    if (loadedLocation != null) {
                        System.out.println("Location named '" + loadedLocation.getFirst().getName() + "' has been loaded.");
                        MacroObjectManager.getMacroObjects().clear();
                        MacroObjectManager.getMacroObjects().addAll(loadedLocation);
                        microObjectManager.clearAllMicroObjects();
                        fansFolder = new File("fans");
                        if (fansFolder.exists() && fansFolder.isDirectory()) {
                            for (File file : Objects.requireNonNull(fansFolder.listFiles())) {
                                if (file.isFile() && file.getName().endsWith(".ser")) {
                                    Fan loadedFan = (Fan) Serialize.loadObject(file);
                                    if (loadedFan != null) {
                                        microObjectManager.addNewMicroObject(loadedFan);
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Failed to load the location from the selected file.");
                    }
                    break;

            }
            microObjectManager.moveActiveMicroObjects(event.getCode());
        });


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.0 / FPS), event -> {
            render(mainScrollPane);
            update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        canvas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                microObjectManager.activateMicroObject(event.getX(), event.getY());
            } else {
                microObjectManager.createMicroObjectDialog();
            }
        });

        miniMap.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double xRatio = event.getX() / miniMap.getWidth();
                double yRatio = event.getY() / miniMap.getHeight();
                mainScrollPane.setHvalue(xRatio * (mainScrollPane.getContent().getBoundsInLocal().getWidth() - mainScrollPane.getViewportBounds().getWidth()) / mainScrollPane.getContent().getBoundsInLocal().getWidth());
                mainScrollPane.setVvalue(yRatio * (mainScrollPane.getContent().getBoundsInLocal().getHeight() - mainScrollPane.getViewportBounds().getHeight()) / mainScrollPane.getContent().getBoundsInLocal().getHeight());
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

    private void render(ScrollPane scrollPane) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        GraphicsContext miniMapGc = miniMap.getGraphicsContext2D();
        miniMapGc.clearRect(0, 0, miniMap.getWidth(), miniMap.getHeight());
        double miniMapScaleX = miniMap.getWidth() / ((AnchorPane) canvas.getParent()).getWidth();
        double miniMapScaleY = miniMap.getHeight() / ((AnchorPane) canvas.getParent()).getHeight();
        miniMapGc.save();
        miniMapGc.scale(miniMapScaleX, miniMapScaleY);
        microObjectManager.drawAllMicroObjects(miniMapGc);
        macroObjectManager.drawAllMacroObjects(miniMapGc);
        miniMapGc.restore();
        macroObjectManager.drawAllMacroObjects(gc);
        microObjectManager.drawAllMicroObjects(gc);
        double viewportX = scrollPane.getHvalue() * scrollPane.getContent().getLayoutBounds().getWidth() * miniMapScaleX;
        double viewportY = scrollPane.getVvalue() * scrollPane.getContent().getLayoutBounds().getHeight() * miniMapScaleY;
        double viewportWidth = scrollPane.getViewportBounds().getWidth() * miniMapScaleX;
        double viewportHeight = scrollPane.getViewportBounds().getHeight() * miniMapScaleY;
        miniMapGc.setStroke(Color.RED);
        miniMapGc.strokeRect(viewportX, viewportY, viewportWidth, viewportHeight);
    }
}