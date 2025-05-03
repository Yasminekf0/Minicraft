package view.game.elements;

import model.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.settings.ScreenSettings.playerScreenX;
import static view.settings.ScreenSettings.playerScreenY;
import static view.settings.ScreenSettings.tileSize;

import model.entity.npcs.Enemy;
import model.entity.npcs.MobManager;

@SuppressWarnings("FieldCanBeLocal")
public class EnemyView extends MobView{


    public EnemyView() {
        super();
        this.mobs = MobManager.getInstance().getEnemies();
        MobsAngles = new double[mobs.length];
    }
}
