package ro.dimitriedavid.orefinder;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MyListener implements Listener {
    HashMap<UUID, HashMap<Material, Long>> journal = new HashMap<>();

    FileConfiguration config;
    int reuseInterval;
    int radius;

    public MyListener(FileConfiguration config) {
        this.config = config;
        this.reuseInterval = config.getInt("reuse_interval");
        this.radius = config.getInt("search_radius");
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
        Player p = event.getPlayer();

        ItemStack item = p.getInventory().getItemInMainHand();

        if (item.getAmount() == 1 && item.getType() == Material.CLOCK && item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
            for (var ore : Ores.All()) {
                checkOre(ore, item, p);
            }
        }
    }

    private void checkOre(Ore ore, ItemStack item, Player p) {
        if (item.getItemMeta().getDisplayName().equals(ore.displayName) && isInTime(p.getUniqueId(), ore.craftIngredient)) {
            var distanceToCoal = getDistanceToOre(ore.sourceBlocks, p.getLocation());
            if (distanceToCoal > 0) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ore.textColor + ore.name + " is §a" + distanceToCoal + ore.textColor + " blocks away"));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ore.textColor + ore.name + " not around"));
            }
        }
    }

    private boolean isInTime(UUID player, Material ore) {
        // we check if there is an entry
        if (journal.get(player) != null && journal.get(player).get(ore) != null) {
            if (journal.get(player).get(ore) + reuseInterval < System.currentTimeMillis()) {
                journal.get(player).put(ore, System.currentTimeMillis());
                return true;
            } else {
                return false;
            }
        }

        // we check if the ore is missing
        if (journal.get(player) != null) {
            journal.get(player).put(ore, System.currentTimeMillis());
            return true;
        }

        // create the entry fully
        var oreMap = new HashMap<Material, Long>();
        oreMap.put(ore, System.currentTimeMillis());
        journal.put(player, oreMap);
        return true;
    }

    private int getDistanceToOre(List<Material> ores, Location playerLoc) {
        var blocks = getNearbyBlocks(playerLoc, radius);

        var oreBlock = blocks.stream().filter(block -> ores.contains(block.getBlockData().getMaterial())).sorted(Comparator.comparingInt(o -> (int) o.getLocation().distance(playerLoc))).findFirst().orElse(null);

        return oreBlock == null ? -1 : (int) oreBlock.getLocation().distance(playerLoc);
    }

    private static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks.stream().filter(block -> block.getLocation().distance(location) < radius + 1).collect(Collectors.toList());
    }
}
