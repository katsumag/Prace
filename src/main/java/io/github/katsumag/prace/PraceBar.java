package io.github.katsumag.prace;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class PraceBar{

    private BossBar base;
    private UUID player;

    public PraceBar fromBossBar(BossBar bar, UUID player){
        base = bar;
        this.player = player;
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
                int level = PlayerManager.getLevelByJob(player, job);

                if (level != 0){
                    nextLevelXP += level * 200;
                }

                double toAdd = progress / nextLevelXP;
                //System.out.println("toAdd = " + toAdd);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP);
                //System.out.println("level = " + level);

                getBossBar().setProgress(toAdd);

                Prace.db.setMinerEXP(player, (int) Math.floor(progress));

                if (toAdd >= 1){
                    Prace.db.setMinerLevel(player, level + 1);
                }

                return this;

            case "WoodCutter":
                double nextLevelXP1 = 400;
                int level1 = PlayerManager.getLevelByJob(player, job);

                if (level1 != 0){
                    nextLevelXP1 += level1 * 200;
                }

                double toAdd1 = progress / nextLevelXP1;
                //System.out.println("toAdd = " + toAdd1);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP1);
                //System.out.println("level = " + level1);

                getBossBar().setProgress(toAdd1);

                Prace.db.setWoodCutterEXP(player, (int) Math.floor(progress));

                if (toAdd1 >= 1){
                    Prace.db.setWoodCutterLevel(player, level1 + 1);
                }

                return this;

            case "Builder":
                double nextLevelXP2 = 400;
                int level2 = PlayerManager.getLevelByJob(player, job);

                if (level2 != 0){
                    nextLevelXP2 += level2 * 200;
                }

                double toAdd2 = progress / nextLevelXP2;
                //System.out.println("toAdd = " + toAdd2);
                //System.out.println("progress = " + progress);
                //System.out.println("nextLevelXP = " + nextLevelXP2);
                //System.out.println("level = " + level2);

                getBossBar().setProgress(toAdd2);

                Prace.db.setMinerEXP(player, (int) Math.floor(progress));

                if (toAdd2 >= 1){
                    Prace.db.setBuilderLevel(player, level2 + 1);
                }

        }

        return this;
    }

    public BossBar getBossBar(){
        return base;
    }

}
