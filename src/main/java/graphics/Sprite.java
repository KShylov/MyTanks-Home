package graphics;

import Utils.Utiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by KShilov on 07.02.17.
 */
public class Sprite {
    private SpriteSheet sheet;
    private float scale;
    private BufferedImage image ;

    public void reSize(float scale) {

        image = Utiles.resiaze(image,(int)(image.getWidth()*scale),(int)(image.getHeight()*scale));

    }

    public Sprite(SpriteSheet sheet, float scale) {
        this.sheet = sheet;
        this.scale = scale;
        image = sheet.getSprite(0);
        image = Utiles.resiaze(image,(int)(image.getWidth()*scale),(int)(image.getHeight()*scale));
    }

    public void render(Graphics2D g,float x, float y){

        g.drawImage(image,(int)x,(int)y,null);

    }
}
