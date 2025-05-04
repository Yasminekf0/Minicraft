package view.game.elements;

import model.world.DayCycleManager;

import java.awt.*;

import static view.settings.ScreenSettings.screenHeight;
import static view.settings.ScreenSettings.screenWidth;

public class NightFilterView implements GameElementView {

    public void draw(Graphics2D g2) {

        double level = DayCycleManager.getInstance().getNightFilterLevel();

        if (level != 0) {
            int alpha = (int) (166 * level);
            Color oldColor = g2.getColor();
            g2.setColor(new Color(29, 39, 88, alpha));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(oldColor);
        }
    }


}
