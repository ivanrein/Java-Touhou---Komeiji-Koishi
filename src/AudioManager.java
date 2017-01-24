import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.nio.file.Paths;

/**
 * Created by rei on 11/21/2016.
 */
public class AudioManager {
    public static Clip play(String music, float volumeAlter, long at){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/Musics/" +  music+".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.setMicrosecondPosition(at);

            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volumeAlter); // Reduce volume by 10 decibels.

            clip.start();
            return clip;
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return null;
    }
    public static Clip play(String music, float volumeAlter){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/Musics/" +  music+".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volumeAlter); // Reduce volume by 10 decibels.

            clip.start();
            return clip;
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return null;
    }
    public static Clip play(String music){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/Musics/" +  music+".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.f); // Reduce volume by 10 decibels.

            clip.start();
            return clip;
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return null;
    }
    public static Clip playLoop(String music){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/Musics/" +  music+".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            clip.start();
            return clip;
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return null;
    }


    public static void playCount(String music, int count){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/Musics/" +  music+".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);
            clip.loop(count);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    static{
        new JFXPanel();
    }
    public static MediaPlayer playMp3(String music){
        try{
            Media hit = new Media(Paths.get(music + ".mp3").toUri().toString());
            MediaPlayer pl = new MediaPlayer(hit);
            return pl;
        }catch (Exception e){

        }
        return null;
    }
}
