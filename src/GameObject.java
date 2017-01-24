import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Vector;

/**
 * Created by rei on 11/20/2016.
 */
public final class GameObject {
    public boolean toBeDestroyed = false;
    public Vec2 position; // position relative to parent, or world if parent is null
    public Vec2 velocity;
    public Vec2 scale;
    public float rotation;
    public GameObject parent;
    public String name = "";
    public String tag = "default";
    Composite composite;
    SpriteRenderer renderer;
    AffineTransform at;
    public Vector<GameComponent> components;
    public GameObject(){
        at = new AffineTransform();
        components = new Vector<GameComponent>();
        position = new Vec2();
        velocity = new Vec2();
        scale = new Vec2(1,1);
        GamePanel.newGameObjects.add(this);
    }
    public Vec2 getParentOffset(){
        if(parent != null)
            return parent.position.add(parent.getParentOffset());
        else
            return new Vec2();
    }
    public void destroy(){
        toBeDestroyed = true;
    }
}
