package controller;

import model.entity.npcs.Mob;
import model.world.DayCycleManager;
import model.entity.Player;
import view.HUD.HUDView;
import view.audio.SoundManager;
import view.game.elements.EnemyView;
import view.game.core.GameView;
import view.game.elements.MobView;
import view.game.elements.NPCView;
import view.menus.DeathView;
import view.menus.OptionsView;

import javax.swing.*;

public class GameController {

    private final GameView gameView;

    private final DayCycleManager dayCycleManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FPS = 60;
    private boolean gamePaused = false;
    private OptionsView optionsView;
    private DeathView deathView;
    private final HUDView hudView;
    private final NPCController npcController;
    private final EnemyController enemyController;

    public GameController(GameView gameView, HUDView hudView, MobView mobView) {
        this.gameView = gameView;
        this.hudView = hudView;

        this.dayCycleManager = DayCycleManager.getInstance();
        this.npcController = new NPCController(mobView);
        this.enemyController = new EnemyController(mobView);



        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

    // Called from MainView after instantiating OptionsView.
    public void setOptionsView(OptionsView optionsView) {
        this.optionsView = optionsView;
    }

    public void setDeathView(DeathView deathView) { this.deathView = deathView; }

    private void startGameLoop() {

        int delay = 1000 / FPS; // ms per frame
        Timer timer = new Timer(delay, _ -> {

            if (!gamePaused) {
                dayCycleManager.tick();
                npcController.tick();
                enemyController.tick();
                gameView.repaint();

                if (Player.getInstance().getHealth() <= 0) {
                    gamePaused = true;
                    deathView.setVisible(true);
                    deathView.requestFocusInWindow();
                    SoundManager.getInstance().playSound("dead");
                }
            }
        });
        timer.start();
    }

    GameView getGameView(){
        return gameView;
    }

    void pauseGame() {
        gamePaused = true;
        optionsView.setVisible(true);
        optionsView.requestFocusInWindow();
    }

    void resumeGame() {
        gamePaused = false;
        optionsView.setVisible(false);
        gameView.requestFocusInWindow();
    }

    public HUDView getHudView() {
        return hudView;
    }
}
