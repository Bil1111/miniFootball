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
    private boolean complete = false;
    private boolean isHolding = false;
    private transient Location handler;
    private boolean previousCashRegister;
    private boolean previousFanTribune = true;
    private boolean previousBlueTeamTrainingBase;
    private boolean previousRedTeamTrainingBase;
    private int randomGoalX = -1;
    private int randomGoalY = -1;
    private int tickExisted;
    private final Random random;
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


    public void walk(Location object) {
        checkCollision(object);
        if (!isActive) {
            if (complete) {
                if (randomGoalX == -1 && randomGoalY == -1) {
                    randomGoalX = random.nextInt(100, 1200);
                    randomGoalY = random.nextInt(100, 1200);
                } else {
                    if (Math.abs(xPos - randomGoalX) < 30 && Math.abs(yPos - randomGoalY) < 30) {
                        randomGoalX = -1;
                        randomGoalY = -1;
                        complete = false;
                        isHolding = false;
                    } else {
                        moveTo(randomGoalX, randomGoalY);
                    }
                }
            } else {
                if (!isHolding) {
                    if (previousFanTribune && !previousBlueTeamTrainingBase && !previousRedTeamTrainingBase) {
                        moveTo(MacroObjectManager.X_POS_CASH_REGISTER, MacroObjectManager.Y_POS_CASH_REGISTER);
                    } else if (previousCashRegister && this.isBlueTeam) {
                        moveTo(MacroObjectManager.X_POS_BLUE_TEAM_TRAINING_BASE, MacroObjectManager.Y_POS_BLUE_TEAM_TRAINING_BASE);
                    } else if (previousCashRegister && !this.isBlueTeam) {
                        moveTo(MacroObjectManager.X_POS_RED_TEAM_TRAINING_BASE, MacroObjectManager.Y_POS_RED_TEAM_TRAINING_BASE);
                    } else if (previousBlueTeamTrainingBase || previousRedTeamTrainingBase) {
                        moveTo(MacroObjectManager.X_POS_FAN_TRIBUNE, MacroObjectManager.Y_POS_FAN_TRIBUNE);
                    }
                }
            }
        }
    }


    protected void checkCollision(Location location) {
        if (((xPos >= location.xPos && xPos <= location.xPos + location.getWidth() ||
                xPos + width >= location.xPos && xPos <= location.xPos + location.getWidth()) &&
                (yPos >= location.yPos && yPos <= location.yPos + location.getHeight() ||
                        yPos + height >= location.yPos && yPos <= location.yPos + location.getHeight()))) {

            if (handler != null) {
               // handler.addNewMicroObject(this);
            }
            handler = location;

            if (tickExisted != 200) {
                tickExisted++;
            } else {
                complete = true;
                tickExisted = 0;
            }

            if (this instanceof Fan) {
                switch (location) {
                    case BlueTeamTrainingBase blueTeamTrainingBase -> {
                        if (previousCashRegister) {
                            tickExisted = 0;
                            complete = false;
                            isHolding = true;
                        }
                       // location.addNewMicroObject(this);
                        location.interact(this);
                        previousCashRegister = false;
                        previousFanTribune = false;
                        previousRedTeamTrainingBase = false;
                        previousBlueTeamTrainingBase = true;
                    }
                    case RedTeamTrainingBase redTeamTrainingBase -> {
                        if (previousCashRegister) {
                            tickExisted = 0;
                            complete = false;
                            isHolding = true;
                        }
                       // location.addNewMicroObject(this);
                        location.interact(this);
                        previousCashRegister = false;
                        previousFanTribune = false;
                        previousRedTeamTrainingBase = true;
                        previousBlueTeamTrainingBase = false;
                    }
                    case CashRegister cashRegister -> {
                        if (previousFanTribune) {
                            tickExisted = 0;
                            complete = false;
                            isHolding = true;
                        }

                      //  location.addNewMicroObject(this);
                        location.interact(this);
                        previousCashRegister = true;
                        previousFanTribune = false;
                        previousRedTeamTrainingBase = false;
                        previousBlueTeamTrainingBase = false;
                    }
                    case FanTribune fanTribune -> {
                        if (previousBlueTeamTrainingBase) {
                            tickExisted = 0;
                            complete = false;
                            isHolding = true;
                            previousCashRegister = false;
                            previousFanTribune = true;
                            previousRedTeamTrainingBase = false;
                            previousBlueTeamTrainingBase = false;
                          //  location.addNewMicroObject(this);
                            location.interact(this);


                        }
                        if (previousRedTeamTrainingBase) {
                            tickExisted = 0;
                            complete = false;
                            isHolding = true;
                         //   location.addNewMicroObject(this);
                            location.interact(this);
                            previousCashRegister = false;
                            previousFanTribune = true;
                            previousRedTeamTrainingBase = false;
                            previousBlueTeamTrainingBase = false;
                        }
                    }
                    default -> {
                    }
                }
            }
        } else {
            if (handler != null) {
               // handler.removeNewMicroObject(this);
                handler = null;
            }
        }
    }

    public void moveTo(int x, int y) {
        int currentX = (int) getXPos();
        int currentY = (int) getYPos();
        int dx = x - currentX;
        int dy = y - currentY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normDx = (distance > 10) ? dx / distance : 10;
        double normDy = (distance > 10) ? dy / distance : 10;
        int moveDistance = 2;
        int newX = (int) (currentX + moveDistance * normDx);
        int newY = (int) (currentY + moveDistance * normDy);
        setXPos(newX);
        setYPos(newY);
    }

    public void cheer() {
        this.setStamina(new int[]{this.getStamina()[0] - 1, 5});
        if (this.getStamina()[0] == 0) {
            for (int i = 0; i < 10; i++) {
                this.setStamina(new int[]{this.getStamina()[0] + 1, 5});
            }
        }
    }

    public void earnMoney() {
        this.setMoney(this.getMoney()+50);
    }

    public void setInteract(Location location) {
        location.interact(this);
    } // Статичний поліморфізм (Compile-time Polymorphism)
    public void setInteract() {
        this.setMoney(100000);
    }
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
        random = new Random();
        this.uniqueID = createID();
        //System.out.println("Constructor Fan(String name, double money, boolean hasTicket, float xPos, float yPos) was called. An object was created with parameters: " + toString());
    }

    public Fan() {
        this("Ivan", 50, false, false, 100, 100);
        //System.out.println("Constructor Fan() was called. An object was created with parameters: " + toString());
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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isHolding() {
        return isHolding;
    }

    public void setHolding(boolean holding) {
        isHolding = holding;
    }

    public Location getHandler() {
        return handler;
    }

    public void setHandler(Location handler) {
        this.handler = handler;
    }

    public boolean isPreviousBlueTeamTrainingBase() {
        return previousBlueTeamTrainingBase;
    }

    public boolean isPreviousRedTeamTrainingBase() {
        return previousRedTeamTrainingBase;
    }

    public void setPreviousBlueTeamTrainingBase(boolean previousBlueTeamTrainingBase) {
        this.previousBlueTeamTrainingBase = previousBlueTeamTrainingBase;
    }

    public void setPreviousRedTeamTrainingBase(boolean previousRedTeamTrainingBase) {
        this.previousRedTeamTrainingBase = previousRedTeamTrainingBase;
    }

    public boolean isPreviousFanTribune() {
        return previousFanTribune;
    }

    public void setPreviousFanTribune(boolean previousFanTribune) {
        this.previousFanTribune = previousFanTribune;
    }

    public void setPreviousCashRegister(boolean previousCashRegister) {
        this.previousCashRegister = previousCashRegister;
    }

    public int getRandomGoalX() {
        return randomGoalX;
    }

    public void setRandomGoalX(int randomGoalX) {
        this.randomGoalX = randomGoalX;
    }

    public void setRandomGoalY(int randomGoalY) {
        this.randomGoalY = randomGoalY;
    }

    public int getRandomGoalY() {
        return randomGoalY;
    }

    public int getTickExisted() {
        return tickExisted;
    }

    public void setTickExisted(int tickExisted) {
        this.tickExisted = tickExisted;
    }

    public Random getRandom() {
        return random;
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
        tmp.setXPos(this.xPos + 90);
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
