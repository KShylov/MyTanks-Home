package Game.Level;

/**
 * Created by KShilov on 09.02.17.
 */
public enum TileType  {
    EMPTY(0),BRICK(1),METAL(2),WATER(3),GRASS(4),ICE(5),EAGLE(6),EAGLE_DEAD(7),
    BRONYA(8),TIMER(9),ZABOR(10),STAR(11),BOOMB(12),TANKS(13),PISTOL(14),
    A(15),E(16),G(17),M(18),O(19),P(20),R(21),S(22),U(23),V(24),;
    private int p;
    TileType(int p) {
        this.p = p;
    }
    public int numeric(){
        return p;
    }


    public static TileType fromNumeric(int n){
        switch (n){
            case 1:
                return BRICK;
            case 2:
                return METAL;
            case 3:
                return WATER;
            case 4:
                return GRASS;
            case 5:
                return ICE;
            case 6:
                return EAGLE;
            case 7:
                return EAGLE_DEAD;
            case 8:
                return BRONYA;
            case 9:
                return TIMER;
            case 10:
                return ZABOR;
            case 11:
                return STAR;
            case 12:
                return BOOMB;
            case 13:
                return TANKS;
            case 14:
                return PISTOL;
            case 15:
                return A;
            case 16:
                return E;
            case 17:
                return G;
            case 18:
                return M;
            case 19:
                return O;
            case 20:
                return P;
            case 21:
                return R;
            case 22:
                return S;
            case 23:
                return U;
            case 24:
                return V;


            default:
                return EMPTY;

        }

    }
}
