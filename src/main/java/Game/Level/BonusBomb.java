package Game.Level;

import Utils.Timer;

import java.awt.*;

public class BonusBomb implements BonusWork{
    private Bonus bonus;
    private Radar radar;
    private int id;

    public BonusBomb(Bonus bonus, Radar radar,int id) {
        this.bonus = bonus;
        this.radar = radar;
        this.id = id;
        workBonus();

    }

    public void workBonus() {
        radar.detonation_of_bomb(id);
        bonus.finishBonus();
    }



    @Override
    public void render(Graphics2D g, float x, float y) {

    }

    @Override
    public void upDate() {

    }


}
