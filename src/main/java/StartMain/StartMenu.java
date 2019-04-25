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

public class StartMenu implements MyRunning{
    private final String ADRESS = "Res/";
    private ArrayList<String> nameFieldList;
    private final int tankScale = 16;
    private MyWriter myWriter;
    private int step = 25, x = 500,y =275;
    private int nomber = 0;
    private ArrayList<Point> stringlistPoint ;
    private Timer timer;
    private boolean chaengePosition = false;
    private Sprite sprite ;
    private boolean running = true;
    private int workSizeNameFieldList;

    public StartMenu(TextureAtlas atlas) {
        timer = new Timer();
        timer.setTimerMlSec(100);
        myWriter = new MyWriter();
        stringlistPoint = new ArrayList<Point>();
        nameFieldList = new ArrayList<String>();
        SpriteSheet sheet = new SpriteSheet(atlas.cut(2* tankScale,1* tankScale,1*tankScale,1* tankScale),1,tankScale);
        sprite = new Sprite(sheet,1.8f);
        instNameList();
        setPointLisr();

    }

    public void update(Input input){
        if (!running)return;
        canContinius();
        timer.update();
        if (timer.isTimer())return;
        else {
            monipuletion(input);
        }
    }
    private void instNameList(){
        nameFieldList.add("Select map");
        nameFieldList.add("Create new Map");
        nameFieldList.add("Continius");
    }
    private void monipuletion(Input input){
        if (input.getKey(KeyEvent.VK_ENTER))
        {   if (nomber == 0) {
            GameStart.scena = Scena.SELECT_MENU;
            GameStart.cheangeScena = true;
        }else if(nomber == 1) {
            GameStart.scena = Scena.CREATE_LEVEL;
            GameStart.cheangeScena = true;
        }else if (nomber == 2){
            GameStart.scena = Scena.GAME_RUN_OLD;
            GameStart.cheangeScena = true;
        }
        }

        if (input.getKey(KeyEvent.VK_UP))
        {
            if (nomber > 0){
                nomber--;
                y-=step;
                chaengePosition = true;
            }
        }
        if (input.getKey(KeyEvent.VK_DOWN))
        {
            int f;
            if (GameStart.oldGameRun == null)
                f = 2;
            else
                f = 1;
            if (nomber <stringlistPoint.size()-f ){
                nomber++;
                chaengePosition = true;
                y+=step;
            }
        }
        if (chaengePosition) {
            timer.startTimer();
            chaengePosition = false;
        }
    }
    public void render(Graphics2D g) {
        if (!running)return;
        startMenu(g);
        drowTank();
    }
    private void startMenu(Graphics2D g){

        myWriter.world_of_tanks(g);

        for (int i = 0; i < workSizeNameFieldList; i++) {
            int a = stringlistPoint.get(i).x;
            int b = stringlistPoint.get(i).y;
            if (nomber != i)
                myWriter.drowNextStringOff(g,nameFieldList.get(i),a,b);
            else
                myWriter.drowNextStringOn(g,nameFieldList.get(i),a,b);
        }

    }
    private void canContinius(){

        if (GameStart.oldGameRun == null)
            workSizeNameFieldList = stringlistPoint.size()-1;
        else
            workSizeNameFieldList = stringlistPoint.size();
    }
    private void setPointLisr(){
        int step = 25, x = 300,y =300;

        for (int i = 0; i < nameFieldList.size(); i++) {
            Point point = new Point();
            point.setLocation(x,y);
            stringlistPoint.add(point);
            y += step;
        }
    }
    public boolean isRunning() {
        return running;
    }

    private void drowTank(){
        sprite.render(Display.getGraphics(),x,y);
    }

}
