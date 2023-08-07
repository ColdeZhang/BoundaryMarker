package cn.lunadeer.boundarymarker.chestui;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.Notification;
import org.bukkit.entity.Player;

public abstract class InputHandler {

    Player player;

    public InputHandler(Player player, String title, ChestUI belong) {
        belong.close();
        Notification.info(player, title + " - 输入小写c取消");
        this.player = player;
        BoundaryMarker.instance.PlayerInput.put(player.getUniqueId().toString(), this);
    }


    public void Cancel() {
        BoundaryMarker.instance.PlayerInput.remove(player.getUniqueId().toString());
        Notification.info(player, "已取消");
    }


    public abstract void onInput(String input);

}
