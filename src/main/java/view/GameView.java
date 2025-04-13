package view;

import model.entity.Player;
import model.world.Tile;
import model.world.World;
import view.hud.OptionsMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static view.ScreenSettings.*;

public class GameView extends JPanel {
    private final PlayerView playerView;
    private final WorldView worldView;
    private final OptionsMenuView optionsMenuView;

    public GameView(World world, Player player) {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        // Create the PlayerView to handle the player's sprites
        playerView = new PlayerView();
        worldView = new WorldView(world, player);
        optionsMenuView = new OptionsMenuView(screenWidth, screenHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        worldView.draw((Graphics2D) g);
        playerView.draw((Graphics2D) g);
        optionsMenuView.draw((Graphics2D) g);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public OptionsMenuView getOptionsMenuView() {
        return optionsMenuView;
    }


}
