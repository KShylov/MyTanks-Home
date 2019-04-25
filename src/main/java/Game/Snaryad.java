package Game;

import Game.Level.Contact;
import Game.Level.Level;
import Game.Level.Radar;
import Game.Level.TileType;
import Utils.Timer;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KShilov on 15.02.17.
 */
public class Snaryad  implements SnaryadInt {
    private static final int SPRITE_SCALE = 8;
    private static final int HS_S = SPRITE_SCALE / 2;
    private static final int SPRITES_PER_HEADINH = 1;


    private enum HeadSnaryad{
        NORTH(40*SPRITE_SCALE+1,13*SPRITE_SCALE - HS_S,1*SPRITE_SCALE,1*SPRITE_SCALE),
        EAST(43*SPRITE_SCALE,13*SPRITE_SCALE - HS_S,1*SPRITE_SCALE,1*SPRITE_SCALE),
        SOUTH(42*SPRITE_SCALE+1,13*SPRITE_SCALE - HS_S,1*SPRITE_SCALE,1*SPRITE_SCALE),
        WEST(41*SPRITE_SCALE,13*SPRITE_SCALE - HS_S,1*SPRITE_SCALE,1*SPRITE_SCALE);
        private int x, y, h, w;

        HeadSnaryad(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }
        protected BufferedImage texture(TextureAtlas atlas){
            return atlas.cut(x,y,w,h);
        }
    }
    private TankSkill levelTank;
    private boolean gilza;
    private HeadSnaryad headsnaryad;
    private Map<HeadSnaryad, Sprite> spriteMap;
    private float scale;
    private float speed;
    private float x,y;
    private int id;
    private int gameBoardCord_X, gameBoardCord_Y;
    private Level gameBoard;
    private List<Point> tilePoints;
    private Timer timeFlySnaryad;
    private Contact contactSnaryad;
    private Radar radar;
    private boolean dead = false;



    public Snaryad(int id,float x, float y, TextureAtlas atlas, float speed, int headings, Level gameBoard, TankSkill levelTank, Radar radar) {
        this.x = x + SPRITE_SCALE +2;
        this.y = y + SPRITE_SCALE +2;
        this.id = id;
        this.levelTank = levelTank;
        gilza = false;
        this.scale = GameStart.GAME_SCALE;
        this.speed = speed*2.5f;
        this.radar = radar;
        tilePoints = gameBoard.getTilePoint();
        this.gameBoard = gameBoard;
        setHeadSnaryad(headings);
        contactSnaryad = new Contact(axisDirectionXY(0),SPRITE_SCALE*scale);
        timeFlySnaryad = new Timer(7);
        spriteMap = new HashMap<Snaryad.HeadSnaryad, Sprite>() ;

        for(HeadSnaryad h: HeadSnaryad.values()){
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas),SPRITES_PER_HEADINH,SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet,scale);
            spriteMap.put(h,sprite);
        }
    }


    public void update() {
        moveSnaryad();
    }
    private void moveSnaryad(){
        if (isHitTheTarget()){gilza = true;}
        float newX = x, newY = y;
        if (gilza){
            newX = 0 - 2*(SPRITE_SCALE + HS_S) * scale ;
            newY = 0 - 2*(SPRITE_SCALE + HS_S) * scale ;
        }else {
            if (headsnaryad == HeadSnaryad.NORTH){
                if (canMove())
                    newY -= speed;
                else {
                    gilza = true;
                    setGBTileMap();
                }
            }
            if (headsnaryad == HeadSnaryad.EAST)
            {
                if (canMove())
                    newX += speed;
                else {
                    gilza = true;
                    setGBTileMap();
                }
            }
            if (headsnaryad == HeadSnaryad.SOUTH)
                if (canMove())
                    newY += speed;
                else {
                    gilza = true;
                    setGBTileMap();
                }

            if (headsnaryad == HeadSnaryad.WEST)
                if (canMove())
                    newX -= speed;
                else {
                    gilza = true;
                    setGBTileMap();
                }
        }
        fieldCheck();
        timeFlySnaryad.update();
        flyseTimeSnaryad();
        x = newX;
        y = newY;
    }
    private void flyseTimeSnaryad(){
        if (!getTimeFlySnaryad().isTimer()){
            gilza = true;
        }
    }
    private void fieldCheck(){
        // проверяет вышел ли снаряд за поле если да присваивает статус мертвого
        float newX = x;
        float newY = y;
        if (newX + SPRITE_SCALE * 2 + speed < 0){
            gilza = true;
        }
        if (newX >= (GameStart.widthField - SPRITE_SCALE * scale + speed)){
            gilza = true;
        }
        if (newY >= (GameStart.HEIGHT  - SPRITE_SCALE * scale + speed)){
            gilza = true;
        }
        if (newY + SPRITE_SCALE * scale + speed < 0){
            gilza = true;
        }
    }

    public void render(Graphics2D g)
    {
        spriteMap.get(headsnaryad).render(g,x ,y );
    }
    public boolean isGilza(){
        return gilza;
    }


    private boolean canMove(){

        Integer[][] tilemap = gameBoard.getTileMap();

        for(Point point: tilePoints){
            TileType tileType = TileType.fromNumeric(tilemap[point.y/ GameStart.TILE_SCALE_SIZE][point.x/ GameStart.TILE_SCALE_SIZE]);
            if (tachBrick(tileType,point)){
                setGBCordXY(point);
                return false;
            }

            if (tachMetal(tileType,point)){
                if (destroyBlok()) {
                    setGBCordXY(point);
                    return false;
                }else return false;
            }

        }
        if (!gameBoard.getMapTileListPoint().get(TileType.EAGLE).isEmpty()){
            if (tachEagle(gameBoard.getMapTileListPoint().get(TileType.EAGLE).get(0))) {
                setGBCordXY(gameBoard.getMapTileListPoint().get(TileType.EAGLE).get(0));
                setGBTileMapTile(TileType.EAGLE_DEAD.numeric());
                dead = true;
                return false;
            }
        }
        return true;
    }
    private boolean isHitTheTarget(){
        Point point = new Point();
        point.setLocation(x,y);
        return radar.isContactWithSnaryad(point,id);
    }
    private boolean destroyBlok(){
        if(levelTank.getLeveltank() == 3)
            return true;
        return false;
    }
    private void setGBCordXY(Point point){
        gameBoardCord_X = point.x / GameStart.TILE_SCALE_SIZE;
        gameBoardCord_Y = point.y / GameStart.TILE_SCALE_SIZE;
    }
    private void setGBTileMap(){
        if (dead)return;
        gameBoard.setTileMap(gameBoardCord_Y,gameBoardCord_X);
    }
    private void setGBTileMapTile(int n){

        gameBoard.setTileMap(gameBoardCord_X,gameBoardCord_Y,n);
    }
    private boolean tachBrick(TileType tileType,Point tilePoint){
        if (tileType == TileType.BRICK && tachCorect(tilePoint))
            return  true;
        return false;
    }
    private boolean tachEagle(Point tilePoint){
        if (tachEagleCorect(tilePoint))
            return  true;
        return false;
    }
    private boolean tachMetal(TileType tileType,Point tilePoint){
        if (tileType == TileType.METAL && tachCorect(tilePoint))
            return  true;
        return false;
    }
    private boolean tachCorect(Point tilePoint){
        boolean dot1;
        boolean dot2;
        Point atack = axisDirectionXY(2);
        dot1 = contactSnaryad.atackObstacle(atack,tilePoint);
        atack = axisDirectionXY(13);
        dot2 = contactSnaryad.atackObstacle(atack,tilePoint);
        if (dot1 || dot2)return true;
        else return false;

    }
    private boolean tachEagleCorect(Point point){
        contactSnaryad.getField().setScale(scale*SPRITE_SCALE*2);
        boolean rezult = tachCorect(point);
        contactSnaryad.getField().setScale(scale*SPRITE_SCALE);
        return rezult;
    }
    private Point axisDirectionXY(int bias){
        Point point = new Point();
        switch (headsnaryad){
            case NORTH:
                point.setLocation(x + bias,y );
                break;
            case EAST:
                point.setLocation(x +SPRITE_SCALE*scale,y + bias );
                break;
            case SOUTH:
                point.setLocation(x + bias,y + SPRITE_SCALE*scale);
                break;
            case WEST:
                point.setLocation(x ,y + bias );
                break;
        }
        return point;
    }

    // Направление движения снаряда
    private void setHeadSnaryad(int headings){
        switch (headings){
            case 1:
                headsnaryad = HeadSnaryad.NORTH;
                break;
            case 2:
                headsnaryad = HeadSnaryad.EAST;
                break;
            case 3:
                headsnaryad = HeadSnaryad.SOUTH;
                break;
            case 4:
                headsnaryad = HeadSnaryad.WEST;
                break;

        }

    }
    public Timer getTimeFlySnaryad() {
        return timeFlySnaryad;
    }

    @Override
    public int getIdSnaryad() {
        return 0;
    }

}
