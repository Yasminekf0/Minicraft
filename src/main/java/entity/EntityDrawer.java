package entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class EntityDrawer {
    Entity entity;
    int spriteNum;

    public EntityDrawer(Entity entity){
        this.entity = entity;
    }
    public void draw(Graphics2D g2) {
        spriteNum = entity.getSpriteNum();
        BufferedImage image = null;

        if (spriteNum == 1 || spriteNum == 3) {
            image = entity.getRightstand();
        }
        if (spriteNum == 2) {
            image = entity.getRightwalk1();
        }
        if (spriteNum == 4) {
            image = entity.getRightwalk2();
        }

        assert image != null;
        AffineTransform at = createAffineTransform(image);

        g2.drawImage(image, at, null);

    }

    private AffineTransform createAffineTransform(BufferedImage image) {
        AffineTransform at = new AffineTransform();

        // Move the object to the center
        at.translate(entity.getScreenX(), entity.getScreenY());

        // Rotate it
        at.rotate(entity.getAngle());

        // Scale it
        at.scale((double) entity.gp.tileSize / image.getWidth(), (double) entity.gp.tileSize / image.getWidth());

        // Make the object rotate around the center
        at.translate((double) -image.getWidth(null) / 2, (double) -image.getHeight(null) / 2);

        return at;
    }
}
