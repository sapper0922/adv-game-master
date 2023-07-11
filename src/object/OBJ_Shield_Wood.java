package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

    public static final String objName = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        
        type = type_shield;
        name = objName;
        down1 = setup("/res/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenceValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 35;
    }
    
}
