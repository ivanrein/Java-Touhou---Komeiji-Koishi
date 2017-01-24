import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

/**
 * Created by rei on 11/20/2016.
 */
public class PlayerScript extends GameComponent implements  GameSpriteAnimatorDelegates{
    GameSpriteAnimator animator;
    final String IDLE = "idle";
    final String MOVELEFT = "moveleft";
    final String MOVERIGHT = "moveright";
    SpriteRenderer renderer = null;
    boolean movingLastFrame = true;
    float dieAnim = 0.1f;
    float reload = 0;
    public GameObject target;
    public PlayerScript(GameObject obj){
        super(obj);
        animator =  new GameSpriteAnimator(this);
        gameObject.renderer = new SpriteRenderer("assets/player00.png",0,0,31,48);
        animator.renderer = gameObject.renderer;
        animator.addSpriteForAnim(IDLE, gameObject.renderer.sprite);
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/player00.png", 32,0, 31,48));
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/player00.png", 65,0, 31,48));
        animator.addSpriteForAnim(IDLE, BufferedImageManager.getImage("assets/player00.png", 96,0, 31,48));

        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 0, 50, 31, 45));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 33, 50, 31, 48));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 66, 50, 33, 48));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 99, 50, 31, 48));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 131, 50, 32, 48));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 162, 50, 33, 48));
        animator.addSpriteForAnim(MOVELEFT, BufferedImageManager.getImage("assets/player00.png", 195, 50, 30, 48));

        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 0, 146, 29, 43));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 33, 146, 30, 44));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 63, 145, 30, 44));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 99, 145, 31, 48));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 130, 145, 29, 46));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 165, 145, 29, 44));
        animator.addSpriteForAnim(MOVERIGHT, BufferedImageManager.getImage("assets/player00.png", 200, 148, 28, 44));

        animator.setCurrentAnim(IDLE);
        GamePanel.input.put(new Integer(KeyEvent.VK_LEFT), false);
        GamePanel.input.put(new Integer(KeyEvent.VK_RIGHT), false);
        GamePanel.input.put(new Integer(KeyEvent.VK_Z), false);
        GamePanel.input.put(new Integer(KeyEvent.VK_UP), false);
        GamePanel.input.put(new Integer(KeyEvent.VK_DOWN), false);
        GamePanel.input.put(new Integer(KeyEvent.VK_SHIFT), false);
    }

    @Override
    public int indexOnLoopFinish(String name) {
        if(name == IDLE)
            return 0;
        else
            return 3;
    }

    @Override
    public float spriteTimeForAnim(String name, int index) {
        return 0.1f;
    }

    float invis = 0;
    float lastFrameInvis = 0;
    @Override
    public void update() {
        lastFrameInvis = invis;
        invis -= GamePanel.deltaTime;
        if(target == null){
            target = GamePanel.getGameObjectByName("koishi");
        }
        if(invis > 0){
            dieAnim -= GamePanel.deltaTime;
            if(dieAnim <= 0){
                gameObject.renderer = null;
                if(dieAnim <= -0.1f){
                    dieAnim = 0.1f;
                }
            }else{
                gameObject.renderer = renderer;
            }
        }else if(lastFrameInvis > 0)
            gameObject.renderer = renderer;
        animator.update();
        reload -= GamePanel.deltaTime;
        if(GamePanel.input.containsKey(new Integer(KeyEvent.VK_Z)) && GamePanel.input.get(new Integer(KeyEvent.VK_Z)) == true ){
            if(reload <= 0) {
                reload = 0.1f;
                fire();
            }
        }

        if((GamePanel.input.get(new Integer(KeyEvent.VK_LEFT)))
                || ( GamePanel.input.get(new Integer(KeyEvent.VK_RIGHT)))){
            if(!movingLastFrame) {
                if(GamePanel.input.get(new Integer(KeyEvent.VK_LEFT)))
                    animator.setCurrentAnim(MOVELEFT);
                if(GamePanel.input.get(new Integer(KeyEvent.VK_RIGHT)))
                    animator.setCurrentAnim(MOVERIGHT);
                movingLastFrame = true;
            }
        }else if(movingLastFrame){
            movingLastFrame = false;
            animator.setCurrentAnim(IDLE);
        }

        float speed = 300;
        Vec2 go = new Vec2(0,0);
        if(GamePanel.input.get(new Integer(KeyEvent.VK_SHIFT))) {
            speed = 150;

        }
        if(GamePanel.input.get(new Integer(KeyEvent.VK_LEFT))) {
            go.x = -speed;

        }
        if(GamePanel.input.get(new Integer(KeyEvent.VK_RIGHT))) {
            go.x = speed;


        }
        if(GamePanel.input.get(new Integer(KeyEvent.VK_UP)))
            go.y = -speed;
        if(GamePanel.input.get(new Integer(KeyEvent.VK_DOWN)))
            go.y = speed;
        if(go.magsqr() > 0) {
            go = go.norm();
            go = go.mul(speed);
        }

        gameObject.position = gameObject.position.add(go.mul((float)GamePanel.deltaTime));

    }

    @Override
    public void lateUpdate() {


    }

    @Override
    public void onCollision(GameObject obj) {
            if(obj.tag == "enemybl"){
                if(invis <= 0) {
                    AudioManager.play("player_mati", -10.f);
                    invis = 3;
                    GamePanel.score -= 10000;
                    renderer = gameObject.renderer;
                }
            }
    }

    void fire(){
        GameObject obj = new GameObject();
        obj.components.add(new GeneralBulletScript(obj));
        obj.velocity = new Vec2(0, -1500);
        obj.renderer = new SpriteRenderer("assets/player00.png", 0,99,68,17);
        obj.rotation = -(float)Math.PI/2;
        obj.position = new Vec2(gameObject.position);
        obj.position.x -= 10;
        obj = new GameObject();
        obj.components.add(new GeneralBulletScript(obj));
        obj.velocity = new Vec2(0, -1500);
        obj.renderer = new SpriteRenderer("assets/player00.png", 0,99,68,17);
        obj.rotation = -(float)Math.PI/2;
        obj.position = new Vec2(gameObject.position);
        obj.position.x += 10;

        obj = new GameObject();
        obj.components.add(new GeneralBulletScript(obj));
        ReimuBlueCardScript blsc = new ReimuBlueCardScript(obj);
        blsc.target = target;
        obj.components.add(blsc);
        obj.velocity = new Vec2(-707, -707);
        obj.renderer = new SpriteRenderer("assets/player00.png", 0,119,18,16);
        obj.rotation = -2.356f;
        obj.position = new Vec2(gameObject.position);

        obj = new GameObject();
        obj.components.add(new GeneralBulletScript(obj));
        blsc = new ReimuBlueCardScript(obj);
        blsc.target = target;
        obj.components.add(blsc);
        obj.velocity = new Vec2(707, -707);
        obj.renderer = new SpriteRenderer("assets/player00.png", 0,119,18,16);
        obj.rotation = -0.785f;
        obj.position = new Vec2(gameObject.position);
        AudioManager.play("player_serang");
    }
}
