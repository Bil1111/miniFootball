package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;

public class FootballRefereeSchool extends Location {
    public FootballRefereeSchool(String name, float x, float y) {
        super(name, x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;

        gc.setFill(Color.BLACK);
        if (imageView == null) {
            loadImage();
        }
        gc.fillOval(x - radius + 190, y - radius + 400, radius * 7, radius * 7);
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 30);
        gc.fillText("Owners: " + counter, x - radius + 20, y - radius + 10);
        gc.drawImage(imageView.getImage(), x - radius + 20, y - radius + 30);
    }

    @Override
    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("/com/example/kr/FootballRefereeSchool.png");
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
        contains(3);
        if (fan instanceof Referee) {
            System.out.println("You are already a referee");
            return fan;

        } else if (fan instanceof Footballer) {
            if (fan.getMoney() >= 5000) {
                Referee referee = new Referee();
                referee.setName(fan.getName());
                referee.setHasTicket(fan.isHasTicket());
                referee.setBlueTeam(fan.isBlueTeam());
                referee.setAge(fan.getAge());
                referee.setMoney(fan.getMoney() - 5000);
                referee.setStamina(new int[]{10, 5});
                referee.setXPos(fan.getXPos());
                referee.setYPos(fan.getYPos());
                referee.setImageView(new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/kr/Referee.png")).toExternalForm()));

                return referee;
            }
            return fan;
        } else if (fan instanceof Fan) {
            System.out.println("You must be a footballer first!!!");
            return fan;
        }
        return fan;
    }
}
