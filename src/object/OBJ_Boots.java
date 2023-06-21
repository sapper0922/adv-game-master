package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Boots extends Entity{

    //try to make of boots image and catch is printStackTrace
    public OBJ_Boots(GamePanel gp) {
        
        super(gp);
        name = "Boots";
        setup("/res/objects/boots", gp.tileSize, gp.tileSize);
    }

}
