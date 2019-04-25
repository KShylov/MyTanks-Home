package Game;

import Utils.Timer;

/**
 * Created by Vaio on 17.11.17.
 */
public class AnimacionTank {
    boolean isChaengeSprite;
    private Heading[] masHead;
    private Timer timer;

    public AnimacionTank() {
        this.isChaengeSprite = false;
        masHead = Heading.values();
    }
    public AnimacionTank(Heading[] masHead) {
        this.isChaengeSprite = true;
        this.masHead = masHead;
        timer = new Timer();
        timer.setTimerMlSec(60);
        timer.startTimer();
    }

    public Heading TankMoveUpAnimacion(TankSkill tankSkill ){
        Heading heading;
        int skillSum = tankSkill.getLeveltank() + tankSkill.getTypetank();
        if (isChaengeSprite){
            heading = masHead[0 + skillSum* 8];
           // isChaengeSprite = false;
        }
        else{
            heading = masHead[4 +  skillSum* 8];
           // isChaengeSprite = true;
        }
        return heading;
    }
    public Heading TankMoveRightAnimacion(TankSkill tankSkill ){
        Heading heading;
        int skillSum = tankSkill.getLeveltank() + tankSkill.getTypetank();
        if (isChaengeSprite){
            heading = masHead[1 + (skillSum) * 8];
           // isChaengeSprite = false;
        }
        else{
            heading = masHead[5 + (skillSum) * 8];
           // isChaengeSprite = true;
        }
        return heading;
    }
    public Heading TankMoveDownAnimacion(TankSkill tankSkill ){
        Heading heading;
        int skillSum = tankSkill.getLeveltank() + tankSkill.getTypetank();
        if (isChaengeSprite){
            heading = masHead[2 + (skillSum) * 8];
            //isChaengeSprite = false;
        }
        else{
            heading = masHead[6 + (skillSum) * 8];
           // isChaengeSprite = true;
        }
        return heading;
    }
    public Heading TankMoveLeftAnimacion(TankSkill tankSkill ){
        Heading heading;
        int skillSum = tankSkill.getLeveltank() + tankSkill.getTypetank();
        if (isChaengeSprite){
            heading = masHead[3 + (skillSum) * 8];
           // isChaengeSprite = false;
        }
        else{
            heading = masHead[7 + (skillSum) * 8];
            //isChaengeSprite = true;
        }
        return heading;
    }
    public void updateChangeSprite(){
        timer.update();
        if (!timer.isTimer()){
            changeSprite();
            timer.startTimer();
        }
    }
    private void changeSprite(){
        if (isChaengeSprite)isChaengeSprite = false;
        else isChaengeSprite = true;
    }
}
