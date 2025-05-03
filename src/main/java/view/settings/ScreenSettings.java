package view.settings;

public class ScreenSettings {
    // Screen settings
    final static int originalTileSize = 16;
    public final static int scale = 4;
    public final static int scaleHUD = 3;
    public final static int tileSize = originalTileSize * scale;
    public final static int maxScreenCol = 17;
    public final static int maxScreenRow = 17;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    public final static int playerScreenX = screenWidth / 2;
    public final static int playerScreenY = screenHeight / 2;

}
