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
        gc.fillOval(x - radius + 70, y - radius + 140, radius * 2, radius * 2);
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 30);
        gc.fillText("Owners: " + microObjectsNames.toString(), x - radius + 20, y - radius + 10);
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
    public void interact(Fan fan) {
        if (fan instanceof Referee || (fan instanceof Fan && fan.isReferee())) {
            Referee referee;
//            if (fan instanceof Referee) {
//                referee = (Referee) fan;
//            } else {
                referee = new Referee();
                referee.setMoney(fan.getMoney());
                referee.setStamina(new int[]{10, 5});
                referee.setImageView(fan.getImageView());
                referee.setXPos(fan.getXPos());
                referee.setYPos(fan.getYPos());
                referee.setName(fan.getName());
                referee.setHasTicket(fan.isHasTicket());
                referee.setBlueTeam(fan.isBlueTeam());
                referee.setAge(fan.getAge());
            //}
        } else if (fan instanceof Footballer) {
            Referee referee = new Referee();
            referee.setName(fan.getName());
            referee.setHasTicket(fan.isHasTicket());
            referee.setBlueTeam(fan.isBlueTeam());
            referee.setAge(fan.getAge());
            referee.setMoney(fan.getMoney() - 3000);
            referee.setStamina(new int[]{10, 5});
            referee.setXPos(fan.getXPos());
            referee.setYPos(fan.getYPos());
            referee.setImageView(new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/kr/Referee.png")).toExternalForm()));


            fan.setReferee(true);
            fan.setName(referee.getName());
            fan.setMoney(referee.getMoney());
            fan.setHasTicket(referee.isHasTicket());
            fan.setAge(referee.getAge());
            fan.setXPos(referee.getXPos());
            fan.setYPos(referee.getYPos());
            fan.setStamina(referee.getStamina());
            fan.setImageView(referee.getImageView());
        } else if (fan instanceof Fan) {
            System.out.println("You must be a footballer first!!!");
        }
    }
}
