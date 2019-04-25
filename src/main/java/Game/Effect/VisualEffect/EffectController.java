package Game.Effect.VisualEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EffectController implements Effect {
    private HashMap<Integer,Effect> effectMap;
    private Integer key;
    private int startSize;
    private Integer keyDel;

    public EffectController() {
        this.effectMap = new HashMap<Integer, Effect>();
        this.key = 1;
        this.startSize = effectMap.size();
    }

    public boolean addEffect(Effect effect){
        boolean add = false;
        effectMap.put(key,effect);
        if (effectMap.size() == startSize + 1){
            add = true;
            key++;
        }
        return add;
    }
    public boolean removeEffect(Integer key){
        boolean del = false;
        effectMap.remove(key);
        if (effectMap.size() == startSize -1){
            del = true;
        }
        return del;
    }

    @Override
    public void update() {
        removeEffect(keyDel);
        for (Map.Entry<Integer,Effect> pair:effectMap.entrySet()) {
            if (!pair.getValue().isRun())
                keyDel = pair.getKey();
            else
                pair.getValue().update();
        }
        removeEffect(keyDel);
    }

    @Override
    public void render(Graphics2D graphics) {
        for (Map.Entry<Integer,Effect> pair:effectMap.entrySet()) {
            pair.getValue().render(graphics);
        }
    }

    @Override
    public void render(Graphics2D graphics, float x, float y) {

    }

    @Override
    public boolean isRun() {
        return false;
    }
    public void show(){
        System.out.println("Key = " + key);
        System.out.println("size = " + effectMap.size());

    }
}
