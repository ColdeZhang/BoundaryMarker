package cn.lunadeer.boundarymarker.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

// https://www.cnblogs.com/juno3550/p/15582661.html#yaml-%E6%96%87%E4%BB%B6%E4%B8%8E-bean-%E7%B1%BB

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private String name;

    private String owner_uuid;
    private String owner_name;
    private String world_name = "";
    private BigInteger last_maintain_time;
    private String welcome_message = "";
    private String farewell_message = "欢迎下次再来！";

    private SortedMap<Integer, Integer> markers_x = new TreeMap<>();        // <index, x>
    private SortedMap<Integer, Integer> markers_z = new TreeMap<>();        // <index, z>
    private Integer y_bottom = 60;                       // y方向底部层高
    private Integer y_top = 99;                          // y方向顶部层高

    private Map<String, Role> roles = new HashMap<>();                // <role_name, Role>

    private Map<String, String> players_role = new HashMap<>();       // <player_uuid, role_name>

    private Boolean explode = false;            // 行政区域内TNT是否可以爆炸
    private Boolean creeper_explode = false;    // 行政区域内苦力怕是否可以爆炸
    private Boolean glow = true;                // 行政区域内玩家是否发光
    private Boolean water_flow = false;         // 行政区域内水是否会流动
    private Boolean lava_flow = false;          // 行政区域内岩浆是否会流动
    private Boolean fire_spread = false;        // 行政区域内火是否会蔓延
    private Boolean trample = false;            // 行政区域内农作物是否会被践踏

    private Boolean illegal = false;
}
