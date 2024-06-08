package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;

public class FootballPitch extends Location {

//
//    protected String name;
//    protected ArrayList<Fan> microObjects;
//    protected ArrayList<String> microObjectsNames;
//    protected int height;
//    protected int width;
//    protected float xPos;
//    protected float yPos;
//    protected ImageView imageView;
//    protected void addNewMicroObject(Object object) {
//        if (object instanceof Knight) {
//            microObjectsNames.add(((Knight) object).getName());
//            microObjects.add((Knight) object);
//        }
//    }

    public FootballPitch(String name, float x, float y) {
        super(name, x, y);
    }

    //    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//    public void setHeight(int height) {
//        this.height = height;
//    }
//
////    public int getWidth() {
////        return width;
////    }
//
//    public void setWidth(int width) {
//        this.width = width;
//    }
    @Override
    public void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;


        gc.setFill(Color.BLACK);
        gc.fillOval(x - radius + 190, y - radius + 320, radius * 7, radius * 7);
        gc.setFont(new Font("Arial", 10));
        gc.fillText("Name: " + getName(), x - radius + 20, y - radius + 40);
        gc.fillText("Owners: " + microObjectsNames, x - radius + 20, y - radius + 20);


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
    /*
фанати повинні стати футболістами, щоб грати
футболісти і судді заробляють гроші
суддя може вилучити гравця з поля на трибуну на певний час

*/
    @Override
    public Fan interact(Fan fan) {
        if (fan instanceof Referee) {
            Referee referee = (Referee) fan;
            referee.removeFromField(new Fan());
            referee.setMoney(referee.getMoney()+1000);
        } else if (fan instanceof Footballer) {
            Footballer footballer = (Footballer) fan;
            footballer.play();
            footballer.setMoney(footballer.getMoney()+5000);
        } else if (fan instanceof Fan) {
            //System.out.println("You must first be a footballer!");
        }
        return fan;
    }
}
