package environment;

import Main.GamePanel;
import java.awt.Graphics2D;

public class EnvironmentManager {
    
    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }
    public void setUp() {

        lighting = new Lighting(gp, 350);
    }
    public void draw(Graphics2D g2) {

        lighting.draw(g2);
    }
}
