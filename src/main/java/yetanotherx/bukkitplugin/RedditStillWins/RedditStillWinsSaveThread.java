package yetanotherx.bukkitplugin.RedditStillWins;

import java.util.List;
import org.bukkit.World;

/**
 * Replacing Saver
 */
public class RedditStillWinsSaveThread implements Runnable {

    private RedditStillWins parent;

    public RedditStillWinsSaveThread(RedditStillWins parent) {
        this.parent = parent;
    }

    public void run() {

        if( RedditStillWinsSettings.saveFrequency <= 0 ) return;

        while (true) {
            try {
                Thread.sleep( RedditStillWinsSettings.saveFrequency );
            } catch (InterruptedException ex) {
                return;
            }

            //System.out.println("Saving");
            
            this.parent.getServer().savePlayers();
            List<World> worlds = this.parent.getServer().getWorlds();
            for (World world : worlds) {
                world.save();
            }

        }

    }

}
