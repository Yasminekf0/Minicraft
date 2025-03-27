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
        spriteNum = entity.spriteNum;
        BufferedImage image = null;

        if (spriteNum == 1 || spriteNum == 3) {
            image = entity.rightstand;
        }
        if (spriteNum == 2) {
            image = entity.rightwalk1;
        }
        if (spriteNum == 4) {
            image = entity.rightwalk2;
        }

        assert image != null;
        AffineTransform at = createAffineTransform(image);

        g2.drawImage(image, at, null);

    }

    private AffineTransform createAffineTransform(BufferedImage image) {
        AffineTransform at = new AffineTransform();

        // Move the object to the center
        at.translate(entity.screenX, entity.screenY);

        // Rotate it
        at.rotate(entity.getAngle());

        // Scale it
        at.scale((double) entity.gp.tileSize / image.getWidth(), (double) entity.gp.tileSize / image.getWidth());

        // Make the object rotate around the center
        at.translate((double) -image.getWidth(null) / 2, (double) -image.getHeight(null) / 2);

        return at;
    }
}
