package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;

public class FootballPitch extends Location {


    public FootballPitch(String name, float x, float y) {
        super(name, x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;


        gc.setFill(Color.BLACK);
        gc.fillOval(x - radius + 190, y - radius + 320, radius * 7, radius * 7);
        gc.setFont(new Font("Arial", 10));
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 40);
        gc.fillText("Owners: " + counter, x - radius + 20, y - radius + 20);


        if (imageView == null) {
            loadImage();
        }
        gc.drawImage(imageView.getImage(), x, y);
    }

    @Override
    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("/com/example/kr/FootballPitch.png");
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
    public Fan interact(Fan fan) {
        contains(0);
        if (fan instanceof Referee referee) {
            referee.work();
        } else if (fan instanceof Footballer footballer) {
            footballer.play();
        } else if (fan != null) {
            System.out.println("You must first be a footballer!");
        }
        return fan;
    }
}
