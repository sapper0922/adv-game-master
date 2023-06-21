package Main;

import javax.swing.JFrame;

public class Game {

    public static JFrame window;

    public static void main(String[] args) throws Exception {
        //Hellooo
        window = new JFrame();

        //Able to close the window clicking the X button on the top right corner
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Sets window Resizable to false
        window.setResizable(false);

        //Sets the Title of the window
        window.setTitle("2d Adventure.");

        //making variable called gamePanel which has all of the stuff from the class GamePanel
        GamePanel gamePanel = new GamePanel();

        //Adding gamePanel to the window
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn) {
            //setUndecorated removes the top bar
            window.setUndecorated(true);
        }

        //Causes the Window to be sized to fit perfered size
        window.pack();

        //Display the window at the center of the screen
        window.setLocationRelativeTo(null);

        //Set the Window's visibility
        window.setVisible(true);

        //Calls function setupGame in GamePanel class
        gamePanel.setupGame();

        //Calls function startGameThread in gamePanel
        gamePanel.startGameThread();

    }
}
