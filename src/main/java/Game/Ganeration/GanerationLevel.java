package Game.Ganeration;


import Game.GameStart;
import Game.Level.Level;
import Io.Input;
import Save.SaveMap;
import StartMain.ProcessGame;
import StartMain.Scena;
import Utils.ResyrseLoader;
import Utils.Timer;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GanerationLevel extends ProcessGame {
    private final String filenames = ResyrseLoader.PATH + "Other/MapsName.txt";
    public static final String TEMP_ADRESS = ResyrseLoader.PATH + "Other/Temp.lvl";
    private final int tankScale = 16;
    private final int tankScaleView = tankScale*3/2;
    private float scale = 3;
    private float tankScaleGame = tankScale * scale;
    private Sprite sprite ;
    private Icon icon;
    private Point tankPoint;
    private boolean select = false;
    private Timer timer;
    private Integer[][]gameBoard;
    private Level gameBoardLevel;
    private String nameMap;

    public GanerationLevel(TextureAtlas atlas) {
        gameBoard = new Integer[GameStart.HEIGHT/(tankScale*3/2)][GameStart.widthField/(tankScale*3/2)];
        initGameBoard();
        gameBoardLevel = new Level(atlas,gameBoard);
        timer = new Timer();
        timer.setTimerMlSec(150);
        timer.startTimer();
        icon = new Icon(atlas);
        tankPoint = new Point();
        tankPoint.setLocation(9 * tankScaleGame, GameStart.HEIGHT - tankScaleGame);
        SpriteSheet sheet = new SpriteSheet(atlas.cut(0* tankScale,0* tankScale,1*tankScale,1* tankScale),1,tankScale);
        sprite = new Sprite(sheet,scale);
    }
    public void update(Input input){

        timer.update();
        gameBoardLevel.upDate();
        if (!timer.isTimer()) {
            OnOffselect(input);
            setTileOnBoard(input);
            saveMap(input);
            exitInStartMenu(input);
            changeScale(input);
            runGameWithNewMap(input);
        }

            icon.update(input);

            moveTank(input);
            //System.out.println(tankPoint.x/tankScaleGame + "  " + tankPoint.y/tankScaleGame);

    }

    @Override
    public void render(Graphics2D graphics) {
        gameBoardLevel.render(graphics);
        gameBoardLevel.renderGrass(graphics);
        drowStatisticField(graphics);
        icon.render(graphics);
        drawRazmetka(graphics);
        sprite.render(graphics,tankPoint.x,tankPoint.y);
    }
    private void saveMap(Input input){
        if (input.getKey(KeyEvent.VK_S)) {
            saveMap();
            timer.startTimer();
        }
    }

    private void runGameWithNewMap(Input input){

        if (input.getKey(KeyEvent.VK_R)){
            SaveMap saveMap = new SaveMap();
            try {
                saveMap.save(gameBoard, TEMP_ADRESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameStart.adress = TEMP_ADRESS;
            GameStart.scena = Scena.GAME_RUN;
            GameStart.cheangeScena = true;
        }
    }
    private void addMapinList(){
        GameStart.nameFieldList.add(nameMap);
    }
    private void saveMap(){
        SaveMap saveMap = new SaveMap();
        try {
            saveMap.save(gameBoard);
            nameMap = saveMap.getName();
            saveMap.save(filenames," " + saveMap.getName());
            addMapinList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveTank(Input input){

        if (!timer.isTimer()){
            moveTankDown(input);
            moveTankLeft(input);
            moveTankRight(input);
            moveTankUp(input);
        }

    }
    private void setTileOnBoard(Input input){
        if (input.getKey(KeyEvent.VK_ENTER)){
            int w = tankPoint.x/tankScaleView;
            int h = tankPoint.y/tankScaleView;
            int n = icon.getNumericTileType();
        if (scale == 3){
            gameBoardLevel.setTileMap(w,h,n);
            gameBoardLevel.setTileMap(w,h+1,n);
            gameBoardLevel.setTileMap(w+1,h,n);
            gameBoardLevel.setTileMap(w+1,h+1,n);
        }else if (scale == 1){
            gameBoardLevel.setTileMap(w,h,n);
        }
        }
    }
    private void changeScale(Input input){
        if (input.getKey(KeyEvent.VK_1) && scale == 3) {
            scale = 1;
            sprite.reSize(scale*0.5f);
            tankScaleGame /= 2;
            System.out.println(tankScaleGame);
        }
        else if (input.getKey(KeyEvent.VK_2 ) && scale == 1) {
            scale = 3;
            sprite.reSize(scale-1);
            tankScaleGame *= (scale-1);
            System.out.println(tankScaleGame);
        }
    }
    private void moveTankUp(Input input){
        int y;
        int x = tankPoint.x;
        if (input.getKey(KeyEvent.VK_UP)){
            timer.startTimer();
            y = (int)(tankPoint.y - tankScaleGame);
            if(isCanMove(x,y))tankPoint.y = y;
            else return;
        }
    }
    private void moveTankDown(Input input){
        int y;
        int x = tankPoint.x;
        if (input.getKey(KeyEvent.VK_DOWN)){
            timer.startTimer();
            y = (int)(tankPoint.y + tankScaleGame);
            if(isCanMove(x,y))tankPoint.y = y;
            else return;
        }
    }
    private void moveTankLeft(Input input){
        int y = tankPoint.y;
        int x;
        if (input.getKey(KeyEvent.VK_LEFT)){
            timer.startTimer();
            x = (int)(tankPoint.x - tankScaleGame);
            if(isCanMove(x,y))tankPoint.x = x;
            else return;
        }
    }
    private void moveTankRight(Input input){
        int y = tankPoint.y;
        int x;
        if (input.getKey(KeyEvent.VK_RIGHT)){
            timer.startTimer();
            x =(int) (tankPoint.x + tankScaleGame);
            if(isCanMove(x,y))tankPoint.x = x;
            else return;
        }

    }
    private boolean isCanMove(int x,int y){
        if (x<0)return false;
        if ((x+tankScaleGame)> GameStart.widthField)return false;
        if (y<0)return false;
        if ((y+tankScaleGame)> GameStart.HEIGHT)return false;
        return true;
    }
    private void OnOffselect(Input input){
        if (input.getKey(KeyEvent.VK_C)){
            timer.startTimer();
            if (select)select = false;
            else select = true;
        }
    }
    private void drawRazmetka(Graphics2D graphics){

        int x = 0,y = 0,w = GameStart.widthField,h = GameStart.HEIGHT,step = tankScaleView*2;
        graphics.setColor(Color.WHITE);
        for (int i = 1; i < 13; i++) {
            graphics.drawLine(x, (y+i) * step, w, (y+i) * step);
            graphics.drawLine((x+i)*step, y, (x+i)*step,h);
        }
    }
    private void initGameBoard(){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = 0;
            }
        }
    }

}
