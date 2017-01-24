/**
 * Created by rei on 11/22/2016.
 */
public class HeartAttackScript extends GameComponent{
    float rotationalSpeed = 20;

    public HeartAttackScript(GameObject obj){
        super(obj);
    }

    @Override
    public void update() {


        gameObject.velocity= gameObject.velocity.rotate(rotationalSpeed * (float)GamePanel.deltaTime);
        gameObject.rotation += rotationalSpeed * (float) GamePanel.deltaTime;


    }

    @Override
    public void lateUpdate() {
//        GameObject obj = GamePanel.getGameObjectByName("koishi");
//        if(obj != null) {
//            if (gameObject.position.sub(obj.position).mag() < 5){
//                gameObject.components.remove(this);
//                //gameObject.components.add(new DisappearingHeartScript(gameObject));
//            }
//        }

    }
}
