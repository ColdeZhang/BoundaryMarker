package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.Notification;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import cn.lunadeer.boundarymarker.dto.Role;

public class BlockEvent implements Listener {

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onBlockDestroy(BlockDestroyEvent event) {
//        Block block = event.getBlock();
//        // 如果方块上有界碑牌，取消破坏
//        if (isMarkSignAttachedOn(block)) {
//            event.setCancelled(true);
//        }
//    }
//
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (BoundaryMarkerAPI.hasPermission(player, block.getLocation(), Role::getDestroy)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有destroy权限");
    }
}
