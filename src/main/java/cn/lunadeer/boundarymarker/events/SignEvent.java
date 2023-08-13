package cn.lunadeer.boundarymarker.events;

import org.bukkit.event.Listener;

public class SignEvent implements Listener {

//    @EventHandler
//    public void onMarkerChange(SignChangeEvent event) {
//        Sign sign = (Sign) event.getBlock().getState();
//        Block block = BoundaryMarkerAPI.getBlockSignAttachedOn(sign);
//        if (block == null) {
//            return;
//        }
//        if (!BoundaryMarkerAPI.isMarkerSign(sign.getSide(Side.FRONT))) {
//            return;
//        }
//        // create or update marker
//        Player player = event.getPlayer();
//        String area_name = BoundaryMarkerAPI.getMarkerAreaName(sign);
//        if (!BoundaryMarkerAPI.checkString(area_name)) {
//            Notification.error(player, "区域名仅支持大小写英文、数字、下划线！");
//            return;
//        }
//        Integer marker_index = BoundaryMarkerAPI.getMarkerIndex(sign);
//        Area area = BoundaryMarkerAPI.getAreaByName(sign.getWorld(), area_name);
//        if (area == null) {
//            // 创建新的区域
//            Notification.info(player, "创建新的区域 " + area_name);
//            Notification.info(player, "添加第 " + marker_index + " 个界碑，坐标 " + block.getX() + " " + block.getY() + " " + block.getZ());
//            Area new_area = BoundaryMarkerAPI.createNewAreaWithDefaultConfiguration(area_name, player, block.getWorld().getName());
//            new_area.getMarkers_x().put(marker_index, block.getX());
//            new_area.getMarkers_z().put(marker_index, block.getZ());
//            BoundaryMarker.instance.cache.getAreas().computeIfAbsent(block.getWorld().getName(), k -> new HashMap<>());
//            BoundaryMarker.instance.cache.getAreas().get(block.getWorld().getName()).put(area_name, new_area);
//            return;
//        }
//        Area area_1 = BoundaryMarkerAPI.getAreaByLocation(sign.getLocation());
//        if (area_1 != null && !Objects.equals(area_1.getName(), area_name)) {
//            // 区域冲突
//            Notification.error(player, "与 " + area.getName() + " 区域冲突！");
//            Notification.info(player, "如果需要修改区域名，请先移除所有的界碑后重新添加！");
//            event.setCancelled(true);
//            return;
//        }
//        if (BoundaryMarkerAPI.isOwner(area, player)) {
//            // 更新区域
//            Notification.info(player, "更新区域 " + area_name + " 的第 " + marker_index + " 个界碑，坐标 " + block.getX() + " " + block.getY() + " " + block.getZ());
//            area.getMarkers_x().put(marker_index, block.getX());
//            area.getMarkers_z().put(marker_index, block.getZ());
//            Map<String, Area> areas = BoundaryMarker.instance.cache.getAreas().get(block.getWorld().getName());
//            for (Area area1 : areas.values()) {
//                if (Objects.equals(area1.getName(), area_name)) {
//                    continue;
//                }
//                for (Integer index : area1.getMarkers_x().keySet()) {
//                    Location location = new Location(block.getWorld(), area1.getMarkers_x().get(index), block.getY(), area1.getMarkers_z().get(index));
//                    if (BoundaryMarkerAPI.isInside(location, area)) {
//                        Notification.error(player, "与 " + area1.getName() + " 区域冲突！本次修改无效！");
//                        area.getMarkers_x().remove(marker_index);
//                        area.getMarkers_z().remove(marker_index);
//                        event.setCancelled(true);
//                        return;
//                    }
//                }
//            }
//            return;
//        }
//        // 不是所有者 不可以修改
//        Notification.error(player, "这是一块界碑，你不是这个区域的所有者，无法修改！或者可能此名称已被占用！");
//        event.setCancelled(true);
//    }
//
//    // 破坏界碑牌
//    @EventHandler
//    public void onMarkerDestroy(BlockBreakEvent event) {
//        Block block = event.getBlock();
//        Player player = event.getPlayer();
//        if (!BoundaryMarkerAPI.isSign(block)) {
//            // not a sign
//            return;
//        }
//        Sign sign = (Sign) block.getState();
//        if (!BoundaryMarkerAPI.isMarkerSign(sign.getSide(Side.FRONT))) {
//            // not a marker sign
//            return;
//        }
//        Area area = BoundaryMarkerAPI.getAreaByName(sign.getWorld(), BoundaryMarkerAPI.getMarkerAreaName(sign));
//        if (area == null) {
//            return;
//        }
//        // if is a marker sign, then check if is owner of marker
//        if (BoundaryMarkerAPI.isOwner(area, player)) {
//            // 是所有者 可以破坏
//            player.sendMessage(Component.text("移除区域 " + area.getName() + " 的第 " + BoundaryMarkerAPI.getMarkerIndex(sign) + " 个界碑，坐标 " + sign.getX() + " " + sign.getY() + " " + sign.getZ()));
//            Integer marker_index = BoundaryMarkerAPI.getMarkerIndex(sign);
//            area.getMarkers_x().remove(marker_index);
//            area.getMarkers_z().remove(marker_index);
//            if (area.getMarkers_z().isEmpty() && area.getMarkers_x().isEmpty()) {
//                // 区域没有界碑了，移除区域
//                player.sendMessage(Component.text("区域 " + area.getName() + " 没有界碑了，自动移除区域"));
//                BoundaryMarker.instance.cache.getAreas().get(sign.getWorld().getName()).remove(area.getName());
//            }
//        } else {
//            // 不是所有者 不可以破坏
//            Notification.error(player, "你不是这个区域的所有者，无法破坏这个界碑牌！");
//            event.setCancelled(true);
//        }
//    }

}
