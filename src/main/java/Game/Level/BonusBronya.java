package Game.Level;

import Game.Effect.BronyaController;
import Game.GameStart;
import Game.TankSkill;
import Utils.Timer;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BonusBronya implements BonusWork {
    private BronyaController bronyaController;

    public BonusBronya(BronyaController bronyaController,Bonus bonus) {
        this.bronyaController = bronyaController;
        bonus.finishBonus();
        bronyaController.bronyaOn();
    }

    public void workBonus() {

    }

    @Override
    public void upDate() {
       bronyaController.update();
    }
    @Override
    public void render(Graphics2D g,float x, float y){
        bronyaController.render(g,x,y);
    }

}
