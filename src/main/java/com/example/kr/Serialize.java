package com.example.kr;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Serialize {

    public static void saveGame(ArrayList<Location> macroObjects, ArrayList<Fan> fans, String locationFilename, String fansFolder) {
        saveMacroObjects(macroObjects, locationFilename);
        saveFans(fans, fansFolder);
    }

    public static void saveMacroObjects(ArrayList<Location> macroObjects, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(macroObjects);
            System.out.println("MacroObjects data is saved in " + filename);
        } catch (IOException ignored) {

        }
    }

    public static void saveFans(ArrayList<Fan> fans, String fansFolder) {
        for (Fan fan : fans) {
            String filename = fansFolder + File.separator + fan.getName() + new Random().nextInt(1,100000) + ".ser";
            saveObject(fan, filename);
        }
    }

    public static ArrayList<Location> loadGame(String locationFilename, String fansFolder) {
        ArrayList<Location> macroObjects = loadMacroObjects(locationFilename);
        if (macroObjects != null) {
            System.out.println("MacroObjects data is loaded from " + locationFilename);
            MicroObjectManager.getInstance().clearAllMicroObjects();
            ArrayList<Fan> fans = new ArrayList<>();
            File fansFolderFile = new File(fansFolder);
            if (fansFolderFile.exists() && fansFolderFile.isDirectory()) {
                for (File file : Objects.requireNonNull(fansFolderFile.listFiles())) {
                    if (file.isFile() && file.getName().endsWith(".ser")) {
                        Fan loadedFan = (Fan) loadObject(file);
                        if (loadedFan != null) {
                            fans.add(loadedFan);
                        }
                    }
                }
            }

            return macroObjects;
        } else {
            System.out.println("Failed to load MacroObjects from the selected file.");
            return null;
        }
    }

    public static ArrayList<Location> loadMacroObjects(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (ArrayList<Location>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static Object loadObject(File file) {
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Object object = in.readObject();
            System.out.println("Object data is loaded from " + file.getName());
            return object;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void saveObject(Object object, String filename) {
        File fansFolder = new File("fans");
        if (!fansFolder.exists()) {
            if (!fansFolder.mkdirs()) {
                System.err.println("Failed to create fans folder!");
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            System.out.println("Object data is saved in " + filename);
        } catch (IOException ignored) {

        }
    }
    public static void saveFan(Fan fan, String filename) {
        saveObject(fan, filename);
    }
}