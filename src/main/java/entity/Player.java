package entity;

import main.GamePanel;
import main.KeyInputs;
import world.position.ScreenPosition;
import world.position.WorldPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Entity {

    public GamePanel gp;
    private final KeyInputs keyI;



    private Map<String, ArrayList<String>> inventory;
    private String currentSection;
    private String selectedItem;

    public Player(GamePanel gp, KeyInputs keyI) {

        super();
        this.gp = gp;
        this.keyI = keyI;

        setDefaultValues();
        getPlayerImage();
        initializeInventory();

    }

    public void setDefaultValues() {

        health = 10;
        maxHealth = 10;

        worldPos = new WorldPosition(gp.worldWidth/2.0, gp.worldHeight/2.0);
        screenPos = new ScreenPosition(gp.screenWidth / 2 - (gp.tileSize / 2), gp.screenHeight / 2 - (gp.tileSize / 2));

        speed = 10;
        angle = Math.PI / 2;
    }

    public void getPlayerImage() {
        try {

            rightstand = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/rightstand.png")));
            rightwalk1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/rightwalk1.png")));
            rightwalk2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/rightwalk2.png")));
            rightdo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/rightdo.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean moving = false;
        boolean Do = false;

        double dx = 0;
        double dy = 0;

        if (keyI.upPressed) {
            dy = -speed;
            moving = true;
        }
        if (keyI.downPressed) {
            dy = speed;
            moving = true;
        }
        if (keyI.leftPressed) {
            dx = -speed;
            moving = true;
        }
        if (keyI.rightPressed) {
            dx = speed;
            moving = true;
        }

        if (moving) {
            double vectorNorm = Math.sqrt(dx * dx + dy * dy);
            double normalizedDx = dx / vectorNorm;
            double normalizedDy = dy / vectorNorm;

            setAngle(Math.atan2(dy, dx));

            worldPos.increment(normalizedDx * speed, normalizedDy * speed);
        }

        if (moving) {
            spriteCounter++;
            if (spriteCounter > 11) { // Speed of changing frame
                spriteNum++;
                if (spriteNum > 4) {  // Cycle through frames 1-4
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;  // Reset to standing when not moving
        }

    }




    public int getHealth() {
        return health;
    }

    public void setHealth(int new_health) {
        this.health = new_health;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, this.health + healAmount);
    }
    public KeyInputs getKeyI () {
        return keyI;
    }



    public void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put("Tools", new ArrayList<>());
        inventory.put("Blocks", new ArrayList<>());
        inventory.put("Potions", new ArrayList<>());
        currentSection = "Tools";
        selectedItem = null;
    }

    public void addItem(String section, String item) {
        if (!inventory.containsKey(section)) return;

        ArrayList<String> items = inventory.get(section);

        if (section.equals("Tools") && items.contains(item)) {
            System.err.println("You already have this tool");
            return;
        }

        items.add(item);
        if (selectedItem == null) selectedItem = item;
    }


    public ArrayList<String> getInventorySection(String section) {
        return inventory.get(section);
    }

    public String getItemFromInventory(String section, String item) {
        if (inventory.containsKey(section)) {
            ArrayList<String> items = inventory.get(section);

            if (items.contains(item)) {
                return item;
            }
        }

        return "Item not found";
    }

    public int countItem(String section, String item) {
        return 1;
    }

    public void openChest() { //*****************************
    }

    public void upgradeTool() {} //***************************

    public String getSelectedItem(){return selectedItem;}

    public void setSelectedItem(String item){selectedItem = item;}

    public String getCurrentSection(){return currentSection;}

    public void setCurrentSection(String section){
        if (inventory.containsKey(section)) {
            currentSection = section;
        }}

}
