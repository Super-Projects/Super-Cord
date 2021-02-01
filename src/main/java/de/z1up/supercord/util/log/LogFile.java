package de.z1up.supercord.util.log;

import de.z1up.supercord.SuperCord;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFile {

    private static String tag;
    private static File file;
    private static String path;
    private static int count = 1;

    private static String createTag() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-DD");
        LocalDateTime ldt = LocalDateTime.now();
        String formatted = dtf.format(ldt);

        File f = new File(path, formatted + "-" + count + ".txt");

        if(f.exists()) {
            count++;
            return createTag();
        }

        return formatted + "-" + count + ".txt";
    }

    private static void loadFile() {

        file = new File(path, tag);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public static void write(String str) {

        if(file == null) {
            tag = createTag();
            path = SuperCord.instance.getDataFolder().toString() + "//logs";

            File pathFile = new File(path);
            if(!pathFile.exists()) {
                pathFile.mkdirs();
            }

            loadFile();
        }

        str = format(str);

        try {
            Writer out;
            out = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
            out.append(System.lineSeparator() + str);
            out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private static String format(String s) {
        s = s.replaceAll("null", "");
        s = s.replaceAll("§7", "");

        for(int i = 0; i < s.length(); i++) {
            char tag = s.charAt(i);
            if(tag == '§') {
                char identifier = s.charAt(i+1);
                s = s.replaceAll("§" + identifier, "");
            }
        }
        return s;
    }

}
