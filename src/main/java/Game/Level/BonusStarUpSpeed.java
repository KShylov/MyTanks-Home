package Game.Level;

import Game.TankSkill;
import Utils.Timer;

import java.awt.*;

public class BonusStarUpSpeed implements BonusWork {
    private TankSkill levelTank;
    private Timer timerBonusWork;
    private Bonus bonus;
    private boolean working;

    public BonusStarUpSpeed(TankSkill levelTank, Bonus bonus) {
        this.levelTank = levelTank;
        timerBonusWork = new Timer(20);
        timerBonusWork.startTimer();
        this.bonus = bonus;
        workBonus();
    }

    public void workBonus() {
        working = true;
        levelTank.UpSpeedTank();
        bonus.finishBonus();
    }

    @Override
    public void render(Graphics2D g,float x, float y) {

    }

    @Override
    public void upDate() {
        timerBonusWork.update();
        if (!timerBonusWork.isTimer()&& working) {
            levelTank.DownSpeedTank();
            working = false;
        }
    }
}
