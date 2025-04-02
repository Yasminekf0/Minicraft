import controller.GameController;
import controller.KeyController;
import model.entity.Player;
import model.world.WorldGenerator;
import view.GameView;
import view.StartView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Minicraft");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Create the world
        int worldSize = 1000;
        int seed = 8845;
        WorldGenerator world = new WorldGenerator(worldSize, seed);

        Player player = new Player();

        // Create the views
        GameView gameView = new GameView(world, player);
        StartView startView = new StartView();

        // Use CardLayout to switch between start screen and game
        JPanel container = new JPanel(new CardLayout());
        container.add(startView, "start");
        container.add(gameView, "game");

        window.setContentPane(container);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // When user clicks "New Game," switch to game and start the controller
        startView.addNewGameListener(e -> {
            CardLayout cl = (CardLayout) container.getLayout();
            cl.show(container, "game");
            KeyController keyController = new KeyController();
            new GameController(world, player, gameView, keyController);
        });
    }
}
