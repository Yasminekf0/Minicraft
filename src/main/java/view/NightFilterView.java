package view;

import model.world.DayCycleManager;

import java.awt.*;

import static view.ScreenSettings.screenHeight;
import static view.ScreenSettings.screenWidth;

public class NightFilterView extends GameElementView {

    private double level;

    @Override
    public void draw(Graphics2D g2) {

        level = DayCycleManager.getInstance().getNightFilterLevel();

        if (level != 0) {
            int alpha = (int) (166 * level);
            Color oldColor = g2.getColor();
            g2.setColor(new Color(29, 39, 88, alpha));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(oldColor);
        }
    }

    public void setLevel(double level) {
        this.level = level;
    }

}
