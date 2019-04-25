package Game.Ganeration;

import Game.GameStart;
import Game.Level.Tile;
import Game.Level.TileType;
import Game.MyWriter;
import Io.Input;
import Utils.Timer;
import graphics.Sprite;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Icon {

    public final int TILE_SCALE = 16;
    private int scaleGame = 2;
    private Point viewTilePoint;
    private int numericTileType = 1;
    private Map<TileType,Tile> tileTypeTileMap;
    private TextureAtlas atlas;
    private Timer timer;
    private int bias = 5;

    public Icon(TextureAtlas atlas) {
        this.atlas = atlas;
        timer = new Timer();
        timer.setTimerMlSec(150);
        viewTilePoint = new Point();
        viewTilePoint.setLocation(41*TILE_SCALE,0);
        instMapTileType_Tile();
    }
    public void update(Input input){
        timer.update();
        if (!timer.isTimer())
            if (input.getKey(KeyEvent.VK_C))
        changeTile();


    }
    public void render(Graphics2D g){
        tileTypeTileMap.get(TileType.fromNumeric(numericTileType)).render(g,viewTilePoint.x,viewTilePoint.y);
        g.setColor(Color.BLACK);
        g.setFont(new Font("shrift",Font.BOLD,11));
        g.drawString("Сhaenge Tile", GameStart.widthField+bias,3*TILE_SCALE);
        g.drawString("press 'C'",GameStart.widthField+bias,4*TILE_SCALE);
        g.drawString("Save",GameStart.widthField+bias,6*TILE_SCALE);
        g.drawString("press 'S'",GameStart.widthField+bias,7*TILE_SCALE);
        g.drawString("Start",GameStart.widthField+bias,9*TILE_SCALE);
        g.drawString("press 'R'",GameStart.widthField+bias,10*TILE_SCALE);
        g.drawString("Chaenge",GameStart.widthField+bias,12*TILE_SCALE);
        g.drawString("scale",GameStart.widthField+bias,13*TILE_SCALE);
        g.drawString("press 1 or 2",GameStart.widthField+bias,14*TILE_SCALE);
    }
    private void upNumeric() {
        if (numericTileType < 5)
            numericTileType++;
        timer.startTimer();
    }
    private void downNumeric(){
        if (numericTileType > 0)
            numericTileType--;
        timer.startTimer();
    }
    private void changeTile(){
        if (numericTileType < 5)
            numericTileType++;
        else
            numericTileType = 0;
        timer.startTimer();
    }

    public int getNumericTileType() {
        return numericTileType;
    }

    private void instMapTileType_Tile(){
        tileTypeTileMap = new HashMap<TileType, Tile>();

        tileTypeTileMap.put(TileType.BRICK,new Tile(atlas.cut(16 * TILE_SCALE,0 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.BRICK, scaleGame));
        tileTypeTileMap.put(TileType.METAL,new Tile(atlas.cut(16 * TILE_SCALE,1 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.METAL, scaleGame));
        tileTypeTileMap.put(TileType.WATER,new Tile(atlas.cut(16 * TILE_SCALE,2 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.WATER, scaleGame));
        tileTypeTileMap.put(TileType.GRASS,new Tile(atlas.cut(17 * TILE_SCALE,2 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.GRASS, scaleGame));
        tileTypeTileMap.put(TileType.ICE,new Tile(atlas.cut(18 * TILE_SCALE,2 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.ICE, scaleGame));
        tileTypeTileMap.put(TileType.EMPTY,new Tile(atlas.cut(18 * TILE_SCALE,3 * TILE_SCALE,TILE_SCALE,TILE_SCALE),
                TileType.EMPTY, scaleGame));
    }
}
