package view.game.elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import model.entity.mobs.MobManager;

@SuppressWarnings("FieldCanBeLocal")
public class EnemyView extends MobView{
    private BufferedImage z, z1, z2, sk1, sk2, sk;
    private final BufferedImage[] zombieImages;
    private final BufferedImage[] skeletonImages;

    public EnemyView() {
        super();
        zombieImages = new BufferedImage[] {z,z1,z2};
        skeletonImages = new BufferedImage[] {sk,sk1,sk2};
        this.mobs = MobManager.getInstance().getEnemies();
        MobsAngles = new double[mobs.length];
        spriteMap.put(0,skeletonImages);
        spriteMap.put(1,zombieImages);
    }

    protected void loadImages() {
        try {
            z = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie.png")));
            z2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie2.png")));
            z1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie1.png")));
            sk = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton.png")));
            sk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton2.png")));
            sk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton1.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
