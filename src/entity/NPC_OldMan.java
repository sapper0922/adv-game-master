package entity;

import java.awt.Rectangle;
import java.util.Random; 
import Main.GamePanel;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.x;
        solidArea.width = 30;
        solidArea.height = 30;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/res/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/npc/oldman_right_2", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {

        dialogues[0][0] = "Hello, lad!";
        dialogues[0][1] = "So you've come to this island to \nfind the treasure?";
        dialogues[0][2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogues[0][3] = "Well, good luck to you.";

        dialogues[1][0] = "If you become tired, rest at the water.";
        dialogues[1][1] = "However, the monsters reapear if you rest.\nI don't know why but that's how it works.";
        dialogues[1][2] = "In any case, don't push yourself too hard.";

        dialogues[2][0] = "I wonder how to open that door...";

    }

    //Action for npc
    public void setAction() {

        if(onPath == true) {

            // int goalCol = 12;
            // int goalRow = 9;
            // int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            // int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            // searchPath(goalCol,goalRow);
        }
        else {

            actionLockCounter++;

            if(actionLockCounter == 120) {
                
                //picks a random number 1 to 10
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if(i <= 25) {
                    direction = "up";
                }
                if(i > 25 && i <= 50) {
                    direction = "down";
                }
                if(i > 50 && i <=75) {
                    direction = "left";
                }
                if(i > 75 && i <= 100) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

     public void speak() {

        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null) {
            dialogueSet = 0;
        }

        
        // onPath = true;
     }

}
