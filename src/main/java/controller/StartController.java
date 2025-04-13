package controller;

import view.MainView;
import view.StartView;

import javax.swing.*;

public class StartController {
    private MainView mainView;

    public StartController(MainView mainView) {
        this.mainView = mainView;
        initListeners();
    }

    private void initListeners() {
        StartView startView = mainView.getStartView();

        // New Game Listener
        startView.addNewGameListener(e -> mainView.startNewGame());

        // Load Game Listener
        startView.addLoadGameListener(e -> JOptionPane.showMessageDialog(
                mainView.getWindow(),
                "Load Game is not implemented yet.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // Quit Game Listener
        startView.addQuitListener(e -> System.exit(0));
    }
}
