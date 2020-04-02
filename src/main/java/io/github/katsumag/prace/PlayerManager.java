package io.github.katsumag.prace;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private static final ConcurrentHashMap<UUID, Double> money = new ConcurrentHashMap<>();
    private BarManager manager = new BarManager();
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

    public void addEXP(UUID player, double amount, String job){

        System.out.println("here");
        System.out.println("amount = " + amount);

        if (job.equalsIgnoreCase("Miner")){
            System.out.println("miner");
            Prace.db.setMinerEXP(player, (int) Math.floor(Prace.db.getMinerEXP(player) + amount));

            System.out.println("(int) Math.floor(Prace.db.getMinerEXP(player) + amount) = " + (int) Math.floor(Prace.db.getMinerEXP(player) + amount));

            if (manager.hasBar(player)){
                StringBuilder builder = new StringBuilder();
                builder.append(Prace.db.getMinerEXP(player));
                builder.append(" / ");
                builder.append( 400 + (Prace.db.getMinerLevel(player) * 200));
                System.out.println("builder.toString() = " + builder.toString());
                manager.getBar(player).getBossBar().setTitle(builder.toString());
            } else{
                manager.createBar(player, 1 + " / 400", BarColor.BLUE, BarStyle.SEGMENTED_20);
            }

        } else{
            if (job.equalsIgnoreCase("Builder")){
                System.out.println("here");
                Prace.db.setBuilderEXP(player, (int) Math.floor(Prace.db.getBuilderEXP(player) + amount));
                System.out.println("Prace.db.getBuilderEXP(player) = " + Prace.db.getBuilderEXP(player));
                System.out.println("Math.floor(Prace.db.getBuilderEXP(player)) = " + Math.floor(Prace.db.getBuilderEXP(player)));

                System.out.println("(int) Math.floor(Prace.db.getBuilderEXP(player) + amount) = " + (int) Math.floor(Prace.db.getBuilderEXP(player) + amount));

                if (manager.hasBar(player)){
                    StringBuilder builder = new StringBuilder();
                    builder.append(Prace.db.getBuilderEXP(player));
                    builder.append(" / ");
                    builder.append( 400 + (Prace.db.getBuilderLevel(player) * 200));
                    System.out.println("builder.toString() = " + builder.toString());
                    manager.getBar(player).getBossBar().setTitle(builder.toString());
                } else{
                    manager.createBar(player, 1 + " / 400", BarColor.BLUE, BarStyle.SEGMENTED_20);
                }

            } else{
                Prace.db.setWoodCutterEXP(player, (int) Math.floor(Prace.db.getWoodCutterEXP(player) + amount));

                if (manager.hasBar(player)){
                    StringBuilder builder = new StringBuilder();
                    builder.append(Prace.db.getWoodCutterEXP(player));
                    builder.append(" / ");
                    builder.append( 400 + (Prace.db.getWoodCutterLevel(player) * 200));
                    manager.getBar(player).getBossBar().setTitle(builder.toString());
                } else{
                    manager.createBar(player, 1 + " / 400", BarColor.BLUE, BarStyle.SEGMENTED_20);
                }

            }
        }

    }

    public static double getEXPByJob(UUID player, String job){

        if (job.equalsIgnoreCase("Miner")){

            return Prace.db.getMinerEXP(player);

        } else{
            if (job.equalsIgnoreCase("WoodCutter")){

                return Prace.db.getWoodCutterEXP(player);

            } else{

                return Prace.db.getBuilderEXP(player);

            }
        }

    }

    public static int getLevelByJob(UUID player, String job){

        if (job.equalsIgnoreCase("Miner")){

            return Prace.db.getMinerLevel(player);

        } else{
            if (job.equalsIgnoreCase("WoodCutter")){

                return Prace.db.getWoodCutterLevel(player);

            } else{

                return Prace.db.getBuilderLevel(player);

            }
        }

    }

}
