package Game.Level;

import Game.TankSkill;
import Utils.Timer;

import java.awt.*;

public class BonusTimer implements BonusWork{
    private Radar radar;
    private Bonus bonus;
    private Timer timerBonusWork;
    private boolean working;
    private int id;

    public BonusTimer(Radar radar, Bonus bonus,int id) {
        this.radar = radar;
        this.bonus = bonus;
        this.id = id;
        timerBonusWork = new Timer(10);
        timerBonusWork.startTimer();
        workBonus();
    }

    @Override
    public void workBonus() {
        working = true;
        radar.chaengeStatusPauseAllPlayers(id);
        bonus.finishBonus();
    }

    @Override
    public void upDate() {
        timerBonusWork.update();
        if (!timerBonusWork.isTimer()&& working) {
            radar.chaengeStatusPauseAllPlayers(id);
            working = false;
        }
    }

    @Override
    public void render(Graphics2D g, float x, float y) {

    }
}
