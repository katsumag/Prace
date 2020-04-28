package io.github.katsumag.prace;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private static final ConcurrentHashMap<UUID, Double> money = new ConcurrentHashMap<>();
    private BukkitRunnable task = new BukkitRunnable(){
        @Override
        public void run() {

            //System.out.println("money = " + money);

            money.forEach((uuid, aDouble) -> {
                //System.out.println("uuid = " + uuid);
                //System.out.println("aDouble = " + aDouble);

                if (Prace.getEconomy().hasAccount(Bukkit.getOfflinePlayer(uuid))){
                    Prace.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), aDouble);
                } else {
                    Prace.getEconomy().createPlayerAccount(Bukkit.getOfflinePlayer(uuid));
                    Prace.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), aDouble);
                }

                Player p = Bukkit.getPlayer(uuid);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 10, 60);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bEagle&7Craft&8] &e>> Twoja nagroda za prace wynosi &a%money%$&e.").replaceAll("%money%", String.valueOf(aDouble)));
                money.remove(uuid);

            });
        }
    };

    public PlayerManager(Prace main){

        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, task, (20 * 60) * 5, (20 * 60)* 5);

    }

    public boolean hasMoney(UUID player){
        if (money.containsKey(player)){
            return true;
        } else return false;
    }

    public double getMoney(UUID player){
        return (hasMoney(player) ? money.get(player) : 0);
    }

    public void addMoney(UUID player, double amount){
        if (hasMoney(player)){
            money.replace(player, money.get(player) + amount);
        } else {
            money.put(player, amount);
        }
    }

    public void setMoney(UUID player, double amount){
        if (hasMoney(player)){
            money.replace(player, amount);
        } else money.put(player, amount);
    }

}
