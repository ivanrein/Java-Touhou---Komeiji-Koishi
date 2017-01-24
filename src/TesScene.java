import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Created by rei on 11/20/2016.
 */
class TesSceneWatcher extends GameComponent{
    float t = 0;
    boolean musicPlayed = false;
    MediaPlayer hartman;
    //Clip senya;
    float soundAnim = -40;
    float soundAnimHartman = -10;
    boolean playedSenya = false;
    public TesSceneWatcher(GameObject obj){
        super(obj);
        hartman = AudioManager.playMp3("assets/Musics/hartman");
        hartman.setVolume(0.3f);
        hartman.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                hartman.seek(hartman.getStartTime());
            }
        });

//        senya = AudioManager.play("senyakoishi", soundAnim, 146000000L);
//        senya.stop();
    }

    @Override
    public void update() {
        t += GamePanel.deltaTime;
        if(!musicPlayed && t > 1){
            musicPlayed = true;
            hartman.play();
        }
//        if(t >= 73){
//            if(!playedSenya) {
//
//                senya.start();
//
//                playedSenya = true;
//            }
//            else if(t <= 78) {
//
//                soundAnim += (30/5)*GamePanel.deltaTime;
//                soundAnimHartman -= (30/5) * GamePanel.deltaTime;
//                FloatControl gainControl =
//                        (FloatControl) senya.getControl(FloatControl.Type.MASTER_GAIN);
//                gainControl.setValue(soundAnim);
//                FloatControl hartmanControl =
//                        (FloatControl) hartman.getControl(FloatControl.Type.MASTER_GAIN);
//                hartmanControl.setValue(soundAnimHartman);
//            }else{
//                hartman.stop();
//                gameObject.destroy();
//            }
//        }
    }

    @Override
    public void lateUpdate() {

    }
}
public class TesScene extends GameScene {
    GameObject tes;
    GameObject player;
    public TesScene(){

        GameObject bg = new GameObject();
        bg.renderer = new SpriteRenderer("assets/bg.png",0,12,383, 383);
        bg.scale = new Vec2(3.f, 3.f);

        tes = new GameObject();
        tes.rotation = 0;
        tes.name = "koishi";
        tes.position.x = 0;
        tes.position.y = -100;
        tes.components.add(new KomeijiScript(tes));


        player = new GameObject();
        player.components.add(new PlayerScript(player));
        player.tag = "player";
//        GameObject ps = new GameObject();
//        ps.components.add(new ParticleSystemScript(ps));
        GameObject watcher = new GameObject();
        watcher.tag = "none";
        watcher.components.add(new TesSceneWatcher(watcher));
    }

    @Override
    public void update() {

    }
}
