package object;

import entity.Entity;
import Main.GamePanel;

//extends SuperObject to this class
public class OBJ_Key extends Entity {

    //try to make of door image and catch is printStackTrace
    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Key";
        down1 = setup("/res/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
        stackable = true;
    }
    public boolean use(Entity entity) {

        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Door");
        System.out.println("a");

        if(objIndex != 999) {
            gp.ui.currentDialogue = "You used a key" + name + "and open the door!";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "What are you doing?";
            return false;
        }
    }
}