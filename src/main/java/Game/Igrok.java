package Game;

import Game.Level.Level;
import Game.Level.Radar;
import Io.Input;
import graphics.TextureAtlas;

import java.awt.*;

/**
 * Created by Vaio on 16.11.17.
 */
public class Igrok extends Entity {
    protected float scale;
    protected float speed;
    protected TextureAtlas atlas;
    protected Radar radar;
    protected Tank tank;

    public Igrok(EntityType type, float x, float y, Integer id, float scale, float speed, Level gameBoard, TextureAtlas atlas, Radar radar) {
        super(type, x, y, id);
        this.scale = scale;
        this.speed = speed;
        this.atlas = atlas;
        this.radar = radar;
        tank = new Tank(id,x,y,atlas,scale,speed,gameBoard,radar);
    }

    @Override
    public void update(Input input) {
        if (tank.getLevelTank().isPause())return;
        tank.update(input);
        tank.moveTank(input);
        tank.nextShot(input);

    }

    @Override
    public void render(Graphics2D g) {
        tank.render(g);
        renderLife(g);
        renderShot(g);

    }
    private void renderLife(Graphics2D g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("shrifte",Font.BOLD,16));
        String str = "Life " + tank.getLevelTank().getLife();
        g.drawString(str,630,20);
    }
    private void renderShot(Graphics2D g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("shrifte",Font.BOLD,16));
        String str = "Shot's " + tank.getCountShoot();
        g.drawString(str,630,40);
    }


    public Tank getTank() {
        return tank;
    }
}
