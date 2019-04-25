package Game;

import Io.Input;

import java.awt.*;

/**
 * Created by KShilov on 07.02.17.
 */
public abstract class Entity {
    public final EntityType type;
    public final Integer id;
    public boolean Status;

    protected  float x;
    protected  float y;

    protected Entity(EntityType type, float x, float y, Integer id) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.id = id;
        Status = true;
    }
    public abstract void update(Input input);
    public abstract void render(Graphics2D g);
}
