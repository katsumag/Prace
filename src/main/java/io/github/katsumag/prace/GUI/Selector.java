package io.github.katsumag.prace.GUI;

import io.github.katsumag.prace.BarManager;
import io.github.katsumag.prace.Jobs.JobType;
import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.WrappedPlayer;
import io.github.katsumag.prace.misc.ItemFactory;
import io.github.katsumag.prace.misc.Skull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
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
    private BarManager manager = new BarManager();
    public static final ItemStack BUILDER_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/dcb7a15eda1cbe47a8d5d7f780e89bbc35e0c177fcb9c6480a11b02cc8165c1c"))
            .setDisplayName("§6Budowniczy")
            .setLore(ChatColor.translateAlternateColorCodes('&', "&7Otrzymujesz pieniadze za"), ChatColor.translateAlternateColorCodes('&', "&7stawianie blokow."))
            .build();
    public static final ItemStack LOG_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/dc1754851e367e8beba2a6d8f7c2fede87ae793ac546b0f299d673215b293"))
            .setDisplayName("§6Drwal")
            .setLore(ChatColor.translateAlternateColorCodes('&', "&7Otrzymujesz pieniadze za"), ChatColor.translateAlternateColorCodes('&', "&7niszczenie kamienia i bruku."))
            .build();
    public static final ItemStack COBBLE_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/6d2e310879a6450af5625bcd45093dd7e5d8f827ccbfeac69c81537768406b"))
            .setDisplayName("§6Gornik")
            .setLore(ChatColor.translateAlternateColorCodes('&', "&7Otrzymujesz pieniadze za"), ChatColor.translateAlternateColorCodes('&', "&7scinanie drzew."))
            .build();
    public static final ItemStack CLEAR_HEAD = ItemFactory.builder(Skull.getCustomSkull("http://textures.minecraft.net/texture/beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7"))
            .setDisplayName("§cWylacz Prace")
            .setLore("§7Kliknij aby wylaczyc prace.")
            .build();

    public Inventory inventory;

    public Selector(Prace main){
        this.main = main;

        inventory = Bukkit.createInventory(new JobHolder(main), InventoryType.HOPPER, "Prace");

        inventory.setItem(0, CLEAR_HEAD);
        inventory.setItem(1, COBBLE_HEAD);
        inventory.setItem(2, LOG_HEAD);
        inventory.setItem(3, BUILDER_HEAD);
        inventory.setItem(4, CLEAR_HEAD);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if (e.getClickedInventory() == null) return;
        if (!(e.getClickedInventory().getHolder() instanceof JobHolder)) return;

        ItemStack item = e.getClickedInventory().getItem(e.getSlot());
        Player p = (Player) e.getWhoClicked();
        WrappedPlayer player = WrappedPlayer.getWrappedPlayer(p.getUniqueId());

        if (item.isSimilar(BUILDER_HEAD)){

            //Bukkit.broadcastMessage("BUILDER");
            //Builder
            player.setCurrentJob(JobType.BUILDER);
            e.getWhoClicked().setMetadata("Job", new FixedMetadataValue(main, "Builder"));
            e.getWhoClicked().closeInventory();

        } else{
            if (item.isSimilar(LOG_HEAD)){
                //Bukkit.broadcastMessage("WOODCUTTER");
                //WoodCutter
                player.setCurrentJob(JobType.WOODCUTTER);
                e.getWhoClicked().setMetadata("Job", new FixedMetadataValue(main, "WoodCutter"));
                e.getWhoClicked().closeInventory();

            } else{
                if (item.isSimilar(COBBLE_HEAD)){
                    //Bukkit.broadcastMessage("MINER");
                    //Miner
                    player.setCurrentJob(JobType.MINER);
                    e.getWhoClicked().setMetadata("Job", new FixedMetadataValue(main, "Miner"));
                    e.getWhoClicked().closeInventory();
                } else {
                    player.setCurrentJob(JobType.NONE);
                    e.getWhoClicked().setMetadata("Job", new FixedMetadataValue(main, "None"));
                    e.getWhoClicked().closeInventory();
                    manager.getBar(p.getUniqueId()).removePlayer(p);
                }
            }
        }

        if (! manager.hasBar(p.getUniqueId())){
            manager.createBar(p.getUniqueId(), ChatColor.translateAlternateColorCodes('&', "&eNastepna wyplata"), BarColor.BLUE, BarStyle.SEGMENTED_20);
        }

    }

}
