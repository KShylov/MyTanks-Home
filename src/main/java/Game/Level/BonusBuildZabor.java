package Game.Level;

import Utils.Timer;

import java.awt.*;

public class BonusBuildZabor implements BonusWork {
    private Timer timerBonusWork;
    private Level gameBoard;
    private boolean working;

    public BonusBuildZabor(Level gameBoard) {
        this.gameBoard = gameBoard;
        workBonus();
        timerBonusWork = new Timer(15);
        timerBonusWork.startTimer();
    }

    public void workBonus() {
        working = true;
        bildZabor(TileType.METAL.numeric());
        gameBoard.getBonus().finishBonus();
    }



    @Override
    public void render(Graphics2D g,float x, float y) {

    }

    @Override
    public void upDate() {
        timerBonusWork.update();
        if (!timerBonusWork.isTimer()&& working) {
            bildZabor(TileType.BRICK.numeric());
            working = false;
        }
    }

    private void bildZabor(int tiletype){

        Integer gameBoardMassiv[][] = gameBoard.getTileMap();

        int h = gameBoardMassiv.length;
        int w = gameBoardMassiv[0].length;
        int centr = w/2;

        for (int i = centr-2; i < centr+1; i++) {
            gameBoard.setTileMap(i,h-3,tiletype);
        }
        for (int i = h - 3; i < h; i++) {
            gameBoard.setTileMap(centr-2,i,tiletype);
            gameBoard.setTileMap(centr+1,i,tiletype);
        }
    }
}
