package com.example.kr;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
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
    private static boolean HOLD = false;
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
                    microObjectManager.interactionBetweenMicroAndMacro();//взаємодія мікро з макро
                    break;
                case V:
                    microObjectManager.changeMovement();
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
                    saveInFile(primaryStage);
                    break;
                case L:
                    loadFromFile(primaryStage);
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

        addPressAndHoldHandler(mainAnchorPane, event -> {
            double xPos = event.getX();
            double yPos = event.getY();
            Fan fan = MicroObjectManager.getInstance().getFanHoldingPos((int) xPos, (int) yPos);

            if (fan != null) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    initialOffsetX = xPos - fan.getXPos();
                    initialOffsetY = yPos - fan.getYPos();
                    HOLD = true;
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && HOLD) {
                    double newX = xPos - initialOffsetX;
                    double newY = yPos - initialOffsetY;
                    MicroObjectManager.getInstance().moveSimpleFan((int) newX, (int) newY, fan);
                } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    HOLD = false;
                }
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
    private void saveInFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save fame in file!");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveGame(file.getAbsolutePath());
        }
    }

    private void loadFromFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game from file!");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            loadGame(file.getAbsolutePath());
        }
    }

    private void saveGame(String filePath) {
        Serialize.saveGame(MacroObjectManager.getMacroObjects(), microObjectManager.getFans(), filePath, "fans");
        File fansFolder = new File("fans");
        if (fansFolder.exists()) {
            for (File file : Objects.requireNonNull(fansFolder.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".ser")) {
                    file.delete();
                }
            }
        }
        for (Fan fan : microObjectManager.getFans()) {
            Serialize.saveFan(fan, "fans/" + fan.getUniqueID() + ".ser");
        }
    }

    private void loadGame(String filePath) {
        ArrayList<Location> loadedLocation = Serialize.loadGame(filePath, "fans");
        if (loadedLocation != null) {
            System.out.println("Location '" + loadedLocation.getFirst().getName() + "' loaded successfully.");
            MacroObjectManager.getMacroObjects().clear();
            MacroObjectManager.getMacroObjects().addAll(loadedLocation);
            microObjectManager.clearAllMicroObjects();
            File fansFolder = new File("fans");
            if (fansFolder.exists()) {
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
            System.out.println("Failed to load location.");
        }
    }

    private void addPressAndHoldHandler(Node node, EventHandler<MouseEvent> handler) {
        class Wrapper<T> { T content; }
        Wrapper<MouseEvent> eventWrapper = new Wrapper<>();

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            eventWrapper.content = event;
            handler.handle(event);
        });
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            eventWrapper.content = event;
            handler.handle(event);
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            eventWrapper.content = event;
            handler.handle(event);
        });
    }

}