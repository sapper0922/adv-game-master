package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import Main.GamePanel;
import Main.KeyHandler;      
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import java.awt.AlphaComposite;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;

public class Player extends Entity{
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        //place player at the center of the screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //Size of Collision Box in pixels and location
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Attack area
        // attackArea.width = 36;
        // attackArea.height = 36;

        //Calls setDefaultValues function
        setDefaultValues();
    }
    public void setDefaultValues() {

        gp.currentMap = 0;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        defaultSpeed = 4;

        //how fast the player character moves in pixels
        speed = defaultSpeed;

        //direction the player is facing
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 20; //More strength means he does more damage
        dexterity = 1; // More dexterity means he recieves less damage
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strentgh and weapon
        defence = getDefence(); // The total defence value is decided by dexterity and shield

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }
    public void setDefaultPositions() {

        gp.currentMap = 0;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void setDialogue() {

        dialogues[0][0] = "You are level " + level + " now!\n" 
                + "You feel stronger!";
    }
    public void restoreStatus() {

        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));  
        inventory.add(new OBJ_Key(gp)); 
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefence() {
        return defence = dexterity * currentShield.defenceValue;
    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot() {
        int CurrentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentShield) {
                CurrentShieldSlot = i;
            }
        }
        return CurrentShieldSlot;
    }

    //Images for up1, up2, down1, down2, left1, left2, right1, right2.
    public void getImage() {
        up1 = setup("/res/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/boy_right_2", gp.tileSize, gp.tileSize);

    }
    public void getSleepingImage(BufferedImage image) {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    public void getAttackImage() {

        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/res/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/res/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/res/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/res/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/res/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe) {
            attackUp1 = setup("/res/player/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/res/player/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/res/player/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/res/player/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/res/player/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
        }
        
    }
    public void getGuardImage() {
        guardUp = setup("/res/player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/res/player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/res/player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/res/player/boy_guard_right", gp.tileSize, gp.tileSize);
    }
    public void update() {
        
        collisionOn = false;
        gp.cChecker.checkTile(this);
        //Check Object Collision
        gp.cChecker.checkObject(this, true);
        //Check Npc Collision
        gp.cChecker.checkEntity(this, gp.npc);
        //Check Monster Collision
        gp.cChecker.checkEntity(this, gp.monster);

        //Check interactive tile collision
        gp.cChecker.checkEntity(this, gp.iTile);        

        //This is setting the knockback system
        if(knockBack == true) {

            if(collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(!collisionOn) {
                switch(knockBackDirection) {
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

        else if(attacking == true) {
            attacking();
        }
        else if(keyH.spacePressed == true) {
            guarding = true;
            guardCounter++;
        }
        //This if statement checks if the animiation of the player only happenes when you are pushing w, a, s, d.
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            //if W key pressed the direction will be set to up.
            if (keyH.upPressed == true) {
                direction = "up";
            }
            //if S key pressed the direction will be set to down.
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            //if A key pressed the direction will be set to left.
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            //if D key pressed the direction will be set to right.
            else if(keyH.rightPressed == true) {
                direction = "right";
            } 

            //Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check Npc Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
    
            //Check Monster Collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //Check interactive tile collision
            gp.cChecker.checkEntity(this, gp.iTile);        

            //CHECK EVENT
            gp.eHandler.checkEvent();

            //If Collision is false, Player can move
            if(collisionOn == false && keyH.enterPressed == false) {

                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            if(keyH.enterPressed && !attackCanceled) {
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            
            //Every 12 framces the animation changes
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {

            // Set default coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // Subtract the cost (Mana, Ammo, ETC.)
            projectile.subtractResource(this);

            // Check Vacancy
            for(int i = 0; i < gp.projectile[1].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        //This needs to be outside of key if statement
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter >= 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }
        if(keyH.godModeOn == false) {
            if(life <= 0) {
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = -1;
                gp.stopMusic();
                gp.playSE(12);
            }
        }
    }

    //checks if player picked up a key and uses the key
    public void pickUpObject(int i) {
        if(i != 999) {

            // Pickup only items
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // Obstacle
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // Inventory items
            else {
                String text;

                if(canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "You cannot carry anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }

            
        }
    }

    public void interactNPC(int i) {
        
        if(i != 999) {

            if(gp.keyH.enterPressed == true) {
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
            
            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    public void contactMonster(int i) {

        if(i != 999) {
            if(!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);

                //Setting damage variable which controls how much life to take away depending on attack and defence
                int damage = gp.monster[gp.currentMap][i].attack - defence;
                if(damage < 1) {
                    damage = 1;
                }

                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {

        if(i != 999) {
            if(!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(5);

                if(knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance == true) {
                    attack *= 5;
                }

                //Setting damage variable which controls how much life to take away depending on attack and defence
                int damage = attack - gp.monster[gp.currentMap][i].defence;
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage! ");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("EXP + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i) {

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible) {

            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            // Generate particle
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();

            }
        }
    }
    public void damageProjectile(int i) {

        if(i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this,0);

        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
        
        if(itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defence = getDefence();
            }
            if(selectedItem.type == type_light) {
                if(currentLight == selectedItem) {
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable) {
                if(selectedItem.use(this)) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName) {

        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item) {

        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        //Check if stackable
        if(newItem.stackable) {

            int index = searchItemInInventory(newItem.name);

            if(index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            else { //New item
                if(inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else { //Not stackable
            if(inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2) {

        //create variable image with BufferedImage
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        //if direction is up the image will change from up1 and up2
        switch(direction) {
        case "up":
        if(attacking == false) {
            if(spriteNum == 1) { image = up1; }
            if(spriteNum == 2) { image = up2; }
        }
        if(attacking == true) {
            tempScreenY = screenY - gp.tileSize;
            if(spriteNum == 1) { image = attackUp1; }
            if(spriteNum == 2) { image = attackUp2; }
        }
        if(guarding == true) {
            image = guardUp;
        }
            break;
        //if direction is down the image will change from down2 and down2
        case "down":
            if(attacking == false) {
                if(spriteNum == 1) { image = down1; }
                if(spriteNum == 2) { image = down2; }
            }
            if(attacking == true) {
                if(spriteNum == 1) { image = attackDown1; }
                if(spriteNum == 2) { image = attackDown2; }
            }
            if(guarding == true) {
                image = guardDown;
            }
            break;
        //if direction is left the image will change from left1 and left2
        case "left":
            if(attacking == false) {
                if(spriteNum == 1) { image = left1; }
                if(spriteNum == 2) { image = left2; }
            }
            if(attacking == true) {
                tempScreenX = screenX - gp.tileSize;
                if(spriteNum == 1) { image = attackLeft1; }
                if(spriteNum == 2) { image = attackLeft2; }
            }
            if(guarding == true) {
                image = guardLeft;
            }
            break;
        case "right":
            if(attacking == false) {
                if(spriteNum == 1) { image = right1; }
                if(spriteNum == 2) { image = right2; }
            }
            if(attacking == true) {
                if(spriteNum == 1) { image = attackRight1; }
                if(spriteNum == 2) { image = attackRight2; }
            }
            if(guarding == true) {
                image = guardRight;
            }
            break;
        }

        if(transparent == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        //draws image on the screen, since screenX and screenY is final variable he doesn't change location on the screen(final variable cannot be changed unless you change it in the class)
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        // g2.setFont(new Font("Arial", Font.PLAIN, 26));
        // g2.setColor(Color.white);
        // g2.drawString("Invincible:"+invincibleCounter, 10, 400);
    }
}
