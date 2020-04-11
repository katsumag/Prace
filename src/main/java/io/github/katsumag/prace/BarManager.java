package io.github.katsumag.prace;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class BarManager implements Listener{

    private static HashMap<UUID, BossBar> bars = new HashMap<>();

    public HashMap<UUID, BossBar> getBars(){
        return bars;
    }

    public boolean hasBar(UUID player){
        if (getBars().containsKey(player)) return true;
        return false;
    }

    public BossBar getBar(UUID player){

        if (hasBar(player)){
            return getBars().get(player);
        } else return null;

    }

    public void createBar(UUID player, String title, BarColor colour, BarStyle style){
        BossBar bar = Bukkit.createBossBar(title, colour, style);
        getBars().put(player, bar);
        bar.addPlayer(Bukkit.getPlayer(player));

    }
}
