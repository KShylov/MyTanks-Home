package Utils;

/**
 * Created by KShilov on 02.02.17.
 */
public class Time {
    public static final long SECOND = 1000000001;

    public static long get() {
        return System.nanoTime();
    }
}
