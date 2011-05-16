package yetanotherx.bukkitplugin.RedditStillWins;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RedditStillWinsPermissions {

    /**
     * Permission handlers
     */
    private static PermissionHandler Permissions;

    private static HandlerType handlerType;

    private enum HandlerType {
	PERMISSIONS,
	VANILLA
    }

    /**
     * Check if permissions is installed, and initiate it
     */
    public static void load( RedditStillWins parent ) {

	Plugin perm_plugin = parent.getServer().getPluginManager().getPlugin("Permissions");

	if( Permissions == null ) {

            if( perm_plugin != null ) {
		Permissions = ((Permissions) perm_plugin).getHandler();
		handlerType = HandlerType.PERMISSIONS;

		RedditStillWins.log.info("Using Permissions version " + perm_plugin.getDescription().getVersion() + " for permission handling");
	    }

            else {
		handlerType = HandlerType.VANILLA;

		RedditStillWins.log.info("Neither Permissions or GroupManager found. Using ops.txt for permission handling");
	    }
	}

    }

    public static boolean has( Player player, String permission, boolean restricted ) {
	switch( handlerType ) {
	case PERMISSIONS:
	    return Permissions.has(player, permission);
	default:
	    if( restricted ) {
		return player.isOp();
	    }
	    return true;

	}

    }

    public static boolean has( Player player, String permission ) {
	return has(player, permission, true);
    }

}
