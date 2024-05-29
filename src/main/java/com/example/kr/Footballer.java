package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class Footballer extends Fan {
    Footballer(String name, double money, boolean hasTicket, boolean isBlueTeam, float xPos, float yPos) {
        super(name, money, hasTicket, isBlueTeam, xPos, yPos);
    }

    public Footballer() {
        this("Ivan", 50, false, false, 100, 100);//null or ""
        System.out.println("Constructor Fan() was called. An object was created with parameters: " + toString());
        System.out.println();
    }
    public void play() {
        System.out.println("I'm playing");
    }
    public void train() {
        System.out.println("I'm training");
    }
    public void recovery() {
        if (this.getStamina()[0] < 10) {
            this.setStamina(new int[]{this.getStamina()[0] + 1, 5});
        }
        System.out.println("I'm recovering");
    }

    //Динамічний поліморфізм (Run-time Polymorphism)
    @Override
    public void earnMoney() {
        System.out.println("Go play football to earn money!");
    }

    @Override
    protected void loadImage() {
        if (super.getImageIcon() == null) {
            String imagePath;
            if (isBlueTeam()) {
                imagePath = "/com/example/kr/BlueTeamFootballer.png";
            } else {
                imagePath = "/com/example/kr/RedTeamFootballer.png";
            }
            super.setImageIcon(new ImageView(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
            super.setHeight((int) super.getImageIcon().getImage().getHeight());
            super.setWidth((int) super.getImageIcon().getImage().getWidth());
        }
    }

    @Override
    protected void draw(GraphicsContext gc) {
        float x = super.getXPos();
        float y = super.getYPos();
        double radius = 25;

        gc.setFill(Color.PURPLE);
        gc.fillOval(x - radius + 20, y - radius + 40, radius * 2, radius * 2);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 10));
        gc.fillText("Money: " + getMoney(), x - radius + 20, y - radius + 5);

        double staminaWidth = 8 * getStamina()[0];
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x - radius + 23, y - radius + 20, 1, 1);
        gc.fillRect(x - radius + 23, y - radius + 10, staminaWidth, 10);

        if (super.getImageIcon() == null) {
            loadImage();
        }
        if (super.isActive()) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
            gc.strokeRect(x, y, super.getImageIcon().getImage().getHeight(), super.getImageIcon().getImage().getWidth());
        }
        gc.drawImage(super.getImageIcon().getImage(), x, y);
    }

}
