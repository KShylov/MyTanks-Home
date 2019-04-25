package graphics;

import Utils.ResyrseLoader;

import java.awt.image.BufferedImage;

/**
 * Created by KShilov on 06.02.17.
 */
public class TextureAtlas {
    BufferedImage image;
    public TextureAtlas(String imageName){
        image = ResyrseLoader.loadImage(imageName);
    }
    public BufferedImage cut(int x,int y,int w,int h){
        return image.getSubimage(x,y,w,h);
    }
}
