package view;

public class ScreenSettings {

    // Screen settings
    final static int originalTileSize = 16;
    public final static int scale = 4;
    public final static int tileSize = originalTileSize * scale;
    final static int maxScreenCol = 17;
    final static int maxScreenRow = 17;
    static final int screenWidth = tileSize * maxScreenCol;
    static final int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    final static int playerScreenX = screenWidth / 2;
    final static int playerScreenY = screenHeight / 2;

}
