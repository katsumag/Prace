package io.github.katsumag.prace.Jobs;

import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.WrappedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class Builder implements Listener {

    private Prace main;

    public Builder(Prace main){
        this.main = main;
    }

    @EventHandler
    public void onBlockBuild(BlockPlaceEvent e){

        Player p = e.getPlayer();
        if (! p.hasMetadata("Job")) return;

        List<MetadataValue> values = p.getMetadata("Job");
        for (MetadataValue value : values) {
            if (value.getOwningPlugin() instanceof Prace){
                if (value.asString().equalsIgnoreCase("Builder")){
                    WrappedPlayer player = WrappedPlayer.getWrappedPlayer(p.getUniqueId());
                    int level = player.getLevel(JobType.BUILDER);
                    double multiplier = level * 0.001;
                    player.setEXP(player.getEXP(JobType.BUILDER) + 1, JobType.BUILDER);
                    main.getPlayerManager().addMoney(p.getUniqueId(), 0.005 + multiplier);

                }

            }
        }

    }

}
