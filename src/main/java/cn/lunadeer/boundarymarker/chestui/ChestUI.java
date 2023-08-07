package cn.lunadeer.boundarymarker.chestui;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import cn.lunadeer.boundarymarker.XLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class ChestUI {

    private final Player player;
    private ChestUI parent = null;
    Component title_c;
    private final Inventory view;
    private final Map<Integer, ButtonUI> buttons = new HashMap<>();
    private final String back_btn_skin_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiNWU5ZDVhZmFjMTgzZjFmNTcwYzFiNmVmNTE1NmMxMjFjMWVmYmQ4NTUyN2Q4ZDc5ZDBhZGVlYjY3MjQ4NSJ9fX0=";
    private final Map<String, String> data = new HashMap<>();   // 保存一些元数据

    ChestUI(Player player, String parent_title, String title) {
        this.player = player;
        this.parent = BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).get(parent_title);
        title_c = Component.text("BM - " + title, Style.style(TextColor.color(0x8BFF7B)));
        this.view = BoundaryMarker.instance.getServer().createInventory(null, 54, title_c);
        if (parent != null) {
            setButton(45, new ButtonUI(this, BoundaryMarkerAPI.createSkullByValue(back_btn_skin_value), "返回") {
                @Override
                public void onClick() {
                    back();
                }
            });
        }
    }

    /**
     * 打开界面 如果存在父界面则关闭父界面
     */
    public void open() {
        if (parent != null) {
            parent.close();
        }
        XLogger.debug("open ui: " + title_c.toString());
        player.openInventory(view);
        Map<String, ChestUI> player_uis = BoundaryMarker.instance.UIs.computeIfAbsent(player.getUniqueId().toString(), k -> new HashMap<>());
        player_uis.put(title_c.toString(), this);
    }

    public void close() {
        XLogger.debug("close ui: " + title_c.toString());
        player.closeInventory();
        BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).remove(title_c.toString());
    }

    public void back() {
        XLogger.debug("back ui: " + title_c.toString());
        this.close();
        if (parent == null) {
            return;
        }
        parent.open();
    }

    public String getTitle() {
        return title_c.toString();
    }

    public void setButton(int slot, ButtonUI button) {
        view.setItem(slot, button.getMaterial());
        buttons.put(slot, button);
        button.setSlot(slot);
    }

    public void onClick(int slot) {
        if (buttons.containsKey(slot)) {
            buttons.get(slot).onClick();
        }
    }

    public void setData(String key, String value) {
        data.put(key, value);
    }

    public String getData(String key) {
        return data.get(key);
    }

}
