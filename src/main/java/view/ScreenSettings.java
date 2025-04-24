package view;

public class ScreenSettings {
    // Screen settings
    final static int originalTileSize = 16; // 16x16 px
    public final static int scale = 4;//4;
    public final static int tileSize = originalTileSize * scale; // 48x48 px
    final static int maxScreenCol = 17;//14;
    final static int maxScreenRow = 17;//14;
    static final int screenWidth = tileSize * maxScreenCol;
    static final int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    final static int playerScreenX = screenWidth / 2;
    final static int playerScreenY = screenHeight / 2;

}
