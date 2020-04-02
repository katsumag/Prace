package io.github.katsumag.prace.GUI;

import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.misc.ItemFactory;
import io.github.katsumag.prace.misc.Skull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Selector implements Listener {

    private Prace main;
    public static final ItemStack BUILDER_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/dcb7a15eda1cbe47a8d5d7f780e89bbc35e0c177fcb9c6480a11b02cc8165c1c")).setDisplayName("Builder").build();
    public static final ItemStack LOG_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/dc1754851e367e8beba2a6d8f7c2fede87ae793ac546b0f299d673215b293")).setDisplayName("WoodCutter").build();
    public static final ItemStack COBBLE_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/6d2e310879a6450af5625bcd45093dd7e5d8f827ccbfeac69c81537768406b")).setDisplayName("Miner").build();

    public Inventory inventory;

    public Selector(Prace main){
        this.main = main;

        if (inventory == null){
            inventory = Bukkit.createInventory(new JobHolder(main), InventoryType.HOPPER, "Prace");
        }

        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE);
        inventory.setItem(0, stack);
        inventory.setItem(1, COBBLE_HEAD);
        inventory.setItem(2, LOG_HEAD);
        inventory.setItem(3, BUILDER_HEAD);
        inventory.setItem(4, stack);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if (e.getClickedInventory() == null) return;

        if (!(e.getClickedInventory().getHolder() instanceof JobHolder)) return;

        if (!(e.getSlot() >= 1) && e.getSlot() <= 3) return;

        ItemStack item = e.getClickedInventory().getItem(e.getSlot());

        if (item.isSimilar(BUILDER_HEAD)){

            //Bukkit.broadcastMessage("BUILDER");
            //Builder
            main.getDataBase().setCurrentJob(e.getWhoClicked().getUniqueId(), "Builder");
            ((Player) e.getWhoClicked()).setMetadata("Job", new FixedMetadataValue(main, "Builder"));
            ((Player) e.getWhoClicked()).closeInventory();

        } else{
            if (item.isSimilar(LOG_HEAD)){
                //Bukkit.broadcastMessage("WOODCUTTER");
                //WoodCutter
                main.getDataBase().setCurrentJob(e.getWhoClicked().getUniqueId(), "WoodCutter");
                ((Player) e.getWhoClicked()).setMetadata("Job", new FixedMetadataValue(main, "WoodCutter"));
                ((Player) e.getWhoClicked()).closeInventory();

            } else{
                //Bukkit.broadcastMessage("MINER");
                //Miner
                main.getDataBase().setCurrentJob(e.getWhoClicked().getUniqueId(), "Miner");
                ((Player) e.getWhoClicked()).setMetadata("Job", new FixedMetadataValue(main, "Miner"));
                ((Player) e.getWhoClicked()).closeInventory();

            }
        }

    }

}
