package Game.Effect;

import Game.Animation.Animation;
import Game.Effect.VisualEffect.Effect;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.ArrayList;

public class EffectBronya implements Effect {
    private Animation animation;
    private final int SCALE_SPRITE_CUT = 16;
    private ArrayList<Sprite> spriteList;
    private TextureAtlas atlas;
    private float scaleGame;
    private boolean run;



    public EffectBronya(TextureAtlas atlas, float scaleGame) {
        this.atlas = atlas;
        this.scaleGame = scaleGame;
        this.spriteList = instSpriteList();
        this.animation = new Animation(spriteList,100);
        run = true;

    }

    @Override
    public void update() {
        if (run)
        animation.update();
    }

    @Override
    public void render(Graphics2D graphics) {

    }
    @Override
    public void render(Graphics2D graphics,float x , float y) {
        if (run)
        animation.render(graphics,x,y);
    }

    public void setRun(boolean run) {
        this.run = run;
    }
    @Override
    public boolean isRun() {
        return run;
    }
    private ArrayList<Sprite> instSpriteList(){
        ArrayList<Sprite> list = new ArrayList<Sprite>();
        for (int i = 0; i < 2; i++) {
            SpriteSheet sheet = new SpriteSheet(atlas.cut((i + 16)* SCALE_SPRITE_CUT,9* SCALE_SPRITE_CUT,1*SCALE_SPRITE_CUT,1* SCALE_SPRITE_CUT),1,SCALE_SPRITE_CUT);
            list.add(new Sprite(sheet,scaleGame));
        }
        return list;
    }
}
