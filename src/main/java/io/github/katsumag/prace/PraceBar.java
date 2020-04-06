package io.github.katsumag.prace;

import io.github.katsumag.prace.Jobs.JobType;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class PraceBar{

    private BossBar base;
    private UUID player;
    private WrappedPlayer wrappedPlayer;

    public PraceBar fromBossBar(BossBar bar, UUID player){
        base = bar;
        this.player = player;
        this.wrappedPlayer = WrappedPlayer.getWrappedPlayer(player);
        return this;
    }

    public void open(Player p){
        this.base.addPlayer(p);
    }

    public double getProgress(){
        return base.getProgress();
    }

    public PraceBar setProgress(double progress){
        //System.out.println("player = " + player);
        String job = "";
        for (MetadataValue meta : Bukkit.getPlayer(player).getMetadata("Job")) {
            if (meta.getOwningPlugin() instanceof Prace){
                job = meta.asString();
            }
        }

        if (job.equalsIgnoreCase("")){
            return this;
        }

        switch (job){
            case "Miner":
                double nextLevelXP = 400;
                int level = wrappedPlayer.getLevel(JobType.MINER);

                if (level != 0){
                    nextLevelXP += level * 200;
                }

                double toAdd = progress / nextLevelXP;
                //System.out.println("toAdd = " + toAdd);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP);
                //System.out.println("level = " + level);

                getBossBar().setProgress(toAdd);
                wrappedPlayer.setEXP((int) Math.floor(progress), JobType.MINER);

                if (toAdd >= 1){
                    wrappedPlayer.setLevel(level +1, JobType.MINER);
                }

                return this;

            case "WoodCutter":
                double nextLevelXP1 = 400;
                int level1 = wrappedPlayer.getLevel(JobType.WOODCUTTER);

                if (level1 != 0){
                    nextLevelXP1 += level1 * 200;
                }

                double toAdd1 = progress / nextLevelXP1;
                //System.out.println("toAdd = " + toAdd1);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP1);
                //System.out.println("level = " + level1);

                getBossBar().setProgress(toAdd1);
                wrappedPlayer.setEXP((int) Math.floor(progress), JobType.WOODCUTTER);

                if (toAdd1 >= 1){
                    wrappedPlayer.setLevel(level1 +1, JobType.WOODCUTTER);
                }

                return this;

            case "Builder":
                double nextLevelXP2 = 400;
                int level2 = wrappedPlayer.getLevel(JobType.BUILDER);

                if (level2 != 0){
                    nextLevelXP2 += level2 * 200;
                }

                double toAdd2 = progress / nextLevelXP2;
                //System.out.println("toAdd = " + toAdd2);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP2);
                //System.out.println("level = " + level2);

                getBossBar().setProgress(toAdd2);
                wrappedPlayer.setEXP((int) Math.floor(progress), JobType.BUILDER);

                if (toAdd2 >= 1){
                    wrappedPlayer.setLevel(level2 +1, JobType.BUILDER);
                }

        }

        return this;
    }

    public BossBar getBossBar(){
        return base;
    }

}
