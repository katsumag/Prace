package io.github.katsumag.prace;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private static final ConcurrentHashMap<UUID, Double> money = new ConcurrentHashMap<>();
    private Prace main;
    private BukkitRunnable task = new BukkitRunnable(){
        @Override
        public void run() {

            //System.out.println("money = " + money);

            money.forEach((uuid, aDouble) -> {
                //System.out.println("uuid = " + uuid);
                //System.out.println("aDouble = " + aDouble);

                if (main.getEconomy().hasAccount(Bukkit.getOfflinePlayer(uuid))){
                    main.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), aDouble);
                } else {
                    main.getEconomy().createPlayerAccount(Bukkit.getOfflinePlayer(uuid));
                    main.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), aDouble);
                }

                money.remove(uuid);

            });
        }
    };

    public PlayerManager(Prace main){
        this.main = main;

        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(main, task, (20 * 60) * 5, (20 * 60)* 5);

    }

    public boolean hasMoney(UUID player){
        if (money.containsKey(player)){
            return true;
        } else return false;
    }

    public double getMoney(UUID player){
        if (hasMoney(player)){
            return money.get(player);
        }

        return 0;
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
