package view.hud;

import model.position.ScreenPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum HUDButton {
    BACKTOGAME("/hud/backToGameButton.png", 400, 50),
    SAVE("/hud/saveButton.png", 400, 50),
    QUIT("/hud/quitButton.png", 400, 50);

    private BufferedImage img;
    private ScreenPosition topLeftPos;
    private int width, height;

    HUDButton(String imagePath, int w, int h) {
        try {
            this.img = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = w;
        height = h;
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
}
