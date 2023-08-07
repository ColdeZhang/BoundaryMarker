package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.dto.Area;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Commands implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            if (commandSender instanceof Player) {
                Notification.info((Player) commandSender, "使用 /bm help 查看帮助");
            } else {
                XLogger.warn("使用 /bm help 查看帮助");
            }
            return true;
        }
        if (Objects.equals(strings[0], "cui")) {
            if (commandSender instanceof Player) {
                CUI.cities_main_page((Player) commandSender);
            } else {
                XLogger.warn("控制台无法使用 CUI");
            }
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                list.add("cui");
                if (player.isOp()) {
                    list.add("reload");
                }
//                List<Area> areas = BoundaryMarkerAPI.getAreasByPlayer(player);
//                for (Area area : areas) {
//                    list.add(area.getName());
//                }
            } else {
                list.add("reload");
            }
            return list;
        }
        return Collections.emptyList();
    }
}
