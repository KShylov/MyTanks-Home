package Game;

import Game.Level.Level;
import Game.Level.Radar;
import Utils.Timer;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GanerationBots {
    private final int MAX_CAUNT_BOT = 4;
    private final int MIN_BOTS_ON_BOARD = 3;
    private TextureAtlas atlas;
    private Level gameBoard;
    private Radar radar;
    private int countBot = 1;
    private boolean finishedCreate = false;
    private boolean finished = false;
    private Timer timer;
    private int scale = GameStart.GAME_SCALE * Tank.SPRITE_SCALE;
    private int[] xPoint = {2*scale,GameStart.widthField/2 -(1 * Tank.SPRITE_SCALE+4),GameStart.widthField-3*scale};
    private int y = 0;
    private ArrayList<TankSkill> skillArrayList;


    public GanerationBots(TextureAtlas atlas, Level gameBoard, Radar radar) {
        this.atlas = atlas;
        this.gameBoard = gameBoard;
        this.radar = radar;
        timer = new Timer(4);
        timer.startTimer();
        skillArrayList = instSkillList();
    }
    public void update(){
        timer.update();
        workGaneration();
        finish();
        newBot();
    }
    private boolean isCanCreateOnPosition(int x){
        Point atack = new Point();
        atack.setLocation(x,y);
            if (radar.isContactWithEntity(atack,countBot))
            return false;
        return true;
    }
    private void newBot(){
        Random random = new Random(new Date().getTime());
        int i =  random.nextInt(xPoint.length);
        int x = xPoint[i];
        if (!isCanGaneration())return;
        if (!isCanCreateOnPosition(x))return;
        i = random.nextInt(skillArrayList.size());
        Bot bot = new Bot(EntityType.Bot,x,y,countBot,GameStart.SCALE,1,gameBoard,atlas,radar);
        bot.getTank().getLevelTank().setTankSkill(skillArrayList.get(i));
        radar.addEntity(bot);
        countBot++;
    }

    public boolean isFinished() {
        return finished;
    }
    private void finish(){
        if (finishedCreate && radar.size() == 1)
            finished = true;
    }
    private boolean isCanGaneration(){
        if (!timer.isTimer() && (radar.size()) <= MIN_BOTS_ON_BOARD && !finishedCreate){
            timer.startTimer();
            return true;
        }
        return false;
    }
    private ArrayList<TankSkill> instSkillList(){
        ArrayList<TankSkill> list = new ArrayList<TankSkill>();
        for (int level = 0; level < 4; level++) {
            for (int type = 0; type < 8; type++) {
                list.add(new TankSkill(type,level,1.5f));
            }
        }
        return list;
    }

    private void workGaneration(){
        if (countBot == MAX_CAUNT_BOT){
            finishedCreate = true;
        }

    }

}
