package view;

public class ScreenSettings {
    // Screen settings
    final static int originalTileSize = 16; // 16x16 px
    final static int scale = 1;
    final static int tileSize = originalTileSize * scale; // 48x48 px
    final static int maxScreenCol = 150;
    final static int maxScreenRow = 80;
    static final int screenWidth = tileSize * maxScreenCol;
    static final int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    final static int playerScreenX = screenWidth / 2;
    final static int playerScreenY = screenHeight / 2;

}
