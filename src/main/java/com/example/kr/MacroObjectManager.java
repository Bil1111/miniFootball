package com.example.kr;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class MacroObjectManager {
    private static MacroObjectManager instance;
    private final ArrayList<Location> macroObjects;

    public ArrayList<Location> getMacroObjects() {
        return macroObjects;
    }
    private MacroObjectManager() {
        macroObjects = new ArrayList<>();
//      фффLocation location=new Location("",100,100);
        FootballPitch footballPitch = new FootballPitch("FootballPitch", 450, 300);
        addMacroObject(footballPitch);
        BlueTeamTrainingBase blueTeamTrainingBase = new BlueTeamTrainingBase("BlueTeamTrainingBase", 700, 300);
        addMacroObject(blueTeamTrainingBase);
        RedTeamTrainingBase redTeamTrainingBase = new RedTeamTrainingBase("RedTeamTrainingBase", 150, 300);
        addMacroObject(redTeamTrainingBase);
        FootballRefereeSchool footballRefereeSchool = new FootballRefereeSchool("FootballRefereeSchool", 450, 600);
        addMacroObject(footballRefereeSchool);
        CashRegister cashRegister = new CashRegister("CashRegister", 150, 50);
        addMacroObject(cashRegister);
        FanTribune fanTribune = new FanTribune("FanTribune", 450, 30);
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
