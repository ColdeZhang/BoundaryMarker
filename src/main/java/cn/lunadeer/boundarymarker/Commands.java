package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Role;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        if (Objects.equals(strings[0], "help")) {
            if (commandSender instanceof Player) {
                help((Player) commandSender);
            } else {
                XLogger.warn("控制台请查阅文档获取帮助。");
            }
            return true;
        }
        if (Objects.equals(strings[0], "point") && strings.length >= 2) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(strings));
            point(commandSender, list.subList(1, strings.length));
            return true;
        }
        if (Objects.equals(strings[0], "points")) {
            points(commandSender);
            return true;
        }
        if (Objects.equals(strings[0], "delete") && strings.length == 2) {
            deleteArea(commandSender, strings[1]);
            return true;
        }
        if (Objects.equals(strings[0], "list")) {
            listArea(commandSender);
            return true;
        }
        if (Objects.equals(strings[0], "convert") && strings.length == 2) {
            convertAreaToSquare(commandSender, strings[1]);
            return true;
        }
        if (Objects.equals(strings[0], "create") && strings.length >= 4) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(strings));
            createArea(commandSender, list.get(1), list.subList(2, strings.length));
            return true;
        }
        if (Objects.equals(strings[0], "expand") && strings.length == 2) {
            expandSquare(commandSender, strings[1]);
            return true;
        }
        if (Objects.equals(strings[0], "contract") && strings.length == 2) {
            contractSquare(commandSender, strings[1]);
            return true;
        }
        if (Objects.equals(strings[0], "give") && strings.length == 3) {
            give(commandSender, strings[1], strings[2]);
            return true;
        }
        if (Objects.equals(strings[0], "area")) {
            if (strings.length == 4 && Objects.equals(strings[2], "points") && Objects.equals(strings[3], "list")) {
                listAreaPoints(commandSender, strings[1]);
                return true;
            }
            if (strings.length == 7 && Objects.equals(strings[2], "points") && Objects.equals(strings[3], "update")) {
                updateAreaPoint(commandSender, strings[1], strings[4], strings[5], strings[6]);
                return true;
            }
            if (strings.length == 5 && Objects.equals(strings[2], "points") && Objects.equals(strings[3], "remove")) {
                removeAreaPoint(commandSender, strings[1], strings[4]);
                return true;
            }
            if (strings.length == 5 && Objects.equals(strings[2], "set")) {
                if (Objects.equals(strings[3], "y_top")) {
                    setAreaYTop(commandSender, strings[1], strings[4]);
                    return true;
                }
                if (Objects.equals(strings[3], "y_bottom")) {
                    setAreaYBottom(commandSender, strings[1], strings[4]);
                    return true;
                }
            }
            if (strings.length >= 3 && Objects.equals(strings[2], "role")) {
                if (strings.length == 4 && Objects.equals(strings[3], "list")) {
                    areaRoleList(commandSender, strings[1]);
                    return true;
                }
                if (strings.length == 5 && Objects.equals(strings[3], "create")) {
                    areaCreateRole(commandSender, strings[1], strings[4]);
                    return true;
                }
                if (strings.length == 6 && Objects.equals(strings[3], "add")) {
                    areaRoleAddPlayer(commandSender, strings[1], strings[4], strings[5]);
                    return true;
                }
                if (strings.length == 6 && Objects.equals(strings[3], "remove")) {
                    areaRoleRemovePlayer(commandSender, strings[1], strings[4], strings[5]);
                    return true;
                }
                if (strings.length == 5 && Objects.equals(strings[3], "delete")) {
                    areaRoleDelete(commandSender, strings[1], strings[4]);
                    return true;
                }
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
            } else {
                list.add("reload");
            }
            list.add("point");
            list.add("points");
            list.add("delete");
            list.add("list");
            list.add("create");
            list.add("area");
            list.add("expand");
            list.add("contract");
            list.add("convert");
            return list;
        } else if (strings.length == 2) {
            switch (strings[0]) {
                case "point":
                    return Arrays.asList("<index> <x> <y> <z> - 创建一个点", "<index> - 以你的位置创建一个点");
                case "points":
                case "list":
                    return Collections.emptyList();
                case "delete":
                case "area":
                case "convert":
                case "give":
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        List<String> list = new ArrayList<>();
                        for (Area area : BoundaryMarkerAPI.getAreasOfPlayer(player)) {
                            list.add(area.getName());
                        }
                        return list;
                    } else {
                        return Collections.emptyList();
                    }
                case "create":
                    return Arrays.asList("<name> <p1> <p2> <p3> ... - 使用多个点创建多边形区域", "<name> <p1> <p2> - 以p1 p2为对角线创建长方体区域");
                case "expand":
                case "contract":
                    return Collections.singletonList("要扩张或缩小的数量（只支持长方体区域）");
            }
        } else if (strings.length == 3) {
            if (strings[0].equals("give")) {
                Collection<?> players = BoundaryMarker.instance.getServer().getOnlinePlayers();
                ArrayList<String> players_name = new ArrayList<>();
                for (Object player : players) {
                    players_name.add(((Player) player).getName());
                }
                return players_name;
            }
            if (!strings[0].equals("area")) {
                return Collections.emptyList();
            }
            return Arrays.asList("points", "set", "role");
        } else if (strings.length == 4) {
            if (strings[0].equals("area") && strings[2].equals("points")) {
                return Arrays.asList("update", "remove", "list");
            } else if (strings[0].equals("area") && strings[2].equals("set")) {
                return Arrays.asList("y_top", "y_bottom");
            } else if (strings[0].equals("area") && strings[2].equals("role")) {
                return Arrays.asList("create", "add", "remove", "list");
            }
            return Collections.emptyList();
        } else if (strings.length == 5) {
            if (strings[0].equals("area") && strings[2].equals("role")) {
                Area area = BoundaryMarkerAPI.getAreaByName(strings[2]);
                if (area == null) {
                    return Collections.emptyList();
                }
                if (!BoundaryMarkerAPI.isOwner(area, (Player) commandSender)) {
                    return Collections.emptyList();
                }
                ArrayList<String> roles_name = new ArrayList<>();
                for (Map.Entry<String, Role> entry : area.getRoles().entrySet()) {
                    roles_name.add(entry.getKey());
                }
                switch (strings[3]) {
                    case "add":
                    case "remove":
                    case "delete":
                        return roles_name;
                    case "create":
                        return Collections.singletonList("角色组名（只支持英文）");
                    default:
                        return Collections.emptyList();
                }
            } else if (strings[0].equals("area") && strings[2].equals("set")) {
                if (strings[3].equals("y_top") || strings[3].equals("y_bottom")) {
                    return Collections.singletonList("y坐标(整数)");
                }
            } else if (strings[0].equals("area") && strings[2].equals("points")) {
                if (strings[3].equals("update")) {
                    return Collections.singletonList("<顶点编号> <x> <z>");
                }
                if (strings[3].equals("remove")) {
                    return Collections.singletonList("<要移除的顶点编号>");
                }
            }
        } else if (strings.length == 6) {
            if (strings[0].equals("area") && strings[2].equals("role")) {
                if (strings[3].equals("add")) {
                    Collection<?> players = BoundaryMarker.instance.getServer().getOnlinePlayers();
                    ArrayList<String> players_name = new ArrayList<>();
                    for (Object player : players) {
                        players_name.add(((Player) player).getName());
                    }
                    return players_name;
                } else if (strings[3].equals("remove")) {
                    Area area = BoundaryMarkerAPI.getAreaByName(strings[2]);
                    if (area == null) {
                        return Collections.emptyList();
                    }
                    if (!BoundaryMarkerAPI.isOwner(area, (Player) commandSender)) {
                        return Collections.emptyList();
                    }
                    Map<String, String> player_role = area.getPlayers_role();
                    ArrayList<String> players_name = new ArrayList<>();
                    for (Map.Entry<String, String> entry : player_role.entrySet()) {
                        if (Objects.equals(entry.getValue(), strings[4])) {
                            Player player = Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getPlayer();
                            if (player == null) {
                                continue;
                            }
                            players_name.add(player.getName());
                        }
                    }
                    return players_name;
                }
            }
        }
        return Collections.emptyList();
    }

    private static void printHelpInfo(Player player) {
        Notification.warn(player, "-------BoundaryMarker系统帮助-------");
        Notification.info(player, " /bm help - 显示此帮助");
        Notification.info(player, " /bm cui - 打开CUI界面");
        Notification.warn(player, "-----点操作-----");
        Notification.info(player, " /bm point <index> <x> <y> <z> - 创建一个点");
        Notification.info(player, " /bm point <index> - 以你的位置创建一个点");
        Notification.info(player, " /bm points - 列出你的所有创建的点");
        Notification.warn(player, "-----区域操作-----");
        Notification.info(player, " /bm delete <area> - 删除区域");
        Notification.info(player, " /bm list - 列出所有区域");
        Notification.info(player, " /bm convert <area> - 将多边形区域转换为长方体区域");
        Notification.info(player, " /bm give <area> <player> - 将区域转让给他人");
        Notification.warn(player, "-----多边形区域特殊功能-----");
        Notification.info(player, " /bm create <area> <p1> <p2> <p3> ... - 使用多个点创建多边形区域");
        Notification.info(player, " /bm area <area> points list - 列出多边形区域顶点");
        Notification.info(player, " /bm area <area> points update <index> <x> <z> - 更新多边形区域顶点 (不存在的顶点会被创建)");
        Notification.info(player, " /bm area <area> points remove <index> - 删除多边形区域顶点");
        Notification.warn(player, "-----长方体区域特殊功能-----");
        Notification.info(player, " /bm create <area> <p1> <p2> - 以p1 p2为对角线创建长方体区域");
        Notification.info(player, " /bm expand <distance> - 将所处区域向面朝方向扩展distance格 (只支持长方体区域)");
        Notification.info(player, " /bm contract <distance> - 将所处区域向面朝方向收缩distance格 (只支持长方体区域)");
        Notification.warn(player, "-----区域设置-----");
        Notification.info(player, " /bm area <area> set y_top <y> - 设置区域上界y坐标");
        Notification.info(player, " /bm area <area> set y_bottom <y> - 设置区域下界y坐标");
        Notification.warn(player, "-----角色组配置-----");
        Notification.info(player, " /bm area <area> role list - 列出区域角色组");
        Notification.info(player, " /bm area <area> role create <role> - 创建一个角色组");
        Notification.info(player, " /bm area <area> role add <role> <player> - 将玩家添加到角色组");
        Notification.info(player, " /bm area <area> role remove <role> <player> - 将玩家从角色组移除");
        Notification.info(player, " /bm area <area> role delete <role> - 删除角色组");
    }

    private static void help(Player player) {
        printHelpInfo(player);
    }

    private static boolean notPlayer(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            XLogger.warn("该指令只能由玩家执行");
            return true;
        }
        return false;
    }

    private static Map<Integer, Location> getPointsCache(Player player) {
        return BoundaryMarker.instance.points.computeIfAbsent(player.getUniqueId().toString(), k -> new HashMap<>());
    }

    // /bm point <index> <x> <y> <z>
    // /bm point <index>
    private static void point(CommandSender commandSender, List<String> params) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        if (params.size() != 1 && params.size() != 4) {
            Notification.error(player, "参数错误");
            Notification.info(player, "示例 /bm point <编号> <x> <y> <z> - 创建一个点");
            Notification.info(player, "示例 /bm point <编号> - 以你的位置创建一个点");
            return;
        }
        List<Integer> params_int = new ArrayList<>();
        for (String param : params) {
            try {
                params_int.add(Integer.parseInt(param));
            } catch (NumberFormatException e) {
                Notification.error(player, "参数错误 (参数必须为整数)");
                return;
            }
        }
        Integer index = params_int.get(0);
        Location location = null;
        if (params_int.size() == 1) {
            location = player.getLocation();
        }
        if (params_int.size() == 4) {
            location = new Location(player.getWorld(), params_int.get(1), params_int.get(2), params_int.get(3));
        }
        if (location == null) {
            Notification.error(player, "参数错误");
            Notification.info(player, "示例 /bm point <编号> <x> <y> <z> - 创建一个点");
            Notification.info(player, "示例 /bm point <编号> - 以你的位置创建一个点");
            return;
        }
        getPointsCache(player).put(index, location);
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        Notification.info(player, "创建了一个编号为" + index + "的点 坐标为" + x + " " + y + " " + z);
    }

    // /bm points
    private static void points(CommandSender commandSender) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Map<Integer, Location> player_cache = getPointsCache(player);
        if (player_cache.isEmpty()) {
            Notification.info(player, "你还没有创建过点");
            return;
        }
        Notification.info(player, "你创建的点:");
        for (Map.Entry<Integer, Location> entry : player_cache.entrySet()) {
            int index = entry.getKey();
            int x = entry.getValue().getBlockX();
            int y = entry.getValue().getBlockY();
            int z = entry.getValue().getBlockZ();
            String world = entry.getValue().getWorld().getName();
            Notification.info(player, " - 编号: " + index + " 坐标: " + x + " " + y + " " + z + " 世界: " + world);
        }
    }

    // /bm delete <name>
    private static void deleteArea(CommandSender commandSender, String area_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        if (area_name == null) {
            Notification.error(player, "参数错误 你需要指定一个区域名");
            Notification.info(player, "示例 /bm delete <区域名> - 删除一个区域");
            return;
        }
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "区域不存在");
            return;
        }
        if (BoundaryMarkerAPI.isOwner(area, player)) {
            BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).remove(area_name);
            Notification.info(player, "区域 " + area_name + " 已删除");
        } else {
            Notification.error(player, "你不是该区域的所有者");
        }
    }

    // /bm list
    private static void listArea(CommandSender commandSender) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        List<Area> areas = BoundaryMarkerAPI.getAreasOfPlayer(player);
        if (areas.isEmpty()) {
            Notification.info(player, "你还没有创建过区域");
            return;
        }
        Notification.info(player, "当前的区域:");
        for (Area area : areas) {
            Notification.info(player, " - 名称: " + area.getName() + " 世界: " + area.getWorld_name());
        }
    }

    // /bm convert <name>
    private static void convertAreaToSquare(CommandSender commandSender, String area_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "区域不存在");
            return;
        }
        if (BoundaryMarkerAPI.isOwner(area, player)) {
            List<Integer> x_list = new ArrayList<>();
            List<Integer> z_list = new ArrayList<>();
            for (SortedMap.Entry<Integer, Integer> entry : area.getMarkers_x().entrySet()) {
                x_list.add(area.getMarkers_x().get(entry.getKey()));
                z_list.add(area.getMarkers_z().get(entry.getKey()));
            }
            int x_min = Collections.min(x_list);
            int x_max = Collections.max(x_list);
            int z_min = Collections.min(z_list);
            int z_max = Collections.max(z_list);
            SortedMap<Integer, Integer> markers_x = new TreeMap<>();
            SortedMap<Integer, Integer> markers_z = new TreeMap<>();
            markers_x.put(1, x_min);
            markers_x.put(2, x_max);
            markers_x.put(3, x_max);
            markers_x.put(4, x_min);
            markers_z.put(1, z_min);
            markers_z.put(2, z_min);
            markers_z.put(3, z_max);
            markers_z.put(4, z_max);
            area.setMarkers_x(markers_x);
            area.setMarkers_z(markers_z);
            Area conflict = BoundaryMarkerAPI.conflictCheck(area);
            if (conflict != null) {
                Notification.error(player, "警告 与 " + conflict.getName() + " 冲突");
                area.setIllegal(true);
                Notification.error(player, "区域已被标记为非法区域，权限控制不会生效，请编辑区域范围，合法后会自动取消标记");
            } else {
                area.setIllegal(false);
                Notification.info(player, "区域已被标记为合法区域");
            }
            Notification.info(player, "区域 " + area_name + " 已转换为标准矩形区域");
            BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
        } else {
            Notification.error(player, "你不是该区域的所有者");
        }
    }

    // /bm create <name> <p1> <p2> ....
    private static void createArea(CommandSender commandSender, String area_name, List<String> points) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        if (area_name == null) {
            Notification.error(player, "参数错误 你需要指定一个区域名");
            Notification.info(player, "示例 /bm create <区域名> <点1> <点2> .... - 创建一个区域");
            return;
        }
        if (!BoundaryMarkerAPI.checkString(area_name)) {
            Notification.error(player, "区域名仅支持大小写英文、数字、下划线！");
            return;
        }
        if (points.size() < 2) {
            Notification.error(player, "参数错误 你需要指定至少两个点");
            Notification.info(player, "示例 /bm create <区域名> <点1> <点2> <点3> .... - 创建一个多边形区域");
            Notification.info(player, "示例 /bm create <区域名> <点1> <点2> - 以亮点为对角线创建一个长方体区域");
            return;
        }
        Area conflict_area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (conflict_area != null && !conflict_area.getIllegal()) {
            Notification.error(player, "区域已存在");
            return;
        }
        Area new_area = BoundaryMarkerAPI.createNewAreaWithDefaultConfiguration(area_name, player, player.getWorld().getName());
        List<Location> locations = new ArrayList<>();
        for (String point : points) {
            int index;
            try {
                index = Integer.parseInt(point);
            } catch (NumberFormatException e) {
                Notification.error(player, "参数错误 点编号必须是一个整数");
                return;
            }
            Location location = BoundaryMarker.instance.points.get(player.getUniqueId().toString()).get(index);
            if (location == null) {
                Notification.error(player, "没有找到编号为 " + index + " 的点，使用 /bm points 查看你创建的点");
                return;
            }
            if (!location.getWorld().getName().equals(player.getWorld().getName())) {
                Notification.error(player, "不可以使用不同世界的点");
                return;
            }
            locations.add(location);
        }
        if (locations.size() == 2) {
            new_area.setY_top(Math.max(locations.get(0).getBlockY(), locations.get(1).getBlockY()));
            new_area.setY_bottom(Math.min(locations.get(0).getBlockY(), locations.get(1).getBlockY()));
            Location p1 = new Location(player.getWorld(), locations.get(0).getBlockX(), 0, locations.get(0).getBlockZ());
            Location p2 = new Location(player.getWorld(), locations.get(0).getBlockX(), 0, locations.get(1).getBlockZ());
            Location p3 = new Location(player.getWorld(), locations.get(1).getBlockX(), 0, locations.get(1).getBlockZ());
            Location p4 = new Location(player.getWorld(), locations.get(1).getBlockX(), 0, locations.get(0).getBlockZ());
            locations.clear();
            locations.add(p1);
            locations.add(p2);
            locations.add(p3);
            locations.add(p4);
        }
        for (int i = 0; i < locations.size(); i++) {
            new_area.getMarkers_x().put((i + 1) * 10, locations.get(i).getBlockX());
            new_area.getMarkers_z().put((i + 1) * 10, locations.get(i).getBlockZ());
        }
        Notification.info(player, "区域 " + area_name + " 已创建");
        conflict_area = BoundaryMarkerAPI.conflictCheck(new_area);
        if (conflict_area != null) {
            Notification.error(player, "与 " + conflict_area.getName() + " 冲突");
            new_area.setIllegal(true);
            Notification.error(player, "区域已被标记为非法区域，权限控制不会生效，请编辑区域范围，合法后会自动取消标记");
        } else {
            new_area.setIllegal(false);
            Notification.info(player, "区域已被标记为合法区域");
        }
        BoundaryMarker.instance.cache.getAreas().get(new_area.getWorld_name()).put(new_area.getName(), new_area);
    }

    // /bm expand <distance> - 将所处区域向面朝方向扩展distance格 (只支持长方体区域)
    public static void expandSquare(CommandSender commandSender, String distance) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        int dist;
        try {
            dist = Integer.parseInt(distance);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 距离必须是一个整数");
            Notification.info(player, "示例 /bm expand <距离> - 将所处区域向面朝方向扩展distance格 (只支持长方体区域)");
            return;
        }
        Area are = BoundaryMarkerAPI.getAreaByLocation(player.getLocation());
        if (are == null) {
            Notification.error(player, "你需要站在一个长方体区域内才可以使用该命令");
            return;
        }
        Area expanded_area = ResidenceStyleAPI.expand(are, player, dist);
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(expanded_area);
        if (conflict_area != null) {
            Notification.error(player, "延伸失败 与 " + conflict_area.getName() + " 冲突");
            return;
        }
        Notification.info(player, "区域 " + are.getName() + " 已延伸 " + dist + " 格");
        BoundaryMarker.instance.cache.getAreas().get(are.getWorld_name()).put(are.getName(), expanded_area);
    }

    // /bm contract <distance> - 将所处区域向面朝方向收缩distance格 (只支持长方体区域)
    public static void contractSquare(CommandSender commandSender, String distance) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        int dist;
        try {
            dist = Integer.parseInt(distance);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 距离必须是一个整数");
            Notification.info(player, "示例 /bm contract <距离> - 将所处区域向面朝方向收缩distance格 (只支持长方体区域)");
            return;
        }
        Area are = BoundaryMarkerAPI.getAreaByLocation(player.getLocation());
        if (are == null) {
            Notification.error(player, "你需要站在一个长方体区域内才可以使用该命令");
            return;
        }
        Area contracted_area = ResidenceStyleAPI.contract(are, player, dist);
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(contracted_area);
        if (conflict_area != null) {
            Notification.error(player, "收缩失败 与 " + conflict_area.getName() + " 冲突");
            return;
        }
        Notification.info(player, "区域 " + are.getName() + " 已收缩 " + dist + " 格");
        BoundaryMarker.instance.cache.getAreas().get(are.getWorld_name()).put(are.getName(), contracted_area);
    }

    // /bm area <area> points list - 列出多边形区域顶点
    public static void listAreaPoints(CommandSender commandSender, String area_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        Notification.info(player, "区域 " + area_name + " 的顶点列表");
        for (Map.Entry<Integer, Integer> entry : area.getMarkers_x().entrySet()) {
            Notification.info(player, " - 顶点 " + entry.getKey() + " x: " + entry.getValue() + " z: " + area.getMarkers_z().get(entry.getKey()));
        }
    }

    // /bm area <area> points update <index> <x> <z> - 更新多边形区域顶点 (不存在的顶点会被创建)
    public static void updateAreaPoint(CommandSender commandSender, String area_name, String index, String x, String z) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        int i;
        int x1;
        int z1;
        try {
            i = Integer.parseInt(index);
            x1 = Integer.parseInt(x);
            z1 = Integer.parseInt(z);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 顶点序号和坐标必须是整数");
            Notification.info(player, "示例 /bm area <area> points update <index> <x> <z> - 更新多边形区域顶点 (不存在的顶点会被创建)");
            return;
        }
        area.getMarkers_x().put(i, x1);
        area.getMarkers_z().put(i, z1);
        Notification.info(player, "区域 " + area_name + " 的顶点 " + i + " 已更新");
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(area);
        if (conflict_area != null) {
            Notification.error(player, "与 " + conflict_area.getName() + " 冲突，该区域已被标记为非法区域，权限控制不会生效，请继续修改点位，不冲突后会自动转换为合法区域");
            area.setIllegal(true);
        } else {
            area.setIllegal(false);
        }
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> points remove <index> - 删除多边形区域顶点
    public static void removeAreaPoint(CommandSender commandSender, String area_name, String index) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        int i;
        try {
            i = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 顶点序号必须是整数");
            Notification.info(player, "示例 /bm area <area> points remove <index> - 删除多边形区域顶点");
            return;
        }
        area.getMarkers_x().remove(i);
        area.getMarkers_z().remove(i);
        Notification.info(player, "区域 " + area_name + " 的顶点 " + i + " 已删除");
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(area);
        if (conflict_area != null) {
            Notification.error(player, "与 " + conflict_area.getName() + " 冲突，该区域已被标记为非法区域，权限控制不会生效，请继续修改点位，不冲突后会自动转换为合法区域");
            area.setIllegal(true);
        } else {
            area.setIllegal(false);
        }
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> set y_top <y> - 设置区域上界y坐标
    public static void setAreaYTop(CommandSender commandSender, String area_name, String y) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        int i;
        try {
            i = Integer.parseInt(y);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 Y值必须是整数");
            Notification.info(player, "示例 /bm area <area> set y_top <y> - 设置区域上界y坐标");
            return;
        }
        area.setY_top(i);
        Notification.info(player, "区域 " + area_name + " 的上界y坐标已设置为 " + i);
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(area);
        if (conflict_area != null) {
            Notification.error(player, "与 " + conflict_area.getName() + " 冲突，该区域已被标记为非法区域，权限控制不会生效，请继续修改点位，不冲突后会自动转换为合法区域");
            area.setIllegal(true);
        } else {
            area.setIllegal(false);
        }
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> set y_bottom <y> - 设置区域下界y坐标
    public static void setAreaYBottom(CommandSender commandSender, String area_name, String y) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        int i;
        try {
            i = Integer.parseInt(y);
        } catch (NumberFormatException e) {
            Notification.error(player, "参数错误 Y值必须是整数");
            Notification.info(player, "示例 /bm area <area> set y_bottom <y> - 设置区域下界y坐标");
            return;
        }
        area.setY_bottom(i);
        Notification.info(player, "区域 " + area_name + " 的下界y坐标已设置为 " + i);
        Area conflict_area = BoundaryMarkerAPI.conflictCheck(area);
        if (conflict_area != null) {
            Notification.error(player, "与 " + conflict_area.getName() + " 冲突，该区域已被标记为非法区域，权限控制不会生效，请继续修改点位，不冲突后会自动转换为合法区域");
            area.setIllegal(true);
        } else {
            area.setIllegal(false);
        }
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> role list - 列出区域角色组
    public static void areaRoleList(CommandSender commandSender, String area_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        StringBuilder role_list = new StringBuilder();
        for (String role : area.getRoles().keySet()) {
            role_list.append(role).append(", ");
        }
        if (role_list.length() > 0) {
            role_list = new StringBuilder(role_list.substring(0, role_list.length() - 2));
        }
        Notification.info(player, "区域 " + area_name + " 的角色组有 " + role_list);
    }

    // /bm area <area> role create <role> - 创建一个角色组
    public static void areaCreateRole(CommandSender commandSender, String area_name, String role_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        if (area.getRoles().containsKey(role_name)) {
            Notification.error(player, "该区域已存在名为 " + role_name + " 的角色组");
            return;
        }
        if (!BoundaryMarkerAPI.checkString(role_name)) {
            Notification.error(player, "权限组名仅支持大小写英文、数字、下划线！");
            return;
        }
        area.getRoles().put(role_name, new Role());
        Notification.info(player, "区域 " + area_name + " 已创建名为 " + role_name + " 的角色组");
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> role add <role> <player> - 将玩家添加到角色组
    public static void areaRoleAddPlayer(CommandSender commandSender, String area_name, String role_name, String player_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        if (!area.getRoles().containsKey(role_name)) {
            Notification.error(player, "该区域不存在名为 " + role_name + " 的角色组");
            return;
        }
        Player player_to_be_add = Bukkit.getOfflinePlayer(player_name).getPlayer();
        if (player_to_be_add == null) {
            Notification.error(player, "没有找到名为 " + player_name + " 的玩家");
            return;
        }
        area.getPlayers_role().put(player_to_be_add.getUniqueId().toString(), role_name);
        Notification.info(player, "区域 " + area_name + " 已将玩家 " + player_name + " 添加到角色组 " + role_name);
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> role remove <role> <player> - 将玩家从角色组移除
    public static void areaRoleRemovePlayer(CommandSender commandSender, String area_name, String role_name, String player_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        if (!area.getRoles().containsKey(role_name)) {
            Notification.error(player, "该区域不存在名为 " + role_name + " 的角色组");
            return;
        }
        Player player_to_be_remove = Bukkit.getOfflinePlayer(player_name).getPlayer();
        if (player_to_be_remove == null) {
            Notification.error(player, "没有找到名为 " + player_name + " 的玩家");
            return;
        }
        area.getPlayers_role().remove(player_to_be_remove.getUniqueId().toString());
        Notification.info(player, "区域 " + area_name + " 已将玩家 " + player_name + " 从角色组 " + role_name + " 移除");
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }

    // /bm area <area> role delete <role> - 删除角色组
    public static void areaRoleDelete(CommandSender commandSender, String area_name, String role_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        if (!area.getRoles().containsKey(role_name)) {
            Notification.error(player, "该区域不存在名为 " + role_name + " 的角色组");
            return;
        }
        area.getRoles().remove(role_name);
        Notification.info(player, "区域 " + area_name + " 已删除名为 " + role_name + " 的角色组");
        for (Map.Entry<String, String> e : area.getPlayers_role().entrySet()) {
            if (Objects.equals(e.getValue(), role_name)) {
                area.getPlayers_role().remove(e.getKey());
                Player p = Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getPlayer();
                if (p == null) {
                    continue;
                }
                Notification.info(player, " - 区域 " + area_name + " 已将玩家 " + p.getName() + " 从角色组 " + role_name + " 移除");
            }
        }
    }

    // /bm give <area> <player> - 将区域赠送给玩家
    public static void give(CommandSender commandSender, String area_name, String player_name) {
        if (notPlayer(commandSender)) {
            return;
        }
        Player player = (Player) commandSender;
        Area area = BoundaryMarkerAPI.getAreaByName(area_name);
        if (area == null) {
            Notification.error(player, "没有找到名为 " + area_name + " 的区域");
            return;
        }
        if (!BoundaryMarkerAPI.isOwner(area, player)) {
            Notification.error(player, "你不是该区域的主人");
            return;
        }
        Player player_to_be_give = Bukkit.getOfflinePlayer(player_name).getPlayer();
        if (player_to_be_give == null) {
            Notification.error(player, "没有找到名为 " + player_name + " 的玩家");
            return;
        }
        area.setOwner_name(player_to_be_give.getName());
        area.setOwner_uuid(player_to_be_give.getUniqueId().toString());
        Notification.info(player, "区域 " + area_name + " 已赠送给玩家 " + player_name);
        BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).put(area.getName(), area);
    }
}
