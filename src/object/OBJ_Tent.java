package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity{

    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_consumable;
        name = "Tent";
        down1 = setup("/res/objects/tent",gp.tileSize,gp.tileSize);
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }
    public boolean use(Entity entity) {

        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
