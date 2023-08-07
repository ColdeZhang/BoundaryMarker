package cn.lunadeer.boundarymarker;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    Configuration(BoundaryMarker plugin) {
        _plugin = plugin;
        _plugin.saveDefaultConfig();
        reload();
    }

    public void reload() {
        _plugin.reloadConfig();
        _file = _plugin.getConfig();
        _debug = _file.getBoolean("debug", false);
        _name_line = _file.getInt("name.line", 0);
        _name_prefix = _file.getString("name.prefix", "#");
        _name_suffix = _file.getString("name.suffix", "#");
        _marker_id_line = _file.getInt("marker_id.line", 1);
        _marker_id_prefix = _file.getString("marker_id.prefix", "No.");
        _marker_id_suffix = _file.getString("marker_id.suffix", "*");
        _max_marks_per_district = _file.getInt("max_marks_per_district", 10);
        _blue_map = _file.getBoolean("blue_map", false);
    }

    public void setDebug(Boolean debug) {
        _debug = debug;
        _file.set("debug", debug);
        _plugin.saveConfig();
    }

    public void setNameLine(Integer name_line) {
        _name_line = name_line;
        _file.set("name.line", name_line);
        _plugin.saveConfig();
    }

    public void setNamePrefix(String name_prefix) {
        _name_prefix = name_prefix;
        _file.set("name.prefix", name_prefix);
        _plugin.saveConfig();
    }

    public void setNameSuffix(String name_suffix) {
        _name_suffix = name_suffix;
        _file.set("name.suffix", name_suffix);
        _plugin.saveConfig();
    }

    public void setMarkerIdLine(Integer marker_id_line) {
        _marker_id_line = marker_id_line;
        _file.set("marker_id.line", marker_id_line);
        _plugin.saveConfig();
    }

    public void setMarkerIdPrefix(String marker_id_prefix) {
        _marker_id_prefix = marker_id_prefix;
        _file.set("marker_id.prefix", marker_id_prefix);
        _plugin.saveConfig();
    }

    public void setMarkerIdSuffix(String marker_id_suffix) {
        _marker_id_suffix = marker_id_suffix;
        _file.set("marker_id.suffix", marker_id_suffix);
        _plugin.saveConfig();
    }

    public void setMaxMarksPerDistrict(Integer max_marks_per_district) {
        _max_marks_per_district = max_marks_per_district;
        _file.set("max_marks_per_district", max_marks_per_district);
        _plugin.saveConfig();
    }

    public Integer max_marks_per_district() {
        return _max_marks_per_district;
    }

    public void setBlueMap(Boolean blue_map) {
        _blue_map = blue_map;
        _file.set("blue_map", blue_map);
        _plugin.saveConfig();
    }

    public Boolean blue_map() {
        return _blue_map;
    }


    public Boolean debug() {
        return _debug;
    }

    public Integer name_line() {
        return _name_line;
    }

    public String name_prefix() {
        return _name_prefix;
    }

    public String name_suffix() {
        return _name_suffix;
    }

    public Integer marker_id_line() {
        return _marker_id_line;
    }

    public String marker_id_prefix() {
        return _marker_id_prefix;
    }

    public String marker_id_suffix() {
        return _marker_id_suffix;
    }


    private BoundaryMarker _plugin = BoundaryMarker.instance;
    private FileConfiguration _file;
    private Boolean _debug;

    private Integer _name_line;
    private String _name_prefix;
    private String _name_suffix;

    private Integer _marker_id_line;
    private String _marker_id_prefix;
    private String _marker_id_suffix;

    private Integer _max_marks_per_district;

    private Boolean _blue_map;

}
