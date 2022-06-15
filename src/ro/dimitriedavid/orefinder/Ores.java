package ro.dimitriedavid.orefinder;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Ores {
    public static List<Ore> All() {
        var arr = new ArrayList<Ore>();
        // normal
        arr.add(new Ore(new Material[]{ Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE }, Material.COAL, "Coal Finder", "Coal", "§0"));
        arr.add(new Ore(new Material[]{ Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE }, Material.COPPER_INGOT, "Copper Finder", "Copper", "§d"));
        arr.add(new Ore(new Material[]{ Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE }, Material.LAPIS_LAZULI, "Lapis Finder", "Lapis", "§1"));
        arr.add(new Ore(new Material[]{ Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE }, Material.IRON_INGOT, "Iron Finder", "Iron", "§3"));
        arr.add(new Ore(new Material[]{ Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, Material.NETHER_GOLD_ORE }, Material.GOLD_INGOT, "Gold Finder", "Gold", "§6"));
        arr.add(new Ore(new Material[]{ Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE }, Material.REDSTONE, "Redstone Finder", "Redstone", "§4"));
        arr.add(new Ore(new Material[]{ Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE }, Material.DIAMOND, "Diamond Finder", "Diamond", "§b"));
        arr.add(new Ore(new Material[]{ Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE }, Material.EMERALD, "Emerald Finder", "Emerald", "§2"));

        // nether
        arr.add(new Ore(new Material[]{ Material.ANCIENT_DEBRIS }, Material.NETHERITE_INGOT, "Netherite Finder", "Netherite", "§c"));
        arr.add(new Ore(new Material[]{ Material.NETHER_QUARTZ_ORE }, Material.QUARTZ, "Quartz Finder", "Quartz", "§f"));
        return arr;
    }
}
