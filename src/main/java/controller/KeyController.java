package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {
    private final PlayerController playerController;
    private final GameController gameController;

    public KeyController(GameController gameController, PlayerController playerController) {
        this.gameController = gameController;
        this.playerController = playerController;

        gameController.getGameView().addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            playerController.updateMoving(0, -1);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            playerController.updateMoving(0, 1);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            playerController.updateMoving(-1, 0);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            playerController.updateMoving(1, 0);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gameController.pauseGame();
        }
        if (code == KeyEvent.VK_SPACE) {
            playerController.doAction();
        }
        if (code == KeyEvent.VK_Q) {
            playerController.switchInventorySection();
            gameController.getHudView().repaint();
        }
        if (code == KeyEvent.VK_E) {
            playerController.switchSelectedItem();
            gameController.getHudView().repaint();
        }

        if (code == KeyEvent.VK_P) {
            playerController.takeDamage(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            playerController.updateMoving(0, 1);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            playerController.updateMoving(0, -1);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            playerController.updateMoving(1, 0);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            playerController.updateMoving(-1, 0);
        }
        if (code == KeyEvent.VK_SPACE) {
            playerController.stopAction();
        }
    }
}
