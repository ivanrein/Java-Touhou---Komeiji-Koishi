import java.util.Vector;

/**
 * Created by rei on 11/20/2016.
 */
public class GeneralBulletScript extends GameComponent{
    public GeneralBulletScript(GameObject obj){
        super(obj);
    }
    static float grazeSoundCooldown = 0;
    float grazeCooldown = 0;
    static boolean hasSoundCooldown = false;
    float radius = 5;
    @Override
    public void lateUpdate() {
        hasSoundCooldown = false;
        if(gameObject.tag == "enemybl"){ // graze only cos no collider
            Vector<GameObject> e = GamePanel.getListObjectByTag("player");
            GameObject player = null;
            if(e != null)
                player = e.elementAt(0);

            if(player != null && player.position.sub(gameObject.position).mag() < 20){
                if(grazeCooldown <= 0) {
                    GamePanel.score += 100;
                    grazeCooldown += 0.1f;
                }
                if(grazeSoundCooldown <= 0){
                    AudioManager.play("ambil_item");
                    grazeSoundCooldown = 0.1f;
                }
            }
            if(player != null && player.position.sub(gameObject.position).mag()  < radius){
                for (GameComponent c : player.components){
                    c.onCollision(gameObject);
                }
                this.onCollision(player);

            }
        }
    }

    @Override
    public void update() {
        grazeCooldown -= GamePanel.deltaTime;
        if(!hasSoundCooldown) {
            grazeSoundCooldown -= GamePanel.deltaTime;
            hasSoundCooldown = true;
        }
        gameObject.position = gameObject.position.add(gameObject.velocity.mul((float) GamePanel.deltaTime));
    }

    @Override
    public void onCollision(GameObject obj) {
        if(obj.tag == "bulletcleaner" || obj.tag == "player"){
            gameObject.destroy();
            GameObject ps = new GameObject();
            ParticleSystemScript psc= new ParticleSystemScript(ps);
            ps.position = new Vec2(gameObject.position);
            psc.loop = false;
            ps.components.add(psc);
        }

    }

    @Override
    public void onBecomeInvisible() {
        gameObject.destroy();
    }
}
