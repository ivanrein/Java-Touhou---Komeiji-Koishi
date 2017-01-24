/**
 * Created by rei on 11/21/2016.
 */
public class ReimuBlueCardScript extends GameComponent {
    GameObject target;
    float angularVelocity = 10;
    float velocity = 1500;
    public ReimuBlueCardScript(GameObject obj){
        super(obj);
    }

    @Override
    public void update() {
        if(target != null && !target.toBeDestroyed) {
            Vec2 dir = target.position.sub(gameObject.position);
            if (dir.magsqr() < 100) {
                gameObject.destroy();
                GameObject ps = new GameObject();
                ParticleSystemScript psc = new ParticleSystemScript(ps);
                ps.position = new Vec2(gameObject.position);
                psc.loop = false;
                ps.components.add(psc);
                return;
            }
            Vec2 velNorm = gameObject.velocity.norm();
            Vec2 directionNorm = dir.norm();
            angularVelocity += 30 * GamePanel.deltaTime;
            float dot = velNorm.dot(directionNorm);
            float angle = (float) Math.acos(dot);
            if (angle >= 0.1f) {

                float rotation = angularVelocity * (float) GamePanel.deltaTime;
                if (angle - rotation < 0)
                    rotation += angle - rotation;

                if (velNorm.y * directionNorm.x > velNorm.x * directionNorm.y) {
                    gameObject.rotation -= rotation;
                    gameObject.velocity = velNorm.rotate(-rotation).mul(velocity);
                } else {
                    gameObject.rotation += rotation;
                    gameObject.velocity = velNorm.rotate(rotation).mul(velocity);
                }
            }
        }else{
            target = null;
        }
    }

    @Override
    public void lateUpdate() {

    }
}
