/**
 * Created by rei on 11/25/2016.
 */
public class MenuObjectMoveScript extends GameComponent {

    boolean moving = false;
    Vec2 movingTo;
    public MenuObjectMoveScript(GameObject obj){
        super(obj);
    }

    @Override
    public void update() {

        if(moving){
            Vec2 direction = new Vec2(movingTo.sub(gameObject.position)).norm();
            gameObject.position = gameObject.position.add(direction.mul(3000 * (float) GamePanel.deltaTime));
            if(movingTo.sub(gameObject.position).mag() < 10){
                moving = false;
            }
        }

    }

    public void move(Vec2 by){
        movingTo = gameObject.position.add(by);
        moving = true;
    }

    @Override
    public void lateUpdate() {

    }
}
