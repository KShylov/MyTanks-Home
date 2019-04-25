package graphics;

import java.awt.image.BufferedImage;

/**
 * Created by KShilov on 07.02.17.
 */
public class SpriteSheet {
    private BufferedImage SHEET;
    private int spriteCount;
    private int scale;
    private int spritesINWidth;

    public SpriteSheet(BufferedImage SHEET, int spriteCount, int scale) {
        this.SHEET = SHEET;
        this.spriteCount = spriteCount;
        this.scale = scale;
        this.spritesINWidth = SHEET.getWidth()/scale;
    }
    public BufferedImage getSprite(int index){
        index = index%spriteCount;
        int x = index%spritesINWidth*scale;
        int y = index/spritesINWidth*scale;
        return SHEET.getSubimage(x,y,scale,scale);
    }
}
