package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.chestui.ChestUI;
import cn.lunadeer.boundarymarker.chestui.ClickEvent;
import cn.lunadeer.boundarymarker.chestui.InputHandler;
import cn.lunadeer.boundarymarker.chestui.UICaches;
import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Cache;
import cn.lunadeer.boundarymarker.dto.Parser;
import cn.lunadeer.boundarymarker.events.*;
import com.flowpowered.math.vector.Vector2d;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.ExtrudeMarker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class BoundaryMarker extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        XLogger.info("开始加载 BoundaryMarker");
        config = new Configuration(this);
        cache = Parser.Load();

        // 清理非法区域
        for (Map.Entry<String, Map<String, Area>> world_areas : cache.getAreas().entrySet()) {
            for (Map.Entry<String, Area> area : world_areas.getValue().entrySet()) {
                if (area.getValue().getIllegal()) {
                    XLogger.warn("清理非法区域: " + area.getKey());
                    world_areas.getValue().remove(area.getKey());
                }
            }
        }

        getServer().getPluginManager().registerEvents(new BlockEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new FlowEvent(), this);

        Objects.requireNonNull(Bukkit.getPluginCommand("BoundaryMarker")).setExecutor(new Commands());
        Objects.requireNonNull(Bukkit.getPluginCommand("BoundaryMarker")).setTabCompleter(new Commands());

        BoundaryMarkerAPI.runAtFixedRateAsync(this, () -> {
            Parser.Save(cache);

            if (!config.blue_map()) {
                return;
            }

            BlueMapAPI.getInstance().ifPresent(api -> {
                for (Map.Entry<String, Map<String, Area>> world_areas : cache.getAreas().entrySet()) {
                    api.getWorld(world_areas.getKey()).ifPresent(world -> {
                        MarkerSet markerSet = MarkerSet.builder()
                                .label("城镇")
                                .build();


                        for (Map.Entry<String, Area> area : world_areas.getValue().entrySet()) {
                            Collection<Vector2d> vectors = new ArrayList<>();
                            for (Integer index : area.getValue().getMarkers_x().keySet()) {
                                vectors.add(new Vector2d(area.getValue().getMarkers_x().get(index), area.getValue().getMarkers_z().get(index)));
                            }
                            if (vectors.size() < 3) continue;
                            Shape shape = new Shape(vectors);
                            double x = vectors.iterator().next().getX();
                            double z = vectors.iterator().next().getY();
                            double y = area.getValue().getY_bottom();

                            Color line = new Color(0, 191, 255, 0.8F);
                            Color fill = new Color(0, 191, 255, 0.2F);
                            if (area.getValue().getIllegal()) {
                                line = new Color(255, 0, 0, 0.8F);
                                fill = new Color(255, 0, 0, 0.2F);
                            }
                            ExtrudeMarker marker = ExtrudeMarker.builder()
                                    .label(area.getKey())
                                    .position(x, y, z)
                                    .shape(shape, area.getValue().getY_bottom(), area.getValue().getY_top())
                                    .lineColor(line)
                                    .fillColor(fill)
                                    .build();
                            markerSet.getMarkers()
                                    .put(area.getKey(), marker);
                        }
                        for (BlueMapMap map : world.getMaps()) {
                            map.getMarkerSets().put(world_areas.getKey() + "-" + markerSet.getLabel(), markerSet);
                        }
                    });
                }
            });
        }, 20 * 30);
        XLogger.info("加载完成！");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Parser.Save(cache);
    }

    public static BoundaryMarker instance;
    public Cache cache;
    public UICaches uiCaches = new UICaches();
    public Map<String, Map<String, ChestUI>> UIs = uiCaches.UIs; // <player, <title, ui>>
    public Map<String, InputHandler> PlayerInput = uiCaches.PlayerInput;
    public Configuration config;
    public Map<String, Map<Integer, Location>> points = new HashMap<>(); // <player, <index, Location>>
}
