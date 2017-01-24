import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.HashMap;

/**
 * Created by rei on 11/20/2016.
 */
public class SpriteRenderer {

    public BufferedImage sprite;
    public Vec2 offset;
    BufferedImageOp spriteOp;



    public SpriteRenderer(String image, int x, int y, int w, int h){
        sprite = BufferedImageManager.getImage(image, x, y, w, h);
        offset = new Vec2(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
    }





}
