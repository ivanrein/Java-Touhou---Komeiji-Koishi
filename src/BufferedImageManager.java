import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.HashMap;

/**
 * Created by rei on 11/20/2016.
 */
public class BufferedImageManager {
    static HashMap<String, BufferedImage> map;
    static {
        map = new HashMap<String, BufferedImage>();
    }
    static BufferedImage getCopy(BufferedImage sprite){
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        BufferedImage new_image = gfx_config.createCompatibleImage(
                sprite.getWidth(), sprite.getHeight(), sprite.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(sprite, 0, 0, null);
        g2d.dispose();
        // return the new optimized image
        return new_image;
    }
    static BufferedImage getImage(String image, int x, int y, int w, int h){
        if(!map.containsKey(image + x + "" + y + ""+  w + "" + h)) {
            try {
                BufferedImage sprite = ImageIO.read(new File(image));
                sprite = sprite.getSubimage(x, y, w, h);


                GraphicsConfiguration gfx_config = GraphicsEnvironment.
                        getLocalGraphicsEnvironment().getDefaultScreenDevice().
                        getDefaultConfiguration();

                if (sprite.getColorModel().equals(gfx_config.getColorModel())) {
                    map.put(image + x + "" + y + ""+  w + "" + h, sprite);
                    return sprite;
                }

                // image is not optimized, so create a new image that is
                BufferedImage new_image = gfx_config.createCompatibleImage(
                        sprite.getWidth(), sprite.getHeight(), sprite.getTransparency());

                // get the graphics context of the new image to draw the old image on
                Graphics2D g2d = (Graphics2D) new_image.getGraphics();

                // actually draw the image and dispose of context no longer needed
                g2d.drawImage(sprite, 0, 0, null);
                g2d.dispose();
                map.put(image + x + "" + y + ""+  w + "" + h, new_image);
                // return the new optimized image
                return new_image;
            } catch (Exception e) {
                System.out.println(image + " failed to load");
            }
        }

        BufferedImage bi = map.get(image + x + "" + y + "" + w + "" + h);
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

    }
}
