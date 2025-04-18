package controller;

import view.OptionsView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OptionsController {
    private OptionsView optionsView;
    private GameController gameController;

    public OptionsController(OptionsView optionsView, GameController gameController) {
        this.optionsView = optionsView;
        this.gameController = gameController;

        initListeners();
        //initKeyListener();
    }

    private void initListeners() {
        // "Back to Game": hide the overlay and resume the game.
        optionsView.addBackListener(e -> {
            optionsView.setVisible(false);
            gameController.resumeGame();
        });

        // "Save Game": show a message (saving not implemented).
        optionsView.addSaveListener(e -> {
            JOptionPane.showMessageDialog(
            null,
            "Save Game is not implemented yet.",
            "Information",
            JOptionPane.INFORMATION_MESSAGE
        );
        optionsView.requestFocusInWindow();
        });

        // "Quit Game": exit the application.
        optionsView.addQuitListener(e -> System.exit(0));
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
