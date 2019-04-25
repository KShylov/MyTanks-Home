package Game;

import java.awt.*;

public class MyWriter {

    public void select_levelDraw(Graphics2D graphics){
        graphics.setFont(new Font("shrift",Font.BOLD,50));
        graphics.setColor(Color.RED);
        graphics.drawString("SELECT GAME CARD",130,200);
    }
    public void world_of_tanks(Graphics2D graphics){
        graphics.setFont(new Font("shrift",Font.BOLD,50));
        graphics.setColor(Color.RED);
        graphics.drawString("WORLD OF TANKS",130,200);
    }
    public void drowNextStringOff(Graphics2D graphics,String str, int x, int y){
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("shrift",Font.ITALIC,20));
        graphics.drawString(str,x,y);
    }
    public void drowNextStringOn(Graphics2D graphics,String str, int x, int y){
        graphics.setColor(Color.ORANGE);
        graphics.setFont(new Font("shrift",Font.ITALIC,20));
        graphics.drawString(str,x,y);
    }


}
