package StartMain;

import Game.GameStart;
import Io.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class ProcessGame implements MyRunning {
    public void exitInStartMenu(Input input){
        if (input.getKey(KeyEvent.VK_ESCAPE)){
            exitInStartMenu();
        }
    }
    public void exitInStartMenu(){
            GameStart.scena = Scena.START_MENU;
            GameStart.cheangeScena = true;
    }
    public void drowStatisticField(Graphics2D graphics){
        graphics.setColor(Color.GRAY);
        graphics.fillRect(GameStart.widthField,0,GameStart.widthField,GameStart.HEIGHT);
    }
}
