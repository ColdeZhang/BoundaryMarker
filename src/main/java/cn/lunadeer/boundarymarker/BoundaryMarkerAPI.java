package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Cache;
import cn.lunadeer.boundarymarker.dto.Role;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BoundaryMarkerAPI {
    public static BlockFace[] newsfaces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    public static BlockFace[] allfaces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};

//    /**
//     * 判断是否是界碑牌
//     *
//     * @param sign 牌子正面
//     * @return 是否是界碑牌
//     */
//    public static boolean isMarkerSign(SignSide sign) {
//        Configuration conf = BoundaryMarker.instance.config;
//        Integer name_line = conf.name_line();
//        Integer id_line = conf.marker_id_line();
//        String name_prefix = conf.name_prefix();
//        String name_suffix = conf.name_suffix();
//        String id_prefix = conf.marker_id_prefix();
//        String id_suffix = conf.marker_id_suffix();
//        String[] lines = sign.getLines();
//        Pattern name_pattern = Pattern.compile("^" + Pattern.quote(name_prefix) + ".*" + Pattern.quote(name_suffix) + "$");
//        Pattern id_pattern = Pattern.compile("^" + Pattern.quote(id_prefix) + ".*" + Pattern.quote(id_suffix) + "$");
//        return name_pattern.matcher(lines[name_line]).matches() && id_pattern.matcher(lines[id_line]).matches();
//    }
//
//    public static boolean isMarkerSign(Sign sign) {
//        return isMarkerSign(sign.getSide(Side.FRONT));
//    }
//
//    /**
//     * 获取界碑牌设置的边界名
//     * 不会检查是否是界碑牌 应当先使用isMarkerSign判断
//     *
//     * @param sign 界碑牌
//     * @return 边界名 如果匹配失败返回null
//     */
//    public static String getMarkerAreaName(SignSide sign) {
//        Configuration conf = BoundaryMarker.instance.config;
//        Integer name_line = conf.name_line();
//        String name_prefix = conf.name_prefix();
//        String name_suffix = conf.name_suffix();
//        String[] lines = sign.getLines();
//        Pattern name_pattern = Pattern.compile("^" + Pattern.quote(name_prefix) + ".*" + Pattern.quote(name_suffix) + "$");
//        if (name_pattern.matcher(lines[name_line]).matches()) {
//            return lines[name_line].substring(name_prefix.length(), lines[name_line].length() - name_suffix.length());
//        }
//        return null;
//    }
//
//    public static String getMarkerAreaName(Sign sign) {
//        return getMarkerAreaName(sign.getSide(Side.FRONT));
//    }
//
//    /**
//     * 获取界碑牌设置的边界ID
//     * 不会检查是否是界碑牌 应当先使用isMarkerSign判断
//     *
//     * @param sign 界碑牌
//     * @return 边界ID 如果匹配失败返回null
//     */
//    public static Integer getMarkerIndex(SignSide sign) {
//        Configuration conf = BoundaryMarker.instance.config;
//        Integer id_line = conf.marker_id_line();
//        String id_prefix = conf.marker_id_prefix();
//        String id_suffix = conf.marker_id_suffix();
//        String[] lines = sign.getLines();
//        Pattern id_pattern = Pattern.compile("^" + Pattern.quote(id_prefix) + ".*" + Pattern.quote(id_suffix) + "$");
//        if (id_pattern.matcher(lines[id_line]).matches()) {
//            return Integer.parseInt(lines[id_line].substring(id_prefix.length(), lines[id_line].length() - id_suffix.length()));
//        }
//        return null;
//    }
//
//    public static Integer getMarkerIndex(Sign sign) {
//        return getMarkerIndex(sign.getSide(Side.FRONT));
//    }
//
//    /**
//     * 判断是否是木牌
//     *
//     * @param block 方块
//     * @return 是否是木牌
//     */
//    public static boolean isSign(Block block) {
//        return Tag.WALL_SIGNS.isTagged(block.getType());
//    }
//
//    /**
//     * 判断是否是界碑牌的所有者
//     * 不会检查是否是界碑牌 应该先用isMarkerSign检查
//     *
//     * @param sign   界碑牌
//     * @param player 玩家
//     * @return 是否是所有者
//     */
//    public static boolean isOwner(SignSide sign, Player player) {
//        Configuration conf = BoundaryMarker.instance.config;
//        Cache cache = BoundaryMarker.instance.cache;
//        Integer name_line = conf.name_line();
//        String name_prefix = conf.name_prefix();
//        String name_suffix = conf.name_suffix();
//        String name_line_str = sign.getLine(name_line);
//        String name = name_line_str.substring(name_prefix.length(), name_line_str.length() - name_suffix.length());
//        Map<String, Area> areas = cache.getAreas().get(player.getWorld().getName());
//        if (areas.containsKey(name)) {
//            return areas.get(name).getOwner_uuid().equals(player.getUniqueId().toString());
//        }
//        return true;
//    }

    /**
     * 判断是否是区域的所有者
     * 会顺便更新一下区域的名字和最后维护时间
     *
     * @param area   区域
     * @param player 玩家
     * @return 是否是所有者
     */
    public static boolean isOwner(Area area, Player player) {
        if (player.isOp()) {
            return true;
        }
        if (area.getOwner_uuid().equals(player.getUniqueId().toString())) {
            // 判断的时候顺便更新一下名字和最后维护时间
            area.setOwner_name(player.getName());
            area.setLast_maintain_time(BigInteger.valueOf(System.currentTimeMillis()));
            return true;
        }
        return false;
    }

//    /**
//     * 判断一个方块上是否有界碑牌
//     *
//     * @param block 方块
//     * @return 是否有界碑牌
//     */
//    public static boolean isMarkSignAttachedOn(Block block) {
//        // 查找附近的界碑牌
//        for (BlockFace blockface : allfaces) {
//            Block relativeblock = block.getRelative(blockface);
//            // 如果是界碑牌
//            if (isSign(relativeblock) && isMarkerSign(((Sign) relativeblock.getState()).getSide(Side.FRONT))) {
//                BlockFace sign_face = getFacing(relativeblock);
//                if (sign_face == blockface) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 创建一个新的区域并配置默认权限组等设置
     * 默认权限组包含一个黑名单和一个白名单
     * 黑名单 无权限
     * 白名单 所有权限
     *
     * @param area_name 区域名
     * @param player    玩家
     * @return 新的区域 pair<区域名, 区域>
     */
    public static Area createNewAreaWithDefaultConfiguration(String area_name, Player player, String world_name) {
        Area area = new Area();
        area.setWorld_name(world_name);
        area.setOwner_uuid(player.getUniqueId().toString());
        area.setOwner_name(player.getName());
        area.setName(area_name);
        area.setWelcome_message("欢迎来到 " + area_name + "!");
        area.setLast_maintain_time(BigInteger.valueOf(System.currentTimeMillis()));
        // 配置默认权限
        Role black_list = new Role();
        Role white_list = new Role();
        white_list.setAnimal_killing(true);
        white_list.setUse(true);
        white_list.setBed(true);
        white_list.setContainer(true);
        white_list.setDestroy(true);
        white_list.setRed_stone(true);
        white_list.setDoor(true);
        white_list.setDye(true);
        white_list.setThrowing(true);
        white_list.setPlant(true);
        white_list.setHook(true);
        white_list.setLeash(true);
        white_list.setMob_killing(true);
        white_list.setMove(true);
        white_list.setName_tag(true);
        white_list.setPlace(true);
        white_list.setRiding(true);
        white_list.setShear(true);
        white_list.setShoot(true);
        white_list.setTrade(true);
        white_list.setVehicle_destroy(true);
        white_list.setHarvest(true);
        white_list.setHopper(true);
        Role default_role = new Role();
        default_role.setMove(true);
        Map<String, Role> roles = new HashMap<>();
        roles.put("ban", black_list);
        roles.put("trust", white_list);
        roles.put("default", default_role); // 不可修改 不可添加用户
        area.setRoles(roles);

        return area;
    }

    /**
     * 判断xz是否在多边形内 用射线法
     * 对于在顶点或者边上的情况 会返回true
     *
     * @param location 位置
     * @param area     区域
     * @return 是否在多边形内
     */
    public static boolean isInside(Location location, Area area) {
        int location_y = location.getBlockY();
        int location_x = location.getBlockX();
        int location_z = location.getBlockZ();
        if (location_y < area.getY_bottom() || location_y > area.getY_top()) {
            // XLogger.debug("不在区域 " + area.getName() + " 的高度范围内");
            return false;
        }

        int i, j;
        boolean c = false;

        List<Integer> x = new ArrayList<>(area.getMarkers_x().values());
        List<Integer> z = new ArrayList<>(area.getMarkers_z().values());
        if (x.contains(location_x) && z.contains(location_z)) {
            return true;
        }

        int n = x.size();
        if (n < 3) {
            return false;
        }
        for (i = 0, j = n - 1; i < n; j = i++) {
            if (((z.get(i) > location_z) != (z.get(j) > location_z)) &&
                    (location_x < (x.get(j) - x.get(i)) * (location_z - z.get(i)) / (z.get(j) - z.get(i)) + x.get(i))) {
                c = !c;
            }
        }
        return c;
    }

    /**
     * 获取一个坐标所在的Area
     *
     * @param location 坐标
     * @return Area 如果没有返回null
     */
    public static Area getAreaByLocation(Location location) {
        Cache cache = BoundaryMarker.instance.cache;
        Map<String, Area> areas = cache.getAreas().get(location.getWorld().getName());
        if (areas == null) {
            return null;
        }
        for (Area area : areas.values()) {
            if (isInside(location, area)) {
                return area;
            }
        }
        return null;
    }

    /**
     * 获取一个玩家拥有的所有区域
     *
     * @param player 玩家
     * @return 区域列表
     */
    public static List<Area> getAreasOfPlayer(Player player) {
        Cache cache = BoundaryMarker.instance.cache;
        List<Area> list = new ArrayList<>();
        for (Map.Entry<String, Map<String, Area>> entry : cache.getAreas().entrySet()) {
            Map<String, Area> areas = entry.getValue();
            if (areas == null) {
                continue;
            }
            for (Area area : areas.values()) {
                if (Objects.equals(area.getOwner_uuid(), player.getUniqueId().toString())) {
                    list.add(area);
                }
            }
        }
        return list;
    }

    public static Area getAreaByName(World world, String name) {
        Cache cache = BoundaryMarker.instance.cache;
        Map<String, Area> areas = cache.getAreas().get(world.getName());
        if (areas == null) {
            return null;
        }
        if (areas.containsKey(name)) {
            return areas.get(name);
        }
        return null;
    }

    public static Area getAreaByName(String name) {
        Cache cache = BoundaryMarker.instance.cache;
        for (Map.Entry<String, Map<String, Area>> entry : cache.getAreas().entrySet()) {
            Map<String, Area> areas = entry.getValue();
            if (areas == null) {
                continue;
            }
            if (areas.containsKey(name)) {
                return areas.get(name);
            }
        }
        return null;
    }

    /**
     * 获取一个方块的朝向（此程序里主要是木牌的朝向）
     *
     * @param block 方块
     * @return 方块朝向
     */
    public static BlockFace getFacing(Block block) {
        BlockData data = block.getBlockData();
        BlockFace f = null;
        if (data instanceof Directional && data instanceof Waterlogged && ((Waterlogged) data).isWaterlogged()) {
            String str = ((Directional) data).toString();
            if (str.contains("facing=west")) {
                f = BlockFace.WEST;
            } else if (str.contains("facing=east")) {
                f = BlockFace.EAST;
            } else if (str.contains("facing=south")) {
                f = BlockFace.SOUTH;
            } else if (str.contains("facing=north")) {
                f = BlockFace.NORTH;
            }
        } else if (data instanceof Directional) {
            f = ((Directional) data).getFacing();
        }
        return f;
    }

    /**
     * 获取木牌附着的方块
     */
    public static Block getBlockSignAttachedOn(Sign sign) {
        Block block = sign.getBlock();
        BlockFace f = getFacing(block);
        if (f == null) {
            return null;
        }
        switch (f) {
            case NORTH:
                f = BlockFace.SOUTH;
                break;
            case SOUTH:
                f = BlockFace.NORTH;
                break;
            case EAST:
                f = BlockFace.WEST;
                break;
            case WEST:
                f = BlockFace.EAST;
                break;
        }
        return block.getRelative(f);
    }

    public interface PermissionCallback {
        boolean check(Role role);
    }

    /**
     * 判断玩家是否有权限
     *
     * @param player   玩家
     * @param location 发生位置
     * @param cb       回调
     * @return 是否有权限
     */
    public static boolean hasPermission(Player player, Location location, PermissionCallback cb) {
        if (player.isOp()) {
            return true;
        }
        World world = location.getWorld();
        Map<String, Area> areas = BoundaryMarker.instance.cache.getAreas().get(world.getName());
        if (areas == null || areas.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, Area> entry : areas.entrySet()) {
            Area area = entry.getValue();
            if (BoundaryMarkerAPI.isInside(location, area)) {
                // 如果是非法区域，直接返回true
                if (area.getIllegal()) {
                    return true;
                }
                if (BoundaryMarkerAPI.isOwner(area, player)) {
                    XLogger.debug("玩家 " + player.getName() + " 是区域 " + area.getName() + " 的所有者");
                    return true;
                }
                String role_name = area.getPlayers_role().get(player.getUniqueId().toString());
                if (role_name == null) {
                    XLogger.debug("玩家 " + player.getName() + " 不属于区域 " + area.getName() + " 的任何角色");
                    role_name = "default";
                }
                XLogger.debug("玩家将 " + player.getName() + " 使用角色 " + role_name);
                Role role = area.getRoles().get(role_name);
                if (role == null) {
                    area.getPlayers_role().remove(player.getUniqueId().toString());
                    return false;
                }
                if (!cb.check(role)) {
                    XLogger.debug("玩家 " + player.getName() + " 没有权限");
                    return false;
                }
            }
        }
        return true;
    }

    private static Boolean IS_FOLIA = null;

    /**
     * 尝试获取folia的调度器
     *
     * @return 是否成功
     */
    private static boolean tryFolia() {
        try {
            Bukkit.getAsyncScheduler();
            return true;
        } catch (Throwable ignored) {
        }
        return false;
    }

    /**
     * 判断是否是folia核心
     *
     * @return 是否是folia核心
     */
    public static Boolean isFolia() {
        if (IS_FOLIA == null) IS_FOLIA = tryFolia();
        return IS_FOLIA;
    }

    /**
     * 定时异步任务
     *
     * @param plugin   插件
     * @param runnable 任务
     * @param ticks    间隔
     */
    public static void runAtFixedRateAsync(Plugin plugin, Runnable runnable, int ticks) {
        if (isFolia())
            Bukkit.getAsyncScheduler().runAtFixedRate(plugin, (task) -> runnable.run(), ticks / 20, ticks / 20, TimeUnit.SECONDS);
        else Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, ticks, ticks);
    }

    /**
     * 获取玩家的头颅
     *
     * @param image_url 皮肤图片地址
     * @return 头颅
     */
    public static ItemStack createSkullByUrl(String image_url) {
        String textures_value = Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + image_url + "\"}}}").getBytes());
        return createSkullByValue(textures_value);
    }

    /**
     * 获取玩家的头颅
     *
     * @param value 材质value
     * @return 头颅
     */
    public static ItemStack createSkullByValue(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (value.isEmpty())
            return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack getPlayerSkull(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack getPlayerSkull(String uuid) {
        return getPlayerSkull(Bukkit.getPlayer(UUID.fromString(uuid)));
    }

    // 检查输入的字符串是否为26个英文字母以及下划线、数字
    public static boolean checkString(String str) {
        String regex = "^[a-zA-Z0-9_]+$";
        return str.matches(regex);
    }

    /**
     * 检查一个区域是否和已有区域冲突
     *
     * @param area 区域
     * @return 冲突的区域 如果没有冲突则返回null
     */
    public static Area conflictCheck(Area area) {
        SortedMap<Integer, Integer> x = area.getMarkers_x();
        SortedMap<Integer, Integer> z = area.getMarkers_z();
        for (Map.Entry<Integer, Integer> entry : x.entrySet()) {
            int id = entry.getKey();
            int x1 = x.get(id);
            int z1 = z.get(id);
            Location p_b = new Location(Bukkit.getWorld(area.getWorld_name()), x1, area.getY_bottom(), z1);
            Location p_t = new Location(Bukkit.getWorld(area.getWorld_name()), x1, area.getY_top(), z1);
            Area a_c = getAreaByLocation(p_b);
            if (a_c != null && !a_c.getName().equals(area.getName()) && !a_c.getIllegal()) {
                return a_c;
            }
            a_c = getAreaByLocation(p_t);
            if (a_c != null && !a_c.getName().equals(area.getName()) && !a_c.getIllegal()) {
                return a_c;
            }
        }
        return null;
    }


}
