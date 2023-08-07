package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.Notification;
import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Role;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

import static cn.lunadeer.boundarymarker.BoundaryMarkerAPI.*;

public class BlockEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDestroy(BlockDestroyEvent event) {
        Block block = event.getBlock();
        // 如果方块上有界碑牌，取消破坏
        if (isMarkSignAttachedOn(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        // 如果方块上有界碑牌，取消破坏
        if (isMarkSignAttachedOn(block)) {
            event.setCancelled(true);
            Notification.warn(player,"这个方块上似乎有界碑牌，安全起见已取消此破坏。");
            return;
        }

        if (BoundaryMarkerAPI.hasPermission(player, block.getLocation(), Role::getDestroy)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有destroy权限");
    }
}
