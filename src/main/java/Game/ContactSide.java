package Game;

import java.awt.*;

public class ContactSide {
    private Field boxAtack;
    private Field boxObstacle;
    private float Ax0;
    private float Ay0;
    private float AxS;
    private float AyS;
    private float Ox0;
    private float Oy0;
    private float OxS;
    private float OyS;

    public ContactSide(float x , float y, float scale) {

        boxAtack = new Field(x,y,scale);
        boxObstacle = new Field(scale);
    }
    public ContactSide(Point forBoxAtack, float scale) {

        boxAtack = new Field(forBoxAtack.x,forBoxAtack.y,scale);
        boxObstacle = new Field(scale);
    }

    public ContactSide(float scale) {
        boxAtack = new Field(scale);
        boxObstacle = new Field(scale);
    }

    private void initXY(){
        Ax0 = boxAtack.getX0();
        Ay0 = boxAtack.getY0();
        AxS = boxAtack.getxS();
        AyS = boxAtack.getyS();
        Ox0 = boxObstacle.getX0();
        Oy0 = boxObstacle.getY0();
        OxS = boxObstacle.getxS();
        OyS = boxObstacle.getyS();
    }
    public boolean atackObstacle(){
        if (pointBelongsTo_theField(Ax0,Ay0))
        return true;
        if (pointBelongsTo_theField(AxS,Ay0))
        return true;
        if (pointBelongsTo_theField(Ax0,AyS))
        return true;
        if (pointBelongsTo_theField(AxS,AyS))
        return true;
        return false;
    }
    private boolean pointBelongsTo_theField(float x, float y){
        if (x >= Ox0 && x <= OxS && y <= OyS && y >= Oy0)
            return true;
        return false;
    }

    public void setBoxAtack(Point forBoxAtack) {
        boxAtack.setX0Y0(forBoxAtack.x,forBoxAtack.y);
        initXY();
    }

    public void setBoxAtackScale(float scale) {
        boxAtack.setScale(scale);
    }
    public void setBoxObstacleScale(float scale) {
        boxObstacle.setScale(scale);
    }

    public void setBoxObstacle(Point forBoxObstacle) {
        boxObstacle.setX0Y0(forBoxObstacle.x,forBoxObstacle.y);
        initXY();
    }
    public void setLocation(Point atack,Point obstacle){
        setBoxAtack(atack);
        setBoxObstacle(obstacle);
    }
}
