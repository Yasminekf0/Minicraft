package view.hud;

import view.GameElementView;

public abstract class HUDView extends GameElementView {
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected boolean visible;

}
