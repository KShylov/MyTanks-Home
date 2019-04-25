package Game;

import java.awt.*;

public class Field {
    private float x0;
    private float y0;
    private float xS;
    private float yS;
    private float scale;

    public Field(float scale) {
        this.x0 = 0;
        this.y0 = 0;
        this.scale = scale;
        xS = x0 + scale;
        yS = y0 + scale;
    }
    public Field(float x0, float y0, float scale) {
        this.x0 = x0;
        this.y0 = y0;
        this.scale = scale;
        this.xS = x0 + scale;
        this.yS = y0 + scale;
    }

    public void setX0(float x0) {
        this.x0 = x0;
        xS = x0 + scale;
    }

    public void setY0(float y0) {
        this.y0 = y0;
        yS = y0 + scale;
    }

    public float getX0() {
        return x0;
    }
    public void setX0Y0(float x0,float y0){
        setX0(x0);
        setY0(y0);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getY0() {
        return y0;
    }

    public float getxS() {
        return xS;
    }

    public float getyS() {
        return yS;
    }
}
