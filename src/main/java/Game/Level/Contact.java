package Game.Level;

import Game.Field;

import java.awt.*;
import java.util.ArrayList;

public class Contact {
    private Point dot;
    private Field field;
    private float Ox0;
    private float Oy0;
    private float OxS;
    private float OyS;

    public Contact(float x ,float y,float scale) {
        dot = new Point();
        dot.setLocation(x,y);

        field = new Field(scale);
        //System.out.println("scale = " + scale);
    }
    public Contact(Point dot, float scale) {
        this.dot = dot;
        field = new Field(scale);
        //System.out.println("scale = " + scale);
    }

    private void initXY(){
        Ox0 = field.getX0();
        Oy0 = field.getY0();
        OxS = field.getxS();
        OyS = field.getyS();
    }
    public boolean atackObstacle(Point atack,Point obstacle){
        setLocation(atack,obstacle);
        if (pointBelongsTo_theField(dot.x,dot.y))
            return true;
        return false;
    }
    private boolean pointBelongsTo_theField(float x, float y){
        if (x >= Ox0 && x <= OxS && y <= OyS && y >= Oy0)
            return true;
        return false;
    }
    private void setField(Point forBoxObstacle) {
        field.setX0Y0(forBoxObstacle.x,forBoxObstacle.y);
        initXY();
    }
    private void setLocation(Point atack,Point obstacle){
        dot = atack;
        setField(obstacle);
    }
    public Field getField() {
        return field;
    }
}
