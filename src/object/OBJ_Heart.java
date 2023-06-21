package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Heart extends Entity{

    GamePanel gp;

    //try to make of door image and catch is printStackTrace
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/res/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/res/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/res/objects/heart_blank", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        return true;
    }
}
