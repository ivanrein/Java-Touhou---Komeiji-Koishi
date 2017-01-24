import java.util.Random;

/**
 * Created by rei on 11/22/2016.
 */

class ParticleScript extends GameComponent{
    float lifetime = 0.2f;
    public ParticleScript(GameObject obj){
        super(obj);
    }

    @Override
    public void update() {
        lifetime -= GamePanel.deltaTime;
        gameObject.position = gameObject.position.add(gameObject.velocity.mul((float)GamePanel.deltaTime));
        if(lifetime <= 0)
            gameObject.destroy();
    }

    @Override
    public void lateUpdate() {

    }
}
public class ParticleSystemScript extends GameComponent{
    boolean loop = true;
    float t = 0.1f;
    float lifetime = 0.1f;
    Random rand = new Random();
    public ParticleSystemScript(GameObject obj){
        super(obj);
    }

    @Override
    public void update() {


        t -= GamePanel.deltaTime;
        if(t <= 0) {
            t += 0.1f;
            GameObject obj = new GameObject();
            obj.position = new Vec2(gameObject.position);
            obj.renderer = new SpriteRenderer("assets/eff01.png", 113,1, 6,6);
            obj.velocity = new Vec2(rand.nextInt(50)-25, rand.nextInt(50) - 25);
            obj.scale = new Vec2(2,2);
            obj.components.add(new ParticleScript(obj));
        }
        if(!loop){
            lifetime -= GamePanel.deltaTime;
            if(lifetime <= 0)
                gameObject.destroy();
        }
    }

    @Override
    public void lateUpdate() {

    }
}
