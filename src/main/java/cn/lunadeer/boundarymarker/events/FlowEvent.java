package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.dto.Area;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class FlowEvent implements Listener {

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (!type.equals(Material.WATER)) {
            return;
        }
        Area area = BoundaryMarkerAPI.getAreaByLocation(event.getBlock().getLocation());
        if (area == null) {
            return;
        }
        if (area.getWater_flow()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onLavaFlow(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (!type.equals(Material.LAVA)) {
            return;
        }
        Area area = BoundaryMarkerAPI.getAreaByLocation(event.getBlock().getLocation());
        if (area == null) {
            return;
        }
        if (area.getLava_flow()) {
            return;
        }
        event.setCancelled(true);
    }

}
