package Game;

import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import Utils.Timer;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.Date;
import java.util.Random;

public class Bot extends Igrok{
    private int direction;
    private boolean chaengDirection;
    private Timer timerChaengDirection;
    private Timer timerPlainDirection;

    public Bot(EntityType type, float x, float y, Integer id, float scale, float speed, Level gameBoard, TextureAtlas atlas, Radar radar) {
        super(type, x, y, id, scale, speed, gameBoard, atlas,radar);
        direction = 1;
        chaengDirection = false;
        tank.getLevelTank().UpType_Tanks();
        timerChaengDirection = new Timer(4);
        timerChaengDirection.startTimer();
        timerPlainDirection = new Timer();
    }
    @Override
    public void update(Input input) {
        if (tank.getLevelTank().isPause())return;
       tank.update(input);
       chaengDirectionByTimer();
       chaengeDirectionIsObstacle();
       shotIfchaengeDirection();
       timerPlainDirection.update();
       timerChaengDirection.update();
       pricel();

    }
    private void shotIfchaengeDirection(){
        if (chaengDirection  ){
            tank.shot();
            chaengDirection = false;
        }
    }
    private void chaengDirection(){
        Random random = new Random();
        if (!timerPlainDirection.isTimer()){
            direction = random.nextInt(4)+1;
            chaengDirection = true;
            timerPlainDirection.startTimer();
        }
    }
    private void pricel(){
        switch (tank.headingCorect()){
            case 1: if (pricelIfheadingNorth()) tank.shot();break;
            case 2: if (pricelIfheadingEast())tank.shot();break;
            case 3: if (pricelIfheadingSouth())tank.shot();break;
            case 4: if (pricelIfheadingWest())tank.shot();break;

        }
    }
    private boolean pricelIfheadingNorth(){
        if (radar.getPointPlayer() == null)return false;
        float sCALE = Tank.SPRITE_SCALE * scale;
        int bxc = tank.getPositionTank().x + Tank.SPRITE_SCALE;
        int by = tank.getPositionTank().y;
        int px = radar.getPointPlayer().x;
        int py = radar.getPointPlayer().y;

        if (by > py + sCALE && bxc < px + sCALE && bxc > px)
            return true;
        return false;
    }
    private boolean pricelIfheadingSouth(){
        if (radar.getPointPlayer() == null)return false;
        float sCALE = Tank.SPRITE_SCALE * scale;
        int bxc = tank.getPositionTank().x + Tank.SPRITE_SCALE;
        int by = tank.getPositionTank().y;
        int px = radar.getPointPlayer().x;
        int py = radar.getPointPlayer().y;

        if (by + sCALE < py && bxc < px + sCALE && bxc > px)
            return true;
        return false;
    }
    private boolean pricelIfheadingEast(){
        if (radar.getPointPlayer() == null)return false;
        float sCALE = Tank.SPRITE_SCALE * scale;
        int bx = tank.getPositionTank().x;
        int byc = tank.getPositionTank().y + Tank.SPRITE_SCALE;
        int px = radar.getPointPlayer().x;
        int py = radar.getPointPlayer().y;

        if (bx + sCALE < px && byc < py + sCALE && byc > py)
            return true;
        return false;
    }
    private boolean pricelIfheadingWest(){
        if (radar.getPointPlayer() == null)return false;
        float sCALE = Tank.SPRITE_SCALE * scale;
        int bx = tank.getPositionTank().x;
        int byc = tank.getPositionTank().y + Tank.SPRITE_SCALE;
        int px = radar.getPointPlayer().x;
        int py = radar.getPointPlayer().y;

        if (bx > px + sCALE && byc < py + sCALE && byc > py)
            return true;
        return false;
    }
    private void chaengDirectionByTimer(){
        timerChaengDirection.update();
        if (!timerChaengDirection.isTimer()){
            chaengDirection();
            timerChaengDirection.startTimer();
        }
    }
    private void chaengeDirectionIsObstacle(){
        switch (direction){

            case 1:
                if (tank.isCanMoveDown()) {
                    tank.moveTankDown();
                }
                else {
                    chaengDirection();
                }
                break;

            case 2:
                if (tank.isCanMoveLeft()) {
                    tank.moveTankLeft();
                }
                else {
                    chaengDirection();
                }
                break;

            case 3:
                if (tank.isCanMoveRight()) {
                    tank.moveTankRight();
                }
                else {
                    chaengDirection();
                }
                break;

            case 4:
                if (tank.isCanMoveUp()) {
                    tank.moveTankUp();
                }
                else {
                    chaengDirection();
                }
                break;
        }
    }

    @Override
    public void render(Graphics2D g) {
        tank.render(g);
    }

}
