package Save;

import Utils.ResyrseLoader;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveMap {
    private String path2 = ".lvl";
    private String path1 = "Temp/";
    private String name;
    public void save(Integer[][] mas) throws IOException {
        FileWriter writer = new FileWriter(ResyrseLoader.PATH +path1 + newName()+path2);
        for (int i = 0; i < mas.length; i++) {
            writer.write(prockod(mas[i]));
            writer.append('\n');
        }
        writer.flush();
        writer.close();


    } public void save(Integer[][] mas,String adress) throws IOException {
        FileWriter writer = new FileWriter(adress);
        for (int i = 0; i < mas.length; i++) {
            writer.write(prockod(mas[i]));
            writer.append('\n');
        }
        writer.flush();
        writer.close();


    }
    public void save(String nameFile,String str) throws IOException {
        FileWriter writer = new FileWriter(nameFile,true);
        writer.write(str);
        writer.flush();
        writer.close();
    }
    private String prockod(Integer[]mas){
        String string = "";
        for (int i = 0; i < mas.length; i++) {
            string += (mas[i] + " ");
        }
        return string;
    }
    private String newName() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите уникальное имя файла");
        name = reader.readLine();
        //reader.close();
        return name;
    }

    public String getName() {
        return name;
    }
}
