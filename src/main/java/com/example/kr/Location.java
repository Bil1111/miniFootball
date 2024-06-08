package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

public class Location implements Serializable {
    protected String name;
    protected ArrayList<Fan> microObjects;
    protected ArrayList<String> microObjectsNames;
    protected int height;
    protected int width;
    protected float xPos;
    protected float yPos;
    protected transient ImageView imageView;

    final public static double WIDTH = Main.mainAnchorPane.getPrefWidth();
    final public static double HEIGHT = Main.mainAnchorPane.getPrefHeight();

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ArrayList<Fan> getMicroObjects() {
        return microObjects;
    }

    public void setMicroObjects(ArrayList<Fan> microObjects) {
        this.microObjects = microObjects;
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        loadImage();
    }

    public Location(String name, float x, float y) {
        this.name = name;
        this.xPos = x;
        this.yPos = y;
        microObjects = new ArrayList<>();
        microObjectsNames = new ArrayList<>();
    }

    public void addFan(Fan fan) {
        microObjects.add(fan);
        microObjectsNames.add(fan.getName());
    }

    public void removeFan(Fan fan) {
        microObjects.remove(fan);
        microObjectsNames.remove(fan.getName());
    }

    public void update(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Fan interact(Fan fan) {
        return fan;
    }
    public void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;

        gc.setFill(Color.BLACK);
        gc.fillOval(x - radius + 70, y - radius + 110, radius * 2, radius * 2);
        gc.setFont(new Font("Arial", 10));
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 20);
        gc.fillText("Owners: " + microObjectsNames, x - radius + 20, y - radius + 10);

        if (imageView == null) {
            loadImage();
        }
        gc.drawImage(imageView.getImage(), x, y);
    }

    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("/com/example/kr/MiniMapBorder1.png");
            if (imageURL != null) {
                this.imageView = new ImageView(imageURL.toExternalForm());
                setHeight((int) imageView.getImage().getHeight());
                setWidth((int) imageView.getImage().getWidth());
            } else {
                System.out.println("Failed to load image");
            }
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                '}';
    }
}
