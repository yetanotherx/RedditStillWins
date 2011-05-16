package yetanotherx.bukkitplugin.RedditStillWins;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Replacing Restarter
 */
public class RedditStillWinsManualRestartThread implements Runnable {

    private RedditStillWins parent;

    public RedditStillWinsManualRestartThread(RedditStillWins parent) {
        this.parent = parent;
    }

    public void run() {

        parent.broadcast( ChatColor.WHITE + "[" + ChatColor.RED + "Broadcast" + ChatColor.WHITE + "] " + ChatColor.GREEN + "Restart in 60 seconds! All work will be saved!" );

        try {
            Thread.sleep(60000L);
        } catch (InterruptedException ex) {
            return;
        }

        parent.getServer().dispatchCommand(new ConsoleCommandSender(parent.getServer()), "save-all");

        for (Player player : parent.getServer().getOnlinePlayers()) {
            player.kickPlayer("Server is restarting. Please reconnect.");
        }

        //To anyone who doesn't know what's going on here... we have our server in a wrapper
        //that starts the server in a while(true) command.
        //Therefore, stopping the server restarts it
        parent.getServer().dispatchCommand(new ConsoleCommandSender(parent.getServer()), "stop");

    }
}
