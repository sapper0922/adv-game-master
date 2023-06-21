package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import Main.GamePanel;
import Main.UtilityTool;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;


public class Entity {
    
    public GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(8, 16, 30, 30);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;


    //Dialogues
    String dialogues[] = new String[20];

    //State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    
    //Counter
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;

    //Character Attibutes
    public String name;
    public int defaultSpeed;
    public boolean collision = false;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int nextLevelExp;
    public int exp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //Item Attributes
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;

    // Type
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public int getLeftX() {
        return worldX + solidArea.x;
    }
    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY() {
        return worldY + solidArea.y;
    }
    public int getBottomY() {
        return worldX + solidArea.y + solidArea.height;
    }
    public int getCol() {
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public void setAction() {}
    public void damageReaction() {}
    public void speak() {

        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
        case "up": direction = "down"; break;
        case "down": direction = "up"; break;
        case "left": direction = "right"; break;
        case "right": direction = "left"; break;
        }

    }
    public void interact() {}
    public boolean use(Entity entity) {return false;}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // Dead monsters worldX and WorldY
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void checkCollision() {

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);     

        if(this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }

    }
    public void update() {

        //This is setting the knockback system
        if(knockBack) {

            checkCollision();

            if(collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(!collisionOn) {
                switch(gp.player.direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break; 
                }
            }

            knockBackCounter++;
            if(knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else {
            setAction();
            checkCollision();   
    
            if(collisionOn == false) {
                switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
                }
            }
        }



        spriteCounter++;
        
        //Every 12 framces the animation changes
        if(spriteCounter > 24) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        //Invincible state after Entity gets hit
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter >= 40) {
                invincible = false;
                invincibleCounter = 0;
                System.out.println("Hit!");
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if(!gp.player.invincible) {
            //we can give damage
            gp.playSE(6);

            int damage = attack - gp.player.defence;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        //if the object is in the screen it will draw
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            //if direction is up the image will change from up1 and up2
            switch(direction) {
                case "up":
                    if(spriteNum == 1) { image = up1; }
                    if(spriteNum == 2) { image = up2; }
                    break;
                //if direction is down the image will change from down2 and down2
                case "down":
                    if(spriteNum == 1) { image = down1; }
                    if(spriteNum == 2) { image = down2; }
                    break;
                //if direction is left the image will change from left1 and left2
                case "left":
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2) { image = left2; }
                    break;
                //if direction is right the image will change from right1 and right2
                case "right":
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2) { image = right2; }
                    break;
                }

                //Monster HP bar
                if(type == 2 && hpBarOn) {

                    double oneScale = (double)gp.tileSize/maxLife;
                    double hpBarValue = oneScale*life;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX-1, screenY-16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                    hpBarCounter++;

                    if(hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if(invincible) {
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2,0.4f);
                }
                if(dying) {
                    dyingAnimation(g2);
                }

                //Draw Tiles on the screen
                g2.drawImage(image, screenX, screenY, null);

                changeAlpha(g2,1f);

        }

    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 10;

        if(dyingCounter <= i) { changeAlpha(g2,0f); }
        if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2,1f); }
        if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2,0f); }
        if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2,1f); }
        if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2,0f); }
        if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2,1f); }
        if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2,0f); }
        if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2,1f); }
        if(dyingCounter > i*8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    //creates image path for player and npc so the images are loaded here
    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;

    }
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if(gp.pFinder.search()) {
            
            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                // left or right
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }

            // If reaches the goal, stop the search
            // int nextCol = gp.pFinder.pathList.get(0).col;
            // int nextRow = gp.pFinder.pathList.get(0).row;
            // if(nextCol == goalCol && nextRow == goalRow) {
            //     onPath = false;
            // }
        }
    }
    public int getDetected(Entity user, Entity target[][], String targetName) {

        int index = 999;

        // Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.direction) {
        case "up": nextWorldY = user.getTopY()-user.speed; break;
        case "down": nextWorldY = user.getBottomY()+user.speed; break;
        case "left": nextWorldX = user.getLeftX()-user.speed; break;
        case "right": nextWorldX = user.getRightX()+user.speed; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null) {
                if(target[gp.currentMap][i].getCol() == col && 
                   15 == row &&
                   target[gp.currentMap][i].name.equals(targetName) || target[gp.currentMap][i].getCol() == col && 12 == row && target[gp.currentMap][i].name.equals(targetName)) {

                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
