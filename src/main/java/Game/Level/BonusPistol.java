package Game.Level;

import Game.TankSkill;
import Utils.Timer;

import java.awt.*;

public class BonusPistol implements BonusWork {
    private TankSkill levelTank;
    private Timer timerBonusWork;
    private Bonus bonus;
    private boolean working;

    public BonusPistol(TankSkill levelTank, Bonus bonus) {
        this.levelTank = levelTank;
        timerBonusWork = new Timer(20);
        timerBonusWork.startTimer();
        this.bonus = bonus;
        workBonus();
    }

    public void workBonus() {
        working = true;
        levelTank.UpLevel_Tanks();
        bonus.finishBonus();
    }

    @Override
    public void upDate() {

    }

    @Override
    public void render(Graphics2D g,float x, float y) {

    }

}
