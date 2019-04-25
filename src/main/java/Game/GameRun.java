package Game;

import Game.Effect.PlayerMidiFiles;
import Game.Ganeration.GanerationLevel;
import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import StartMain.MyRunning;
import StartMain.ProcessGame;
import StartMain.SelectMenu;
import Utils.Time;
import Utils.Timer;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class GameRun extends ProcessGame {



    private boolean running;
    private Thread gameThread;
    private Thread soundTreckThread;

    private TextureAtlas textureAtlas;
    private Igrok igrok;
    private Level gameBoard;
    private Radar radar;
    private boolean pause = false;
    private boolean gameOver = false;
    private GanerationBots gBots;
    public static final int x = 4*Tank.SPRITE_SCALE*GameStart.GAME_SCALE;
    public static final int y = GameStart.HEIGHT - Tank.SPRITE_SCALE * GameStart.GAME_SCALE ;
    private boolean winer = false;
    private   boolean exitGame = false;
    private Timer timerCliks;
    public GameRun(TextureAtlas atlas){
        timerCliks = new Timer();
        timerCliks.setTimerMlSec(100);
        gameBoard = new Level(atlas);
        radar = new Radar(GameStart.SCALE * Tank.SPRITE_SCALE,atlas);
        igrok = new Igrok(EntityType.Player,x,y,0,GameStart.SCALE,1.5f,gameBoard,atlas,radar);
        igrok.tank.getLevelTank().setLife(3);
        radar.addEntity(igrok);
        gBots = new GanerationBots(atlas,gameBoard,radar);
        soundTreckThread = new Thread(new PlayerMidiFiles("Res/battle_city__dandy_.mid"));
        soundTreckThread.start();
        deleteTempFile();

    }
    @Override
    public void update(Input input) {
        if (!pause) {
            gameBoard.upDate();
            radar.update(input);
            gBots.update();
        }
        timerCliks.update();
        pause(input);
        exit(input);

    }

    @Override
    public void render(Graphics2D graphics) {
        gameBoard.render(graphics);
        drowStatisticField(graphics);
        radar.render(graphics);
        gameBoard.renderGrass(graphics);
        renderExit(graphics);
        renderPause(graphics);
        renderGameOver(graphics);
        renderWiner(graphics);

    }
    private void exit(Input input){

        if (input.getKey(KeyEvent.VK_ESCAPE)) {
            if (gameOver){
                exitInStartMenu();
            }
            if (winer){
                exitInStartMenu();
            }
            pause = true;
            exitGame = true;
        }
        YorN(input);

    }
    private void pause(Input input){
        if (!timerCliks.isTimer()) {
            if (input.getKey(KeyEvent.VK_PAUSE)) {
            pauseWithoutTimer();
            }
        }
    }
    private void pauseWithoutTimer(){

            if (pause == false)pause = true;
            else pause = false;
            timerCliks.startTimer();


    }
    private void YorN(Input input){
        if (!exitGame)return;
        if (input.getKey(KeyEvent.VK_Y)){
            exitInStartMenu();
        }
        if (input.getKey(KeyEvent.VK_N)){
            exitGame = false;
            pause = false;
        }
    }
    private void renderExit(Graphics2D graphics){
        String str = "You shure Y/N";
        float length = str.length()/2;
        int size = 50;
        int x = GameStart.widthField/2 -(int)(size*0.60f*length);
        int y = GameStart.HEIGHT/2;
        if (pause && exitGame && !winer && !gameOver){

            graphics.setFont(new Font("shrift",Font.BOLD,size));
            graphics.setColor(Color.RED);
            graphics.drawString(str,x,y);
        }
    }
    private void renderGameOver(Graphics2D graphics){
        String str = "Game over";
        float length = str.length()/2;
        int size = 50;
        int x = GameStart.widthField/2 -(int)(size*0.60f*length);
        int y = GameStart.HEIGHT/2;
        if (gameBoard.isEgle_dead() || igrok.getTank().getLevelTank().getLife()< 1){
            graphics.setFont(new Font("shrift",Font.BOLD,size));
            graphics.setColor(Color.RED);
            graphics.drawString(str,x,y);
            gameOver= true;
            pause = true;
        }
    }
    private void renderPause(Graphics2D graphics){
        String str = "Pause";
        float length = str.length()/2;
        int size = 50;
        int x = GameStart.widthField/2 -(int)(size*0.60f*length);
        int y = GameStart.HEIGHT/2;
        if (pause && !exitGame && !gameOver && !winer){
            graphics.setFont(new Font("shrift",Font.BOLD,size));
            graphics.setColor(Color.RED);
            graphics.drawString(str,x,y);
        }
    }
    private void renderWiner(Graphics2D graphics){
        if (radar.size() == 1 && gBots.isFinished() && igrok.getTank().getLevelTank().getLife() > 0){
            drowString("You WINER!",graphics);

            winer = true;
            pause = true;
        }
    }
    private void drowString(String str,Graphics2D graphics){
        float length = str.length()/2;
        int size = 50;
        int x = GameStart.widthField/2 -(int)(size*0.60f*length);
        int y = GameStart.HEIGHT/2;

            graphics.setFont(new Font("shrift",Font.BOLD,size));
            graphics.setColor(Color.RED);
            graphics.drawString(str,x,y);

    }
    private void deleteTempFile(){
        File file = new File(GanerationLevel.TEMP_ADRESS);
        file.delete();
    }

}
