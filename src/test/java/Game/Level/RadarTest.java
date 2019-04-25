package Game.Level;

import Game.Bot;
import Game.EntityType;
import Game.Igrok;
import Game.MyExeption.CountExeption;
import Game.MyExeption.NotIdExeption;
import graphics.TextureAtlas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RadarTest {

    public static final String atlasFileName = "resursimage.png";
    TextureAtlas textureAtlas = new TextureAtlas(atlasFileName);
    Level gameBoard = new Level(textureAtlas);
    Radar radar = new Radar(32,textureAtlas);
    private void inst(){
        radar.addEntity(new Igrok(EntityType.Player,100,300,0,2,1,gameBoard,textureAtlas,radar));
        radar.addEntity(new Bot(EntityType.Bot,300,300,1,2,1,gameBoard,textureAtlas,radar));
    }

    @Test
    void remuveById() {
        inst();
        int expectSize = radar.size() - 1;
        try {
            radar.remuveById(0);
        } catch (NotIdExeption notIdExeption) {
            notIdExeption.printStackTrace();
        } catch (CountExeption countExeption) {
            countExeption.printStackTrace();
        }
        int actualSize = radar.size();
        assertEquals(expectSize,actualSize);
    }

}