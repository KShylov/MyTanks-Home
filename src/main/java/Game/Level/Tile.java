package Game.Level;

import Utils.Utiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by KShilov on 09.02.17.
 */
public class Tile {
    private BufferedImage bufferedImage;
    private TileType type;

    public Tile(BufferedImage image,TileType type, int scale) {
        this.type = type;
        bufferedImage = Utiles.resiaze(image,image.getWidth() * scale,image.getHeight()*scale);
    }
    public TileType type(){
        return type;
    }
    public void render(Graphics2D g,int x, int y){
        g.drawImage(bufferedImage,x,y,null);
    }
}
