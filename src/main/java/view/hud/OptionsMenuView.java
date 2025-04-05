package view.hud;

import model.position.ScreenPosition;

import javax.imageio.ImageIO;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OptionsMenuView extends HUDView {

    private HUDButton[] buttons;
    private HUDButton selectedButton;
    private final int buttonCentersGap = 100;

    public OptionsMenuView(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        visible = false;
        buttons = HUDButton.values();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (visible) {
            for (int i = 0; i < buttons.length; i++) {
                HUDButton button = buttons[i];
                button.setCenter(new ScreenPosition(screenWidth/2, getYByIndex(i)));
                g2.drawImage(button.getImg(), button.getTopLeftPos().getX(), button.getTopLeftPos().getY(),
                        button.getWidth(), button.getHeight(), null);
            }
            //g2.drawRect(100, 100, 100, 100);
        }
    }

    private int getYByIndex(int i){
        return screenHeight / 2 - (buttons.length - 1) * buttonCentersGap / 2 + i * buttonCentersGap;
    }

    public void toggle() {
        visible = !visible;
    }

}
