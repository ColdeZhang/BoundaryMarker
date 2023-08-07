package cn.lunadeer.boundarymarker.chestui;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ButtonUI {
    private ChestUI belong;
    private int slot = -1;
    private final ItemStack material;
    private final Component name_c;

    public ButtonUI(ChestUI belong, ItemStack material, String name) {
        this.belong = belong;
        this.material = material;
        if (name == null) {
            name = "";
        }
        this.name_c = Component.text(name);
        setName(name);
    }

    public Component getName() {
        return this.name_c;
    }

    public void setName(String name) {
        ItemMeta meta = this.material.getItemMeta();
        meta.displayName(Component.text(name));
        this.material.setItemMeta(meta);
    }

    public ItemStack getMaterial() {
        return this.material;
    }

    protected ChestUI getBelong() {
        return this.belong;
    }

    public void setBelong(ChestUI belong) {
        this.belong = belong;
    }

    public abstract void onClick();

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public void update() {
        this.belong.setButton(this.slot, this);
    }
}
