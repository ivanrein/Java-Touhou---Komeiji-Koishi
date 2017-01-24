/**
 * Created by rei on 11/20/2016.
 */
public class KoishiFirstPhaseAttackScript extends GameComponent {

    int colorflip = 0;
    float baseRotation = 0;
    public KoishiFirstPhaseAttackScript(GameObject obj){
        super(obj);
    }

    @Override
    public void lateUpdate() {

    }

    @Override
    public void update() {

        float rotation = (float)(2f * Math.PI)/20;
        Vec2 baseVelocity = new Vec2(0,70);
        for (int i = 1; i <= 20; i++){
            GameObject bullet = new GameObject();
            bullet.components.add(new GeneralBulletScript(bullet));
            if(colorflip == 0)
                bullet.renderer = new SpriteRenderer("assets/etama.png", 130,64,12,16);
            else
                bullet.renderer = new SpriteRenderer("assets/etama.png", 160,64,12,16);
            Vec2 rotatedVel = baseVelocity.rotate(baseRotation + (rotation * i));
            bullet.position = new Vec2(gameObject.position);
            bullet.rotation = baseRotation + (rotation*i);
            bullet.velocity = rotatedVel;
            bullet.tag = "enemybl";

        }

        gameObject.destroy();
    }

    @Override
    public void onBecomeInvisible() {

    }
}
