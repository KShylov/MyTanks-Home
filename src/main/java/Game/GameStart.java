package Game;

import Game.Ganeration.GanerationLevel;
import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import StartMain.MyRunning;
import StartMain.Scena;
import StartMain.SelectMenu;
import StartMain.StartMenu;
import Utils.ResyrseLoader;
import Utils.Time;
import View.Display;
import graphics.TextureAtlas;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameStart implements Runnable {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 624;
    public static final int GAME_SCALE = 3;
    public static final int TILE_SCALE = 8;
    public static final int TILE_SCALE_SIZE = GAME_SCALE * TILE_SCALE;
    public static final String TITLE = "World off Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;
    public static final float UPDATE_RATE = 60.0F;
    public static final float UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE;
    public static final long IDLE_TIME = 1;
    public static final String atlasFileName1 = "resursimage.png";
    public static final String atlasFileName2 = "resursimagewithfon.png";
    public static final String atlasFileName = atlasFileName1;
    public static final float SCALE = GAME_SCALE * 0.9f;
    public static int widthField = WIDTH - 96;
    public static int countMaps = 4;

    private boolean running;
    private Thread gameThread;
    private Thread soundTreckThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas textureAtlas;

    private boolean pause = false;
    private boolean gameOver = false;
    public static String adress = "Res/Temp/FirstLevel.lvl";
    public static boolean startGame = false;
    public static ArrayList<String> nameFieldList;
    private boolean winer = false;
    public static boolean exitGame = false;
    private MyRunning scenaRuning;
    public static boolean cheangeScena = false;
    public static Scena scena = Scena.START_MENU;
    public static GameRun oldGameRun;

    public GameStart() {
        running = false;
        Display.create(WIDTH,HEIGHT,TITLE,CLEAR_COLOR,NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        textureAtlas = new TextureAtlas(atlasFileName);
        scenaRuning = new StartMenu(textureAtlas);
        nameFieldList = new ArrayList<String>();
        instNameList();
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
        scenaRuning.update(input);
        selectScena();
    }
    private void instNameList(){
        String names= "";
        String[] name;
        try {
            FileReader reader = new FileReader(ResyrseLoader.PATH + "Other/MapsName.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str;
            while ((str = bufferedReader.readLine())!=null){
                names+= str;
            }
            name = names.split(" ");
            for (int i = 0; i < name.length; i++) {
                System.out.println(name[i]);
                nameFieldList.add(name[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void selectScena(){
        if (cheangeScena){
            if (scena ==Scena.START_MENU)
                scenaRuning = new StartMenu(textureAtlas);
            if (scena == Scena.SELECT_MENU)
                scenaRuning = new SelectMenu(textureAtlas);
            if (scena == Scena.CREATE_LEVEL)
                scenaRuning = new GanerationLevel(textureAtlas);
            if (scena == Scena.GAME_RUN) {
                scenaRuning = new GameRun(textureAtlas);
                oldGameRun = (GameRun)scenaRuning;
            }
            if (scena == Scena.GAME_RUN_OLD)
                scenaRuning = oldGameRun;
            cheangeScena = false;
        }
    }




    private void render(){
        Display.clear();
            scenaRuning.render(graphics);
        Display.swapBuffers();
    }

    private void writer(String str,int x){
        graphics.setFont(new Font("shrift",Font.BOLD,50));
        graphics.setColor(Color.RED);
        graphics.drawString(str,x,300);
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


