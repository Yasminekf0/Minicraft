package view.hud;

import model.position.ScreenPosition;

import java.awt.*;

public class OptionsMenuView extends HUDView {

    private final HUDButton[] buttons;
    private HUDButton selectedButton;
    private final ButtonSelectionBorderView buttonSelectionBorder;
    @SuppressWarnings("FieldCanBeLocal")
    private final int buttonCentersGap = 100;

    public OptionsMenuView(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
        visible = false;
        buttons = HUDButton.values();
        selectedButton = buttons[0];
        buttonSelectionBorder = new ButtonSelectionBorderView(selectedButton);
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
            buttonSelectionBorder.draw(g2);
        }
    }

    private int getYByIndex(int i){
        return screenHeight / 2 - (buttons.length - 1) * buttonCentersGap / 2 + i * buttonCentersGap;
    }

    public void toggle() {
        visible = !visible;
    }

    public void moveSelection(boolean direction) {
        int newIndex;
        if (direction) {
            newIndex = selectedButton.getIndex() + 1;
        } else {
            newIndex = selectedButton.getIndex() - 1;
        }
        if (buttonIndexInRange(newIndex)) {
            selectedButton = buttons[newIndex];
            buttonSelectionBorder.setButton(selectedButton);
        }
    }

    private boolean buttonIndexInRange(int i){
        return (i >= 0 & i < buttons.length);
    }

    public HUDButton getSelectedButton() {
        return selectedButton;
    }
}
