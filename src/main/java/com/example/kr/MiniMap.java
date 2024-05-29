package com.example.kr;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    private final Pane pane;
    private final HashMap<Fan, ImageView> fansMap;
    private final HashMap<Location, ImageView> locationsMap;
    private final Rectangle mainArea;

    public MiniMap() {
        this.pane = new Pane();
        this.pane.setMinWidth(Location.WIDTH * MiniMap.SCALE);
        this.pane.setMinHeight(Location.HEIGHT * MiniMap.SCALE);
        fansMap = new HashMap<>();
        locationsMap = new HashMap<>();


        Rectangle rectangle = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        rectangle.setFill(Color.LIGHTGREY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        Label label = new Label("Map");
        label.setFont(new Font("Segue UI Black Italic", 16));
        label.setLayoutX(pane.getMinWidth() / 2.1);

        mainArea = new Rectangle(0, 0, Main.mainScene.getWidth() * MiniMap.SCALE, Main.mainScene.getHeight() * MiniMap.SCALE);
        mainArea.setFill(Color.TRANSPARENT);
        mainArea.setStrokeWidth(2);
        mainArea.setStroke(Color.YELLOW);
        this.pane.getChildren().addAll(rectangle, label, mainArea);

        this.pane.setOnMousePressed(event -> {
            this.moveTo(event.getX(), event.getY());
        });
    }

    public void moveTo(double x, double y) {
        if (x < mainArea.getWidth() / 2) {
            Main.getMainScrollPane().setHvalue(0);
        } else if (x > pane.getWidth() - mainArea.getWidth() / 2) {
            Main.getMainScrollPane().setHvalue(1);
        } else Main.getMainScrollPane().setHvalue(x / pane.getWidth());

        if (y < mainArea.getHeight() / 2) {
            Main.getMainScrollPane().setVvalue(0);
        } else if (y > pane.getHeight() - mainArea.getHeight() / 2) {
            Main.getMainScrollPane().setVvalue(1);
        } else Main.getMainScrollPane().setVvalue(y / pane.getHeight());
    }

    public Pane getPane() {
        return pane;
    }

    public Rectangle getMainArea() {
        return mainArea;
    }

    public static double getSCALE() {
        return SCALE;
    }

    public void addFan(Fan fan) {
        ImageView imageView = new ImageView(fan.getImageView().getImage());
        imageView.setLayoutX(fan.getXPos() * MiniMap.SCALE);
        imageView.setLayoutY(fan.getYPos() * MiniMap.SCALE);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(190 * MiniMap.SCALE);
        fansMap.put(fan, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteAFan(Fan fan) {
        pane.getChildren().remove(fansMap.get(fan));
        fansMap.remove(fan);
    }

    public void addLocation(Location location) {
        ImageView imageView = new ImageView(location.getImageView().getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(location.getImageView().getFitHeight() * MiniMap.SCALE);
        imageView.setLayoutX(location.getxPos() * MiniMap.SCALE);
        imageView.setLayoutY(location.getyPos() * MiniMap.SCALE);

        locationsMap.put(location, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteALocation(Location location) {
        pane.getChildren().remove(locationsMap.get(location));
        locationsMap.remove(location);
    }
//    public void updateMap() {
//        if (Preferences.isMAP()) {
//            pane.setOpacity(1);
//            mainArea.setWidth(Main.getMainScene().getWidth()* MiniMap.SCALE);
//            mainArea.setHeight(Main.getMainScene().getHeight()*MiniMap.SCALE);
//        } else pane.setOpacity(0);
//        for (Fan fan : Main.getCity().getShoppers()) {
//            ImageView imageView = fansMap.get(fan);
//            imageView.setLayoutX(fan.getXPos() * MiniMap.SCALE);
//            imageView.setLayoutY(fan.getYPos() * MiniMap.SCALE);
//        }
//    }
}
