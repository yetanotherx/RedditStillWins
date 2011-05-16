package yetanotherx.bukkitplugin.RedditStillWins;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.util.config.Configuration;

public class RedditStillWinsSettings {

    /**
     * Settings
     */
    public static boolean debugMode = false;
    public static int crashFrequency = 45000;
    public static int restartFrequency = 5000000;
    public static int saveFrequency = 60000;
    public static int notifyFrequency = 400000;
    public static ArrayList<String> messages = new ArrayList<String>();
    public static ArrayList<String> blacklist = new ArrayList<String>();
    public static HashMap<String, String> triggers = new HashMap<String, String>();

    /**
     * Bukkit config class
     */
    public static Configuration config = null;

    /**
     * Load and parse the YAML config file
     */
    public static void load() {

        File dataDirectory = new File("plugins" + File.separator + "RedditStillWins" + File.separator);

        dataDirectory.mkdirs();

        File file = new File("plugins" + File.separator + "RedditStillWins", "config.yml");

        config = new Configuration(file);
        config.load();

        if (!file.exists()) {
            config.setProperty("debug", debugMode);

            config.setProperty("crash_frequency", crashFrequency);
            config.setProperty("save_frequency", saveFrequency);
            config.setProperty("restart_frequency", restartFrequency);
            config.setProperty("notify_frequency", notifyFrequency);

            config.setProperty("messages", messages);
            config.setProperty("blacklist", blacklist);
            config.setProperty("triggers", triggers);

            config.save();
        }

        setSettings();


    }

    /**
     * Sets the internal variables
     */
    private static void setSettings() {

        debugMode = config.getBoolean("debug", debugMode);

        crashFrequency = config.getInt("crash_frequency", crashFrequency);
        saveFrequency = config.getInt("save_frequency", saveFrequency);
        restartFrequency = config.getInt("restart_frequency", restartFrequency);
        notifyFrequency = config.getInt("notify_frequency", notifyFrequency);

        messages = new ArrayList<String>();
        for( String message : config.getStringList("messages", messages) ) {
            messages.add(message);
        }

        blacklist = new ArrayList<String>();
        for( String blacklisted : config.getStringList("blacklist", messages) ) {
            blacklist.add(blacklisted);
        }

        triggers = new HashMap<String, String>();
        for( String key : config.getKeys("triggers") ) {
            triggers.put(key, config.getString("triggers." + key ) );
        }

    }
}
