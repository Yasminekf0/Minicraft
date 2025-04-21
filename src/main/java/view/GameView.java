package view;

import model.entity.Player;
import model.world.World;

import javax.swing.*;
import java.awt.*;

import static view.ScreenSettings.*;

public class GameView extends JPanel {
    private final PlayerView playerView;
    private final NPCView npcView;
    private final WorldView worldView;
    private final NightFilterView nightFilterView;

    public GameView() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        // Create the PlayerView to handle the player's sprites
        playerView = new PlayerView();
        npcView = new NPCView();
        worldView = new WorldView();
        nightFilterView = new NightFilterView();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        worldView.draw((Graphics2D) g);
        npcView.draw((Graphics2D) g);
        playerView.draw((Graphics2D) g);
        nightFilterView.draw((Graphics2D) g);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public NPCView getNpcView() {return npcView;}
    public NightFilterView getNightFilterView() {
        return nightFilterView;
    }

}
