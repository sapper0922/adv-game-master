package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Boots extends Entity{

    public static final String objName = "Boots";

    //try to make of boots image and catch is printStackTrace
    public OBJ_Boots(GamePanel gp) {
        
        super(gp);
        name = objName;
        setup("/res/objects/boots", gp.tileSize, gp.tileSize);
    }

}
