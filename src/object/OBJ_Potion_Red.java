package object;

import entity.Entity;
import Main.GamePanel;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    public static final String objName = "Red Potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;
        
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/res/objects/potion_red",gp.tileSize,gp.tileSize);
        description = "[Red Potion]\nHeals you life by" + value + ".";
        price = 25;
        stackable = true;

        setDialogue();
    }
    public void setDialogue() {

        dialogues[0][0] = "You drink the " + name + "!\n" + "Your life has been recovered" + value + ".";
    }
    public boolean use(Entity entity) {  

        startDialogue(this,0);
        gp.playSE(2);
        entity.life += value;

        if(gp.player.life > gp.player.maxLife) {

            gp.player.life = gp.player.maxLife;
        }
        return true;
    }
}
