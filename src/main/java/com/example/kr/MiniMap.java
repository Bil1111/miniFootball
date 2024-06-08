//package com.example.kr;
//
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//
//import java.util.HashMap;
//import java.util.Objects;
//
//public class MiniMap {
//    private static final double SCALE = 0.1; // Scale of the mini-map
//    private Pane pane;
//    private HashMap<Fan, ImageView> fanMap;
//    private HashMap<Location, ImageView> locationMap;
//    private Rectangle mainArea;
//
//    public MiniMap() {
//        this.pane = new Pane();
//        this.pane.setMinWidth(Main.mainAnchorPane.getPrefWidth() * MiniMap.SCALE);
//        this.pane.setMinHeight(Main.mainAnchorPane.getPrefHeight() * MiniMap.SCALE);
//        this.pane.setStyle("-fx-background-color: lightgrey;");
//        this.fanMap = new HashMap<>();
//        this.locationMap = new HashMap<>();
//
//        // Add border image
//        ImageView borderImage = new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/kr/MiniMapBorder1.png")).toExternalForm());
//        borderImage.setFitWidth(this.pane.getMinWidth());
//        borderImage.setFitHeight(this.pane.getMinHeight());
//
//        mainArea = new Rectangle(0, 0, Main.mainScrollPane.getViewportBounds().getWidth() * MiniMap.SCALE, Main.mainScrollPane.getViewportBounds().getHeight() * MiniMap.SCALE);
//        mainArea.setFill(Color.TRANSPARENT);
//        mainArea.setStrokeWidth(2);
//        mainArea.setStroke(Color.YELLOW);
//
//        // Add all components to the pane
//        this.pane.getChildren().addAll(borderImage, mainArea);
//
//        this.pane.setOnMousePressed(event -> {
//            moveTo(event.getX(), event.getY());
//        });
//
//        // Listen for main stage size changes
//        Main.getMainStage().widthProperty().addListener((obs, oldVal, newVal) -> updatePosition());
//        Main.getMainStage().heightProperty().addListener((obs, oldVal, newVal) -> updatePosition());
//
//        // Update position to bottom-left corner initially
//        updatePosition();
//    }
//
//    public void moveTo(double x, double y) {
//        double viewportWidth = mainArea.getWidth();
//        double viewportHeight = mainArea.getHeight();
//        double contentWidth = pane.getWidth();
//        double contentHeight = pane.getHeight();
//
//        double hValue = (x - viewportWidth / 2) / (contentWidth - viewportWidth);
//        double vValue = (y - viewportHeight / 2) / (contentHeight - viewportHeight);
//
//        Main.getMainScrollPane().setHvalue(clamp(hValue, 0, 1));
//        Main.getMainScrollPane().setVvalue(clamp(vValue, 0, 1));
//
//        // Ensure the mini-map follows the main viewport
//        updatePosition();
//    }
//
//    private double clamp(double value, double min, double max) {
//        if (value < min) return min;
//        if (value > max) return max;
//        return value;
//    }
//
//    private void updatePosition() {
//        double newX = 10; // Margin from the left edge
//        double newY = Main.getMainStage().getHeight() - pane.getHeight() - 50; // Margin from the bottom edge
//        pane.setLayoutX(newX);
//        pane.setLayoutY(newY);
//    }
//
//    public Pane getPane() {
//        return pane;
//    }
//
//    public void addFan(Fan fan) {
//        ImageView imageView = fan.getImageView();
//        imageView.setLayoutX(fan.getXPos() * MiniMap.SCALE);
//        imageView.setLayoutY(fan.getYPos() * MiniMap.SCALE);
//        imageView.setPreserveRatio(true);
//        imageView.setFitHeight(10);
//        fanMap.put(fan, imageView);
//        pane.getChildren().add(imageView);
//    }
//
//    public void removeFan(Fan fan) {
//        ImageView imageView = fanMap.remove(fan);
//        if (imageView != null) {
//            pane.getChildren().remove(imageView);
//        }
//    }
//
//    public void addLocation(Location location) {
//        ImageView imageView = location.getImageView();
//        imageView.setPreserveRatio(true);
//        imageView.setFitHeight(location.getHeight() * MiniMap.SCALE);
//        imageView.setLayoutX(location.getxPos() * MiniMap.SCALE);
//        imageView.setLayoutY(location.getyPos() * MiniMap.SCALE);
//        locationMap.put(location, imageView);
//        pane.getChildren().add(imageView);
//    }
//
//    public void removeLocation(Location location) {
//        ImageView imageView = locationMap.remove(location);
//        if (imageView != null) {
//            pane.getChildren().remove(imageView);
//        }
//    }
//
//    public void updateMap() {
//        mainArea.setWidth(Main.getMainScrollPane().getViewportBounds().getWidth() * MiniMap.SCALE);
//        mainArea.setHeight(Main.getMainScrollPane().getViewportBounds().getHeight() * MiniMap.SCALE);
//
//        for (Fan fan : fanMap.keySet()) {
//            ImageView imageView = fanMap.get(fan);
//            imageView.setLayoutX(fan.getXPos() * MiniMap.SCALE);
//            imageView.setLayoutY(fan.getYPos() * MiniMap.SCALE);
//        }
//
//        for (Location location : locationMap.keySet()) {
//            ImageView imageView = locationMap.get(location);
//            imageView.setLayoutX(location.getxPos() * MiniMap.SCALE);
//            imageView.setLayoutY(location.getyPos() * MiniMap.SCALE);
//        }
//    }
//}
