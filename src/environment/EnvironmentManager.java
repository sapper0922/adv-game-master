package environment;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Graphics2D;

public class EnvironmentManager {
    
    GamePanel gp;
    //KeyHandler keyH;
    public Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
        //this.keyH = keyH;
    }
    public void setUp() {

        lighting = new Lighting(gp);
    }
    public void update() {

        lighting.update();
    }
    public void draw(Graphics2D g2) {

        if(gp.keyH.godModeOn == false) {
            lighting.draw(g2);
        }

    }
}
