package Game;

import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import Utils.Timer;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Shot{

    private TankSkill levelTank;
    private int headings;
    private float speed;
    private TextureAtlas atlas;
    private Timer timerShots;
    private List<SnaryadInt> shotList;
    private ArrayList<Integer> idList;
    private Level gameBoard;
    private int countShot;
    private Radar radar;
    private int id;


    public Shot(int id,TankSkill levelTank,float speed, Level gameBoard, TextureAtlas atlas, Radar radar) {
        this.levelTank = levelTank;
        this.speed = speed;
        this.id = id;
        this.atlas = atlas;
        this.gameBoard = gameBoard;
        this.shotList = new ArrayList<SnaryadInt>();
        timerShots = new Timer();
        timerShots.setTimerMlSec(500);
        this.radar = radar;
        countShot = 0;
        idList = new ArrayList<Integer>();

    }
    public void upDate(){

            for (SnaryadInt s:shotList) {
                s.update();
            }
            cleanShotList();
        timerShots.update();

    }
    public void render(Graphics2D g){
        for (SnaryadInt s:shotList) {
            s.render(g);
        }
    }
    public void nextShot(int head,float x, float y){
        if (!timerShots.isTimer()){
            timerShots.startTimer();
            setHeading(head);
            SnaryadInt snaryad = new SnaryadNew(id,countShot,x,y,atlas,speed,headings,gameBoard,levelTank,radar);
            shotList.add(snaryad);
            countShot++;
        }
    }
    private void setHeading(int napravlenie){
        headings = napravlenie;
    }
    private void cleanShotList(){
        idList.clear();
        for (SnaryadInt s:shotList) {
            if (s.isGilza())
                idList.add(s.getIdSnaryad());
        }
        if (idList.isEmpty())return;
        for (int id:idList) {
            remuveById(id);
         }

    }
    private void remuveById(int id){
        for (int i = 0; i < shotList.size(); i++) {
            if (id == shotList.get(i).getIdSnaryad()){
                   shotList.remove(i);
                break;
            }
        }
    }

    public int getCountShot() {
        return countShot;
    }
}
