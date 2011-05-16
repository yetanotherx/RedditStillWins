package yetanotherx.bukkitplugin.RedditStillWins;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RedditStillWins extends JavaPlugin {

    public static final Logger log = Logger.getLogger("Minecraft");
    private RedditStillWinsPlayerListener playerListener = new RedditStillWinsPlayerListener(this);
    public ArrayList<Thread> threads = new ArrayList<Thread>();
    
    public void onDisable() {

        for( Thread thread : threads ) {
            try {
                thread.interrupt();
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        log.info("[RedditStillWins] Plugin disabled. (version " + this.getDescription().getVersion() + ")");
    }

    public void onEnable() {

        RedditStillWinsSettings.load();
        RedditStillWinsPermissions.load(this);
        
        threads.add(new Thread(new RedditStillWinsCrashThread(this)));
        threads.add(new Thread(new RedditStillWinsSaveThread(this)));
        threads.add(new Thread(new RedditStillWinsRestartThread(this)));
        threads.add(new Thread(new RedditStillWinsNotifyThread(this)));

        for( Thread thread : threads ) {
            thread.start();
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this.playerListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener, Event.Priority.Normal, this);

        log.info("[RedditStillWins] Plugin enabled! (version " + this.getDescription().getVersion() + ")");

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String commandName = command.getName().toLowerCase();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if( !RedditStillWinsPermissions.has(player, "redditstillwins.admin" ) ) {
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender player = (ConsoleCommandSender) sender;
        } else {
            return true;
        }

        if (commandName.equals("planned-restart")) {
            Thread tmp_thread = new Thread(new RedditStillWinsManualRestartThread(this));
            threads.add(tmp_thread);
            tmp_thread.start();
        }
        if (commandName.equals("restart")) {
            getServer().dispatchCommand(new ConsoleCommandSender(getServer()), "save-all");
            getServer().dispatchCommand(new ConsoleCommandSender(getServer()), "stop");
        }

        return true;
    }

    public int broadcast( String message ) {

        int i = 0;

        for( Player player : getServer().getOnlinePlayers() ) {
            player.sendMessage(message);
            i++;
        }

        return i;
    }
}
