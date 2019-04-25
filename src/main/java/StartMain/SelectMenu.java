package StartMain;

import Game.GameStart;
import Game.MyWriter;
import Io.Input;
import Utils.Timer;
import View.Display;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SelectMenu extends ProcessGame{
    private final String ADRESS = "Res/";
    private final String PETH = "Temp/";
    private final String lvl = ".lvl";
    private ArrayList<String> nameFieldList;
    private final int tankScale = 16;
    private MyWriter myWriter;
    private int step = 25, x = 500,y =275;
    private int nomberStringPoint = 0;
    private int indexNameList = 0;
    private int visa;
    private ArrayList<Point> stringlistPoint ;
    private Timer timer;
    private boolean chaengePosition = false;
    private Sprite sprite ;

    public SelectMenu(TextureAtlas atlas) {
        timer = new Timer();
        timer.setTimerMlSec(150);
        timer.startTimer();
        myWriter = new MyWriter();
        stringlistPoint = new ArrayList<Point>();
        nameFieldList = GameStart.nameFieldList;
        SpriteSheet sheet = new SpriteSheet(atlas.cut(2* tankScale,1* tankScale,1*tankScale,1* tankScale),1,tankScale);
        sprite = new Sprite(sheet,1.8f);
        setPointList();
        //tempInst();
        setVisa();

    }

    public void update(Input input){

        timer.update();
        if (timer.isTimer())return;
        else {

            exitInStartMenu(input);
            monipuletion(input);
        }
    }
    public void render(Graphics2D graphics) {
        selectMenu(graphics);
        drowTank();
    }
    private void instCardAdress(int index){

        GameStart.adress = ADRESS + PETH + nameFieldList.get(index) + lvl;
    }

    private void monipuletion(Input input){
        if (input.getKey(KeyEvent.VK_ENTER))
        {
            instCardAdress(indexNameList);
            GameStart.scena = Scena.GAME_RUN;
            GameStart.cheangeScena = true;
        }

        if (input.getKey(KeyEvent.VK_UP))
        {
            if (nomberStringPoint > 0){
                nomberStringPoint--;
                y-=step;
                chaengePosition = true;
            }
            if (indexNameList > 0){
                indexNameList--;
                chaengePosition = true;
                System.out.println(indexNameList);
            }
        }
        if (input.getKey(KeyEvent.VK_DOWN))
        {
            if (nomberStringPoint < visa-1){
                nomberStringPoint++;
                chaengePosition = true;
                y+=step;
            }
            if (indexNameList < nameFieldList.size()-1){
                indexNameList++;
                chaengePosition = true;
                System.out.println(indexNameList + " " + nomberStringPoint);
            }
        }
        if (chaengePosition) {
            timer.startTimer();
            chaengePosition = false;
        }
    }

    private void selectMenu(Graphics2D graphics){

        myWriter.select_levelDraw(graphics);
        if (nameFieldList == null)return;
        int start = indexNameList-nomberStringPoint;
        for (int i = start; i < visa + start; i++) {
            int a = stringlistPoint.get(i - start).x;
            int b = stringlistPoint.get(i-start).y;
            if (nomberStringPoint != i - start )
                myWriter.drowNextStringOff(graphics,nameFieldList.get(i),a,b);
            else
                myWriter.drowNextStringOn(graphics,nameFieldList.get(i),a,b);
        }

    }
    private void setPointList(){
        int step = 25, x = 300,y =300;

        for (int i = 0; i < 10; i++) {
            Point point = new Point();
            point.setLocation(x,y);
            stringlistPoint.add(point);
            y += step;
        }
    }
    /*private void tempInst(){
        String name = "Map ";
        nameFieldList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            nameFieldList.add(name+i);
        }
    }*/
    private void setVisa(){
        if (nameFieldList.size() < stringlistPoint.size()){
            visa = nameFieldList.size();
        }else
        {
            visa = stringlistPoint.size();
        }
    }
    private void drowTank(){
        sprite.render(Display.getGraphics(),x,y);
    }
}
