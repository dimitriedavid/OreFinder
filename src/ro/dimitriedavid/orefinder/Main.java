package ro.dimitriedavid.orefinder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // config
        saveDefaultConfig();

        // Bukkit.getLogger().info("[Ore Finder] Enabling version " + version);
        getServer().getPluginManager().registerEvents(new MyListener(config), this);
        addRecipes();
    }

    private void addRecipes() {
        for (var ore : Ores.All()) {
            getServer().addRecipe(getRecipe(ore));
        }
    }

    private ShapedRecipe getRecipe(Ore ore) {
        ItemStack clock = new ItemStack(Material.CLOCK);

        var meta = clock.getItemMeta();
        meta.setDisplayName(ore.displayName);
        meta.addEnchant(Enchantment.DURABILITY, 3, false);
        clock.setItemMeta(meta);

        ShapedRecipe coalClockRecipe = new ShapedRecipe(new NamespacedKey(this, ore.name + "_recipe" + UUID.randomUUID()), clock);

        coalClockRecipe.shape(" c ", "coc", " c ");
        coalClockRecipe.setIngredient('c', ore.craftIngredient);
        coalClockRecipe.setIngredient('o', Material.CLOCK);

        return coalClockRecipe;
    }
}
