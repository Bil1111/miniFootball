package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class MacroObjectManager {
    private static MacroObjectManager instance;
    private static final ArrayList<Location> macroObjects = new ArrayList<>();

    public static ArrayList<Location> getMacroObjects() {
        return macroObjects;
    }
    private MacroObjectManager() {
        FootballPitch footballPitch = new FootballPitch("FootballPitch", 1350, 700);
        addMacroObject(footballPitch);
        BlueTeamTrainingBase blueTeamTrainingBase = new BlueTeamTrainingBase("BlueTeamTrainingBase", 2300, 900);
        addMacroObject(blueTeamTrainingBase);
        RedTeamTrainingBase redTeamTrainingBase = new RedTeamTrainingBase("RedTeamTrainingBase", 150, 900);
        addMacroObject(redTeamTrainingBase);
        FootballRefereeSchool footballRefereeSchool = new FootballRefereeSchool("FootballRefereeSchool", 1350, 1700);
        addMacroObject(footballRefereeSchool);
        CashRegister cashRegister = new CashRegister("CashRegister", 450, 130);
        addMacroObject(cashRegister);
        FanTribune fanTribune = new FanTribune("FanTribune", 1850, 130);
        addMacroObject(fanTribune);
    }

    public static synchronized MacroObjectManager getInstance() {
        if (instance == null) {
            instance = new MacroObjectManager();
        }
        return instance;
    }

    public void addMacroObject(Location macroObject) {
        macroObjects.add(macroObject);
    }

    public void drawAllMacroObjects(GraphicsContext gc) {
        for (Location macroObject : macroObjects) {
            macroObject.draw(gc);
        }
    }
}
