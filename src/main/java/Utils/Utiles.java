package Utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KShilov on 09.02.17.
 */
public class Utiles {
    public static BufferedImage resiaze(BufferedImage image,int width,int hight ){
        BufferedImage bufferedImage = new BufferedImage(width,hight,BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image,0,0,width,hight,null);
        return bufferedImage;
    }
    public static Integer[][]levelParser(String filePath){
        Integer[][] rezolt = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            String line = null;
            List<Integer[]> list = new ArrayList<Integer[]>();
            while ((line = reader.readLine()) != null){
                list.add(str2int_arrays(line.split(" ")));
            }
            rezolt = new Integer[list.size()][list.get(0).length];
            for (int i = 0; i < list.size(); i++) {
                rezolt[i] = list.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rezolt;

    }
    public static final Integer[]str2int_arrays(String[] array){
        Integer[] resolt = new Integer[array.length];
        for (int i = 0; i < resolt.length; i++) {

                resolt[i] = Integer.parseInt(array[i]);
        }

        return resolt;
    }
}
