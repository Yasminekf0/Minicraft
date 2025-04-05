package view.hud;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum HUDButton {
    BACKTOGAME("/hud/backToGameButton.png");

    private BufferedImage img;

    HUDButton(String imagePath) {
        try {
            this.img = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImg() {
        return img;
    }
}
