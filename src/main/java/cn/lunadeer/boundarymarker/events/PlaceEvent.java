package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.Notification;
import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Role;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlaceEvent implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (BoundaryMarkerAPI.hasPermission(player, block.getLocation(), Role::getPlace)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有place权限");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BoundaryMarker.instance.UIs.put(player.getUniqueId().toString(), new HashMap<>());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BoundaryMarker.instance.UIs.remove(player.getUniqueId().toString());
    }
}
