package view.game.elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.settings.ScreenSettings.*;

import model.entity.npcs.MobManager;
import model.entity.npcs.NPC;
import model.entity.Player;

@SuppressWarnings("FieldCanBeLocal")
public class NPCView extends MobView{
    // Sprite images
    private final NPC[] npcs;

    public NPCView() {
        this.npcs = MobManager.getInstance().getNpcs();
    }


    /*public void draw(Graphics2D g2) {
        for(int i = 0; i < this.npcs.length; ++i) {
            //if (!npcs[i].isAlive()) {
                //return;
            //}

            int playerWorldX = player.getWorldPos().getXInt();
            int playerWorldY = player.getWorldPos().getYInt();
            int worldX = npcs[i].getWorldPos().getXInt();
            int worldY = npcs[i].getWorldPos().getYInt();

            int screenX = worldX - playerWorldX + playerScreenX;
            int screenY = worldY - playerWorldY + playerScreenY;


            if (worldX + tileSize > playerWorldX - playerScreenX &&
                    worldX - tileSize < playerWorldX + playerScreenX &&
                    worldY + tileSize > playerWorldY - playerScreenY &&
                    worldY - tileSize < playerWorldY + playerScreenY) {

                BufferedImage image = switch (spriteNum) {
                    case 2 -> v1;
                    case 4 -> v2;
                    default -> v;
                };
                if (image == null) return;

                AffineTransform at = createAffineTransform(image, screenX, screenY, angles[i]);
                g2.drawImage(image, at, null);
            }
        }
    }*/



}


