package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc; /*controls the volume of sound */
    int volumeScale = 3;
    float volume;

    /**
     * This function has the sound files in an array
     */
    public Sound() {

        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/res/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/res/sound/receivedamage.wav");
//      soundURL[7] = getClass().getResource("/res/sound/swingweopon1.wav");
        soundURL[8] = getClass().getResource("/res/sound/levelup.wav");
        soundURL[9] = getClass().getResource("/res/sound/cursor.wav");
        soundURL[10] = getClass().getResource("/res/sound/burning.wav");
        soundURL[11] = getClass().getResource("/res/sound/cuttree.wav");
        soundURL[12] = getClass().getResource("/res/sound/gameover.wav");
        soundURL[13] = getClass().getResource("/res/sound/stairs.wav");
        soundURL[14] = getClass().getResource("/res/sound/sleep.wav");
        soundURL[15] = getClass().getResource("/res/sound/blocked.wav");
        soundURL[16] = getClass().getResource("/res/sound/parry.wav");
        soundURL[17] = getClass().getResource("/res/sound/speak.wav");
        soundURL[18] = getClass().getResource("/res/sound/Merchant.wav");
        soundURL[19] = getClass().getResource("/res/sound/Dungeon.wav");
        soundURL[20] = getClass().getResource("/res/sound/chipwall.wav");
        soundURL[21] = getClass().getResource("/res/sound/dooropen.wav");
        soundURL[22] = getClass().getResource("/res/sound/FinalBattle.wav");

    }

    //basic Audio functions
    public void setFile(int i) {

        try {
            
            //This is a format to open an audio file in java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); /*this line allows fc to control the volume */
            checkVolume();

        }catch(Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public void play() {
        //Start the Audio files
        clip.start();
    }
    public void loop() {
        //Loops infinitely
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        //Stops playing Audio files
        clip.stop();
    }
    public void checkVolume() {

        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume); /* sets the volume (-80 - 6) */
    }
}
