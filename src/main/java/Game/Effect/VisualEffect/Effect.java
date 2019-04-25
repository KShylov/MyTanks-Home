package Game.Effect.VisualEffect;

import java.awt.*;

public interface Effect {
    public void update();
    public void render(Graphics2D graphics);
    public void render(Graphics2D graphics,float x, float y);
    public boolean isRun();
}
