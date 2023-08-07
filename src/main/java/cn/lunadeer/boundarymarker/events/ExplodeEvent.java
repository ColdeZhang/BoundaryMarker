package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.dto.Area;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import static cn.lunadeer.boundarymarker.BoundaryMarkerAPI.isMarkSignAttachedOn;

public class ExplodeEvent implements Listener {
    @EventHandler
    public void onEntity(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        Area area = BoundaryMarkerAPI.getAreaByLocation(entity.getLocation());
        if (area == null) {
            return;
        }
        if (entity.getType() == EntityType.MINECART_TNT || entity.getType() == EntityType.PRIMED_TNT) {
            if (area.getExplode()) {
                return;
            }
        }
        if (entity.getType() == EntityType.CREEPER || entity.getType() == EntityType.WITHER_SKULL) {
            if (area.getCreeper_explode()) {
                return;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onWitherSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.WITHER) {
            return;
        }
        Area area = BoundaryMarkerAPI.getAreaByLocation(entity.getLocation());
        if (area == null) {
            return;
        }
        if (area.getCreeper_explode()) {
            return;
        }
        event.setCancelled(true);
    }

}
