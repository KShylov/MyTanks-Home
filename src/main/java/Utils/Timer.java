package Utils;

import java.util.Date;
import java.util.Random;

public class Timer {
    private boolean timer;
    private Date startDate;
    private Date stopDate;
    private int mlSec;

    public Timer() {
        timer = false;
        mlSec = 400;
        stopDate = new Date();
    }
    public Timer(int time) {
        timer = false;
        setTimer(time);
        stopDate = new Date();
    }
    public void update(){
        stopTimer();
    }
    public void startTimer(){
        if (!timer){
            startDate = new Date();
            timer = true;
            setStopDate();
        }
    }
    private void stopTimer(){
        if (stopDate != null && timer){
            if (new Date().getTime() >= stopDate.getTime()){
                finishTimer();
            }
        }
    }
    public void finishTimer(){
        timer = false;
    }

    public void setTimer(int Sec){
        this.mlSec = Sec * 1000;
    }
    public void setTimerMlSec(int mlSec){
        this.mlSec = mlSec;
    }
    public void setTimerRandom(int minTime, int diapozon){
        Random random = new Random();
        mlSec = (random.nextInt(diapozon) + (minTime)) * 1000;


    }
    private void setStopDate() {
        if (startDate != null) {
            stopDate.setTime(startDate.getTime() + (mlSec));
        }
    }

    public boolean isTimer() {
        return timer;
    }
}
