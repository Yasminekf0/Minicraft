package view;

import java.awt.*;

public abstract class GameElementView {
    public GameElementView() {
        loadImages();
    }
    protected abstract void loadImages();
    public abstract void draw(Graphics2D g2);

}
