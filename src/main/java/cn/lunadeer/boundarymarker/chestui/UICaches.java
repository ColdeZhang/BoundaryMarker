package cn.lunadeer.boundarymarker.chestui;

import java.util.HashMap;
import java.util.Map;

public class UICaches {

    public Map<String, Map<String, ChestUI>> UIs = new HashMap<>(); // <player, <title, ui>>

    public Map<String, InputHandler> PlayerInput = new HashMap<>();
}
