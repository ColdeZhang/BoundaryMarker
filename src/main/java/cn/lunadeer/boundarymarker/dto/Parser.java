package cn.lunadeer.boundarymarker.dto;

import cn.lunadeer.boundarymarker.BoundaryMarker;
import cn.lunadeer.boundarymarker.XLogger;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Parser {

    public static Cache Load() {
        XLogger.debug("正在加载存档");
        BackUp();   // 备份存档
        File file = new File(BoundaryMarker.instance.getDataFolder(), "saves.yml");
        if (!file.exists()) {
            XLogger.warn("存档不存在，创建新存档");
            BoundaryMarker.instance.saveResource("saves.yml", false);
            return new Cache();
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        YAMLMapper yamlMapper = new YAMLMapper();
        Cache cache = new Cache();
        try {
            cache = yamlMapper.readValue(yml.saveToString(), cache.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        XLogger.debug("加载完成");
        return cache;
    }


    public static void Save(Cache cache) {
        XLogger.debug("正在保存存档");
        YamlConfiguration yml = new YamlConfiguration();
        YAMLMapper yamlMapper = new YAMLMapper();
        try {
            Writer str = new StringWriter();
            yamlMapper.writeValue(str, cache);
            yml.loadFromString(str.toString());
            yml.save(new File(BoundaryMarker.instance.getDataFolder(), "saves.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        XLogger.debug("保存完成");
    }

    public static void BackUp() {
        XLogger.debug("正在备份存档");
        File file = new File(BoundaryMarker.instance.getDataFolder(), "saves.yml");
        if (!file.exists()) {
            XLogger.warn("存档不存在，创建新存档");
            return;
        }
        try {
            File backupFolder = new File(BoundaryMarker.instance.getDataFolder(), "backups");
            if(!backupFolder.exists()) {
                backupFolder.mkdirs();
            }
            // 获取当前时间 YYYY-MM-DD-HH-mm-ss
            String time = new java.text.SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new java.util.Date());
            // 复制文件
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(new File(BoundaryMarker.instance.getDataFolder(), "backups/saves-" + time + ".yml"));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) fos.write(buffer, 0, len);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        XLogger.debug("备份完成");
    }
}
