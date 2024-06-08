package com.example.kr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class MicroObjectManager {

    private static MicroObjectManager instance;
    private final ArrayList<Fan> fans;


    public static synchronized MicroObjectManager getInstance() {
        if (instance == null) {
            instance = new MicroObjectManager();
        }
        return instance;
    }

    private MicroObjectManager() {
        fans = new ArrayList<>();
    }

    public synchronized ArrayList<Fan> getArray() {
        return fans;
    }

    public ArrayList<Fan> getFans() {
        return getArray();
    }


    private void setupTextInput(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\d.*")) {
                textField.setText(oldValue);
            }
        });
    }

    private void setupNumericInput(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    public void createMicroObjectDialog() {
        Dialog<Object> dialog = new Dialog<>();
        dialog.setTitle("Create MicroObject");
        dialog.setHeaderText("Enter MicroObject details:");

        TextField nameField = new TextField();
        TextField moneyField = new TextField();
        CheckBox isHasTicketCheckBox = new CheckBox("Has ticket?");
        CheckBox isBlueTeamCheckBox = new CheckBox("Blue team?");
        TextField xPosField = new TextField();
        TextField yPosField = new TextField();
        RadioButton radioButton1 = new RadioButton("Fan");
        RadioButton radioButton2 = new RadioButton("Player");
        RadioButton radioButton3 = new RadioButton("Referee");
        setupTextInput(nameField);
        setupNumericInput(moneyField);
        setupNumericInput(xPosField);
        setupNumericInput(yPosField);


        nameField.setPromptText("Name");
        moneyField.setPromptText("Money");
        xPosField.setPromptText("X Position");
        yPosField.setPromptText("Y Position");

        GridPane grid = new GridPane();
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Money:"), 0, 1);
        grid.add(moneyField, 1, 1);
        grid.add(isHasTicketCheckBox, 0, 3);
        grid.add(isBlueTeamCheckBox, 0, 4);
        grid.add(radioButton1, 0, 5);
        grid.add(radioButton2, 1, 5);
        grid.add(radioButton3, 2, 5);
        grid.add(new Label("X Position:"), 0, 6);
        grid.add(xPosField, 1, 6);
        grid.add(new Label("Y Position:"), 0, 7);
        grid.add(yPosField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    String name = nameField.getText();
                    double money = moneyField.getText().isEmpty() ? 0 : Double.parseDouble(moneyField.getText());
                    boolean isHasTicket = isHasTicketCheckBox.isSelected();
                    boolean isBlueTeam = isBlueTeamCheckBox.isSelected();
                    float xPos = xPosField.getText().isEmpty() ? 0 : Float.parseFloat(xPosField.getText());
                    float yPos = yPosField.getText().isEmpty() ? 0 : Float.parseFloat(yPosField.getText());
                    if (radioButton1.isSelected()) {
                        Fan fan = new Fan(name, money, isHasTicket, isBlueTeam, xPos, yPos);
                        fans.add(fan);
                        return fan;
                    } else if (radioButton2.isSelected()) {
                        Footballer player = new Footballer(name, money, isHasTicket, isBlueTeam, xPos, yPos);
                        fans.add(player);
                        return player;
                    } else if (radioButton3.isSelected()) {
                        Referee referee = new Referee(name, money, isHasTicket, isBlueTeam, xPos, yPos);
                        fans.add(referee);
                        return referee;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Default preset");
                }
            }

            if (radioButton1.isSelected()) {
                Fan fan = new Fan();
                fans.add(fan);
                return fan;
            }
            if (radioButton2.isSelected()) {
                Footballer player = new Footballer();
                fans.add(player);
                return player;
            }
            if (radioButton3.isSelected()) {
                Referee referee = new Referee();
                fans.add(referee);
                return referee;
            }
            return new Fan();
        });

        Optional<Object> result = dialog.showAndWait();

        result.ifPresent(microObject -> System.out.println("Created"));
    }

    public void requests() {
        Dialog<Object> dialog = new Dialog<>();
        dialog.setTitle("Create requests");
        dialog.setHeaderText("Enter parameters of the request:");

        TextField nameField = new TextField();
        TextField moneyField = new TextField();
        nameField.setPromptText("Name");
        moneyField.setPromptText("Money");
        CheckBox isHasTicketCheckBox = new CheckBox("Has ticket?");
        CheckBox isBlueTeamCheckBox = new CheckBox("Blue team?");
        RadioButton radioButton1 = new RadioButton("FootballPitch");
        RadioButton radioButton2 = new RadioButton("BlueTeamTrainingBase");
        RadioButton radioButton3 = new RadioButton("RedTeamTrainingBase");
        RadioButton radioButton4 = new RadioButton("FootballRefereeSchool");
        RadioButton radioButton5 = new RadioButton("CashRegister");
        RadioButton radioButton6 = new RadioButton("FanTribune");
        RadioButton radioButton7 = new RadioButton("DoNotBelongToAnyMacroObject");
        RadioButton radioButton8 = new RadioButton("SortByName");
        RadioButton radioButton9 = new RadioButton("SortByMoney");
        RadioButton radioButton10 = new RadioButton("SortByStamina");
        RadioButton radioButton11 = new RadioButton("NumberOfActiveMicroObjects");
        RadioButton radioButton12 = new RadioButton("NumberOfMicroObjectsStamina>50%");
        RadioButton radioButton13 = new RadioButton("NumberOfMicroObjectsMoney>10000");

        GridPane grid = new GridPane();
        grid.add(new Label("Request1:"), 0, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Money:"), 0, 2);
        grid.add(moneyField, 1, 2);
        grid.add(isHasTicketCheckBox, 0, 3);
        grid.add(isBlueTeamCheckBox, 0, 4);
        grid.add(new Label("Request2:"), 0, 5);
        grid.add(radioButton1, 0, 6);
        grid.add(radioButton2, 1, 6);
        grid.add(radioButton3, 2, 6);
        grid.add(radioButton4, 3, 6);
        grid.add(radioButton5, 4, 6);
        grid.add(radioButton6, 5, 6);
        grid.add(radioButton7, 6, 6);
        grid.add(new Label("Request3:"), 0, 7);
        grid.add(radioButton8, 0, 8);
        grid.add(radioButton9, 1, 8);
        grid.add(radioButton10, 2, 8);
        grid.add(new Label("Request4:"), 0, 9);
        grid.add(radioButton11, 0, 10);
        grid.add(radioButton12, 1, 10);
        grid.add(radioButton13, 2, 10);

        dialog.getDialogPane().setContent(grid);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    //РЕАЛІЗУЙ ПОШУК
                    String name = nameField.getText();
                    double money = moneyField.getText().isEmpty() ? 0 : Double.parseDouble(moneyField.getText());
                    boolean isHasTicket = isHasTicketCheckBox.isSelected();
                    boolean isBlueTeam = isBlueTeamCheckBox.isSelected();

                    // Search and filter fans based on input criteria
                    ArrayList<Fan> filteredFans = new ArrayList<>();
                    for (Fan fan : fans) {
                        boolean matches = true;
                        if (!name.isEmpty() && !fan.getName().equalsIgnoreCase(name)) {
                            matches = false;
                        }
                        if (!moneyField.getText().isEmpty() && fan.getMoney() != money) {
                            matches = false;
                        }
                        if (isHasTicket != fan.isHasTicket()) {
                            matches = false;
                        }
                        if (isBlueTeam != fan.isBlueTeam()) {
                            matches = false;
                        }
                        if (matches) {
                            filteredFans.add(fan);
                        }
                    }

                    // Print filtered fans
                    System.out.println("Filtered Fans:");
                    for (Fan fan : filteredFans) {
                        System.out.println("MicroObject found:");
                        System.out.println("Location: " + fan.getXPos() + ", " + fan.getYPos());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Default preset");
                }
            }
//ВИЗНАЧ ПРИНАЛЕЖНІСТЬ
            if (radioButton1.isSelected()) {
                System.out.println("Micro objects in FootballPitch: ");
                FootballPitch footballPitch = new FootballPitch("", 0, 0);
                System.out.println(footballPitch.getMicroObjects());
            }
            if (radioButton2.isSelected()) {
                System.out.println("Micro objects in BlueTeamTrainingBase: ");
                BlueTeamTrainingBase blueTeamTrainingBase = new BlueTeamTrainingBase("", 0, 0);
                System.out.println(blueTeamTrainingBase.getMicroObjects());
            }
            if (radioButton3.isSelected()) {
                System.out.println("Micro objects in RedTeamTrainingBase: ");
                RedTeamTrainingBase redTeamTrainingBase = new RedTeamTrainingBase("", 0, 0);
                System.out.println(redTeamTrainingBase.getMicroObjects());
            }
            if (radioButton4.isSelected()) {
                System.out.println("Micro objects in FootballRefereeSchool: ");
                FootballRefereeSchool footballRefereeSchool = new FootballRefereeSchool("", 0, 0);
                System.out.println(footballRefereeSchool.getMicroObjects());
            }
            if (radioButton5.isSelected()) {
                System.out.println("Micro objects in CashRegister: ");
                CashRegister cashRegister = new CashRegister("", 0, 0);
                System.out.println(cashRegister.getMicroObjects());
            }
            if (radioButton6.isSelected()) {
                System.out.println("Micro objects in FanTribune: ");
                FanTribune fanTribune = new FanTribune("", 0, 0);
                System.out.println(fanTribune.getMicroObjects());
            }
            //ПРОРОБИ
//            if (radioButton7.isSelected()) {
//                System.out.println("Micro objects that DoNotBelongToAnyMacroObject: ");
//                FootballPitch footballPitch=new FootballPitch("", 0, 0);
//                System.out.println(footballPitch.getMicroObjects());
//            }
            if (radioButton8.isSelected()) {
                System.out.println("Sort by name: ");
                MicroObjectManager.getInstance().getFans().sort(new Comparator<Fan>() {
                    @Override
                    public int compare(Fan o1, Fan o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    System.out.println(fan);
                }
            }
            if (radioButton9.isSelected()) {
                System.out.println("Sort by money: ");
                MicroObjectManager.getInstance().getFans().sort(new Comparator<Fan>() {
                    @Override
                    public int compare(Fan o1, Fan o2) {
                        if (o1.getMoney() > o2.getMoney()) {
                            return 1;
                        } else if (o1.getMoney() < o2.getMoney()) {
                            return -1;
                        } else {
                            return 0;
                        }

                    }
                });
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    System.out.println(fan);
                }
            }
            if (radioButton10.isSelected()) {
                System.out.println("Sort by stamina: ");
                MicroObjectManager.getInstance().getFans().sort(new Fan.FanComparator());
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    System.out.println(fan);
                }
            }
            if (radioButton11.isSelected()) {
                System.out.print("NumberOfActiveMicroObjects: ");
                int count = 0;
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    if (fan.isActive()) {
                        count++;
                    }
                }
                System.out.println(count);
            }
            if (radioButton12.isSelected()) {
                System.out.println("NumberOfMicroObjectsStamina>50%: ");
                int count = 0;
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    if (fan.getStamina()[0] > 5) {
                        count++;
                    }
                }
                System.out.println(count);
            }
            if (radioButton13.isSelected()) {
                System.out.println("NumberOfMicroObjectsMoney>10000: ");
                int count = 0;
                for (Fan fan : MicroObjectManager.getInstance().getFans()) {
                    if (fan.getMoney() > 10000) {
                        count++;
                    }
                }
                System.out.println(count);
            }
            return new Fan();
        });

        Optional<Object> result = dialog.showAndWait();

        result.ifPresent(microObject -> System.out.println("Created"));
    }

    public void deactivateAllMicroObjects() {
        for (Fan fan : fans) {
            if (fan.isActive()) {
                fan.setActive();
            }
        }
        for (Fan player : fans) {
            if (player.isActive()) {
                player.setActive();
            }
        }
        for (Fan referee : fans) {
            if (referee.isActive()) {
                referee.setActive();
            }
        }
    }

    public void removeActiveMicroObjects() {
        fans.removeIf(Fan::isActive);
    }

    public void addNewMicroObject(Object o) {
        if (o instanceof Fan) {
            getArray().add((Fan) o);
        }
    }

    public void clearAllMicroObjects() {
        getArray().clear();
    }

    public void moveActiveMicroObjects(KeyCode keyCode) {
        for (Fan fan : fans) {
            if (fan.isActive()) {
                handleButton(fan, keyCode);
            }
        }
    }

    private void handleButton(Object object, KeyCode keyCode) {
        if (object instanceof Fan) {
            switch (keyCode) {
                case W: {
                    ((Fan) object).setYPos(((Fan) object).getYPos() - 5);
                    break;
                }
                case A: {
                    ((Fan) object).setXPos(((Fan) object).getXPos() - 5);
                    break;
                }
                case S: {
                    ((Fan) object).setYPos(((Fan) object).getYPos() + 5);
                    break;
                }
                case D: {
                    ((Fan) object).setXPos(((Fan) object).getXPos() + 5);
                    break;
                }
            }

        }
    }

    public void activateMicroObject(double clickX, double clickY) {
        for (Fan fan : fans) {
            if (clickX >= fan.getXPos() && clickX <= fan.getXPos() + fan.getWidth() &&
                    clickY >= fan.getYPos() && clickY <= fan.getYPos() + fan.getHeight()) {
                fan.setActive();
            }
        }
    }


    public void drawAllMicroObjects(GraphicsContext gc) {
        for (Fan fan : fans) {
            fan.draw(gc);
        }
    }

    public void method() {
        //Взаємодія по клавіші
        for (int i = 0; i < fans.size(); i++) {
            Fan fan = fans.get(i);
            for (Location location : MacroObjectManager.getInstance().getMacroObjects()) {
                if ((fan.getXPos() >= location.getxPos() && fan.getXPos() <= location.getxPos() + location.getWidth() ||
                        fan.getXPos() + fan.getWidth() >= location.getxPos() && fan.getXPos() <= location.getxPos() + location.getWidth()) &&
                        (fan.getYPos() >= location.getyPos() && fan.getYPos() <= location.getyPos() + location.getHeight() ||
                                fan.getYPos() + fan.getHeight() >= location.getyPos() && fan.getYPos() <= location.getyPos() + location.getHeight())
                ) {
                    Fan updatedFan = location.interact(fan);
//                    location.microObjects.add(fan);
//                    location.microObjectsNames.add(fan.getName());
                    if (updatedFan != fan) {
                        fans.set(i, updatedFan);
                    }
                }
            }
        }
    }

    public void updateAllMicroObjects() {
        //Взаємодія постійно
//        for (int i = 0; i < fans.size(); i++) {
//            Fan fan = fans.get(i);
//            for (Location location : MacroObjectManager.getInstance().getMacroObjects()) {
//                if ((fan.getXPos() >= location.getxPos() && fan.getXPos() <= location.getxPos() + location.getWidth() ||
//                        fan.getXPos() + fan.getWidth() >= location.getxPos() && fan.getXPos() <= location.getxPos() + location.getWidth()) &&
//                        (fan.getYPos() >= location.getyPos() && fan.getYPos() <= location.getyPos() + location.getHeight() ||
//                                fan.getYPos() + fan.getHeight() >= location.getyPos() && fan.getYPos() <= location.getyPos() + location.getHeight())
//                ) {
//                    Fan updatedFan = location.interact(fan);
//                    location.microObjects.add(fan);
//                    location.microObjectsNames.add(fan.getName());
//                    if (updatedFan != fan) {
//                        fans.set(i, updatedFan);
//                    }
//                }
//            }
//        }

    }

    public void changeActiveParam() {
        int counter = 0;
        for (Fan fan : fans) {
            if (fan.isActive()) {
                counter++;
                if (counter < 2) {
                    System.out.println(fan);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("changeParam.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);

                        ParamController controller = loader.getController();

                        controller.setFan(fan);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Change Fan Details");
                        stage.show();
                        controller.setStage(stage);
                    } catch (IOException e) {
                        System.out.println("Error loading changeParam.fxml");
                    }
                }
            } else {
                System.out.println("chose only 1 object!!!");
            }
        }
    }

    public void printActiveMicroObjects() {
        System.out.println("Active MicroObjects: ");
        for (Fan fan : fans) {
            if (fan.isActive()) {
                System.out.println(fan);
            }
        }
    }
}
