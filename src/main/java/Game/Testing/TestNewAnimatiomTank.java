package Game.Testing;

import Game.Animation.Animation;
import Game.Heading;
import Io.Input;
import graphics.Sprite;
import graphics.SpriteSheet;
import graphics.TextureAtlas;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestNewAnimatiomTank {
    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADINH = 1;
    private float scale = 3;
    private TextureAtlas atlas;
    private Map<Integer, Sprite> spriteMap;
    private Animation animation;

    public TestNewAnimatiomTank(TextureAtlas atlas) {
        spriteMap = new HashMap<Integer, Sprite>();
        this.atlas = atlas;
        animation = new Animation();
        Heading[] h = Heading.values();
        for (int i = 0; i < h.length; i++) {
            SpriteSheet sheet = new SpriteSheet(h[i].texture(atlas),SPRITES_PER_HEADINH,SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet,scale);
            spriteMap.put(i,sprite);
        }
       /* for(Heading h: Heading.values()){
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas),SPRITES_PER_HEADINH,SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet,scale);
            spriteMap.put(h,sprite);
        }*/
    }
    public void update(Input input){}
    public void render(Graphics2D graphics){

    }
}
