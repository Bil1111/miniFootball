package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class Referee extends Footballer {
    private boolean previousFootballRefereeSchool;

    public boolean isPreviousFootballRefereeSchool() {
        return previousFootballRefereeSchool;
    }

    public void setPreviousFootballRefereeSchool(boolean previousFootballRefereeSchool) {
        this.previousFootballRefereeSchool = previousFootballRefereeSchool;
    }

    Referee(String name, double money, boolean hasTicket, boolean isBlueTeam, float xPos, float yPos) {
        super(name, money, hasTicket, isBlueTeam, xPos, yPos);
    }

    public Referee() {
        this("Ivan", 50, false, false, 100, 100);//null or ""
        //System.out.println("Constructor Fan() was called. An object was created with parameters: " + toString());
    }

    public void work() {
        if (this.getStamina()[0] > 0) {
            this.setStamina(new int[]{this.getStamina()[0] - 1, this.getStamina()[1]});
            this.setMoney(this.getMoney() + 1000);
        }
    }

    @Override
    public void recovery() {
        if (this.getStamina()[0] < 10) {
            this.setStamina(new int[]{this.getStamina()[0] + 2, 5});
            if (this.getStamina()[0] > 10) {
                this.setStamina(new int[]{10, 5});
            }
        }
    }

    @Override
    public void train() {
        if (this.getStamina()[0] < 10) {
            this.setMoney(this.getMoney() - 500);
            this.setStamina(new int[]{10, 5});
        }
    }

    //Динамічний поліморфізм (Run-time Polymorphism)
    @Override
    public void earnMoney() {
        this.setMoney(this.getMoney() + 10);
    }

    @Override
    protected void draw(GraphicsContext gc) {
        float x = super.getXPos();
        float y = super.getYPos();
        double radius = 25;

        gc.setFill(Color.YELLOW);
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

    @Override
    protected void loadImage() {
        if (super.getImageIcon() == null) {
            super.setImageIcon(new ImageView(Objects.requireNonNull(getClass().getResource("/com/example/kr/Referee.png")).toExternalForm()));
            super.setHeight((int) super.getImageIcon().getImage().getHeight());
            super.setWidth((int) super.getImageIcon().getImage().getWidth());
        }
    }

    @Override
    public void walk(Location object) {
        checkCollision(object);
        if (!isActive()) {
            if (isComplete()) {
                if (getRandomGoalX() == -1 && getRandomGoalY() == -1) {
                    setRandomGoalX(getRandom().nextInt(100, 1200));
                    setRandomGoalY(getRandom().nextInt(100, 1200));
                } else {
                    if (Math.abs(getXPos() - getRandomGoalX()) < 30 && Math.abs(getYPos() - getRandomGoalY()) < 30) {
                        setRandomGoalX(-1);
                        setRandomGoalY(-1);
                        setComplete(false);
                        setHolding(false);
                    } else {
                        moveTo(getRandomGoalX(), getRandomGoalY());
                    }
                }
            } else {
                if (!isHolding()) {
                    if (!previousFootballRefereeSchool && (isPreviousBlueTeamTrainingBase() || isPreviousRedTeamTrainingBase())) {
                        moveTo(MacroObjectManager.X_POS_FOOTBALL_PITCH, MacroObjectManager.Y_POS_FOOTBALL_PITCH);
                    } else if (isPreviousFootballPitch()) {
                        moveTo(MacroObjectManager.X_POS_FOOTBALL_REFEREE_SCHOOL, MacroObjectManager.Y_POS_FOOTBALL_REFEREE_SCHOOL);
                    } else if (isPreviousFootballRefereeSchool() && this.isBlueTeam()) {
                        moveTo(MacroObjectManager.X_POS_BLUE_TEAM_TRAINING_BASE, MacroObjectManager.Y_POS_BLUE_TEAM_TRAINING_BASE);
                    } else if (isPreviousFootballRefereeSchool() && !this.isBlueTeam()) {
                        moveTo(MacroObjectManager.X_POS_RED_TEAM_TRAINING_BASE, MacroObjectManager.Y_POS_RED_TEAM_TRAINING_BASE);
                    }
                }
            }
        }
    }

    @Override
    protected void checkCollision(Location location) {
        if (((getXPos() >= location.xPos && getXPos() <= location.xPos + location.getWidth() ||
                getXPos() + getWidth() >= location.xPos && getXPos() <= location.xPos + location.getWidth()) &&
                (getYPos() >= location.yPos && getYPos() <= location.yPos + location.getHeight() ||
                        getYPos() + getHeight() >= location.yPos && getYPos() <= location.yPos + location.getHeight()))) {

            if (getHandler() != null) {
                //  getHandler().addNewMicroObject(this);
            }
            setHandler(location);

            if (getTickExisted() != 200) {
                setTickExisted(getTickExisted() + 1);
            } else {
                setComplete(true);
                setTickExisted(0);
            }

            if (this instanceof Footballer) {
                switch (location) {
                    case FootballRefereeSchool footballRefereeSchool -> {
                        if (isPreviousFootballPitch()) {
                            setTickExisted(0);
                            setComplete(false);
                            setHolding(true);
                            //location.addNewMicroObject(this);
                            location.interact(this);
                            setPreviousBlueTeamTrainingBase(false);
                            setPreviousRedTeamTrainingBase(false);
                            setPreviousFootballPitch(false);
                            setPreviousFootballRefereeSchool(true);
                        }
                    }
                    case FootballPitch footballPitch -> {
                        if (isPreviousBlueTeamTrainingBase()) {
                            setTickExisted(0);
                            setComplete(false);
                            setHolding(true);
                            //location.addNewMicroObject(this);
                            location.interact(this);
                            setPreviousBlueTeamTrainingBase(false);
                            setPreviousRedTeamTrainingBase(false);
                            setPreviousFootballPitch(true);
                            setPreviousFootballRefereeSchool(false);
                        }
                        if (isPreviousRedTeamTrainingBase()) {
                            setTickExisted(0);
                            setComplete(false);
                            setHolding(true);
                            //location.addNewMicroObject(this);
                            location.interact(this);
                            setPreviousBlueTeamTrainingBase(false);
                            setPreviousRedTeamTrainingBase(false);
                            setPreviousFootballPitch(true);
                            setPreviousFootballRefereeSchool(false);
                        }
                    }
                    case BlueTeamTrainingBase blueTeamTrainingBase -> {
                        if (isPreviousFootballRefereeSchool()) {
                            setTickExisted(0);
                            setComplete(false);
                            setHolding(true);
                        }
                        //location.addNewMicroObject(this);
                        location.interact(this);
                        setPreviousBlueTeamTrainingBase(true);
                        setPreviousRedTeamTrainingBase(false);
                        setPreviousFootballPitch(false);
                        setPreviousFootballRefereeSchool(false);
                    }
                    case RedTeamTrainingBase redTeamTrainingBase -> {
                        if (isPreviousFootballRefereeSchool()) {
                            setTickExisted(0);
                            setComplete(false);
                            setHolding(true);
                        }
                        //location.addNewMicroObject(this);
                        location.interact(this);
                        setPreviousBlueTeamTrainingBase(false);
                        setPreviousRedTeamTrainingBase(true);
                        setPreviousFootballPitch(false);
                        setPreviousFootballRefereeSchool(false);
                    }
                    default -> {
                    }
                }
            }
        } else {
            if (getHandler() != null) {
                //getHandler().removeNewMicroObject(this);
                setHandler(null);
            }
        }
    }
}
