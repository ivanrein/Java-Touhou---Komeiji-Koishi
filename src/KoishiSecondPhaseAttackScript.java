/**
 * Created by rei on 11/20/2016.
 */
public class KoishiSecondPhaseAttackScript extends GameComponent {
    Vec2 originalVelocity;
    float timeBeforeVelocityShift = 0;
    float timeBeforeStop = 0;
    boolean stopped = false;
    public KoishiSecondPhaseAttackScript(GameObject obj){
        super(obj);
        originalVelocity = new Vec2(gameObject.velocity);
    }


    @Override
    public void update() {

        timeBeforeStop -= GamePanel.deltaTime;
        if(timeBeforeStop <= 0 && !stopped){
            gameObject.velocity = new Vec2();
            stopped = true;
        }


        if(stopped){

            timeBeforeVelocityShift -= GamePanel.deltaTime;
            if(timeBeforeVelocityShift <= 0) {
                if (originalVelocity.x < originalVelocity.y)
                    gameObject.velocity = new Vec2(originalVelocity.y, -originalVelocity.x).mul(0.7f);
                else
                    gameObject.velocity = new Vec2(-originalVelocity.y, originalVelocity.x).mul(0.7f);
                gameObject.rotation += Math.PI / 2;
                gameObject.components.remove(this);
            }
        }



    }

    @Override
    public void lateUpdate(){
    }

}
