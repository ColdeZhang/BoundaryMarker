package cn.lunadeer.boundarymarker;

import cn.lunadeer.boundarymarker.chestui.*;
import cn.lunadeer.boundarymarker.dto.Area;
import cn.lunadeer.boundarymarker.dto.Role;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.lunadeer.boundarymarker.BoundaryMarkerAPI.createSkullByValue;

public class CUI {

    private static final String group_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWUxYzI0YjEzMmE0Zjk4NmFiOWI5MjViMDk0ZjJkY2UwMGM1YjE2ZGU0ZDdkNDlkMTliMTk3NjRmZmZhNWMxMyJ9fX0=";
    private static final String edit_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI4ZDk4Y2U0N2ZiNzdmOGI2MDRhNzY2ZGRkMjU0OTIzMjU2NGY5NTYyMjVjNTlmM2UzYjdiODczYTU4YzQifX19";
    private static final String minuse_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5YmM3ZGZkM2NhZDcwZWJlMTJmYzM1ZGI2ZmQzZjFkNjUyYzZmZWE5OTI5ZmEzYjIyZmU2ZWVmNWMxIn19fQ==";
    private static final String add_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM1MjM2NDUyMGIzYTliYjhlZDUxNWMwMWY4MGFiN2I5NzcwMjVjZDBiMGZmNmQ4NjQ2OGE1MTY0YzZmYjc4In19fQ==";
    private static final String rename_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmFkOGEzYTNiMzZhZGQ1ZDk1NDFhOGVjOTcwOTk2ZmJkY2RlYTk0MTRjZDc1NGM1MGU0OGU1ZDM0ZjFiZjYwYSJ9fX0=";
    private static final String arrow_up = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q2OTVkMzM1ZTZiZThjYjJhMzRlMDVlMThlYTJkMTJjM2IxN2I4MTY2YmE2MmQ2OTgyYTY0M2RmNzFmZmFjNSJ9fX0=";
    private static final String arrow_down = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM3ODYyY2RjMTU5OTk4ZWQ2YjZmZGNjYWFhNDY3NTg2N2Q0NDg0ZGI1MTJhODRjMzY3ZmFiZjRjYWY2MCJ9fX0=";
    private static final String explode_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVmZmMxMzFkY2Q0ZjAzYjcyZjNkMDVkZjhmYWExOTA1ODhjMmRiZWVhMmJkNDhkNTUzMzgyMjVlZmRhZGVjOSJ9fX0=";
    private static final String glow_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhmYjQwNGFmY2VhMTg3MTg3MmI4MWM3MTM5N2E1YzE2NTY0Mjg2MjYxZTI2NDdmZDY3NmZmYjk5MTc2MzJhZiJ9fX0=";
    private static final String water_flow_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2YzZjJjYTg0N2MwNWE3ZDU1OWY1YjcyYjg1NzY0M2RiNmQyNjM3MDFjYmUyYzVhOWUxMzM3NzQ3ZmFhZmU2YSJ9fX0=";
    private static final String lava_flow_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM2ZTk4YjgxZGNhOWIxZTBlYmExNjI2NzAzMTNjNDBhMWQxOWNmZGU1ODRhMDc5ZjYwMGJjZGE0MWY5YTUxZSJ9fX0=";
    private static final String file_spread_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI1OGI4M2YwNzYxOGVhNzlhMWExMjAyYjVhNzdiMTRkZjFjOGUzNWI2ZTFkZWI4YTI2ZDg5NzZmODUzNjBjMyJ9fX0=";
    private static final String trample_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY1YmE1NjQ1NzcxYmJhZGVjNWM5YWY0NjdlOWYxOGEyMTZlOThjY2NmM2Q0ZTQwMTdhMzA5ZmUxNGY4ZTgwYSJ9fX0=";
    private static final String y_display_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBhMThmZjIzMmE0YjU0MTJmZWYxODU5OTdlNDJlMGI0NDg5MWZiYTk3NzE0NDU1MDM1Y2U4NDY3ZDk2ZWM5NiJ9fX0=";
    private static final String role_manage_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkyYTBhMzY5Yzg5ZTRiYTJjOTNkMWNhN2Y1YzY1MDkwOTVlMGI5YmUyNWI3MzIxNmVhODgyZGM2NzFjMWE3NiJ9fX0=";
    private static final String animal_killing_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDliMjJjNmZjYTBlMDZiYzFmNWFhY2JhZDY2NDA2ZWRmNmRhNzk4MDY5MjViOTI0Y2FiOGU0Y2JkYmRjZDUyYSJ9fX0=";
    private static final String use_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGMzNjA0NTIwOGY5YjVkZGNmOGM0NDMzZTQyNGIxY2ExN2I5NGY2Yjk2MjAyZmIxZTUyNzBlZThkNTM4ODFiMSJ9fX0=";
    private static final String bed_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZiMjkwYTEzZGY4ODI2N2VhNWY1ZmNmNzk2YjYxNTdmZjY0Y2NlZTVjZDM5ZDQ2OTcyNDU5MWJhYmVlZDFmNiJ9fX0=";
    private static final String container_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUzZDM2YmE4YTI5NjYzZGZkYmVmMTFmOWIyZDExY2FlMzg4Yzc1Nzg0Y2FiYzcwNmRjNjY4OWE4Y2IwYjM1MSJ9fX0=";
    private static final String destroy_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZlYTIxMzU4Mzg0NjE1MzQzNzJmMmRhNmM4NjJkMjFjZDVmM2QyYzcxMTlmMmJiNjc0YmJkNDI3OTEifX19";
    private static final String red_stone_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTk1ZWFhOTJkMmViOTY5NDI4NGI0ZTk4Y2FkZWNmZDdhODIzMjU2YmFkNWEzOTQ1OThmNjMyNGNmZTdmNzM3YiJ9fX0=";
    private static final String door_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBlOTNhMTMxOTJkZTU2N2ExM2M5MmUxODNhY2RhYjE5NDg4ZGNhMGE0MmRiMzQxMmMyYzgxY2YyNzUzNCJ9fX0=";
    private static final String dye_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWIxNjVmMWQ5MjJjMGIwMDNjN2Y1NjkzNDk0MjM5ODhhMWM0MTcwOWI0OGQ5OWYxNThkOWFkOWRmNTlhNDljOCJ9fX0=";
    private static final String throwing_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRmODQ3MTVhNjRkYzQ1NTg2ZjdhNjA3OWY4ZTQ5YTk0NzdjMGZlOTY1ODliNGNmZDcxY2JhMzIyNTRhYzgifX19";
    private static final String plant_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEzODkwYWE4Yzc2ZjE3N2NiYWU3ZGMwOTY2MTQ5YTJlMGZiMWMxZWZlYjI4M2RlYjdkZWFhMDlmZDBmYjYifX19";
    private static final String hook_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FjMmIxMTkzYzEyNDU5ZGEzMjc3N2E2NmU3N2MzM2ViYTgzODhkOWNkNTE2ZDgzZjM2NTFiOWY3YmFmMCJ9fX0=";
    private static final String ignite_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI1OGI4M2YwNzYxOGVhNzlhMWExMjAyYjVhNzdiMTRkZjFjOGUzNWI2ZTFkZWI4YTI2ZDg5NzZmODUzNjBjMyJ9fX0=";
    private static final String leash_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFhNzk3M2YyNWNiYmIwNzhmNTk1YzA0MzQ5ZmM1NDFhYzFkZjlhNDRiOGMxYTE2YzUyODU4MWFhNDE3MCJ9fX0=";
    private static final String mob_killing_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODBlYThkMjM4ZTA1YzU0Njc3NGRlMGJlODc1NjZkNzBkMjBjMjUxZTNhYzEzOWFmMzE4Zjg2OTdhMjMyNzI3NiJ9fX0=";
    private static final String move_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";
    private static final String name_tag_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmFkOGEzYTNiMzZhZGQ1ZDk1NDFhOGVjOTcwOTk2ZmJkY2RlYTk0MTRjZDc1NGM1MGU0OGU1ZDM0ZjFiZjYwYSJ9fX0=";
    private static final String place_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZkYTczNWM4OGUwOGI4MGJkZjgxNDBiMGFkZDhkMTg2OTBiODViNGY5MDI1YmI4ODFkN2ExOTEzNDBmM2IzMSJ9fX0=";
    private static final String riding_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ1YzI1OWViMjEzNTIxZmU2MTM1NjBkZGFlNzRlOWU2MmFhZGE3NTZhZTRjNjVhYTcyY2Y5MjY4M2EwNjI4MCJ9fX0=";
    private static final String shear_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzYwN2EzNzM3ZTk5ZjE0MjYyNDE1MmE2ODU4OGY0YmZhZmZiOGNkM2ZhOTljMTk5NzExNTRjYzc0ZjBkZGY3MSJ9fX0=";
    private static final String shoot_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdjZDMwNTQ2MTY3NTA1YmNiMTk1NDMyZTI4NGFhOTliYWY1NzVhZWRjMDQ3ODExM2M4MzgwNjc1MjRiN2Q1NSJ9fX0=";
    private static final String trade_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=";
    private static final String vehicle_destroy_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQyMDcwYWNjODE0YmM5NDZlNTk4NzllYzdkYTQ1ZGU5ODRkM2VlOWExNTkzOTNkZWZiNTk4NTNhYmUzYjYifX19";
    private static final String harvest_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjlmOTkxZjFmNzFiM2EzMzFmY2MxMmQ2ZTY0ZGZmMmU3MzdjOTcxZDllODhlOTcwYzc4N2UwZThjZDA2NDA4NiJ9fX0=";
    private static final String hopper_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhlODRmYTYzMTJlMDViZmY0OGNlZWM3NTBjZTViODUyNjkzOWNlZjYzYjZlZmE3YTUzOWE1YjFkMjVkMDViMCJ9fX0=";
    private static final String creeper_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTE1N2IxNjhjNzM3N2U4NzEzN2VhZDZmNzc4Y2FhZDE4ODM5OTBiNjFiZGQ1NzllMGM2YTljNTFmNWNjZmExZCJ9fX0=";
    private static final String delete_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE2M2IwZjU2ZjdlYzY0ZWFjYmI3MWZjYTMxNTQ5ZDAyMjc0MGQ5YjdkNGI2MTc2MmEyZWZlNTg0MWE0YmYyNSJ9fX0=";
    private static final String present_texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJkOTg3OTJkZDkyZDk3MTk4OTQzNDFhYzkwMTJhNTg0YzQ0Mjg1NThmZDJjNzEyZjc4ZTVmMGQ0ZGE4NTQ3MCJ9fX0=";

    public static void cities_main_page(Player player) {
        List<Area> areas = new ArrayList<>();
        for (Map.Entry<String, Map<String, Area>> world_area : BoundaryMarker.instance.cache.getAreas().entrySet()) {
            for (Map.Entry<String, Area> name_area : world_area.getValue().entrySet()) {
                if (BoundaryMarkerAPI.isOwner(name_area.getValue(), player) || player.isOp()) {
                    areas.add(name_area.getValue());
                }
            }
        }
        class city_btn extends ButtonUI {
            private final Area area;

            city_btn(Area area) {
                super(null, new ItemStack(Material.NAME_TAG), area.getName());
                this.area = area;
            }

            @Override
            public void onClick() {
                PanelUI area_setting_panel = city_setting(player, area);
                area_setting_panel.open();
            }
        }
        List<ButtonUI> cities = new ArrayList<>();
        for (Area area : areas) {
            cities.add(new city_btn(area));
        }
        PageUI.createPages(player, null, "我的城镇", cities).open();
    }

    private static PanelUI city_setting(Player player, Area area) {
        String title = "城镇设置 - " + area.getName();
        PanelUI panel = new PanelUI(player, "我的城镇", title);

        panel.setButton(0, 0,
                new ButtonUI(panel,
                        createSkullByValue(role_manage_texture),
                        "角色组管理") {
                    @Override
                    public void onClick() {
                        PanelUI roles_panel = role_manage(player, title, area);
                        roles_panel.open();
                    }
                });

        panel.setButton(2, 0, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(rename_texture),
                "编辑欢迎用语") {
            @Override
            public void onClick() {
                new InputHandler(player, "请在聊天栏输入欢迎用语", panel) {
                    @Override
                    public void onInput(String input) {
                        area.setWelcome_message(input);
                        Notification.info(player, "欢迎用语已设置为: " + input);
                    }
                };
            }
        });

        panel.setButton(4, 0, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(rename_texture),
                "编辑欢送用语") {
            @Override
            public void onClick() {
                new InputHandler(player, "请在聊天栏输入欢送用语", panel) {
                    @Override
                    public void onInput(String input) {
                        area.setFarewell_message(input);
                        Notification.info(player, "欢送用语已设置为: " + input);
                    }
                };
            }
        });

        panel.setButton(7, 0, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(present_texture),
                "将领地赠与他人") {
            @Override
            public void onClick() {
                ChestUI panel = give_area_to(player,title, area);
                panel.open();
            }
        });

        panel.setButton(8, 0, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(delete_texture),
                "删除此城镇") {
            @Override
            public void onClick() {
                BoundaryMarker.instance.cache.getAreas().get(area.getWorld_name()).remove(area.getName());
            }
        });

        panel.setButton(0, 2,
                new ToggleButtonUI(panel,
                        createSkullByValue(explode_texture),
                        "是否允许TNT爆炸", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setExplode(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getExplode();
                    }
                }));

        panel.setButton(1, 2,
                new ToggleButtonUI(panel,
                        createSkullByValue(glow_texture),
                        "玩家高亮", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setGlow(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getGlow();
                    }
                }));

        panel.setButton(2, 2,
                new ToggleButtonUI(panel,
                        createSkullByValue(water_flow_texture),
                        "是否允许水流动", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setWater_flow(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getWater_flow();
                    }
                }));

        panel.setButton(0, 3,
                new ToggleButtonUI(panel,
                        createSkullByValue(lava_flow_texture),
                        "是否允许岩浆流动", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setLava_flow(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getLava_flow();
                    }
                }));

        panel.setButton(1, 3,
                new ToggleButtonUI(panel,
                        createSkullByValue(file_spread_texture),
                        "是否允许火焰蔓延", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setFire_spread(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getFire_spread();
                    }
                }));

        panel.setButton(2, 3,
                new ToggleButtonUI(panel,
                        createSkullByValue(trample_texture),
                        "农田践踏保护", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setTrample(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getTrample();
                    }
                }));

        panel.setButton(0, 4,
                new ToggleButtonUI(panel,
                        createSkullByValue(creeper_texture),
                        "是否允许生物爆炸（苦力怕、凋零）", new ToggleButtonUI.Setting() {
                    @Override
                    public void set(Boolean status) {
                        area.setCreeper_explode(status);
                    }

                    @Override
                    public boolean status() {
                        return area.getCreeper_explode();
                    }
                }));

        class y_top_display extends ButtonUI {
            public y_top_display() {
                super(panel, createSkullByValue(y_display_texture), "当前顶部高度" + " §a" + area.getY_top());
            }

            @Override
            public void onClick() {
                // do nothing
            }
        }

        class y_bottom_display extends ButtonUI {
            public y_bottom_display() {
                super(panel, createSkullByValue(y_display_texture), "当前底部高度" + " §a" + area.getY_bottom());
            }

            @Override
            public void onClick() {
                // do nothing
            }
        }


        panel.setButton(6, 2, new y_top_display());
        panel.setButton(7, 2,
                new ButtonUI(panel,
                        createSkullByValue(arrow_up),
                        "增加顶部高度") {
                    @Override
                    public void onClick() {
                        area.setY_top(area.getY_top() + 1);
                        getBelong().setButton(24, new y_top_display());
                    }
                });
        panel.setButton(8, 2,
                new ButtonUI(panel,
                        createSkullByValue(arrow_down),
                        "减少顶部高度") {
                    @Override
                    public void onClick() {
                        if (area.getY_top() - 2 < area.getY_bottom()) {
                            Notification.error(player, "顶部高度不能小于底部高度");
                            return;
                        }
                        area.setY_top(area.getY_top() - 1);
                        getBelong().setButton(24, new y_top_display());
                    }
                });

        panel.setButton(6, 3, new y_bottom_display());
        panel.setButton(7, 3,
                new ButtonUI(panel,
                        createSkullByValue(arrow_up),
                        "增加底部高度") {
                    @Override
                    public void onClick() {
                        if (area.getY_bottom() + 2 > area.getY_top()) {
                            Notification.error(player, "底部高度不能大于顶部高度");
                            return;
                        }
                        area.setY_bottom(area.getY_bottom() + 1);
                        getBelong().setButton(33, new y_bottom_display());
                    }
                });
        panel.setButton(8, 3,
                new ButtonUI(panel,
                        createSkullByValue(arrow_down),
                        "减少底部高度") {
                    @Override
                    public void onClick() {
                        area.setY_bottom(area.getY_bottom() - 1);
                        getBelong().setButton(33, new y_bottom_display());
                    }
                });

        return panel;
    }

    private static PanelUI role_manage(Player player, String parent_title, Area area) {
        String title = parent_title + " > 角色组管理";
        PanelUI panel = new PanelUI(player, parent_title, title);

        panel.setButton(1, 3, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(edit_texture),
                "编辑角色组") {
            @Override
            public void onClick() {
                ChestUI roles_edit = roles_edit(player, title, area);
                roles_edit.open();
            }
        });

        panel.setButton(4, 3, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(add_texture),
                "创建角色组") {
            @Override
            public void onClick() {
                new InputHandler(player, "请在聊天栏输入要创建的角色组名称", panel) {
                    @Override
                    public void onInput(String input) {
                        if (!BoundaryMarkerAPI.checkString(input)) {
                            Notification.error(player, "角色组名称仅支持大小写英文、数字、下划线！");
                            return;
                        }
                        area.getRoles().put(input, new Role());
                        Notification.info(player, "角色组 " + input + " 创建成功");
                    }
                };
            }
        });

        panel.setButton(7, 3, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(minuse_texture),
                "删除角色组") {
            @Override
            public void onClick() {
                ChestUI roles_delete = roles_delete(player, title, area);
                roles_delete.open();
            }
        });

        return panel;
    }

    private static ChestUI roles_delete(Player player, String parent_name, Area area) {
        List<ButtonUI> buttons = new ArrayList<>();
        for (Map.Entry<String, Role> entry : area.getRoles().entrySet()) {
            buttons.add(new ButtonUI(null,
                    BoundaryMarkerAPI.createSkullByValue(group_texture),
                    entry.getKey()) {
                @Override
                public void onClick() {
                    area.getRoles().remove(entry.getKey());
                    Map<String, String> p_r = area.getPlayers_role();
                    for (Map.Entry<String, String> e : p_r.entrySet()) {
                        if (e.getValue().equals(entry.getKey())) {
                            area.getPlayers_role().remove(e.getKey());
                        }
                    }
                    getBelong().back();
                }
            });

        }
        return PageUI.createPages(player, parent_name, "选择要删除的权限组", buttons);
    }

    private static ChestUI roles_edit(Player player, String parent_name, Area area) {
        List<ButtonUI> buttons = new ArrayList<>();
        for (Map.Entry<String, Role> entry : area.getRoles().entrySet()) {
            buttons.add(new ButtonUI(null,
                    BoundaryMarkerAPI.createSkullByValue(group_texture),
                    entry.getKey()) {
                @Override
                public void onClick() {
                    ChestUI role_manage = role_manage(player, parent_name, area, entry.getKey());
                    role_manage.open();
                }
            });

        }
        return PageUI.createPages(player, parent_name, "选择要编辑的权限组", buttons);
    }


    private static PanelUI role_manage(Player player, String parent_name, Area area, String role_name) {
        String title = "角色组管理 > " + role_name;
        PanelUI panel = new PanelUI(player, parent_name, title);

        panel.setButton(3, 1, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(rename_texture),
                "重命名") {
            @Override
            public void onClick() {
                new InputHandler(player, "请在聊天栏输入角色组 " + role_name + " 的新名称（建议使用英文作为名称）", panel) {
                    @Override
                    public void onInput(String input) {
                        if (!BoundaryMarkerAPI.checkString(input)) {
                            Notification.error(player, "角色组名称仅支持大小写英文、数字、下划线！");
                            return;
                        }
                        area.getRoles().put(input, area.getRoles().get(role_name));
                        area.getRoles().remove(role_name);
                        Map<String, String> p_r = area.getPlayers_role();
                        for (Map.Entry<String, String> e : p_r.entrySet()) {
                            if (e.getValue().equals(role_name)) {
                                area.getPlayers_role().put(e.getKey(), input);
                            }
                        }
                        Notification.info(player, "角色组 " + role_name + " 重命名为 " + input);
                    }
                };
            }
        });

        panel.setButton(5, 1, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(edit_texture),
                "权限编辑") {
            @Override
            public void onClick() {
                ChestUI role_edit = role_edit_flags(player, title, area, role_name);
                role_edit.open();
            }
        });

        panel.setButton(3, 3, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(add_texture),
                "添加玩家") {
            @Override
            public void onClick() {
                ChestUI role_add_player = role_add_player(player, title, area, role_name);
                role_add_player.open();
            }
        });

        panel.setButton(5, 3, new ButtonUI(
                panel,
                BoundaryMarkerAPI.createSkullByValue(minuse_texture),
                "移除玩家") {
            @Override
            public void onClick() {
                ChestUI role_remove_player = role_remove_player(player, title, area, role_name);
                role_remove_player.open();
            }
        });

        return panel;
    }

    private static ChestUI role_add_player(Player player, String parent_name, Area area, String role_name) {
        List<ButtonUI> buttons = new ArrayList<>();
        for (Player onlinePlayer : BoundaryMarker.instance.getServer().getOnlinePlayers()) {
            buttons.add(new ButtonUI(null,
                    BoundaryMarkerAPI.getPlayerSkull(onlinePlayer),
                    onlinePlayer.getName()) {
                @Override
                public void onClick() {
                    area.getPlayers_role().put(onlinePlayer.getUniqueId().toString(), role_name);
                    getBelong().back();
                }
            });

        }
        return PageUI.createPages(player, parent_name, "选择要添加的玩家", buttons);
    }

    private static ChestUI give_area_to(Player player, String parent_name, Area area) {
        List<ButtonUI> buttons = new ArrayList<>();
        for (Player onlinePlayer : BoundaryMarker.instance.getServer().getOnlinePlayers()) {
            buttons.add(new ButtonUI(null,
                    BoundaryMarkerAPI.getPlayerSkull(onlinePlayer),
                    onlinePlayer.getName()) {
                @Override
                public void onClick() {
                    area.setOwner_name(onlinePlayer.getUniqueId().toString());
                    area.setOwner_name(onlinePlayer.getName());
                    getBelong().close();
                }
            });

        }
        return PageUI.createPages(player, parent_name, "选择要赠与的玩家", buttons);
    }

    private static ChestUI role_remove_player(Player player, String parent_name, Area area, String role_name) {
        List<ButtonUI> buttons = new ArrayList<>();
        for (Map.Entry<String, String> entry : area.getPlayers_role().entrySet()) {
            Player p = Bukkit.getPlayer(UUID.fromString(entry.getKey()));
            if (p == null) continue;
            if (entry.getValue().equals(role_name)) {
                buttons.add(new ButtonUI(null,
                        BoundaryMarkerAPI.getPlayerSkull(entry.getKey()),
                        p.getName()) {
                    @Override
                    public void onClick() {
                        area.getPlayers_role().remove(entry.getKey());
                        getBelong().back();
                    }
                });
            }
        }
        return PageUI.createPages(player, parent_name, "选择要移除的玩家", buttons);
    }

    private static ChestUI role_edit_flags(Player player, String parent_name, Area area, String role_name) {
        PanelUI panel = new PanelUI(player, parent_name, role_name + " > 权限编辑");
        Role role = area.getRoles().get(role_name);
        panel.setButton(0,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(animal_killing_texture),
                        "伤害动物",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setAnimal_killing(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getAnimal_killing();
                            }
                        }
                ));
        panel.setButton(1,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(use_texture),
                        "使用功能方块",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setUse(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getUse();
                            }
                        }
                ));
        panel.setButton(2,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(bed_texture),
                        "使用床",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setBed(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getBed();
                            }
                        }
                ));
        panel.setButton(3,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(container_texture),
                        "使用容器",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setContainer(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getContainer();
                            }
                        }
                ));
        panel.setButton(4,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(destroy_texture),
                        "破坏方块",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setDestroy(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getDestroy();
                            }
                        }
                ));
        panel.setButton(5,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(red_stone_texture),
                        "使用红石元件",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setRed_stone(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getRed_stone();
                            }
                        }
                ));
        panel.setButton(6,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(door_texture),
                        "使用门",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setDoor(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getDoor();
                            }
                        }
                ));
        panel.setButton(7,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(dye_texture),
                        "染色",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setDye(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getDye();
                            }
                        }
                ));
        panel.setButton(8,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(throwing_texture),
                        "投掷物品",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setThrowing(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getThrowing();
                            }
                        }
                ));
        panel.setButton(9,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(plant_texture),
                        "种植",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setPlant(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getPlant();
                            }
                        }
                ));
        panel.setButton(10,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(hook_texture),
                        "钓竿",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setHook(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getHook();
                            }
                        }
                ));
        panel.setButton(11,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(ignite_texture),
                        "点火",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setIgnite(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getIgnite();
                            }
                        }
                ));
        panel.setButton(12,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(leash_texture),
                        "拴绳",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setLeash(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getLeash();
                            }
                        }
                ));
        panel.setButton(13,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(mob_killing_texture),
                        "击杀敌对生物",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setMob_killing(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getMob_killing();
                            }
                        }
                ));
        panel.setButton(14,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(move_texture),
                        "移动",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setMove(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getMove();
                            }
                        }
                ));
        panel.setButton(15,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(name_tag_texture),
                        "命名牌",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setName_tag(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getName_tag();
                            }
                        }
                ));
        panel.setButton(16,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(place_texture),
                        "命名牌",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setPlace(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getPlace();
                            }
                        }
                ));
        panel.setButton(17,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(riding_texture),
                        "骑乘交通工具",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setRiding(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getRiding();
                            }
                        }
                ));
        panel.setButton(18,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(shear_texture),
                        "剪羊毛",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setShear(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getShear();
                            }
                        }
                ));
        panel.setButton(19,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(shoot_texture),
                        "射击",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setShoot(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getShoot();
                            }
                        }
                ));
        panel.setButton(20,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(trade_texture),
                        "交易",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setTrade(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getTrade();
                            }
                        }
                ));
        panel.setButton(21,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(vehicle_destroy_texture),
                        "破坏交通工具",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setVehicle_destroy(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getVehicle_destroy();
                            }
                        }
                ));
        panel.setButton(22,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(harvest_texture),
                        "收获",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setHarvest(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getHarvest();
                            }
                        }
                ));
        panel.setButton(23,
                new ToggleButtonUI(panel,
                        BoundaryMarkerAPI.createSkullByValue(hopper_texture),
                        "特殊容器（漏斗、漏斗矿车、发射器、投掷器）",
                        new ToggleButtonUI.Setting() {
                            @Override
                            public void set(Boolean status) {
                                role.setHopper(status);
                            }

                            @Override
                            public boolean status() {
                                return role.getHopper();
                            }
                        }
                ));

        return panel;
    }
}
