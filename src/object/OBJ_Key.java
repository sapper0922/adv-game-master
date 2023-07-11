package object;

import entity.Entity;
import Main.GamePanel;

//extends SuperObject to this class
public class OBJ_Key extends Entity {

    public static final String objName = "Key";

    //try to make of door image and catch is printStackTrace
    public OBJ_Key(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("/res/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
        stackable = true;

        setDialogue();
    }
    public void setDialogue() {

        dialogues[0][0] = "You used a key" + name + "and open the door!";

        dialogues[1][0] = "What are you doing?";
    }
    public boolean use(Entity entity) {

        int objIndex = getDetected(entity, gp.obj, "Door");
        System.out.println("a");

        if(objIndex != 999) {
            startDialogue(this,0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            startDialogue(this,1);
            return false;
        }
    }
}