package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Fan implements Cloneable, Comparable<Fan>, Serializable {

    static {
        System.out.println("Static initializer 'Fan'");
    }
    private String name;
    private int age;
    private double money;
    private boolean hasTicket;
    private boolean isBlueTeam;
    private float xPos;
    private float yPos;
    private int width;
    private int height;
    private boolean isActive = false;
    private int[] stamina = new int[2];
    private transient ImageView imageView;
    private transient ImageView imageIcon;
    private Map<Integer, String> accessories = new HashMap<>();
    public static Scanner in = new Scanner(System.in);
    private final int uniqueID;

    public int getUniqueID() {
        return uniqueID;
    }

    public int createID() {
        return (int) System.currentTimeMillis() / 432;
    }

    //МЕТОДИ
    public void buyTicket() {
        if (!this.hasTicket) {
            if (this.money >= 10) {
                this.money -= 10;
                this.hasTicket = true;
                System.out.println("You have successfully bought a ticket!");
            } else {
                System.out.println("You have no enough money to buy a ticket!");
            }
        } else {
            System.out.println("You already have a ticket");
        }
    }

    public void walk() {
//            float xChange = (random.nextFloat() - 0.5f) * 20; // Change in x position
//            float yChange = (random.nextFloat() - 0.5f) * 20; // Change in y position
//
//            // Update positions
//            this.xPos += xChange;
//            this.yPos += yChange;
//
//            // Optionally, add boundary checks (assuming canvas width and height are 800x600)
//            if (this.xPos < 0) this.xPos = 0;
//            if (this.xPos > 800) this.xPos = 800;
//            if (this.yPos < 0) this.yPos = 0;
//            if (this.yPos > 600) this.yPos = 600;

        System.out.println("I'm walking");
    }

    public void cheer() {
        this.setStamina(new int[]{this.getStamina()[0] - 1, 5});
        if (this.getStamina()[0] == 0) {
            for (int i = 0; i < 10; i++) {
                this.setStamina(new int[]{this.getStamina()[0] + 1, 5});
            }
        }
        System.out.println(this + " is cheering");
    }

    public void earnMoney() {
        System.out.println("Go to cash register and earn!");
    }

    public void interact(Fan fan) {
        System.out.println(this + " interacts with " + fan);
    }

    public void interact(Fan fan1, Fan fan2) {
        System.out.println(this + " interacts with " + fan1 + " and " + fan2);
    } // Статичний поліморфізм (Compile-time Polymorphism)

    public void setAccessory(Integer number, String accessory) {
        accessories.put(number, accessory);
    }

    //КОНСТРУКТОРИ
    public Fan(String name, double money, boolean hasTicket, boolean isBlueTeam, float xPos, float yPos) {
        this.name = name;
        this.money = money;
        this.hasTicket = hasTicket;
        this.isBlueTeam = isBlueTeam;
        this.xPos = xPos;
        this.yPos = yPos;
        stamina[0] = 10;
        stamina[1] = 10;
        this.uniqueID = createID();
        System.out.println("Constructor Fan(String name, double money, boolean hasTicket, float xPos, float yPos) was called. An object was created with parameters: " + toString());
        System.out.println();
    }

    public Fan() {
        this("Ivan", 50, false, false, 100, 100);
        System.out.println("Constructor Fan() was called. An object was created with parameters: " + toString());
        System.out.println();
    }

    //SET and GET
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            System.out.println("Amount of money can't be negative");
        } else {
            this.money = money;
        }
    }

    public boolean isHasTicket() {
        return hasTicket;
    }

    public void setHasTicket(boolean hasTicket) {
        this.hasTicket = hasTicket;
    }

    public boolean isBlueTeam() {
        return isBlueTeam;
    }

    public void setBlueTeam(boolean blueTeam) {
        isBlueTeam = blueTeam;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    protected void setActive() {
        isActive = !isActive;
    }

    public boolean isActive() {
        return isActive;
    }


    protected ImageView getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageView imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fan fan = (Fan) o;
        return age == fan.age && Double.compare(money, fan.money) == 0 && hasTicket == fan.hasTicket && Objects.equals(name, fan.name) && Objects.equals(accessories, fan.accessories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, money, hasTicket, accessories);
    }

    @Override
    public String toString() {
        return "Fan{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", hasTicket=" + hasTicket +
                ", isBlueTeam=" + isBlueTeam +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", stamina=" + Arrays.toString(stamina) +
                ", accessories=" + accessories +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Fan tmp = (Fan) super.clone();

        tmp.accessories = new HashMap<>();
        tmp.accessories.putAll(this.accessories);
        tmp.setMoney(this.getMoney());
        tmp.setXPos(this.xPos+90);
        tmp.setYPos(this.yPos);
        tmp.setName(this.name);
        tmp.setAge(this.age);
        tmp.setHasTicket(this.hasTicket);
        tmp.setBlueTeam(this.isBlueTeam);

        return tmp;
    }

    public void print() {
        System.out.println(toString());
    }

    public static Fan askStudentParameters() {
        System.out.println("Enter parameters: ");
        System.out.print("Name: ");
        String name = in.nextLine();
        System.out.print("Age: ");
        String a = in.nextLine();
        int age = Integer.parseInt(a);
        System.out.print("Money: ");
        String m = in.nextLine();
        double money = Double.parseDouble(m);
        System.out.print("hasTicket: ");
        String hT = in.nextLine();
        boolean hasTicket = Boolean.parseBoolean(hT);
        return new Fan();
    }

    @Override
    public int compareTo(Fan o) {
        return Double.compare(this.getStamina()[1], o.getStamina()[1]);
    }

    public static class FanComparator implements Comparator<Fan> {
        @Override
        public int compare(Fan o1, Fan o2) {
            return Double.compare(o1.getStamina()[0], o2.getStamina()[0]);
        }
    }

    protected void loadImage() {
        if (imageView == null) {
            String imagePath;
            if (isBlueTeam) {
                imagePath = "/com/example/kr/BlueTeamFan.png";
            } else {
                imagePath = "/com/example/kr/RedTeamFan.png";
            }
            imageView = new ImageView(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            setHeight((int) imageView.getImage().getHeight());
            setWidth((int) imageView.getImage().getWidth());
        }
    }

    protected void draw(GraphicsContext gc) {
        float x = this.xPos;
        float y = this.yPos;
        double radius = 25;

        gc.setFill(Color.GRAY);
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

        loadImage();
        if (isActive) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
            gc.strokeRect(x, y, imageView.getImage().getHeight(), imageView.getImage().getWidth());
        }
        gc.drawImage(imageView.getImage(), x, y);
    }

    public int[] getStamina() {
        return stamina;
    }

    public void setStamina(int[] stamina) {
        this.stamina = stamina;
    }

//    @Serial
//    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//        out.writeObject(currentPNG.getUrl());
//        out.writeObject(currentImageGIF.getImage().getUrl());
//    }
//
//    @Serial
//    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        String currentPNG_URL = (String) in.readObject();
//        String currentGIF_URL = (String) in.readObject();
//        try {
//            if (currentPNG_URL != null && currentGIF_URL != null) {
//                this.currentPNG = new Image(currentPNG_URL);
//                Image currentGIF = new Image(currentGIF_URL);
//                this.currentImagePNG = new ImageView(currentPNG);
//                this.currentImageGIF = new ImageView(currentGIF);
//            }
//        } catch (IllegalArgumentException ignored) {}
//    }
//
//
//    @Serial
//    private void readObjectNoData() throws ObjectStreamException {
//        throw new ObjectStreamException("No data available to deserialize") {
//        };
//    }
}
