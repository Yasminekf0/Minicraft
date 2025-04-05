package view.hud;

import javax.imageio.ImageIO;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OptionsMenuView extends HUDView {

    private HUDButton[] buttons;
    private HUDButton selectedButton;

    public OptionsMenuView() {
        visible = false;
        buttons = HUDButton.values();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (visible) {
            for (HUDButton button : buttons) {
                g2.drawImage(button.getImg(), 100, 100, 100, 100, null);
            }
            g2.drawRect(100, 100, 100, 100);
        }
    }

    public void toggle() {
        visible = !visible;
    }

}
