package Game.Level;

import Game.GameStart;
import Utils.Timer;
import graphics.TextureAtlas;
//import sun.util.locale.LocaleUtils;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bonus {

        public static final int tile_Scale = 8;
        public static final int tile_in_Game_Scale = 2;
        public static final int tile_Scale_size = tile_in_Game_Scale * tile_Scale ;

        private final int timeWorkBonus = 20;

        private Integer[][] gameMap;
        private Map<TileType,Tile> bonusTileMap;
        private Point bonusPoint ;

        private boolean bonusActual = false;
        private int itoe = 0,jtoe = 0, numericFromBonus = 0;
        private Utils.Timer  timeWithOutBonus;
        private Utils.Timer timeBonusInclude;

        public Bonus(TextureAtlas atlas, Integer[][] gameMap) {

            this.gameMap = gameMap;

            bonusTileMap = new HashMap<TileType, Tile>();


            bonusTileMap.put(TileType.BRONYA,new Tile(atlas.cut(32 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.BRONYA,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.TIMER,new Tile(atlas.cut(34 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.TIMER,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.ZABOR,new Tile(atlas.cut(36 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.ZABOR,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.STAR,new Tile(atlas.cut(38 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.STAR,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.BOOMB,new Tile(atlas.cut(40 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.BOOMB,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.TANKS,new Tile(atlas.cut(42 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.TANKS,GameStart.GAME_SCALE));
            bonusTileMap.put(TileType.PISTOL,new Tile(atlas.cut(44 * tile_Scale,14 * tile_Scale,tile_Scale_size,tile_Scale_size),
                    TileType.PISTOL,GameStart.GAME_SCALE));


            timeBonusInclude = new Timer(timeWorkBonus);
            timeWithOutBonus = new Timer();
            timeWithOutBonus.setTimerRandom(15,10 );
            timeWithOutBonus.startTimer();
        }


        public void upDate (){
            timerUpDate();
            workBonus();
        }
        public void render(Graphics2D g){
                renderBonus(g);
        }

        private void renderBonus(Graphics2D g){
            if (bonusActual){
                Tile tile = bonusTileMap.get(TileType.fromNumeric(numericFromBonus));
                tile.render(g,jtoe*tile_Scale_size,itoe*tile_Scale_size);
            }
        }
        // устанавливает расположение бонуса и удаляет его через промежуток времени если не успели воспользоваться

        private void timerUpDate(){
            timeWithOutBonus.update();
            timeBonusInclude.update();
        }
        private void workBonus(){
            startBonus();
           // instBonusPoints();
            stopBonus();
        }

    public boolean isBonusActual() {
        return bonusActual;
    }

    private void startBonus(){
            if (!timeWithOutBonus.isTimer() && !bonusActual && !timeBonusInclude.isTimer()){

                bonusActual = true;
                Date dateForRandom = new Date();
                Random rand = new Random(dateForRandom.getTime());
                itoe = rand.nextInt(gameMap.length - 1);
                jtoe = rand.nextInt(gameMap[0].length - 1);
                numericFromBonus = rand.nextInt(7) + 8;
                if (gameMap[itoe][jtoe] == 0){
               // System.out.println("i = " + itoe + "; j = " + jtoe + "; n = " + numericFromBonus + ";");
                setBonusOnGameMap(itoe,jtoe,numericFromBonus);
                setBonusPoint();
                    timeBonusInclude.startTimer();
                }
            }

        }
        public void startBonusForTest(){
            timeWithOutBonus.finishTimer();
            startBonus();
        }
        private void stopBonus(){
            if (!timeWithOutBonus.isTimer() && bonusActual && !timeBonusInclude.isTimer()){
                timeWithOutBonus.startTimer();
                bonusActual = false;
             //   System.out.println("i = " + itoe + "; j = " + jtoe + "; n = " + 0 + ";");
                setEmptyOnGameMap(itoe,jtoe);
                clearBonusPoint();
            }
        }

    public Integer[][] getGameMap() {
        return gameMap;
    }

    public void finishBonus(){
            timeBonusInclude.finishTimer();
            stopBonus();
        }
        // проверка на наличие бонусов возврвщает true если бонусы уже есть на поле false если нет
        private boolean isBonus(){
            for (int i = 0; i < gameMap.length - 2; i++) {
                for (int j = 0; j < gameMap[i].length - 2; j++) {
                    isBonusOnPositiom(gameMap[i][j]);
                }
            }
            return false;
        }
        private boolean isBonusOnPositiom(Integer tile){
            for (int i = TileType.BRONYA.numeric(); i <= TileType.PISTOL.numeric(); i++) {
                if (tile == i)
                    return true;
            }
            return false;
        }

    public TileType getBonusType(){
            return TileType.fromNumeric(numericFromBonus);
    }
    private void setBonusPoint() {
        bonusPoint = new Point(jtoe * tile_Scale_size,itoe * tile_Scale_size);
    }
    private void clearBonusPoint(){
            bonusPoint = null;
    }

    public  void setBonusOnGameMap(int i, int j, int n){

            gameMap[(int)i][(int)j] = n;

        }
        public  void setEmptyOnGameMap(int i,int j){

            gameMap[(int)i][(int)j] = 0;

        }
        public Point getBonusPoint(){
            return bonusPoint;
        }




    }


