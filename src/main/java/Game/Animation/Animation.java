package Game.Animation;

import Game.Effect.VisualEffect.Effect;
import Utils.Timer;
import graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Sprite> spriteList;
    private Timer timerAnimation;
    private int iterator;
    private int timerTime;
    private boolean looped;
    private boolean finish = false;

    public Animation() {
        this.spriteList = new ArrayList<Sprite>();
        this.timerTime = 50;
        this.timerAnimation = new Timer();
        timerAnimation.setTimerMlSec(timerTime);
        timerAnimation.startTimer();
        this.iterator = 0;
        looped = true;
    }
    public Animation(int time) {
        this.spriteList = new ArrayList<Sprite>();
        this.timerTime = time;
        this.timerAnimation = new Timer();
        timerAnimation.setTimerMlSec(timerTime);
        timerAnimation.startTimer();
        this.iterator = 0;
        looped = true;
    }
    public Animation(ArrayList<Sprite> spriteList) {
        this.spriteList = spriteList;
        this.timerTime = 50;
        this.timerAnimation = new Timer();
        timerAnimation.setTimerMlSec(timerTime);
        this.iterator = 0;
        looped = true;
    }
    public Animation(ArrayList<Sprite> spriteList,int time) {
        this.spriteList = spriteList;
        this.timerTime = time;
        this.timerAnimation = new Timer();
        timerAnimation.setTimerMlSec(timerTime);
        timerAnimation.startTimer();
        this.iterator = 0;
        looped = true;
    }

    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
        timerAnimation.setTimerMlSec(timerTime);
    }

    public void addSprite(Sprite sprite){
        spriteList.add(sprite);
    }

    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    public boolean isLooped() {

        return looped;
    }

    public void update() {
        timerAnimation.update();

        if (!timerAnimation.isTimer() && iterator < spriteList.size() - 1){
            iterator ++;
            timerAnimation.startTimer();
        }else if (iterator == spriteList.size()-1 && !timerAnimation.isTimer() && looped ){
            iterator = 0;
            timerAnimation.startTimer();
        }else if (iterator == spriteList.size()-1 && !timerAnimation.isTimer() && !looped ){
            finish = true;
        }


    }

    public void render(Graphics2D graphics,float x,float y) {
        spriteList.get(iterator).render(graphics,x,y);
    }

   public boolean isFinished(){
        return finish;
   }
}
