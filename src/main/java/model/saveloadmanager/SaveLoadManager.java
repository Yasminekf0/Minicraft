package model.saveloadmanager;

import java.io.*;


public class SaveLoadManager {
    public static void saveGame(GameState state, String filePath) throws IOException {
        // Ensure the directory exists
        File file = new File(filePath);
        Boolean _ = file.getParentFile().mkdirs();

        //Save current gamestate

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(state);  // serialize the GameState object to file
        }
    }

    public static GameState loadGame(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameState) in.readObject();  // deserialize the GameState
        }
    }
}