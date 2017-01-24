import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by rei on 11/20/2016.
 */
interface GameSpriteAnimatorDelegates{

    int indexOnLoopFinish(String name); // -1 for stop at current index
    float spriteTimeForAnim(String name, int index);
}
public class GameSpriteAnimator {
    private float timePassedOfCurrentSprite = 0;
    private HashMap<String,Vector<BufferedImage>> anims;
    private String currentAnim;
    private int step = 1;
    private int currentIndex = 0;

    SpriteRenderer renderer;
    GameSpriteAnimatorDelegates delegate;

    public GameSpriteAnimator(GameSpriteAnimatorDelegates delegate){
        anims = new HashMap<String, Vector<BufferedImage>>();
        this.delegate = delegate;
    }

    public void addSpriteForAnim(String anim, BufferedImage sprite){
        if(!anims.containsKey(anim)){
            anims.put(anim, new Vector<BufferedImage>());
        }
        anims.get(anim).add(sprite);
    }

    public void setCurrentAnim(String name){
        currentAnim = name;
        currentIndex = 0;
        timePassedOfCurrentSprite = 0;
        renderer.sprite = anims.get(name).elementAt(0);
    }

    public String getCurrentAnim(){
        return currentAnim;
    }
    public void update(){
        timePassedOfCurrentSprite += GamePanel.deltaTime;
        if(timePassedOfCurrentSprite > delegate.spriteTimeForAnim(currentAnim, currentIndex)){
            currentIndex += step;
            if(currentIndex <= anims.get(currentAnim).size()){
                if(currentIndex == anims.get(currentAnim).size()){
                    int nextIndex = delegate.indexOnLoopFinish(currentAnim);
                    if(nextIndex >= 0)
                        currentIndex = nextIndex;
                    else{
                        currentIndex--;
                    }
                }
            }
            timePassedOfCurrentSprite = 0;
            renderer.sprite = getCurrentSprite();
        }
    }

    public BufferedImage getCurrentSprite(){
        return anims.get(currentAnim).elementAt(currentIndex);
    }

}
