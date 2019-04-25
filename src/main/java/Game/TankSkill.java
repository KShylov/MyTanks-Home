package Game;

import Io.Input;
import Utils.Timer;

import java.awt.event.KeyEvent;

public class TankSkill {
    private final int TANK_MODEL_CONST = 4;
    private int typetank;
    private int leveltank;
    private float speedTank;
    private boolean statusBronya;
    private boolean pause;
    private int life;
    Timer timer = new Timer();

    public TankSkill(int typetank, int leveltank, float speed) {
        this.typetank = typetank * TANK_MODEL_CONST;
        this.leveltank = leveltank;
        life = 0;
        speedTank = speed;
        statusBronya = false;
        pause = false;
        timer = new Timer();
        timer.setTimerMlSec(150);

    }
    public TankSkill(float speedTank) {
        typetank = 0;
        leveltank = 0;
        life = 0;
        this.speedTank = speedTank;
        statusBronya = false;
        pause = false;
        timer = new Timer();
        timer.setTimerMlSec(150);
    }
    public void setTankSkill(TankSkill tankSkill) {
        typetank = tankSkill.getTypetank();
        leveltank = tankSkill.getLeveltank();
        speedTank = tankSkill.getSpeedTank();
        life = tankSkill.getLife();
        statusBronya = true;
        pause = false;
    }
    public void update(Input input){
        timer.update();
        if (timer.isTimer())return;
        if (input.getKey(KeyEvent.VK_3)){
            DownType_Tanks();
            timer.startTimer();
        }
        if (input.getKey(KeyEvent.VK_4)){
            UpType_Tanks();
            timer.startTimer();

        }
        if (input.getKey(KeyEvent.VK_5)){
            timer.startTimer();
            DownLevel_Tanks();
        }
        if (input.getKey(KeyEvent.VK_6)){
            UpLevel_Tanks();
            timer.startTimer();
        }
    }
   /* public TankSkill(int counterTypeTank) {
        this.counterTypeTank = counterTypeTank;
    }*/

    public void setLife(int life){
        this.life = life;
    }
    public void upLife(){
        life++;
    }
    public void downLife(){
        if (life > 0)
        life--;
    }
    public int getLife(){
        return life;
    }
    public void UpLevel_Tanks(){
        if (leveltank < 3){
            System.out.println("Level do " + leveltank);
            leveltank++;
        }
        System.out.println("Level do " + leveltank);
    }
    public void DownLevel_Tanks(){
        if (leveltank > 0){
            System.out.println("Level do " + leveltank);
            leveltank--;
        }
        System.out.println("Level posle " + leveltank);
    }
    public void UpType_Tanks(){
        if (typetank < 28){
            System.out.println("Type do " + typetank);
            typetank += TANK_MODEL_CONST;
        }
        System.out.println("Type posle " + typetank);
    }
    public void DownType_Tanks(){
        if (typetank > 0){
            System.out.println("Type do " + typetank);
            typetank -= TANK_MODEL_CONST;
        }
        System.out.println("Type posle " + typetank);
    }

    public void setStatusBronya(boolean statusBronya) {
        this.statusBronya = statusBronya;
    }

    public boolean isPause() {
        return pause;
    }

    public void chaengeStatusPause(){
        if (!pause)pause = true;
        else pause = false;
    }
    public int getLeveltank() {
        return leveltank;
    }

    public int getTypetank() {

        return typetank;
    }

    public void UpSpeedTank() {
        if (speedTank < 3)
            speedTank += 0.5;
    }
    public boolean isStatusBronya() {
        return statusBronya;
    }
    public float getSpeedTank() {
        return speedTank;
    }

    public void DownSpeedTank() {
        if (speedTank > 1)
            speedTank -= 0.5;
    }
}
