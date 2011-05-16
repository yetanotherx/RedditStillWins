package yetanotherx.bukkitplugin.RedditStillWins;

import java.util.logging.Level;

/**
 * Replacing DieAnticrash
 */
public class RedditStillWinsCrashThread implements Runnable {

    private RedditStillWins parent;

    public RedditStillWinsCrashThread(RedditStillWins parent) {
        this.parent = parent;
    }

    public void run() {

        if( RedditStillWinsSettings.crashFrequency <= 0 ) return;

        while (true) {
            try {
                Thread.sleep( RedditStillWinsSettings.crashFrequency );
            } catch (InterruptedException ex) {
                return;
            }

            RedditStillWins.log.log(Level.OFF, "beat");

        }

    }

}
