package view.hud;

import model.position.ScreenPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public enum HUDButton {
    BACKTOGAME("/hud/backToGameButton.png", 400, 50, 0),
    SAVE("/hud/saveButton.png", 400, 50, 1),
    QUIT("/hud/quitButton.png", 400, 50, 2);

    private BufferedImage img;
    private ScreenPosition topLeftPos;
    private final int width;
    private final int height;
    private final int index;

    HUDButton(String imagePath, int w, int h, int index) {
        try {
            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = w;
        height = h;
        this.index = index;
    }

    HUDButton(String imagePath, int index) {
        try {
            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = img.getWidth();
        height = img.getHeight();
        this.index = index;
    }

    public void setCenter(ScreenPosition centerCoords){
        int x = centerCoords.getX() - width / 2;
        int y = centerCoords.getY() - height / 2;
        topLeftPos = new ScreenPosition(x, y);
    }

    public ScreenPosition getTopLeftPos(){
        return topLeftPos;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIndex() {
        return index;
    }
}
