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
    private BufferedImage v, v1, v2;

    private final BufferedImage[] villagerImages;
    public NPCView() {
        super();
        villagerImages = new BufferedImage[] {v,v1,v2};
        this.mobs = MobManager.getInstance().getNpcs();
        MobsAngles = new double[mobs.length];
        spriteMap.put(0,villagerImages);
    }

    protected void loadImages() {
        try {
            v = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager.png")));
            v2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager2.png")));
            v1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


