package view.game.core;

import view.game.elements.*;

import javax.swing.*;
import java.awt.*;

import static view.settings.ScreenSettings.*;

public class GameView extends JPanel {
    private final PlayerView playerView;
    private final NPCView npcView;
    private final EnemyView enemyView; //***************************************
    private final WorldView worldView;
    private final NightFilterView nightFilterView;

    public GameView() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        playerView = new PlayerView();
        npcView = new NPCView();
        enemyView = new EnemyView(); //**************************************
        worldView = new WorldView();
        nightFilterView = new NightFilterView();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        worldView.draw((Graphics2D) g);
        npcView.draw((Graphics2D) g);
        playerView.draw((Graphics2D) g);
        enemyView.draw((Graphics2D) g); //****************************************
        nightFilterView.draw((Graphics2D) g);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public NPCView getNpcView() {return npcView;}
    public EnemyView getEnemyView() {return enemyView;}
    public NightFilterView getNightFilterView() {
        return nightFilterView;
    }

}
