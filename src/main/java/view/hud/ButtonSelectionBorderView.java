package view.hud;

import view.GameElementView;

import java.awt.*;

public class ButtonSelectionBorderView extends GameElementView {

    private HUDButton button;

    public ButtonSelectionBorderView(HUDButton button) {
        setButton(button);
    }

    @Override
    public void draw(Graphics2D g2) {
        Stroke oldStroke = g2.getStroke();
        Color oldColor = g2.getColor();

        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.YELLOW);

        g2.drawRect(button.getTopLeftPos().getX(), button.getTopLeftPos().getY(), button.getWidth(), button.getHeight());

        g2.setStroke(oldStroke);
        g2.setColor(oldColor);
    }

    public void setButton(HUDButton button) {
        this.button = button;
    }
}
