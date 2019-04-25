package Game.MyExeption;

public class CountExeption extends Exception {
    private int id;
    public int getId(){
        return id;
    }

    public CountExeption(String message,  int id) {
        super(message);
        this.id = id;
    }
}
