package controller;

import view.OptionsView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.saveloadmanager.SaveLoadManager;
import model.saveloadmanager.GameState;
import view.SoundManager;

import java.io.*;

public class OptionsController {
    private final OptionsView optionsView;
    private final GameController gameController;
    private final SoundManager soundManager = SoundManager.getInstance();


    public OptionsController(OptionsView optionsView, GameController gameController) {
        this.optionsView = optionsView;
        this.gameController = gameController;

        initListeners();
        //initKeyListener();
    }

    private void initListeners() {
        // "Back to Game": hide the overlay and resume the game.
        optionsView.addBackListener(e -> {
            soundManager.playSound("button");
            optionsView.setVisible(false);
            gameController.resumeGame();
        });

        // "Save Game": show a message (saving not implemented).
        optionsView.addSaveListener(e -> {
            soundManager.playSound("button");
            try {
                GameState state = new GameState();
                SaveLoadManager.saveGame(state, "saves/save1.dat"); // possiblity for multiple saves
                JOptionPane.showMessageDialog(
                        null,
                        "Game saved successfully!",
                        "Save Game",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to save the game.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            optionsView.requestFocusInWindow();
        });

        // "Quit Game": exit the application.
        optionsView.addQuitListener(e -> {
            soundManager.playSound("button");
            System.exit(0);
        });
    }

    /*private void initKeyListener() {
        optionsView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    optionsView.setVisible(false);
                    gameController.resumeGame();
                }
            }
        });
        optionsView.setFocusable(true);
    }*/
}
