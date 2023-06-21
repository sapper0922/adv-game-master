package object;

import entity.Entity;
import Main.GamePanel;

//extends SuperObject to this class
public class OBJ_Door extends Entity{

    GamePanel gp;
    //try to make of door image and catch is printStackTrace
    public OBJ_Door(GamePanel gp) {

        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = "Door";
        down1 = setup("/res/objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
    public void interact() {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You need a key to open this.";
    }
}
