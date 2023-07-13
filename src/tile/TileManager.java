package tile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import Main.GamePanel;
import Main.UtilityTool;

import java.awt.Color;
import java.awt.Graphics2D;

public class TileManager {
    
    //variable with GamePanel stuff
    GamePanel gp;

    //array called tile
    public Tile[] tile;

    //array called mapTileNum
    public int mapTileNum[][][];

    boolean drawPath = true;

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager (GamePanel gp) {

        this.gp = gp;

        // Read Tile Data File
        InputStream is = getClass().getResourceAsStream("/res/maps/tileData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting tile names and collision info from the file
        String line;

        try {
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Initialize the tile array based on the filename size
        tile = new Tile[fileNames.size()]; 
        getTileImage();

        // Get the maxWorldCol & row
        is = getClass().getResourceAsStream("/res/maps/worldMap.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            //instantiate mapTileNum
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();
            
        }catch(IOException e) {
            System.out.println("exception!");
        }




        loadMap("src/res/maps/worldmap.txt",0);
        loadMap("src/res/maps/indoor01.txt",1);
        loadMap("src/res/maps/dungeon01.txt",2);
        loadMap("src/res/maps/dungeon02.txt",3);
    }

    public void getTileImage() {

        System.out.println("Image loading started");

        for(int i = 0; i < fileNames.size(); i++) {

            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            if(collisionStatus.get(i).equals("true")) {
                collision = true;
            }
            else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
        
        System.out.println("Image loading finished");

    }


    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName));

            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    //loadMap will read a text in filePath and populate the mapTileNum matrix with what is in the file
    public void loadMap(String filePath, int map) {

        try {
            //read a text file
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                //read a line of text in filePath
                String Line = br.readLine();

                while(col < gp.maxWorldCol) {

                    //splits a string with the space delimiter
                    String numbers[] = Line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            //close the br because you don't need them anymore
            br.close();

        }catch(Exception e) {

        }

    }

    //draw the mapTileNum on the screen
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
 
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            //rendering the map in the screen only.
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    //Draw Tiles on the screen
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        // if(drawPath) {
        //     g2.setColor(new Color(255,0,0,70));
    
        //     for(int i = 0; i <gp.pFinder.pathList.size(); i++) {
    
        //         int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
        //         int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
        //         int screenX = worldX - gp.player.worldX + gp.player.screenX;
        //         int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //         g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        //     }
        // }
    }
}