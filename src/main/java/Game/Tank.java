package Game;

import Game.Effect.BronyaController;
import Game.Level.*;
import Io.Input;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tank  {
    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADINH = 1;

    private TankSkill levelTank;
    private Heading heading;
    private Map<Heading, Sprite> spriteMap;
    private float scale;
    private TextureAtlas atlas;
    private Heading[] masHead ;
    private Level gameBoard;
    private Shot playerShot;
    private AnimacionTank animacionTank;
    private ContactSide contactSide;
    private float x;
    private float y;
    private int id;
    private BonusWork bonusWork;
    private Radar radar;
    private BronyaController bronyaController;




    public Tank(int id,float x, float y, TextureAtlas atlas, float scale, float speed, Level gameBoard, Radar radar) {
        this.x = x;
        this.y = y;
        this.id = id;
        contactSide = new ContactSide(x,y,scale*SPRITE_SCALE);
        levelTank = new TankSkill(speed);
        masHead = Heading.values();
        this.gameBoard = gameBoard;
        animacionTank = new AnimacionTank(masHead);
        heading = animacionTank.TankMoveUpAnimacion(levelTank);
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.atlas = atlas;
        this.radar = radar;
        bronyaController = new BronyaController(atlas,scale,levelTank);
        playerShot = new Shot(id,levelTank,speed,gameBoard,atlas,radar);
        //bonusWork = new BonusBronya(atlas,gameBoard.getBonus(),levelTank);
        for(Heading h: Heading.values()){
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas),SPRITES_PER_HEADINH,SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet,scale);
            spriteMap.put(h,sprite);
        }
    }


    public void update(Input input) {

        //moveTank(input);
        if (bonusWork != null)
            bonusWork.upDate();
        bronyaController.update();
        playerShot.upDate();
        levelTank.update(input);
        animacionTank.updateChangeSprite();

    }
    public void render(Graphics2D g) {
        playerShot.render(g);
        spriteMap.get(heading).render(g, x, y);
        bronyaController.render(g,x,y);
        if ( bonusWork!=null )
            bonusWork.render(g,x,y);
    }
    protected void moveTankUp(){
        y -= levelTank.getSpeedTank();
    }
    protected void moveTankRight(){
            x += levelTank.getSpeedTank();
    }
    protected void moveTankDown(){
            y += levelTank.getSpeedTank();
    }
    protected void moveTankLeft(){
            x -= levelTank.getSpeedTank();
    }
    protected boolean isCanMoveLeft() {
        heading = animacionTank.TankMoveLeftAnimacion(levelTank);
        if (canMove((x - levelTank.getSpeedTank()), y) && x - levelTank.getSpeedTank() >= 0)
            return true;
           return false;
    }
    protected boolean isCanMoveRight() {
        heading = animacionTank.TankMoveRightAnimacion(levelTank);
        if (canMove((x + levelTank.getSpeedTank()), y) &&
                x + levelTank.getSpeedTank() <= GameStart.widthField - SPRITE_SCALE * scale)
            return true;
           return false;
    }
    protected boolean isCanMoveDown() {
        heading = animacionTank.TankMoveDownAnimacion(levelTank);
        if (canMove(x, (y + levelTank.getSpeedTank())) &&
                y +  levelTank.getSpeedTank() <= GameStart.HEIGHT - SPRITE_SCALE * scale)
            return true;
           return false;
    }
    protected boolean isCanMoveUp() {

        heading = animacionTank.TankMoveUpAnimacion(levelTank);
        if (canMove(x, (y - levelTank.getSpeedTank())) &&
                y - levelTank.getSpeedTank() >= 0)
            return true;
           return false;
    }
    public void setLocation(float xNew,float yNew){
        x = xNew;
        y = yNew;
    }
    public void setBonusWork(TileType tileType){
        startBonus(tileType);
    }
    protected void moveTank(Input input){

        if (input.getKey(KeyEvent.VK_UP)){
            if (isCanMoveUp())
            moveTankUp();

        }else if (input.getKey(KeyEvent.VK_RIGHT)){
            if (isCanMoveRight())
            moveTankRight();

        }else if (input.getKey(KeyEvent.VK_DOWN)){
            if (isCanMoveDown())
            moveTankDown();
        }else if (input.getKey(KeyEvent.VK_LEFT)){
            if (isCanMoveLeft())
            moveTankLeft();
        }
    }
    protected void nextShot(Input input){
        if (input.getKey(KeyEvent.VK_SPACE))
            shot();
    }
    protected void shot(){
        int head = headingCorect();
        playerShot.nextShot(head, x, y);

    }

    public TankSkill getLevelTank() {
        return levelTank;
    }

    // Взаимодействие танка с препятствиями
    private boolean canMove(float newX,float newY){
        boolean rezult;
        Point point = new Point();
        point.setLocation(newX,newY);
        startBonus();
        if (contactWithAllObstacle(point))rezult = false;else rezult = true;

        return rezult;
    }
    private boolean contactWithAllObstacle(Point atack){
        boolean brick = contactWithObstacle(gameBoard.getMapTileListPoint().get(TileType.BRICK),atack);
        boolean water = contactWithObstacle(gameBoard.getMapTileListPoint().get(TileType.WATER),atack);
        boolean metal = contactWithObstacle(gameBoard.getMapTileListPoint().get(TileType.METAL),atack);
        boolean entity = radar.isContactWithEntity(atack,id);
        if (brick || water || metal || entity)return true;else return false;
    }
    private boolean contactWithObstacle(ArrayList<Point> pointAtackList, Point obstacle){

        for(Point atack:pointAtackList){
            contactSide.setBoxAtackScale(GameStart.TILE_SCALE_SIZE);
            contactSide.setLocation(atack,obstacle);
            if (contactSide.atackObstacle())
                return true;
        }
        return false;
    }
    private boolean isBonusTach(){
        if  (!gameBoard.getBonus().isBonusActual())return false;
        Point bonuspoint = gameBoard.getBonus().getBonusPoint();
        Point atack = new Point();
        atack.setLocation(x,y);
        contactSide.setBoxAtackScale(SPRITE_SCALE * scale);
        contactSide.setLocation(atack,bonuspoint);
        return contactSide.atackObstacle();
    }
    private void startBonus(){
        if (isBonusTach())
            choiceBonus();
    }
    private void choiceBonus(){
        TileType bonusType = gameBoard.getBonus().getBonusType();
        startBonus(bonusType);

    }
    private void startBonus(TileType bonusType){
        if (bonusType == TileType.ZABOR)
            bonusWork = new BonusBuildZabor(gameBoard);
        if (bonusType == TileType.PISTOL)
            bonusWork = new BonusPistol(levelTank,gameBoard.getBonus());
        if (bonusType == TileType.STAR)
            bonusWork = new BonusStarUpSpeed(levelTank,gameBoard.getBonus());
        if (bonusType == TileType.TANKS)
            bonusWork = new BonusUpLife(levelTank,gameBoard.getBonus());
        if (bonusType == TileType.BRONYA)
            bonusWork = new BonusBronya(bronyaController,gameBoard.getBonus());
        if (bonusType == TileType.BOOMB)
            bonusWork = new BonusBomb(gameBoard.getBonus(),radar,id);
        if (bonusType == TileType.TIMER)
            bonusWork = new BonusTimer(radar,gameBoard.getBonus(),id);
    }

    public void bronyaOn(){
        bronyaController.bronyaOn();
    }
    //  Направление движения танка
    public int headingCorect(){
        int rezult = 0;

        for (int i = 0; i < masHead.length; i++) {
            if (masHead[i] == heading){
                int a = i + 1;
                if (a%4 == 0){return 4;}
                if (a%4 == 3){return 3;}
                if (a%4 == 2){return 2;}
                if (a%4 == 1){return 1;}
            }
        }

        return rezult;
    }
    public Point getPositionTank(){
        Point point = new Point();
        point.x = (int)x;
        point.y = (int)y;
        return point;
    }
    public int getCountShoot(){
        return playerShot.getCountShot();
    }
}
