package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;

public class CashRegister extends Location{
    public CashRegister(String name, float x, float y) {
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
        gc.fillOval(x - radius + 190, y - radius + 420, radius * 7, radius * 7);
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 20);
        gc.fillText("Owners: " + microObjectsNames.toString(), x - radius + 20, y - radius );
        gc.drawImage(imageView.getImage(), x - radius + 20, y - radius + 30);
    }

    @Override
    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("/com/example/kr/CashRegister.png");
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
        if (fan instanceof Referee) {
            Referee referee = (Referee) fan;
            referee.setMoney(referee.getMoney()+10);
        } else if (fan instanceof Footballer) {
            Footballer footballer = (Footballer) fan;
            footballer.setMoney(footballer.getMoney()+25);
        } else if (fan instanceof Fan) {
            fan.setMoney(fan.getMoney()+50);
        }
        return fan;
    }
}
