import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Created by rei on 11/22/2016.
 */
public class AddComposite implements Composite
{
    float addRGBA[] = new float[4];
    public static AddComposite instance = new AddComposite();

    private static AddCompositeContext context = new AddCompositeContext();
    private AddComposite(){}

    @Override
    public CompositeContext createContext(ColorModel srcColorModel,ColorModel dstColorModel,RenderingHints hints)
    {
        return context;
    }

    //
    public static class AddCompositeContext implements CompositeContext
    {
        public AddCompositeContext(){}

        @Override
        public void compose(Raster src,Raster dstIn,WritableRaster dstOut)
        {
            int w = src.getWidth();
            int h = src.getHeight();

            int[] srcRgba = new int[4];
            int[] dstRgba = new int[4];

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    src.getPixel(x, y, srcRgba); // src: apa yang mau digambar
                    dstIn.getPixel(x, y, dstRgba); // dstin: apa yang sudah ada
                    if(srcRgba[3] == 255){
                        dstOut.setPixel(x, y, srcRgba);
                    }else{
                        for (int i = 0; i < 3; i++) {
                            dstRgba[i] += srcRgba[i];
                        }
                    }


                }
            }
        }

        @Override
        public void dispose(){}
    }
}