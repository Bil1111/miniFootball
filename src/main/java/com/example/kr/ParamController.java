package com.example.kr;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ParamController {

    public Button okButton;
    public Button cancelButton;
    private Stage stage;

    private Fan fan;
    @FXML
    private ImageView imageView;

    @FXML
    private TextField moneyInput;

    @FXML
    private TextField nameInput;


    @FXML
    private TextField xPosInput;

    @FXML
    private TextField yPosInput;
    @FXML
    private CheckBox isHasTicketInput;
    @FXML
    private CheckBox isBlueTeamInput;

    @FXML
    void initialize() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeStage() {
        stage.close();
    }

    public void setFan(Fan fan) {
        this.fan = fan;
        nameInput.setText(fan.getName());
        moneyInput.setText(String.valueOf(fan.getMoney()));
        isHasTicketInput.setSelected(fan.isHasTicket());
        isBlueTeamInput.setSelected(fan.isBlueTeam());
        xPosInput.setText(String.valueOf(fan.getXPos()));
        yPosInput.setText(String.valueOf(fan.getYPos()));
    }

    public ParamController() {
    }


    public void okButtonClicked() {
        for (Fan fan : MicroObjectManager.getInstance().getFans()) {
            if (fan.isActive()) {
               if (fan instanceof Footballer) {
                    try {
                        Footballer footballer = (Footballer) fan;
                        footballer.setXPos(Float.parseFloat(xPosInput.getText()));
                        footballer.setYPos(Float.parseFloat(yPosInput.getText()));
                        footballer.setMoney(Double.parseDouble(moneyInput.getText()));
                        footballer.setName(nameInput.getText());
                        footballer.setHasTicket(isHasTicketInput.isSelected());
                        footballer.setBlueTeam(isBlueTeamInput.isSelected());
                        footballer.setImageView(null);
                        footballer.loadImage();
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid input");
                        alert.setContentText("Please enter a valid number");
                        alert.showAndWait();
                    }
                } else if (fan instanceof Fan) {
                    try {
                        fan.setXPos(Float.parseFloat(xPosInput.getText()));
                        fan.setYPos(Float.parseFloat(yPosInput.getText()));
                        fan.setMoney(Double.parseDouble(moneyInput.getText()));
                        fan.setName(nameInput.getText());
                        fan.setHasTicket(isHasTicketInput.isSelected());
                        fan.setBlueTeam(isBlueTeamInput.isSelected());
                        fan.setImageView(null);
                        fan.loadImage();
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid input");
                        alert.setContentText("Please enter a valid number");
                        alert.showAndWait();
                    }
                }
            }
        }
        closeStage();
    }

    public void cancel() {
        closeStage();
    }
}
