package object;

import entity.Entity;
import Main.GamePanel;

//extends SuperObject to this class
public class OBJ_Chest extends Entity{

    GamePanel gp;
    Entity loot;
    boolean opened = false;

    //try to make of chest image and catch is printStackTrace
    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
        image = setup("/res/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact() {

        gp.gameState = gp.dialogueState;

        if(!opened) {
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and find a " + loot.name + "!");

            if(!gp.player.canObtainItem(loot)) {
                sb.append("\n...But you cannot carry anymore!");
            }
            else {
                sb.append("\nYou obtain the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else {
            gp.ui.currentDialogue = "It's empty";
        }
    }
}
