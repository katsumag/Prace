package io.github.katsumag.prace.misc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum WoodType {

    /**
     * Oak = 0
     * Spruce = 1
     * Birch = 2
     * Jungle = 3
     * Acacia = 4
     * Dark Oak = 5
     */

    OAK_WOOD(new ItemStack(Material.LOG)),
    //OAK_WOOD_PLANKS(new ItemStack(Material.WOOD, 1, (short) 0)),
    SPRUCE_WOOD(new ItemStack(Material.LOG, 1, (short) 1)),
    //SPRUCE_WOOD_PLANKS(new ItemStack(Material.WOOD, 1, (short) 1)),
    BIRCH_WOOD(new ItemStack(Material.LOG, 1, (short) 2)),
    //BIRCH_WOOD_PLANKS(new ItemStack(Material.WOOD, 1, (short) 2)),
    JUNGLE_WOOD(new ItemStack(Material.LOG, 1, (short) 3)),
    //JUNGLE_WOOD_PLANKS(new ItemStack(Material.WOOD, 1, (short) 3)),
    ACACIA_WOOD(new ItemStack(Material.LOG, 1, (short) 4)),
    //ACACIA_WOOD_PLANKS(new ItemStack(Material.WOOD, 1, (short) 4)),
    DARK_OAK_WOOD(new ItemStack(Material.LOG, 1, (short) 5));
    //DARK_OAK_PLANKS(new ItemStack(Material.WOOD, 1, (short) 5))

    ItemStack type;

    WoodType(ItemStack type){
        this.type = type;
    }

    public ItemStack getItem(){
        return type;
    }

}
