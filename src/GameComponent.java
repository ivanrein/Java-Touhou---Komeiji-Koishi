/**
 * Created by rei on 11/20/2016.
 */
public abstract class GameComponent {
    public GameObject gameObject;
    public GameComponent(GameObject obj){
        gameObject = obj;
    }
    public abstract void update();


    public abstract void lateUpdate();

    public void onBecomeInvisible(){

    }

    public void onCollision(GameObject obj){

    }
}
