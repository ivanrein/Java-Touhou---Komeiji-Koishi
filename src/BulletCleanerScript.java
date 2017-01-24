import java.util.Vector;

/**
 * Created by rei on 11/22/2016.
 */
public class BulletCleanerScript extends GameComponent {
    float radius = 5;
    float timePassed = 0;
    public BulletCleanerScript(GameObject obj){
        super(obj);
        obj.tag = "bulletcleaner";
    }

    @Override
    public void update() {
        timePassed += GamePanel.deltaTime;
        radius += 700 * (float)GamePanel.deltaTime;
        if(timePassed >= 2.f)
            gameObject.destroy();;
    }

    @Override
    public void lateUpdate() {
        Vector<GameObject> e = GamePanel.getListObjectByTag("enemybl");
        if(e != null){
            for (GameObject obj: e){
                if(obj.position.sub(gameObject.position).mag() - radius < 5){
                    for (GameComponent com : obj.components){
                        com.onCollision(gameObject);
                    }
                }
            }
        }
    }
}
