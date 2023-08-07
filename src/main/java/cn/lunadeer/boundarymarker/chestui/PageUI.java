package cn.lunadeer.boundarymarker.chestui;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.BoundaryMarkerAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PageUI extends ChestUI {
    private static String pre_skin_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTVlZmQ5Njk3NGMwNDAzZjIyOWNmOTQxODVjZGQwZjcxOTczNjJhY2JkMDMxY2RmNTFmY2M4ZGFmYWM2Yjg1YSJ9fX0=";
    private static String next_skin_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA2NzJiODJmMGQxZjhjNDBjNTZiNDJkMzY5YWMyOTk0Yzk0ZGE0NzQ5MTAxMGMyY2U0MzAzZTM0NjViOTJhNyJ9fX0=";

    PageUI(Player player, String parent_title, String title, int page, int total, List<ButtonUI> buttons) {
        super(player, parent_title, title + " - " + page + "/" + total);
        if (page != 1) {
            setButton(52, new ButtonUI(this, BoundaryMarkerAPI.createSkullByValue(pre_skin_value), "上一页") {
                @Override
                public void onClick() {
                    close();
                    String pre_page_name = title + " - " + (page - 1) + "/" + total;
                    BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).get(pre_page_name).open();
                }
            });
        }
        if (page != total) {
            setButton(53, new ButtonUI(this, BoundaryMarkerAPI.createSkullByValue(next_skin_value), "下一页") {
                @Override
                public void onClick() {
                    close();
                    String next_page_name = title + " - " + (page + 1) + "/" + total;
                    BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).get(next_page_name).open();
                }
            });
        }
        int slot = 0;
        for (ButtonUI button : buttons) {
            button.setBelong(this);
            setButton(slot, button);
            slot++;
        }
    }

    /**
     * 创建分页UI
     *
     * @param player       玩家
     * @param parent_title 父UI
     * @param title        标题
     * @param items        内容
     * @return 返回第一页的UI
     */
    public static ChestUI createPages(Player player, String parent_title, String title, List<ButtonUI> items) {
        int total_pages = items.size() / 45;
        if (items.size() % 45 != 0) {
            total_pages++;
        }
        if (total_pages == 0) {
            total_pages = 1;
        }
        for (int i = 0; i < total_pages; i++) {
            int start = i * 45;
            int end = (i + 1) * 45;
            if (end > items.size()) {
                end = items.size();
            }
            List<ButtonUI> page_contents = items.subList(start, end);
            PageUI pageUI = new PageUI(player, parent_title, title, i + 1, total_pages, page_contents);
            String new_title = title + " - " + (i + 1) + "/" + total_pages;
            BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).put(new_title, pageUI);
        }
        return BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).get(title + " - 1/" + total_pages);
    }
}
