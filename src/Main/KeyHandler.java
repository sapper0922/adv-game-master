package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener is able to receive inputs from keyboards
public class KeyHandler implements KeyListener{

    GamePanel gp;
    //Create Boolean Variable upPressed, downPressed, leftPressed, and rightPressed
    public Boolean
     upPressed = false,
     downPressed = false,
     leftPressed = false,
     rightPressed = false,
     enterPressed = false,
     shotKeyPressed = false;

     //Debug
     boolean showDebugText = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        //getKeyCode returns a number of the key that was pressed EX. if you type A, getKeyCode wil return 65, if you type C, getKeyCode will return 67
        int code = e.getKeyCode();
        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        //Play State
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        //Pause State
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        //Dialogue State
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        //Character State
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
        //Options State
        else if(gp.gameState == gp.optionsState) {
            optionsState(code);
        }
        //Game Over State
        else if(gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        //Trade State
        else if(gp.gameState == gp.tradeState) {
            tradeState(code);
        }
    }

    public void titleState(int code) {
        //checks if W is pressed
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        //checks if S is pressed
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.player.setDefaultValues();
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1) {
                //ADD LATER
            }
            if(gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code) {
        //checks if W is pressed
        if(code == KeyEvent.VK_W) {
            //if W is pressed, upPressed will be true
            upPressed = true; 
        }
        //checks if S is pressed
        if(code == KeyEvent.VK_S) {
            //if S is pressed, downPressed will be true
            downPressed = true;
        }
        //checks if A is pressed
        if(code == KeyEvent.VK_A) {
            //if A is pressed, leftPressed will be true
            leftPressed = true;
        }
        //chekcs if D is pressed
        if(code == KeyEvent.VK_D) {
            //if D is pressed, rightPressed will be true
            rightPressed = true;
        }
        //checks if P is pressed
        if(code == KeyEvent.VK_P) {                
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER) {                
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F) {                
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {                
            gp.gameState = gp.optionsState;
        }
        //Debug
        if(code == KeyEvent.VK_T) {
            if(showDebugText == false) {
                showDebugText = true;
            }
            else if(showDebugText == true) {
                showDebugText = false;
            }
        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P) {                
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    public void optionsState(int code) {

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }
    public void gameOverState(int code) {

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    public void tradeState(int code) {

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSE(9);
            }
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }
    public void playerInventory(int code) {

        if(code == KeyEvent.VK_W) {
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }  
        }
    }
    public void npcInventory(int code) {

        if(code == KeyEvent.VK_W) {
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }  
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }  
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            //if W is pressed, upPressed will be false
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            //if S is pressed, downPressed will be false
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            //if A is pressed, leftPressed will be false
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            //if D is pressed, rightPressed will be false
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F) {                
            shotKeyPressed = false;
        }
        
    }
    
}