package io.github.katsumag.prace.Jobs;

import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.WrappedPlayer;
import io.github.katsumag.prace.misc.WoodType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class WoodCutter implements Listener {

    private final Prace main;

    public WoodCutter(Prace main){
        this.main = main;
    }

    @EventHandler
    public void onBlockMine(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (! p.hasMetadata("Job")) return;

        List<MetadataValue> values = p.getMetadata("Job");
        for (MetadataValue value : values) {
            if (value.getOwningPlugin() instanceof Prace){
                if (value.asString().equalsIgnoreCase("WoodCutter")){

                    for (WoodType type : WoodType.values()) {
                        if(type.getItem().isSimilar(new ItemStack(e.getBlock().getType(), 1, e.getBlock().getData()))){
                            WrappedPlayer player = WrappedPlayer.getWrappedPlayer(p.getUniqueId());

                            player.getLevel(JobType.WOODCUTTER).thenAccept(level -> {
                                double multiplier = level * 0.01;
                                player.getEXP(JobType.WOODCUTTER).thenAccept(xp -> {
                                    player.setEXP(xp + 1, JobType.WOODCUTTER);
                                });
                                main.getPlayerManager().addMoney(p.getUniqueId(), 0.02 + multiplier);
                            });
                        }
                    }
                }
            }
        }
    }
}
