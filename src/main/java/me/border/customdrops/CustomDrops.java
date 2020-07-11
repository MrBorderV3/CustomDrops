package me.border.customdrops;

import me.border.customdrops.listener.BlockBreakHandler;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomDrops extends JavaPlugin {

    private static CustomDrops instance;

    public static CustomDrops getInstance(){
        return instance;
    }

    public static List<Material> blockList = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        instance = this;
        blockList.addAll(getMaterials());
        getServer().getPluginManager().registerEvents(new BlockBreakHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private List<Material> getMaterials(){
        List<Material> materials = new ArrayList<>();
        Set<String> materialSet = getConfig().getConfigurationSection("Blocks").getKeys(false);
        for (String material : materialSet){
            try {
                Material mat = Material.valueOf(material.toUpperCase());
                materials.add(mat);
            } catch (IllegalArgumentException e){
                getLogger().warning("Not adding " + material + " to the blocks list since it is not a valid material.");
            }
        }

        return materials;
    }
}
