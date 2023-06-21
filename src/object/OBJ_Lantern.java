package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Lantern extends Entity{

    public OBJ_Lantern(GamePanel gp) {
        super(gp);
        
        type = type_light;
        name = "Lantern";
        down1 = setup("/res/objects/lantern",gp.tileSize,gp.tileSize);
        description = "[Lantern]\nIlluminates your \nsurroundings.";
        price = 200;
        lightRadius = 150;
    }
    
}
