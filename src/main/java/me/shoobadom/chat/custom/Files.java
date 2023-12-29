package me.shoobadom.chat.custom;

import me.shoobadom.chat.ChatBubbles;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Files {

    private static final ChatBubbles instance = ChatBubbles.getInstance();
    private static final File path = new File(instance.getDataFolder(),"fontWidths.txt");

    private static final Map<Character,Integer> fontWidths = new HashMap<>();

    public static void setup() {
        instance.saveDefaultConfig(); // Create config.yml and resource folder for extra files

        if (!path.exists()) {
            // Creates default presets file
            path.getParentFile().mkdirs();
            instance.saveResource("fontWidths.txt",true);
        }


        try {
            File file = new File(instance.getDataFolder(),"fontWidths.txt");
            Scanner sc = new Scanner(file,"utf-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() >= 3) {
                    fontWidths.put(line.charAt(0),Integer.parseInt(line.substring(2)));
                }

            }
            sc.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }


    }

    public static int readPixels(String str) {
        int total = 0;
        for (int i=0;i<str.length();i++) {
            if (fontWidths.get(str.charAt(i)) != null) {
                total += fontWidths.get(str.charAt(i));
            } else {
                total+=6;
            }
        }

        return total;
    }

    public static int getInt(String key) {
        return instance.getConfig().getInt(key);
    }

    public static double getDouble(String key) {
        return instance.getConfig().getDouble(key);
    }
    public static boolean getBool(String key) {
        return instance.getConfig().getBoolean(key);
    }
    public static String getString(String key) {
        return instance.getConfig().getString(key);
    }
}
