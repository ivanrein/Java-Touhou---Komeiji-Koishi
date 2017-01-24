import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.LookupOp;

/**
 * Created by rei on 11/25/2016.
 */
public class MenuScene extends GameScene {
    int currentlySelected = 0;
    MainMenuOptionScript scripts[] = new MainMenuOptionScript[3];
    MenuObjectMoveScript movingScripts[] = new MenuObjectMoveScript[7];
    float changeMenuReload = 0;
    float enterReload = 0;
    MediaPlayer player;
    boolean hasMusicPlayed = false;
    float timePassed = 0;


    //camera control
    Vec2 cameraOriginalPosition;
    Vec2 cameraDirection;
    boolean movingCamera = false;
    float cameraTime = 0;
    float cameraElapsedTime = 0;

    public MenuScene(){
        GameObject bg = new GameObject();
        bg.renderer = new SpriteRenderer("assets/menubg.jpg",31,16,1227, 685);
        bg.scale = new Vec2(800f/685f, 800f/685f);
        bg.position = new Vec2(100, 0);
        MenuObjectMoveScript mv = new MenuObjectMoveScript(bg);
        bg.components.add(mv);
        movingScripts[0] = mv;

        GameObject top = new GameObject();
        top.renderer = new SpriteRenderer("assets/menutop.png", 8, 10, 180, 82);
        top.position = new Vec2(-100, -200);
        top.scale = new Vec2(2,2);
        mv = new MenuObjectMoveScript(top);
        top.components.add(mv);
        movingScripts[6] = mv;
        final GameObject play = new GameObject();
        play.renderer = new SpriteRenderer("assets/menutext.png", 26,13, 90, 52);
        play.scale = new Vec2(1, 1);
        play.position = new Vec2(-100, -100);
        MainMenuOptionScript sc = new MainMenuOptionScript(play);
        sc.selected = true;
        scripts[0] = sc;
        play.components.add(sc);
        mv = new MenuObjectMoveScript(play);
        play.components.add(mv);
        movingScripts[1] = mv;

        GameObject credit = new GameObject();
        credit.renderer = new SpriteRenderer("assets/menutext.png", 26,72, 127, 39);
        credit.scale = new Vec2(1, 1);
        credit.position = new Vec2(-100, 0);
        sc = new MainMenuOptionScript(credit);
        sc.selected = false;
        scripts[1] = sc;
        credit.components.add(sc);
        mv = new MenuObjectMoveScript(credit);
        credit.components.add(mv);
        movingScripts[2] = mv;

        GameObject exit = new GameObject();
        exit.renderer = new SpriteRenderer("assets/menutext.png", 29,140, 78, 39);
        exit.scale = new Vec2(1, 1);
        exit.position = new Vec2(-100, 100);
        sc = new MainMenuOptionScript(exit);
        sc.selected = false;
        scripts[2] = sc;
        exit.components.add(sc);
        mv = new MenuObjectMoveScript(exit);
        exit.components.add(mv);
        movingScripts[3] = mv;


        GameObject zun = new GameObject();
        zun.renderer = new SpriteRenderer("assets/credit.png",30,36,332,136);
        zun.position = new Vec2(500, -200);
        mv = new MenuObjectMoveScript(zun);
        zun.components.add(mv);
        movingScripts[4] = mv;

        GameObject ivan = new GameObject();
        ivan.renderer = new SpriteRenderer("assets/credit.png",169,267,415,138);
        ivan.position = new Vec2(450, 150);
        mv = new MenuObjectMoveScript(ivan);
        ivan.components.add(mv);
        movingScripts[5] = mv;



        player = AudioManager.playMp3("assets/Musics/Ten Desires");
        player.setVolume(0.3f);
        player.setStartTime(Duration.millis(14250));

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(player.getStartTime());
            }
        });

    }
    @Override
    public void update() {
        timePassed += GamePanel.deltaTime;
        changeMenuReload -= GamePanel.deltaTime;
        enterReload -= GamePanel.deltaTime;

        if(movingCamera){
            cameraElapsedTime += GamePanel.deltaTime;
            if(cameraElapsedTime > cameraTime) {
                cameraElapsedTime = cameraTime;
                movingCamera = false;
            }
            GamePanel.cameraPosition = cameraOriginalPosition.add(cameraDirection.mul(cameraElapsedTime/cameraTime));
        }

        if(!hasMusicPlayed &&  timePassed >= 1 ){
            player.play();
        }
        if(GamePanel.input.containsKey(new Integer(KeyEvent.VK_DOWN))
                && GamePanel.input.get(new Integer(KeyEvent.VK_DOWN))
                && changeMenuReload <= 0 && currentlySelected != 4){
            currentlySelected++;
            if(currentlySelected == 3) {
                currentlySelected = 0;
            }
            for (int i = 0; i < 3; i++){
                if(i == currentlySelected)
                    scripts[i].selected = true;
                else scripts[i].selected = false;
            }
            changeMenuReload = 0.2f;
        }
        else if(GamePanel.input.containsKey(new Integer(KeyEvent.VK_UP))
                && GamePanel.input.get(new Integer(KeyEvent.VK_UP))
                && changeMenuReload <= 0 && currentlySelected != 4){
            currentlySelected--;
            if(currentlySelected == -1) {
                currentlySelected = 2;
            }
            for (int i = 0; i < 3; i++){
                if(i == currentlySelected)
                    scripts[i].selected = true;
                else scripts[i].selected = false;
            }
            changeMenuReload = 0.2f;
        }
        else if(GamePanel.input.containsKey(new Integer(KeyEvent.VK_ENTER))
                && GamePanel.input.get(new Integer(KeyEvent.VK_ENTER))
                && enterReload <= 0){
            enterReload = 0.3f;
            if(currentlySelected == 0){
                player.stop();
                GamePanel.changeScene(new TesScene());
            }else if(currentlySelected == 1){
                currentlySelected = 4;
//                for (int i = 0; i < movingScripts.length; i++){
//                    movingScripts[i].move(new Vec2(-330, 0));
//                }
                // move camera by 330
                moveCamera(new Vec2(430, 0),0.2f);
            }else if(currentlySelected == 2){
                System.exit(0);
            }else if(currentlySelected == 4){
                currentlySelected = 1;
//                for (int i = 0; i < movingScripts.length; i++){
//                    movingScripts[i].move(new Vec2(330, 0));
//                }
                // move camera by -330
                moveCamera(new Vec2(-430,0), 0.2f);
            }
        }
    }

    public void moveCamera(Vec2 moveBy, float inSeconds){
        movingCamera = true;
        cameraOriginalPosition = new Vec2(GamePanel.cameraPosition);
        cameraDirection = moveBy;
        cameraTime = inSeconds;
        cameraElapsedTime = 0;
    }
}
