package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.dto.Area;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResidenceStyleAPI {

//    /**
//     * 创建一个矩形区域
//     *
//     * @param name   区域名
//     * @param a      点a
//     * @param b      点b
//     * @param player 玩家
//     * @return 创建的区域 或 null 如果创建失败
//     */
//    public static Area create(String name, Location a, Location b, Player player) {
//        if (!a.getWorld().getName().equals(b.getWorld().getName())) {
//            Notification.error(player, "两个点不在同一个世界");
//            return null;
//        }
//        Area conflict_area = BoundaryMarkerAPI.getAreaByName(name);
//        if (conflict_area != null) {
//            Notification.error(player, "已存在名为 " + name + " 的区域");
//            return null;
//        }
//        ArrayList<Location> locations = new ArrayList<>();
//        locations.add(a);
//        locations.add(new Location(a.getWorld(), b.getX(), a.getY(), a.getZ()));
//        locations.add(new Location(a.getWorld(), b.getX(), a.getY(), b.getZ()));
//        locations.add(new Location(a.getWorld(), a.getX(), a.getY(), b.getZ()));
//        for (Location location : locations) {
//            conflict_area = BoundaryMarkerAPI.getAreaByLocation(location);
//            if (conflict_area != null) {
//                Notification.error(player, "和区域 " + conflict_area.getName() + " 冲突");
//                return null;
//            }
//        }
//        Area area = BoundaryMarkerAPI.createNewAreaWithDefaultConfiguration(name, player, a.getWorld().getName());
//        int index = 0;
//        for (Location location : locations) {
//            area.getMarkers_x().put(index, location.getBlockX());
//            area.getMarkers_z().put(index, location.getBlockZ());
//        }
//        area.setY_top(Math.max(a.getBlockY(), b.getBlockY()));
//        area.setY_bottom(Math.min(a.getBlockY(), b.getBlockY()));
//        Notification.info(player, "创建了一个名为 " + name + " 的区域");
//        return area;
//    }

    /**
     * 扩充区域
     *
     * @param area     区域
     * @param player   玩家
     * @param distance 距离
     * @return 扩充后的区域
     */
    public static Area expand(Area area, Player player, int distance) {
        if (!squareArea(area)) {
            Notification.error(player, "只有矩形区域才能使用此功能");
            return area;
        }

        BlockFace face_to = player.getFacing();
        switch (face_to) {
            case NORTH:
                if (area.getMarkers_z().get(0) < area.getMarkers_z().get(2)) {
                    N1(area, distance);
                } else {
                    N2(area, distance);
                }
                Notification.info(player, "向北扩展了 " + distance + " 格");
                break;
            case SOUTH:
                if (area.getMarkers_z().get(0) > area.getMarkers_z().get(2)) {
                    S1(area, distance);
                } else {
                    S2(area, distance);
                }
                Notification.info(player, "向南扩展了 " + distance + " 格");
                break;
            case EAST:
                if (area.getMarkers_x().get(0) > area.getMarkers_x().get(2)) {
                    E1(area, distance);
                } else {
                    E2(area, distance);
                }
                Notification.info(player, "向东扩展了 " + distance + " 格");
                break;
            case WEST:
                if (area.getMarkers_x().get(0) < area.getMarkers_x().get(2)) {
                    W1(area, distance);
                } else {
                    W2(area, distance);
                }
                Notification.info(player, "向西扩展了 " + distance + " 格");
                break;
            case UP:
                int after = area.getY_top() + distance;
                if (after > 255) {
                    after = 255;
                }
                area.setY_top(after);
                Notification.info(player, "向上扩展了 " + distance + " 格");
                break;
            case DOWN:
                int before = area.getY_bottom() - distance;
                if (before < -255) {
                    before = -255;
                }
                area.setY_bottom(before);
                Notification.info(player, "向下扩展了 " + distance + " 格");
                break;
            default:
                Notification.error(player, "请面向一个方向");
                return area;
        }
        return area;
    }

    private static void W2(Area area, int distance) {
        if (Objects.equals(area.getMarkers_x().get(0), area.getMarkers_x().get(1))) {
            area.getMarkers_x().put(2, area.getMarkers_x().get(2) - distance);
            area.getMarkers_x().put(3, area.getMarkers_x().get(3) - distance);
        } else {
            area.getMarkers_x().put(1, area.getMarkers_x().get(1) - distance);
            area.getMarkers_x().put(2, area.getMarkers_x().get(2) - distance);
        }
    }

    private static void W1(Area area, int distance) {
        if (Objects.equals(area.getMarkers_x().get(0), area.getMarkers_x().get(1))) {
            area.getMarkers_x().put(0, area.getMarkers_x().get(0) - distance);
            area.getMarkers_x().put(1, area.getMarkers_x().get(1) - distance);
        } else {
            area.getMarkers_x().put(0, area.getMarkers_x().get(0) - distance);
            area.getMarkers_x().put(3, area.getMarkers_x().get(3) - distance);
        }
    }

    private static void E2(Area area, int distance) {
        if (Objects.equals(area.getMarkers_x().get(0), area.getMarkers_x().get(1))) {
            area.getMarkers_x().put(2, area.getMarkers_x().get(2) + distance);
            area.getMarkers_x().put(3, area.getMarkers_x().get(3) + distance);
        } else {
            area.getMarkers_x().put(1, area.getMarkers_x().get(1) + distance);
            area.getMarkers_x().put(2, area.getMarkers_x().get(2) + distance);
        }
    }

    private static void E1(Area area, int distance) {
        if (Objects.equals(area.getMarkers_x().get(0), area.getMarkers_x().get(1))) {
            area.getMarkers_x().put(0, area.getMarkers_x().get(0) + distance);
            area.getMarkers_x().put(1, area.getMarkers_x().get(1) + distance);
        } else {
            area.getMarkers_x().put(0, area.getMarkers_x().get(0) + distance);
            area.getMarkers_x().put(3, area.getMarkers_x().get(3) + distance);
        }
    }

    private static void S2(Area area, int distance) {
        if (Objects.equals(area.getMarkers_z().get(0), area.getMarkers_z().get(1))) {
            area.getMarkers_z().put(2, area.getMarkers_z().get(2) + distance);
            area.getMarkers_z().put(3, area.getMarkers_z().get(3) + distance);
        } else {
            area.getMarkers_z().put(1, area.getMarkers_z().get(1) + distance);
            area.getMarkers_z().put(2, area.getMarkers_z().get(2) + distance);
        }
    }

    private static void S1(Area area, int distance) {
        if (Objects.equals(area.getMarkers_z().get(0), area.getMarkers_z().get(1))) {
            area.getMarkers_z().put(0, area.getMarkers_z().get(0) + distance);
            area.getMarkers_z().put(1, area.getMarkers_z().get(1) + distance);
        } else {
            area.getMarkers_z().put(0, area.getMarkers_z().get(0) + distance);
            area.getMarkers_z().put(3, area.getMarkers_z().get(3) + distance);
        }
    }

    private static void N2(Area area, int distance) {
        if (Objects.equals(area.getMarkers_z().get(0), area.getMarkers_z().get(1))) {
            area.getMarkers_z().put(2, area.getMarkers_z().get(2) - distance);
            area.getMarkers_z().put(3, area.getMarkers_z().get(3) - distance);
        } else {
            area.getMarkers_z().put(1, area.getMarkers_z().get(1) - distance);
            area.getMarkers_z().put(2, area.getMarkers_z().get(2) - distance);
        }
    }

    private static void N1(Area area, int distance) {
        if (Objects.equals(area.getMarkers_z().get(0), area.getMarkers_z().get(1))) {
            area.getMarkers_z().put(0, area.getMarkers_z().get(0) - distance);
            area.getMarkers_z().put(1, area.getMarkers_z().get(1) - distance);
        } else {
            area.getMarkers_z().put(0, area.getMarkers_z().get(0) - distance);
            area.getMarkers_z().put(3, area.getMarkers_z().get(3) - distance);
        }
    }

    /**
     * 缩小区域
     *
     * @param area     区域
     * @param player   玩家
     * @param distance 缩小距离
     * @return 缩小后的区域
     */
    public static Area contract(Area area, Player player, int distance) {
        if (!squareArea(area)) {
            Notification.error(player, "只有矩形区域才能使用此功能");
            return area;
        }

        BlockFace face_to = player.getFacing();
        switch (face_to) {
            case NORTH:
                if (area.getMarkers_z().get(0) > area.getMarkers_z().get(2)) {
                    N1(area, distance);
                } else {
                    N2(area, distance);
                }
                Notification.info(player, "向北缩小了 " + distance + " 格");
                break;
            case SOUTH:
                if (area.getMarkers_z().get(0) < area.getMarkers_z().get(2)) {
                    S1(area, distance);
                } else {
                    S2(area, distance);
                }
                Notification.info(player, "向南缩小了 " + distance + " 格");
                break;
            case EAST:
                if (area.getMarkers_x().get(0) < area.getMarkers_x().get(2)) {
                    E1(area, distance);
                } else {
                    E2(area, distance);
                }
                Notification.info(player, "向东缩小了 " + distance + " 格");
                break;
            case WEST:
                if (area.getMarkers_x().get(0) > area.getMarkers_x().get(2)) {
                    W1(area, distance);
                } else {
                    W2(area, distance);
                }
                Notification.info(player, "向西缩小了 " + distance + " 格");
                break;
            default:
                Notification.error(player, "方向错误");
                break;
        }

        return area;
    }


    /**
     * 判断一个区域是否是矩形
     *
     * @param area 区域
     * @return 是否是矩形
     */
    private static boolean squareArea(Area area) {
        if (area == null) {
            return false;
        }

        if (area.getMarkers_x().size() != 4) {
            return false;
        }

        List<Integer> markers_x = new ArrayList<>();
        List<Integer> markers_z = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            markers_x.add(area.getMarkers_x().get(i));
            markers_z.add(area.getMarkers_z().get(i));
        }

        // 判断四个点是否围成一个矩形
        return markers_x.get(0) - markers_x.get(2) == markers_x.get(3) - markers_x.get(1) &&
                markers_z.get(0) - markers_z.get(2) == markers_z.get(3) - markers_z.get(1);
    }


}
