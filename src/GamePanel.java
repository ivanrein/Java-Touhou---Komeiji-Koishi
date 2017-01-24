import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Vector;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * Created by rei on 11/20/2016.
 */
public class GamePanel extends JPanel implements KeyListener{
    public static double deltaTime;
    public Rectangle SideBlackRect;
    public Rectangle BottomBlackRect;

    static Vector<GameObject> newGameObjects;
    private static Vector<GameObject> gameObjects; // where update and late update called
    private static HashMap<String, Vector<GameObject>> gameObjectsByTag;
    public static HashMap<Integer, Boolean> input = new HashMap<Integer, Boolean>();
    static GameScene scene;
    BufferStrategy bs;
    private final int cameraLeft = -300;
    private final int cameraRight = 300;
    public static Vec2 cameraPosition = new Vec2();
    static int score = 0;
    public static Vector<GameObject> getListObjectByTag(String tag){
        return gameObjectsByTag.get(tag);
    }

    public GamePanel(){

        gameObjectsByTag = new HashMap<String, Vector<GameObject>>();
        newGameObjects = new Vector<GameObject>();
        addKeyListener(this);
        setFocusable(true);
        gameObjects = new Vector<GameObject>();
        scene = new MenuScene();
        SideBlackRect = new Rectangle(0,0);
        BottomBlackRect = new Rectangle(0,0);

    }

    public static void changeScene(GameScene sc){
        scene = sc;
    }
    public void update(){
        scene.update();

        for (int i = 0; i < newGameObjects.size();){
            GameObject obj = newGameObjects.remove(0);
            gameObjects.add(obj);

            if(!gameObjectsByTag.containsKey(obj.tag)) {
                Vector e = new Vector<GameObject>();
                gameObjectsByTag.put(obj.tag, e);
                e.add(obj);
            }else {
                Vector<GameObject> e = gameObjectsByTag.get(obj.tag);
                e.add(obj);
            }
        }

        int areaWidth = getParent().getWidth() - SideBlackRect.width * 2;

        for(int i = 0; i < gameObjects.size(); i++){
            for (int j = 0; j < gameObjects.elementAt(i).components.size(); ++j){
                gameObjects.elementAt(i).components.elementAt(j).update();
            }

            GameObject go = gameObjects.elementAt(i);
            if(go.renderer == null)
                continue;
            Vec2 parentOffset = go.getParentOffset();
            float zzx = (go.position.x + parentOffset.x) / (cameraRight-cameraLeft) * areaWidth;
            float zzy = (go.position.y + parentOffset.y) / (cameraRight-cameraLeft) * areaWidth;
            float translatedPosX = (zzx + getParent().getWidth()/2);
            float translatedPosY = (zzy + getParent().getHeight()/2);

            AffineTransform at = new AffineTransform();

            at.translate((double) (translatedPosX - cameraPosition.x * areaWidth/800 )+ go.renderer.offset.x * areaWidth / 800 * go.scale.x,
                    (double) (translatedPosY - cameraPosition.y * areaWidth/800) + go.renderer.offset.y * areaWidth / 800 * go.scale.y);


            at.rotate(go.rotation, -go.renderer.offset.x *go.scale.x * areaWidth / 800, -go.renderer.offset.y *go.scale.y * areaWidth / 800);
            at.scale(go.scale.x * areaWidth/800, go.scale.y * areaWidth/800);
            go.at = at;
        }

        for(int i = 0; i < gameObjects.size(); i++){

            boolean becomeInvisible = false;


            //TODO maybe: below should be position + parent offset
            if(gameObjects.elementAt(i).position.x < cameraLeft - 50
                    || gameObjects.elementAt(i).position.x > cameraRight + 50
                    || gameObjects.elementAt(i).position.y < cameraLeft - 50
                    || gameObjects.elementAt(i).position.y > cameraRight + 50)
                becomeInvisible = true;


            for (int j = 0; j < gameObjects.elementAt(i).components.size(); ++j){
                gameObjects.elementAt(i).components.elementAt(j).lateUpdate();

                if(becomeInvisible){
                    gameObjects.elementAt(i).components.elementAt(j).onBecomeInvisible();
                }
                if(gameObjects.elementAt(i).toBeDestroyed) {
                    //gameObjects.elementAt(i).renderer = null;
                    GameObject t = gameObjects.remove(i);
                    gameObjectsByTag.get(t.tag).remove(t);
                    i--;
                    break;
                }
            }


        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int areaWidth = getParent().getWidth() - SideBlackRect.width * 2;



        for(int i = 0; i < gameObjects.size(); i++){
            GameObject go = gameObjects.elementAt(i);
            if(go.renderer == null)
                continue;


            Graphics2D g2d = (Graphics2D) g;
            if(go.renderer.spriteOp != null) {
                BufferedImage r = go.renderer.spriteOp.filter(go.renderer.sprite, null);
                g2d.drawImage(r, go.at, null);
            }else {
                g2d.drawImage(go.renderer.sprite, go.at, null);
            }


        }

        g.setColor(Color.black);

        g.fillRect(0, 0, SideBlackRect.width, getParent().getHeight());
        g.fillRect(getParent().getWidth() - SideBlackRect.width, 0, SideBlackRect.width, getParent().getHeight());
        g.fillRect(0, getParent().getHeight() - BottomBlackRect.height, getParent().getWidth(), BottomBlackRect.height);
        g.fillRect(0, 0, getParent().getWidth(), BottomBlackRect.height);
        g.setColor(Color.white);

        DecimalFormat df = new DecimalFormat("#.0000000");
        double d = deltaTimeRender;
        g.setFont(new Font("Arial", 15, 15));
        g.drawString("FPS: " +df.format(d), 20, 20);
        g.drawString("SCORE: " + score, 50 , 50);
    }
    double deltaTimeRender = 0;

    public void play(){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        long t0 = System.nanoTime();
        long t1 = t0;
        deltaTime = 0f;
        while(true){

            if(1/deltaTime < 120) {


                deltaTimeRender = 1/deltaTime;
                t0 = System.nanoTime();
                update();
                repaint();

            }
            t1 = System.nanoTime();
            deltaTime = ((double)(t1-t0)/1000000000L);

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        input.put(new Integer(e.getKeyCode()), true);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        input.put(new Integer(e.getKeyCode()), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static GameObject getGameObjectByName(String name){
        for(GameObject i : gameObjects){
            if(i.name == name)
                return i;
        }
        return null;

    }
}
