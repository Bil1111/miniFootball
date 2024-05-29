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
        gc.fillOval(x - radius + 70, y - radius + 120, radius * 2, radius * 2);
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 30);
        gc.fillText("Owners: " + microObjectsNames.toString(), x - radius + 20, y - radius + 10);
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
    /*
витрачають гроші і відновлюються швидше, ніж на трибунах
розділення по командах
фанати за певну суму стають гравцями
*/
    @Override
    public void interact(Fan fan) {
        if (fan instanceof Referee) {
            Referee referee = (Referee) fan;
            if(referee.getStamina()[0]<10){
                referee.setMoney(referee.getMoney()-500);
                referee.setStamina(new int[]{10, 5});
            }
        } else if (fan instanceof Footballer || (fan instanceof Fan && fan.isFootballer())) {
            // Handle as Footballer
            Footballer footballer;
            if (fan instanceof Footballer) {
                footballer = (Footballer) fan;
            } else {
                // Fan has already been converted to Footballer
                footballer = new Footballer();
                // Transfer properties
                footballer.setMoney(fan.getMoney());
                footballer.setStamina(new int[]{10, 5});
                footballer.setImageView(fan.getImageView());
                footballer.setXPos(fan.getXPos());
                footballer.setYPos(fan.getYPos());
                footballer.setName(fan.getName());
                footballer.setHasTicket(fan.isHasTicket());
                footballer.setBlueTeam(fan.isBlueTeam());
                footballer.setAge(fan.getAge());
                // Other necessary property transfers
            }

            if (footballer.getStamina()[0] < 10) {
                footballer.setMoney(footballer.getMoney() - 100);
                footballer.setStamina(new int[]{10, 5});
            }

        } else if (fan instanceof Fan) {
            if (fan.isBlueTeam()){
                System.out.println("Your team is blue. Go to BlueTeamTrainingBase!");
               }
            else {
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

                fan.setFootballer(true);
                fan.setName(footballer.getName());
                fan.setMoney(footballer.getMoney());
                fan.setHasTicket(footballer.isHasTicket());
                fan.setBlueTeam(footballer.isBlueTeam());
                fan.setAge(footballer.getAge());
                fan.setXPos(footballer.getXPos());
                fan.setYPos(footballer.getYPos());
                fan.setStamina(footballer.getStamina());
                fan.setImageView(footballer.getImageView());
            }
        }
    }
}
