package Game.MyExeption;

public class NotIdExeption extends Exception {
    private int id;
    public int getId(){
        return id;
    }

    public NotIdExeption(String message,  int id) {
        super(message);
        this.id = id;
    }
}
