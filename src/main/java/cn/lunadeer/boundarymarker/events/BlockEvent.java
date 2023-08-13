package cn.lunadeer.boundarymarker.events;

import org.bukkit.event.Listener;

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
//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onBlockBreak(BlockBreakEvent event) {
//        Block block = event.getBlock();
//        Player player = event.getPlayer();
//        // 如果方块上有界碑牌，取消破坏
//        if (isMarkSignAttachedOn(block)) {
//            event.setCancelled(true);
//            Notification.warn(player,"这个方块上似乎有界碑牌，安全起见已取消此破坏。");
//            return;
//        }
//
//        if (BoundaryMarkerAPI.hasPermission(player, block.getLocation(), Role::getDestroy)) {
//            return;
//        }
//        event.setCancelled(true);
//        Notification.error(player, "你没有destroy权限");
//    }
}
