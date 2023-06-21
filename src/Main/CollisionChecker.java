package Main;

import entity.Entity;

//For collision detection the collision solid are is smaller than tileSize.
/*
 * The player's solid area is used for collision detection.
 * We check for colision on all four corners of the player's solid area x and y coordinates + world location, to find the right tile in the world
 * The solid area is smaller then the tilesize, so we check the top left and top right coordinates are overlapping with any tiles, if they are set collision to true
 * 
 */
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    //sets collisionOn to true or false
    public void checkTile(Entity entity) {
        
        //entityLeftWorldX is the left side of the collision rectangle used for detecting collisions.  Collision rectangle is entity.solidArea
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        //convert entity collision variables into the right tilesize
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        //Adds the collision in the direction up, down, left, and right
        switch(entity.direction) {    
        case "up":
            //use the speed to predict where the player is going to be next frame
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
            //the top left tile
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            //the top right tile
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            //if either one collides, set collision to true
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            //use the speed to predict where the player is going to be next frame
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            //the bottom left tile
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            //the bottom right tile
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            //use the speed to predict where the player is going to be next frame
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            //the top left tile
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            //the bottom left tile
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            //use the speed to predict where the player is going to be next frame
            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            //the top right tile
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            //the bottom right tile
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
                break;
        }
    }

    //check if collision is happening with object
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gp.obj[1].length; i++) {

            if(gp.obj[gp.currentMap][i] != null) {

                //Get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //get the object's solid areas position
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                //checks where entity or player will be after they move
                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;   
                }
                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collision == true) {
                        entity.collisionOn = true;
                        System.out.println("d");
                    }
                    if(player == true) {
                        index = i;
                       
                    }
                }
                //reseting these values because these variable will keep increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }

        }

        //since this is an int function, I need to return an Integer
        return index;

    }
    
    // Npc or monster collision
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        for(int i = 0; i < target[1].length; i++) {

            if(target[gp.currentMap][i] != null) {

                //Get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //get the object's solid areas position
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                //checks where entity or player will be after they move
                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;   
                }
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }                    
                }
                //reseting these values because these variable will keep increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }

        }

        //since this is an int function, I need to return an Integer
        return index;

    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        //Get the entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        
        //get the object's solid areas position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        //checks where entity or player will be after they move
        switch(entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;   
        }
        if(entity.solidArea.intersects(gp.player.solidArea)){
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;

    }

}
