package cn.lunadeer.boundarymarker.chestui;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import java.awt.*;
import java.util.Objects;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        String title = view.title().toString();
        if (!title.contains("BM - ")) {
            return;
        }
        String player_uuid = event.getWhoClicked().getUniqueId().toString();
        ChestUI chestUI = BoundaryMarker.instance.UIs.get(player_uuid).get(title);
        if (chestUI == null) {
            return;
        }
        int slot = event.getSlot();
        chestUI.onClick(slot);
        event.setCancelled(true);
    }

    @EventHandler
    public void onInput(AsyncChatEvent event) {
        String player_uuid = event.getPlayer().getUniqueId().toString();
        if (BoundaryMarker.instance.PlayerInput.containsKey(player_uuid)) {

            String input = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());
            InputHandler inputHandler = BoundaryMarker.instance.PlayerInput.get(player_uuid);
            if (Objects.equals(input, "c")) {
                inputHandler.Cancel();
            } else {
                inputHandler.onInput(input);
            }
            event.setCancelled(true);
        }
    }
}
