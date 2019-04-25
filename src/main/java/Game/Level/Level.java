package Game.Level;

import Game.GameRun;
import Game.GameStart;
import Game.Games;
import StartMain.Scena;
import Utils.Utiles;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KShilov on 09.02.17.
 */
public class Level {
    public final int tile_Scale = 8;
    public final int tile_in_Game_Scale = GameStart.GAME_SCALE;
    public final int tile_Scale_size = tile_in_Game_Scale * tile_Scale;
    public final int Tilese_In_Width = GameStart.WIDTH/tile_Scale_size;
    public final int Tilese_In_Height = GameStart.HEIGHT/tile_Scale_size;

    private  Integer[][] gameBoardMassiv;
    private Map<TileType,Tile> mapTileType_Tile;
    private Map<TileType,ArrayList<Point>> mapTileListPoint;
    private boolean tileMapUpdate = false;
    private Bonus bonus;

    public void setEgle_dead(boolean egle_dead) {
        Egle_dead = egle_dead;
    }

    public boolean isEgle_dead() {

        return Egle_dead;
    }

    private boolean Egle_dead = false;

    public Level(TextureAtlas atlas) {

        gameBoardMassiv = new Integer[Tilese_In_Width][Tilese_In_Height];

        mapTileType_Tile = new HashMap<TileType, Tile>();

        mapTileType_Tile.put(TileType.BRICK,new Tile(atlas.cut(32 * tile_Scale,0 * tile_Scale,tile_Scale,tile_Scale),
                TileType.BRICK,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.METAL,new Tile(atlas.cut(32 * tile_Scale,2 * tile_Scale,tile_Scale,tile_Scale),
                TileType.METAL,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.WATER,new Tile(atlas.cut(32 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.WATER,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.GRASS,new Tile(atlas.cut(34 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.GRASS,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.ICE,new Tile(atlas.cut(36 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.ICE,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EMPTY,new Tile(atlas.cut(36 * tile_Scale,2 * tile_Scale,tile_Scale,tile_Scale),
                TileType.EMPTY,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EAGLE,new Tile(atlas.cut(38 * tile_Scale,4 * tile_Scale,tile_Scale*2,tile_Scale*2),
                TileType.EAGLE,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EAGLE_DEAD,new Tile(atlas.cut(40 * tile_Scale,4 * tile_Scale,tile_Scale*2,tile_Scale*2),
                TileType.EAGLE_DEAD,tile_in_Game_Scale));

        gameBoardMassiv = Utiles.levelParser(GameStart.adress);

        bonus = new Bonus(atlas, gameBoardMassiv);
        levelControl();
        instMap();
        reSetTilePoint();

    }
    public Level(TextureAtlas atlas,Integer[][] newBoardMas) {

        gameBoardMassiv = new Integer[Tilese_In_Width][Tilese_In_Height];

        mapTileType_Tile = new HashMap<TileType, Tile>();

        mapTileType_Tile.put(TileType.BRICK,new Tile(atlas.cut(32 * tile_Scale,0 * tile_Scale,tile_Scale,tile_Scale),
                TileType.BRICK,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.METAL,new Tile(atlas.cut(32 * tile_Scale,2 * tile_Scale,tile_Scale,tile_Scale),
                TileType.METAL,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.WATER,new Tile(atlas.cut(32 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.WATER,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.GRASS,new Tile(atlas.cut(34 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.GRASS,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.ICE,new Tile(atlas.cut(36 * tile_Scale,4 * tile_Scale,tile_Scale,tile_Scale),
                TileType.ICE,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EMPTY,new Tile(atlas.cut(36 * tile_Scale,2 * tile_Scale,tile_Scale,tile_Scale),
                TileType.EMPTY,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EAGLE,new Tile(atlas.cut(38 * tile_Scale,4 * tile_Scale,tile_Scale_size,tile_Scale_size),
                TileType.EAGLE,tile_in_Game_Scale));
        mapTileType_Tile.put(TileType.EAGLE_DEAD,new Tile(atlas.cut(40 * tile_Scale,4 * tile_Scale,tile_Scale_size,tile_Scale_size),
                TileType.EAGLE_DEAD,tile_in_Game_Scale));

        gameBoardMassiv = newBoardMas;

        bonus = new Bonus(atlas, gameBoardMassiv);
        levelControl();
        instMap();
        reSetTilePoint();

    }

    public void upDate (){
        if (tileMapUpdate){
            reSetTilePoint();
            tileMapUpdate = false;
        }
        if (GameStart.scena == Scena.GAME_RUN)
        bonus.upDate();
    }
    public void render(Graphics2D g){
        for (int i = 0; i < 8; i++) {
            if (i != TileType.GRASS.numeric()){
                renderAll(g,TileType.fromNumeric(i));
            }
        }
        if (GameStart.scena == Scena.GAME_RUN)
        bonus.render(g);
    }
    private void renderAll(Graphics2D g,TileType tileType){
        if (!mapTileListPoint.get(tileType).isEmpty()) {
            for (Point p : mapTileListPoint.get(tileType)) {
                mapTileType_Tile.get(tileType).render(g, p.x, p.y);
            }
        }
    }
    public void renderGrass(Graphics2D g){
        if (mapTileListPoint.get(TileType.GRASS) != null){
            for (Point p: mapTileListPoint.get(TileType.GRASS)){
                mapTileType_Tile.get(TileType.GRASS).render(g,p.x,p.y);
            }
        }
    }

    public void clearOldPoint(){
        for (int i = 0; i < mapTileListPoint.size(); i++) {
            TileType tileType = TileType.fromNumeric(i);
            mapTileListPoint.get(tileType).clear();
        }
    }
    private   void reSetTilePoint(){
        clearOldPoint();
        //System.out.println("eagle dead size = " + mapTileListPoint.get(TileType.EAGLE_DEAD).size());
        for (int i = 0; i < gameBoardMassiv.length; i++) {
            for (int j = 0; j < gameBoardMassiv[i].length; j++) {
                TileType tileType = TileType.fromNumeric(gameBoardMassiv[i][j]);
                if (tileType == TileType.EAGLE_DEAD)
                   System.out.println("x = " + j + " y = " + i );
                if (tileType.numeric() <= 7) {
                    mapTileListPoint.get(tileType).add(new Point(j * tile_Scale_size, i * tile_Scale_size));
                }
            }
        }
        //System.out.println("eagle dead size1 = " + mapTileListPoint.get(TileType.EAGLE_DEAD).size());
    }
    private boolean isTileTypeBonus(TileType tileType){
        switch (tileType){
            case STAR:return true;
            case TANKS:return true;
            case TIMER:return true;
            case ZABOR:return true;
            case BRONYA:return true;
            case PISTOL:return true;
            case BOOMB:return true;
            default:return false;
        }

    }
    public  void setTileMap(int width,int hight,int n) {

        gameBoardMassiv[hight][width] = n;
        tileMapUpdate = true;
    }
    public  void setTileMap(int width,int hight){

        gameBoardMassiv[hight][width] = 0;
        tileMapUpdate = true;
    }
    private void instMap(){
        mapTileListPoint = new HashMap<TileType, ArrayList<Point>>();
        mapTileListPoint.put(TileType.EMPTY,new ArrayList<Point>());
        mapTileListPoint.put(TileType.BRICK,new ArrayList<Point>());
        mapTileListPoint.put(TileType.METAL,new ArrayList<Point>());
        mapTileListPoint.put(TileType.WATER,new ArrayList<Point>());
        mapTileListPoint.put(TileType.GRASS,new ArrayList<Point>());
        mapTileListPoint.put(TileType.ICE,new ArrayList<Point>());
        mapTileListPoint.put(TileType.EAGLE,new ArrayList<Point>());
        mapTileListPoint.put(TileType.EAGLE_DEAD,new ArrayList<Point>());


    }
    public Bonus getBonus() {
        return bonus;
    }
    public ArrayList<Point> getTilePoint(){
        ArrayList<Point> list = new ArrayList<Point>();
        list.addAll(mapTileListPoint.get(TileType.METAL));
        list.addAll(mapTileListPoint.get(TileType.BRICK));
        list.addAll(mapTileListPoint.get(TileType.WATER));
        list.addAll(mapTileListPoint.get(TileType.ICE));
        return list;
    }
    private void levelControl(){
        cheangeOnNull(4);
        cheangeOnNull(gameBoardMassiv[0].length/2-1);
        cheangeOnNull(gameBoardMassiv[0].length-6);
        instZaborWithEgle();
    }
    private void instZaborWithEgle(){
        int h = gameBoardMassiv.length;
        int w = gameBoardMassiv[0].length;
        int centr = w/2;
        setTileMap(centr-1,h-2,TileType.EAGLE.numeric());
        for (int i = centr-2; i < centr+1; i++) {
            setTileMap(i,h-3,TileType.BRICK.numeric());
        }
        for (int i = h - 3; i < h; i++) {
            setTileMap(centr-2,i,TileType.BRICK.numeric());
            setTileMap(centr+1,i,TileType.BRICK.numeric());
        }
    }
    private void cheangeOnNull(int k){
        for (int i = k; i < k+2; i++) {
            for (int j = 0; j < 2; j++) {
                gameBoardMassiv[j][i] = 0;
            }
        }
    }

    public  Integer[][] getTileMap() { return gameBoardMassiv; }

    public Map<TileType, ArrayList<Point>> getMapTileListPoint() {
        return mapTileListPoint;
    }
}
