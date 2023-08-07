package cn.lunadeer.boundarymarker;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

public class XLogger {
    static void info(String msg) {
        msg = "§aINFO : " + msg;
        BoundaryMarker.instance.getLogger().info(msg);
    }

    public static void warn(String msg) {
        msg = "§eWARN : " + msg;
        BoundaryMarker.instance.getLogger().warning(msg);
    }

    public static void error(String msg) {
        msg = "§cERROR: " + msg;
        BoundaryMarker.instance.getLogger().severe(msg);
    }

    public static void debug(String msg) {
        if (!BoundaryMarker.instance.config.debug()) {
            return;
        }
        msg = "§bDEBUG: " + msg;
        BoundaryMarker.instance.getLogger().info(msg);
    }


}
