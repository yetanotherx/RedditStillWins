package yetanotherx.bukkitplugin.RedditStillWins;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

class RedditStillWinsPlayerListener extends PlayerListener {

    private RedditStillWins parent;

    RedditStillWinsPlayerListener(RedditStillWins parent) {
        this.parent = parent;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {

        for( String trigger : RedditStillWinsSettings.triggers.keySet() ) {
            if( event.getMessage().equals("!" + trigger) ) {
                String[] split = RedditStillWinsSettings.triggers.get(trigger).split("\\\\");

                for( String splat : split ) {
                    parent.getServer().dispatchCommand(new ConsoleCommandSender(parent.getServer()), "say " + splat );
                }

            }
        }
        
        for( String blacklist : RedditStillWinsSettings.blacklist ) {
            if( event.getMessage().matches(blacklist) ) {

                event.setCancelled(true);

                parent.log.warning("WARNING - User has hit the blacklist! User: " + event.getPlayer().getName() + "; Message: " + event.getMessage() );

                for( Player each : event.getPlayer().getServer().getOnlinePlayers() ) {
                    if( RedditStillWinsPermissions.has(each, "redditstillwins.mod" ) ) {
                        each.sendMessage( ChatColor.RED + "Warning! " + event.getPlayer().getName() + " has hit the chat blacklist!");
                        each.sendMessage( ChatColor.RED + "Text: " + event.getMessage() );
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        //Replacing LogShit
        RedditStillWins.log.info( event.getPlayer().getName() + ": " + event.getMessage() );
    }



}
