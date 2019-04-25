package StartMain;


import Utils.ResyrseLoader;

import java.io.File;

public class SaveTest {


    public static void main(String[] args) {
        File file = new File(ResyrseLoader.PATH + "Other/test");
        file.delete();
    }
}
