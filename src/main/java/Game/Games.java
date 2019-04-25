package Game;

import Game.Animation.Animation;
import Game.Effect.EffectBronya;
import Game.Effect.VisualEffect.EffectController;
import Game.Effect.VisualEffect.ExplosionEffect;
import Game.Ganeration.GanerationLevel;
import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import StartMain.SelectMenu;
import Utils.Time;
import Utils.Timer;
import View.Display;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by KShilov on 02.02.17.
 */
public class Games implements Runnable{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 576;
    public static final String TITLE = "World off Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;
    public static final float UPDATE_RATE = 60.0F;
    public static final float UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE;
    public static final long IDLE_TIME = 1;
    public static final String atlasFileName1 = "resursimage.png";
    public static final String atlasFileName2 = "resursimagewithfon.png";
    public static final String atlasFileName = atlasFileName1;

    private boolean running;
    private Thread gameThread;
    private Thread soundTreckThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas textureAtlas;
    private Igrok igrok;
    private Level gameBoard;
    private Radar radar;
    private GanerationLevel gLevel;
    private boolean pause = false;
    private boolean gameOver = false;
    private GanerationBots gBots;
    private int x = 316, y = HEIGHT - Tank.SPRITE_SCALE * 2 ;
    private SelectMenu selectMenu2;
    private EffectController effectController;
    private Timer timerclick;
    private Animation animation;
    private EffectBronya bronya;
    int test = 300;

    public Games() {
        running = false;
        Display.create(WIDTH,HEIGHT,TITLE,CLEAR_COLOR,NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        textureAtlas = new TextureAtlas(atlasFileName);
        gameBoard = new Level(textureAtlas);
        radar = new Radar(32,textureAtlas);
        igrok = new Igrok(EntityType.Player,x,y,0,1.9f,1,gameBoard,textureAtlas,radar);
        igrok.tank.getLevelTank().setLife(2);
        radar.addEntity(igrok);
        gBots = new GanerationBots(textureAtlas,gameBoard,radar);
        effectController = new EffectController();
        timerclick = new Timer();
        timerclick.setTimerMlSec(150);
        animation = new Animation(1000);
        bronya = new EffectBronya(textureAtlas,3);
        animation.addSprite(new Sprite(new SpriteSheet(textureAtlas.cut(0*16,1* 16,1*16,1* 16),1,16),3));
        animation.addSprite(new Sprite(new SpriteSheet(textureAtlas.cut(2*16,1* 16,1*16,1* 16),1,16),3));
        animation.addSprite(new Sprite(new SpriteSheet(textureAtlas.cut(4*16,1* 16,1*16,1* 16),1,16),3));
        animation.addSprite(new Sprite(new SpriteSheet(textureAtlas.cut(6*16,1* 16,1*16,1* 16),1,16),3));

    }
    public synchronized void start(){
        if (running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }
    public synchronized void stop(){
        if (!running)
            return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cleanup();
    }


    private void update() {
        timerclick.update();
        bronya.update();
    }

    private void render(){
        Display.clear();
        bronya.render(graphics,300,300);
        Display.swapBuffers();
    }

    private void cleanup(){
        Display.destroy();
    }

    @Override
    public void run() {
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        long lastTime = Time.get();
        float delta = 0;

        while (running){

            long now = Time.get();
            long elapsTime = now - lastTime;
            lastTime = now;
            count += elapsTime;
            delta += (elapsTime)/UPDATE_INTERVAL;
            boolean render = false;

            while (delta > 1){
                update();
                upd++;
                delta--;
                if (render){
                    updl++;
                }else {
                    render = true;
                }
            }
            if (render){
                render();
                fps++;
            }else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= Time.SECOND){
                Display.setTitle(TITLE + " || Fps: " + fps + " | Upd" + upd + " | Updl" + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }
        }

    }

}
