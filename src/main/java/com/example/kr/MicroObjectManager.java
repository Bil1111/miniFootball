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
        moneyField.setPromptText("Money (optional)");
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

    public void updateAllMicroObjects() {
        for(Fan fan : fans){
            for (Location location: MacroObjectManager.getInstance().getMacroObjects()){
                if(fan.getXPos()==location.getxPos()&&fan.getYPos()==location.getyPos()){
                    fan.interact(location);
                }
            }
        }
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
