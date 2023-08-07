package cn.lunadeer.boundarymarker.events;

import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.Notification;
import cn.lunadeer.boundarymarker.XLogger;
import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Role;
import io.papermc.paper.event.entity.EntityDyeEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityMountEvent;

public class PlayerEvent implements Listener {
    // 火焰蔓延以及点火
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIgnite(BlockIgniteEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        if (player == null) {
            // 如果player为null 则为火焰蔓延
            Area area = BoundaryMarkerAPI.getAreaByLocation(block.getLocation());
            if (area == null) {
                return;
            }
            if (!area.getFire_spread()) {
                XLogger.debug("火焰蔓延触发");
                event.setCancelled(true);
            }
            return;
        }
        if (BoundaryMarkerAPI.hasPermission(player, block.getLocation(), Role::getIgnite)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有 ignite 权限");
    }

    // 怪物和动物击杀
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntitiesKilled(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity causer = event.getDamager();
        if (!(causer instanceof Player)) {
            return;
        }
        Player player = (Player) causer;
        if (entity instanceof Monster) {
            XLogger.debug("Monster killed 触发");
            if (BoundaryMarkerAPI.hasPermission(player, entity.getLocation(), Role::getMob_killing)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 mob_killing 权限");
        } else if (entity instanceof Animals || entity instanceof Villager) {
            XLogger.debug("Animal killed 触发");
            if (BoundaryMarkerAPI.hasPermission(player, entity.getLocation(), Role::getAnimal_killing)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 animal_killing 权限");
        }
    }

    // 工作台 熔炉 等功能方块
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUseBlock(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getLocation() == null) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.WORKBENCH ||
                event.getInventory().getType() == InventoryType.FURNACE ||
                event.getInventory().getType() == InventoryType.BLAST_FURNACE ||
                event.getInventory().getType() == InventoryType.SMOKER ||
                event.getInventory().getType() == InventoryType.BREWING ||
                event.getInventory().getType() == InventoryType.ANVIL ||
                event.getInventory().getType() == InventoryType.ENCHANTING ||
                event.getInventory().getType() == InventoryType.GRINDSTONE ||
                event.getInventory().getType() == InventoryType.STONECUTTER ||
                event.getInventory().getType() == InventoryType.CARTOGRAPHY ||
                event.getInventory().getType() == InventoryType.LOOM ||
                event.getInventory().getType() == InventoryType.SMITHING) {
            XLogger.debug("use 触发 type" + event.getInventory().getType());
            if (BoundaryMarkerAPI.hasPermission(player, event.getInventory().getLocation(), Role::getUse)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 use 权限");
        }
    }

    // 容器
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onContainer(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getLocation() == null) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.CHEST ||
                event.getInventory().getType() == InventoryType.BARREL ||
                event.getInventory().getType() == InventoryType.SHULKER_BOX ||
                event.getInventory().getType() == InventoryType.ENDER_CHEST ||
                event.getInventory().getType() == InventoryType.DROPPER ||
                event.getInventory().getType() == InventoryType.DISPENSER
        ) {
            XLogger.debug("container 触发 type" + event.getInventory().getType());
            if (BoundaryMarkerAPI.hasPermission(player, event.getInventory().getLocation(), Role::getContainer)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 container 权限");
        }
    }

    // 特殊容器
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHopper(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getLocation() == null) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.HOPPER) {
            XLogger.debug("hopper 触发 type" + event.getInventory().getType());
            if (BoundaryMarkerAPI.hasPermission(player, event.getInventory().getLocation(), Role::getHopper)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 hopper 权限");
        }
    }

    // 交易
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTrade(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getLocation() == null) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.MERCHANT) {
            if (BoundaryMarkerAPI.hasPermission(player, event.getInventory().getLocation(), Role::getTrade)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 trade 权限");
        }
    }

    // 红石相关的东西的交互
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRedStone(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) {
            return;
        }
        Material clicked = event.getClickedBlock().getType();
        XLogger.debug("redstone 触发 type" + clicked);
        if (clicked == Material.REPEATER ||
                clicked == Material.COMPARATOR ||
                clicked == Material.DAYLIGHT_DETECTOR ||
                clicked == Material.LEVER ||
                clicked == Material.STONE_BUTTON ||
                clicked == Material.BAMBOO_BUTTON ||
                clicked == Material.POLISHED_BLACKSTONE_BUTTON ||
                clicked == Material.OAK_BUTTON ||
                clicked == Material.SPRUCE_BUTTON ||
                clicked == Material.BIRCH_BUTTON ||
                clicked == Material.JUNGLE_BUTTON ||
                clicked == Material.ACACIA_BUTTON ||
                clicked == Material.CHERRY_BUTTON ||
                clicked == Material.DARK_OAK_BUTTON ||
                clicked == Material.MANGROVE_BUTTON ||
                clicked == Material.CRIMSON_BUTTON ||
                clicked == Material.WARPED_BUTTON ||
                clicked == Material.STONE_PRESSURE_PLATE ||
                clicked == Material.POLISHED_BLACKSTONE_PRESSURE_PLATE ||
                clicked == Material.LIGHT_WEIGHTED_PRESSURE_PLATE ||
                clicked == Material.HEAVY_WEIGHTED_PRESSURE_PLATE ||
                clicked == Material.OAK_PRESSURE_PLATE ||
                clicked == Material.SPRUCE_PRESSURE_PLATE ||
                clicked == Material.BIRCH_PRESSURE_PLATE ||
                clicked == Material.JUNGLE_PRESSURE_PLATE ||
                clicked == Material.ACACIA_PRESSURE_PLATE ||
                clicked == Material.CHERRY_PRESSURE_PLATE ||
                clicked == Material.DARK_OAK_PRESSURE_PLATE ||
                clicked == Material.MANGROVE_PRESSURE_PLATE ||
                clicked == Material.BAMBOO_PRESSURE_PLATE ||
                clicked == Material.CRIMSON_PRESSURE_PLATE ||
                clicked == Material.WARPED_PRESSURE_PLATE) {
            XLogger.debug("redstone 触发 type" + clicked);
            if (BoundaryMarkerAPI.hasPermission(player, event.getClickedBlock().getLocation(), Role::getRed_stone)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 redstone 权限");
        }
    }

    // 门相关的东西的交互
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDoor(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) {
            return;
        }
        Material clicked = event.getClickedBlock().getType();
        XLogger.debug("door 触发 type" + clicked);
        if (clicked == Material.IRON_DOOR ||
                clicked == Material.OAK_DOOR ||
                clicked == Material.SPRUCE_DOOR ||
                clicked == Material.BIRCH_DOOR ||
                clicked == Material.JUNGLE_DOOR ||
                clicked == Material.ACACIA_DOOR ||
                clicked == Material.CHERRY_DOOR ||
                clicked == Material.DARK_OAK_DOOR ||
                clicked == Material.MANGROVE_DOOR ||
                clicked == Material.BAMBOO_DOOR ||
                clicked == Material.CRIMSON_DOOR ||
                clicked == Material.WARPED_DOOR ||
                clicked == Material.IRON_TRAPDOOR ||
                clicked == Material.OAK_TRAPDOOR ||
                clicked == Material.SPRUCE_TRAPDOOR ||
                clicked == Material.BIRCH_TRAPDOOR ||
                clicked == Material.JUNGLE_TRAPDOOR ||
                clicked == Material.ACACIA_TRAPDOOR ||
                clicked == Material.CHERRY_TRAPDOOR ||
                clicked == Material.DARK_OAK_TRAPDOOR ||
                clicked == Material.MANGROVE_TRAPDOOR ||
                clicked == Material.BAMBOO_TRAPDOOR ||
                clicked == Material.CRIMSON_TRAPDOOR ||
                clicked == Material.WARPED_TRAPDOOR ||
                clicked == Material.OAK_FENCE_GATE ||
                clicked == Material.SPRUCE_FENCE_GATE ||
                clicked == Material.BIRCH_FENCE_GATE ||
                clicked == Material.JUNGLE_FENCE_GATE ||
                clicked == Material.ACACIA_FENCE_GATE ||
                clicked == Material.CHERRY_FENCE_GATE ||
                clicked == Material.DARK_OAK_FENCE_GATE ||
                clicked == Material.MANGROVE_FENCE_GATE ||
                clicked == Material.BAMBOO_FENCE_GATE ||
                clicked == Material.CRIMSON_FENCE_GATE ||
                clicked == Material.WARPED_FENCE_GATE
        ) {
            XLogger.debug("door 触发 type" + clicked);
            if (BoundaryMarkerAPI.hasPermission(player, event.getClickedBlock().getLocation(), Role::getDoor)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 door 权限");
        }
    }

    // 染色
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDye(EntityDyeEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (BoundaryMarkerAPI.hasPermission(player, entity.getLocation(), Role::getDye)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有 dye 权限");
    }

    // 投掷 鸡蛋、雪球 一类东西
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThrowing(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (BoundaryMarkerAPI.hasPermission(player, event.getEntity().getLocation(), Role::getThrowing)) {
                return;
            }
            event.setCancelled(true);
            Notification.error(player, "你没有 throwing 权限");
        }
    }

    // 种植
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlant(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) {
            return;
        }
        Material clicked = event.getClickedBlock().getType();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (clicked != Material.FARMLAND) {
            return;
        }
        if (BoundaryMarkerAPI.hasPermission(player, event.getClickedBlock().getLocation(), Role::getPlant)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有 plant 权限");
    }

    // 移动
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (BoundaryMarkerAPI.hasPermission(player, event.getTo(), Role::getMove)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有 move 权限");
    }

    // 发光效果、欢迎语句
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInOut(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Area area_to = BoundaryMarkerAPI.getAreaByLocation(event.getTo());
        Area area_from = BoundaryMarkerAPI.getAreaByLocation(event.getFrom());
        if (area_to != null && area_to.equals(area_from)) {
            return;
        }
        if (area_from == null) {
            // 进入
            if (area_to != null) {
                Notification.info(player, area_to.getWelcome_message());
                if (area_to.getGlow()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000000, 0, false, false));
                }
            }
        }
        if (area_to == null) {
            // 离开
            if (area_from != null) {
                Notification.info(player, area_from.getFarewell_message());
                if (area_from.getGlow()) {
                    player.removePotionEffect(PotionEffectType.GLOWING);
                }
            }
        }
    }

    // 骑乘
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRide(EntityMountEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (BoundaryMarkerAPI.hasPermission(player, event.getMount().getLocation(), Role::getRiding)) {
            return;
        }
        event.setCancelled(true);
        Notification.error(player, "你没有 ride 权限");
    }

    // 作物践踏
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTrample(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        Material clicked = event.getClickedBlock().getType();
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        if (clicked != Material.FARMLAND) {
            return;
        }
        Area area = BoundaryMarkerAPI.getAreaByLocation(event.getClickedBlock().getLocation());
        if (area == null) {
            return;
        }
        if (area.getTrample()) {
            return;
        }
        event.setCancelled(true);
    }

}
