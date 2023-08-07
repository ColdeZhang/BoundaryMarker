package cn.lunadeer.boundarymarker.chestui;

import org.bukkit.inventory.ItemStack;

public class ToggleButtonUI extends ButtonUI {

    @Override
    public void onClick() {
        reverseSetting.set(!reverseSetting.status());
        this.setName(base_name + (reverseSetting.status() ? " §a当前开启" : " §c当前关闭"));
        update();
    }

    public interface Setting {
        void set(Boolean status);
        boolean status();
    }

    public ToggleButtonUI(ChestUI belong, ItemStack material, String name, Setting reverseSetting) {
        super(belong, material, name + (reverseSetting.status() ? " §a当前开启" : " §c当前关闭"));
        this.base_name = name;
        this.reverseSetting = reverseSetting;
    }

    private final Setting reverseSetting;
    private final String base_name;

}
