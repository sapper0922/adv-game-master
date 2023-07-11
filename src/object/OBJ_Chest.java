package object;

import entity.Entity;
import Main.GamePanel;

//extends SuperObject to this class
public class OBJ_Chest extends Entity{

    GamePanel gp;
    public static final String objName = "Chest";

    //try to make of chest image and catch is printStackTrace
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
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
    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }
    public void setDialogue() {

        dialogues[0][0] = "You opened the chest and find a " + loot.name + "!\n...But you cannot carry any more!";
        dialogues[1][0] = "You opened the chest and find a " + loot.name + "!\nYou obtain the " + loot.name + "!";
        dialogues[2][0] = "It's empty.";
    }
    public void interact() {

        if(!opened) {
            gp.playSE(3);

            if(!gp.player.canObtainItem(loot)) {
                startDialogue(this,0);
            }
            else {
                startDialogue(this,1);
                down1 = image2;
                opened = true;
            }
        }
        else {
            startDialogue(this,2);
        }
    }
}
