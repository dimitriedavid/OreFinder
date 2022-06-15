package ro.dimitriedavid.orefinder;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ore {
    public List<Material> sourceBlocks;
    public Material craftIngredient;

    public String displayName;
    public String name;
    public String textColor;

    public Ore(Material[] sourceBlocks, Material craftIngredient, String displayName, String name, String textColor) {
        this.sourceBlocks = new ArrayList<>(Arrays.asList(sourceBlocks));
        this.craftIngredient = craftIngredient;
        this.displayName = displayName;
        this.name = name;
        this.textColor = textColor;
    }
}

