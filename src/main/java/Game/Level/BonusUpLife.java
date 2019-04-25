package Game.Level;

import Game.TankSkill;
import Utils.Timer;

import java.awt.*;

public class BonusUpLife implements BonusWork{
    private TankSkill levelTank;
    private Timer timerBonusWork;
    private Bonus bonus;
    private boolean working;

    public BonusUpLife(TankSkill levelTank, Bonus bonus) {
        this.levelTank = levelTank;
        timerBonusWork = new Timer(20);
        timerBonusWork.startTimer();
        this.bonus = bonus;
        workBonus();
    }

    public void workBonus() {
        working = true;
        levelTank.upLife();
        bonus.finishBonus();
    }

    @Override
    public void render(Graphics2D g,float x, float y) {

    }
    @Override
    public void upDate() {
        timerBonusWork.update();
        if (!timerBonusWork.isTimer()&& working) {
            levelTank.DownType_Tanks();
            working = false;
        }
    }
}
