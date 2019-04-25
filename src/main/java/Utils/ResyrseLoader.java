package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by KShilov on 06.02.17.
 */
public class ResyrseLoader {
    public static final String PATH = "Res/";
    public static BufferedImage loadImage(String filename){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(PATH + filename));
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
