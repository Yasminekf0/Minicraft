package view.hud;

import view.GameElementView;

public abstract class HUDView extends GameElementView {

    protected int screenWidth, screenHeight;

    public HUDView(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected boolean visible;

}
