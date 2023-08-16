package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_BlueHeart extends Entity{

    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/res/objects/blueheart",gp.tileSize,gp.tileSize);

        setDialogue();
    }
    public void setDialogue() {

        dialogues[0][0] = "You pick up a beutiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }
    public boolean use(Entity entity) {

        gp.gameState = gp.cutSceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
