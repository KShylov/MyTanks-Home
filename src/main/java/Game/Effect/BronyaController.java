package Game.Effect;

import Game.TankSkill;
import Utils.Timer;
import graphics.TextureAtlas;

import java.awt.*;

public class BronyaController {
    private Timer timerWork;
    private TextureAtlas atlas;
    private float gameScale;
    private EffectBronya effectBronya;
    private TankSkill tankSkill;

    public BronyaController(TextureAtlas atlas, float gameScale, TankSkill tankSkill) {
        this.timerWork = new Timer(6);
        timerWork.startTimer();
        this.tankSkill = tankSkill;
        effectBronya = new EffectBronya(atlas,gameScale);
    }
    public void update(){
        timerWork.update();
        if (!timerWork.isTimer()){
            tankSkill.setStatusBronya(false);
            effectBronya.setRun(false);
        }
        effectBronya.update();
    }
    public void render(Graphics2D g, float x, float y){
        effectBronya.render(g,x,y);
    }
    public void bronyaOn(){
        timerWork.startTimer();
        tankSkill.setStatusBronya(true);
        effectBronya.setRun(true);
    }


}
