package view.hud;

import java.awt.*;

public class OptionsMenuView extends HUDView {

    public OptionsMenuView() {
        visible = false;
    }

    @Override
    protected void loadImages() {

    }

    @Override
    public void draw(Graphics2D g2) {
        if (visible) {
            g2.drawRect(100, 100, 100, 100);
        }
    }

    public void toggle() {
        visible = !visible;
    }

}
