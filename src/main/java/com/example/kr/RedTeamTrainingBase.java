package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Objects;

public class RedTeamTrainingBase extends Location{
    public RedTeamTrainingBase(String name, float x, float y) {
        super(name, x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;

        gc.setFill(Color.BLACK);
        if(imageView == null) {
            loadImage();
        }
        gc.fillOval(x - radius + 210, y - radius + 360, radius * 7, radius * 7);
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 30);
        gc.fillText("Owners: " + counter, x - radius + 20, y - radius + 10);
        gc.drawImage(imageView.getImage(), x - radius + 20, y - radius + 30);
    }

    @Override
    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("/com/example/kr/RedTeamTrainingBase.png");
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
        contains(2);
        if (fan instanceof Referee) {
            Referee referee = (Referee) fan;
            referee.train();
            return referee;
        } else if (fan instanceof Footballer) {
            if (fan.isBlueTeam()) {
                System.out.println("Your team is blue. Go to BlueTeamTrainingBase!");
                return fan;
            } else {
                ((Footballer) fan).train();
                return fan;
            }

        } else if (fan instanceof Fan) {
            if (fan.isBlueTeam()) {
                System.out.println("Your team is blue. Go to BlueTeamTrainingBase!");
                return fan;
            } else {
                if (fan.getMoney() >= 3000) {
                    Footballer footballer = new Footballer();
                    footballer.setName(fan.getName());
                    footballer.setHasTicket(fan.isHasTicket());
                    footballer.setBlueTeam(fan.isBlueTeam());
                    footballer.setAge(fan.getAge());
                    footballer.setMoney(fan.getMoney() - 3000);
                    footballer.setStamina(new int[]{10, 5});
                    footballer.setXPos(fan.getXPos());
                    footballer.setYPos(fan.getYPos());
                    footballer.setImageView(new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/kr/RedTeamFootballer.png")).toExternalForm()));

                    return footballer;
                }
                System.out.println("You have no enough money!!!");
                return fan;
            }
        }
        return fan;
    }
}
