import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

/**
 * Created by rei on 11/25/2016.
 */
public class MainMenuOptionScript extends GameComponent{

    boolean selected = false;
    BufferedImage original;
    BufferedImage replacement;
    boolean moving = false;
    Vec2 movingTo;
    public MainMenuOptionScript(GameObject obj){
        super(obj);
        original = gameObject.renderer.sprite;
        replacement = colorImage(original);
    }

    @Override
    public void update() {
        if(selected){
            gameObject.renderer.sprite = replacement;

        }else{
            gameObject.renderer.sprite = original;
        }


    }

    @Override
    public void lateUpdate() {

    }

    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageCopy = BufferedImageManager.getCopy(image);
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                Color originalColor = new Color(image.getRGB(xx, yy), true);
                if (originalColor.getAlpha() == 255) {
                    imageCopy.setRGB(xx, yy, Color.BLUE.getRGB());

                }
            }
        }
        return imageCopy;
    }
}
