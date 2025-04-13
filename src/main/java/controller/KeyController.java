package controller;

import view.GameView;
import view.hud.HUDButton;
import view.hud.OptionsMenuView;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

public class KeyController implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private final GameView gameView;

    public KeyController(GameView gameView){
        this.gameView = gameView;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        OptionsMenuView optionsMenuView = gameView.getOptionsMenuView();
        if (optionsMenuView.isVisible()) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                optionsMenuView.moveSelection(false);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                optionsMenuView.moveSelection(true);
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                switch (optionsMenuView.getSelectedButton()) {
                    case HUDButton.BACKTOGAME:
                        gameView.getOptionsMenuView().toggle();
                        break;
                    case HUDButton.SAVE:
                        // TODO call save here
                        break;
                    case HUDButton.QUIT:
                        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(gameView);
                        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                }
            }
        } else {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gameView.getOptionsMenuView().toggle();
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
