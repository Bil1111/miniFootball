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
import java.util.*;

public class Location implements Serializable {
    protected int counter;
    {
        counter=0;
    }
    @Serial
    private static final long serialVersionUID = 1L;
    protected String name;

    protected static ArrayList<Fan> microObjects = new ArrayList<>();
    private Set<Integer> countedFans = new HashSet<>();
    protected List<String> microObjectsNames = new ArrayList<>();
    protected int height;
    protected int width;
    protected float xPos;
    protected float yPos;
    protected transient ImageView imageView;

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

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        loadImage();
    }

    public void contains(int index){
        for(Fan fan: MicroObjectManager.getInstance().getFans()){
            if(((fan.getXPos() >= MacroObjectManager.getMacroObjects().get(index).getxPos() && fan.getXPos() <= MacroObjectManager.getMacroObjects().get(index).getxPos() + MacroObjectManager.getMacroObjects().get(index).getWidth() ||
                    fan.getXPos() + fan.getWidth() >= MacroObjectManager.getMacroObjects().get(index).getxPos() && fan.getXPos() <= MacroObjectManager.getMacroObjects().get(index).getxPos() + MacroObjectManager.getMacroObjects().get(index).getWidth()) &&
                    (fan.getYPos() >= MacroObjectManager.getMacroObjects().get(index).getyPos() && fan.getYPos() <= MacroObjectManager.getMacroObjects().get(index).getyPos() + MacroObjectManager.getMacroObjects().get(index).getHeight() ||
                            fan.getYPos() + fan.getHeight() >= MacroObjectManager.getMacroObjects().get(index).getyPos() && fan.getYPos() <= MacroObjectManager.getMacroObjects().get(index).getyPos() + MacroObjectManager.getMacroObjects().get(index).getHeight()))){
                if (!countedFans.contains(fan.getUniqueID())) {
                    countedFans.add(fan.getUniqueID());
                    counter++;
                }
            }
        }
    }

//    protected void addNewMicroObject(Object object) {
//        if (object instanceof Fan) {
//            if (microObjects.stream().anyMatch(fan -> fan.getUniqueID() == ((Fan) object).getUniqueID())) {
//
//                return;
//            }
    // counter++;
//            microObjectsNames.add(((Fan) object).getName());
//            microObjects.add((Fan) object);
//        }
//    }
//    protected void removeNewMicroObject(Object object) {
//        if (object instanceof Fan) {
//            if(microObjects.contains((Fan)object)) {
//                microObjects.remove(object);
//             //   counter--;
//            }
//            int indexToRemove = microObjectsNames.indexOf(((Fan) object).getName());
//            if (indexToRemove != -1) {
//                microObjectsNames.remove(indexToRemove);
//            }
//        }
//    }


    public Location(String name, float x, float y) {
        this.name = name;
        this.xPos = x;
        this.yPos = y;
        microObjects = new ArrayList<>();
        microObjectsNames = new ArrayList<>();
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
        gc.fillText("Owners: " + counter, x - radius + 20, y - radius + 10);

        if (imageView == null) {
            loadImage();
        }
        gc.drawImage(imageView.getImage(), x, y);
    }

    public void loadImage() {
        if (this.imageView == null) {
            URL imageURL = getClass().getResource("");
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
