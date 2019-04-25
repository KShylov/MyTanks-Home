package Game.Effect.VisualEffect;

import Game.Animation.Animation;
import Io.Input;
import StartMain.MyRunning;
import Utils.Timer;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ExplosionEffect implements Effect {

    private ArrayList<Sprite> spriteList;
    private float x,y;
    private Timer timerBackset;
    private final int SCALE_SPRITE_CUT = 16;
    private float scaleGame;
    private int timerTime = 250;
    private TextureAtlas atlas;
    private Animation animation;
    private boolean run = true;


    public ExplosionEffect(float x, float y, float scaleGame, TextureAtlas atlas) {
        this.x = x;
        this.y = y;
        this.scaleGame = scaleGame;
        this.atlas = atlas;
        this.spriteList = instSpriteList();
        this.timerBackset = new Timer();
        animation = new Animation(instSpriteList(),timerTime);
        animation.setLooped(false);
        timerBackset.startTimer();
        timerBackset.setTimerMlSec(timerTime);
    }
    public ExplosionEffect(float x, float y,float scaleGame,ArrayList<Sprite> spriteList,TextureAtlas atlas) {
        this.x = x;
        this.y = y;
        this.scaleGame = scaleGame*0.5f;
        this.atlas = atlas;
        this.spriteList = spriteList;
        this.timerBackset = new Timer();
        timerBackset.setTimerMlSec(timerTime);
        timerBackset.startTimer();
    }

    @Override
    public void update() {
        animation.update();
        run = !animation.isFinished();
    }

    @Override
    public void render(Graphics2D graphics) {
        animation.render(graphics,x,y);
    }

    @Override
    public void render(Graphics2D graphics, float x, float y) {

    }

    @Override
    public boolean isRun() {
        return run;
    }

    private ArrayList<Sprite> instSpriteList(){
        ArrayList<Sprite> list = new ArrayList<Sprite>();
        for (int i = 0; i < 3; i++) {
            SpriteSheet sheet = new SpriteSheet(atlas.cut((i + 16)* SCALE_SPRITE_CUT,8* SCALE_SPRITE_CUT,1*SCALE_SPRITE_CUT,1* SCALE_SPRITE_CUT),1,SCALE_SPRITE_CUT);
            list.add(new Sprite(sheet,scaleGame));
        }
        return list;
    }
}
