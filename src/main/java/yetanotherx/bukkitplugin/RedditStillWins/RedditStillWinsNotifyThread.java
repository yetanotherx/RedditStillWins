package yetanotherx.bukkitplugin.RedditStillWins;

import java.util.List;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Replacing cool.txt
 */
public class RedditStillWinsNotifyThread implements Runnable {

    private RedditStillWins parent;

    public RedditStillWinsNotifyThread(RedditStillWins parent) {
        this.parent = parent;
    }

    public void run() {
        if( RedditStillWinsSettings.notifyFrequency <= 0 ) return;

        while (true) {
            try {
                Thread.sleep(RedditStillWinsSettings.notifyFrequency);
            } catch (InterruptedException ex) {
                return;
            }

            int size = RedditStillWinsSettings.messages.size();
            int i = new Random().nextInt(size);

            Object[] arr = RedditStillWinsSettings.messages.toArray();

            parent.getServer().dispatchCommand(new ConsoleCommandSender(parent.getServer()), "say " + arr[i] );


        }
    }
}
