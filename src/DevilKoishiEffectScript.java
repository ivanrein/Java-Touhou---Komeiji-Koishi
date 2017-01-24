/**
 * Created by rei on 11/21/2016.
 */
public class DevilKoishiEffectScript extends GameComponent{
    float t = 0;
    float rotationVelocity = 0;
    float scaleSpeed = 0.5f;
    public DevilKoishiEffectScript(GameObject obj){
        super(obj);
        gameObject.scale = new Vec2(0.f, 0.f);

    }

    @Override
    public void update() {
        t += GamePanel.deltaTime;
        if(t > 0) {
            if(rotationVelocity < 10)
            rotationVelocity += 15 * GamePanel.deltaTime;
        }
        else{
            if(rotationVelocity > -10)
            rotationVelocity -= 15 * GamePanel.deltaTime;
        }
        if(t > 15)
            t = -15;

        if(scaleSpeed < 0){

            if(gameObject.scale.x <= 2.5f)
                scaleSpeed = -scaleSpeed;
        }else{
            if(gameObject.scale.x >= 3)
                scaleSpeed = -scaleSpeed;
        }

        gameObject.scale.x +=  scaleSpeed* GamePanel.deltaTime;
        gameObject.scale.y +=  scaleSpeed* GamePanel.deltaTime;

        gameObject.rotation += rotationVelocity * GamePanel.deltaTime;
    }

    @Override
    public void lateUpdate() {

    }
}
