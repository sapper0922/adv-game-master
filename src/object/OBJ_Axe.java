package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Axe extends Entity {

    public static final String objName = "Woodcutter's Axe";

    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        type = type_axe;
        name = objName;
        down1 = setup("/res/objects/axe",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Woodcutter's Axe]\nA bit rusty but still \ncan cut some trees.";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
    
}
