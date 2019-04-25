package StartMain;

public enum Scena {
    START_MENU(1),SELECT_MENU(2),GAME_RUN(3),GAME_RUN_OLD(4),CREATE_LEVEL(5);
    private int n;

    Scena(int n) {
        this.n = n;
    }
    public int getNumeric(){
        return n;
    }
    public static Scena fromNumeric(int n){
        switch (n){
            case 1: return START_MENU;
            case 2: return SELECT_MENU;
            case 3: return GAME_RUN;
            case 4: return GAME_RUN_OLD;
            case 5: return CREATE_LEVEL;
            default:return START_MENU;
        }
    }
}
