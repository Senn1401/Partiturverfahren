package Server;

import java.io.*;
import java.util.ArrayList;

/**
 * @author stsendom
 * The class is to execute once before starting the programm
 * It adds a annotation to the my.ini of sql for the default time
 */
public class BeforeStart {
    private static File fileEng = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\my.ini");
    private static String command = "default-time-zone = '+00:00'";
    private static BufferedWriter bw;
    private static BufferedReader br;
    private static String line;
    private static boolean isEnglish;
    private static ArrayList<String> content = new ArrayList<>();
    public static void main(String[] args){
        if (fileEng.exists()) {
            isEnglish = true;
            try {
                br = new BufferedReader(new FileReader(fileEng));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            while ((line = br.readLine()) != null){
                content.add(line);
                content.add("\n");
                if (line.equals("[mysqld]")){
                    content.add(command);
                    content.add("\n");
                }
            }
            br.close();
            if (isEnglish){
                bw = new BufferedWriter(new FileWriter(fileEng));
            }
            for (String i : content){
                bw.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
