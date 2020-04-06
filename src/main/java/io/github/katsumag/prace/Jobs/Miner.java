package io.github.katsumag.prace.Jobs;

import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.WrappedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class Miner implements Listener {

    private Prace main;

    public Miner(Prace main){
        this.main = main;
    }

    @EventHandler
    public void onBlockMine(BlockBreakEvent e){
        Player p = e.getPlayer();
        if (! p.hasMetadata("Job")) return;

        List<MetadataValue> values = p.getMetadata("Job");
        for (MetadataValue value : values) {
            if (value.getOwningPlugin() instanceof Prace){
                if (value.asString().equalsIgnoreCase("Miner")){
                    WrappedPlayer player = WrappedPlayer.getWrappedPlayer(p.getUniqueId());
                    int level = player.getLevel(JobType.MINER);
                    double multiplier = level * 0.01;
                    player.setEXP(player.getEXP(JobType.MINER) + 1, JobType.MINER);
                    main.getPlayerManager().addMoney(p.getUniqueId(), 0.05 + multiplier);
                }

            }
        }

    }
}
