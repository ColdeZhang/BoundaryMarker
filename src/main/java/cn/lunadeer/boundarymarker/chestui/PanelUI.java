package cn.lunadeer.boundarymarker.chestui;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import org.bukkit.entity.Player;

public class PanelUI extends ChestUI{

    public PanelUI(Player player, String parent_title, String title) {
        super(player, parent_title, title);
        BoundaryMarker.instance.UIs.get(player.getUniqueId().toString()).put(title, this);
    }

    public void setButton(int x, int y, ButtonUI button) {
        setButton(x + y * 9, button);
    }
}
