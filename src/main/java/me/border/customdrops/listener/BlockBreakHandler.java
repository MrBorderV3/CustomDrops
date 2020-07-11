package me.border.customdrops.listener;

import me.border.customdrops.CustomDrops;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class BlockBreakHandler implements Listener {

    private CustomDrops plugin = CustomDrops.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL)
            return;
        Block block = e.getBlock();
        Material mat = block.getType();
        if (getMats().contains(mat)){
            String newMatString = plugin.getConfig().getString("Blocks." + mat + ".drop");
            try {
                Material newMat = Material.valueOf(newMatString);
                ItemStack item = new ItemStack(newMat);
                Random random = new Random();
                int maxDrop = plugin.getConfig().getInt("Blocks." + mat + ".amount");
                int randomNum = random.nextInt(maxDrop + 1);
                if (randomNum == 0){
                    randomNum = 1;
                }
                e.setCancelled(true);
                block.setType(Material.AIR, true);
                for (int i = 0; i < randomNum; i++) {
                    block.getWorld().dropItem(block.getLocation(), item);
                }
            } catch (IllegalArgumentException ex){
                Bukkit.getLogger().warning(newMatString + " Is not a valid material, drop changing cancelled.");
            }
        }
    }

    private List<Material> getMats(){
        return CustomDrops.blockList;
    }
}
