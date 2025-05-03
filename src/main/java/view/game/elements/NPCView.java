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

    public NPCView() {
        super();
        this.mobs = MobManager.getInstance().getNpcs();
        MobsAngles = new double[mobs.length];
    }
}


