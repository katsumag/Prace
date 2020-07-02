package io.github.katsumag.prace;

import io.github.katsumag.prace.Events.registerOnJoin;
import io.github.katsumag.prace.GUI.Selector;
import io.github.katsumag.prace.Jobs.Builder;
import io.github.katsumag.prace.Jobs.Miner;
import io.github.katsumag.prace.Jobs.WoodCutter;
import io.github.katsumag.prace.PAPI.jobPlaceholder;
import io.github.katsumag.prace.PAPI.payoutPlaceholder;
import io.github.katsumag.prace.SQL.NewSQLite;
import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Prace extends JavaPlugin {

    public static Connection con;
    public static NewSQLite database;
    private  PlayerManager manager;
    public static double Time = 300;
    private static Economy econ;
    private static Prace instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getDataFolder().mkdirs();
        database = new NewSQLite(this, "Prace.db");
        database.load();
        con = database.getConnection().get();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT sqlite_version()");
            ResultSet rs = ps.executeQuery();
            //System.out.println("rs.getString(1) = " + rs.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getPluginManager().registerEvents(new Miner(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WoodCutter(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Builder(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Selector(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BarManager(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new registerOnJoin(), this);
        PlaceholderAPI.registerExpansion(new jobPlaceholder());
        PlaceholderAPI.registerExpansion(new payoutPlaceholder());

        try {

            ResultSet set = con.prepareStatement("SELECT `UUID` FROM `Prace`;").executeQuery();

            for (String uuid : getStringList(set, "UUID")) {
                Player p = Bukkit.getPlayer(uuid);
                WrappedPlayer player = WrappedPlayer.getWrappedPlayer(p.getUniqueId());
                p.setMetadata("Job", new FixedMetadataValue(this, player.getCachedJobName()));
            }


        } catch (SQLException e) {
            //e.printStackTrace();
        }

        //VAULT LOAD ---------------------------------------------------------------------------------------------------
        if (! setupEconomy()){
            getLogger().severe("- Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
        }
         manager = new PlayerManager(this);




        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {

                if (Time == 0){
                    Time = 300;
                }

                Time--;

                new BarManager().getBars().forEach((uuid, bossBar) -> {
                    bossBar.setProgress(Time / 300);
                });
            }
        }, 20, 20);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            getDataBase().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDataBase(){
        return con;
    }

    public String[] getStringList(ResultSet rs, String column){
        try {
            return (String[]) rs.getArray(column).getArray();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return new String[0];
    }

    public PlayerManager getPlayerManager(){
        return this.manager;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("Prace")){

            if (sender instanceof Player){
                ((Player) sender).openInventory(new Selector(this).inventory);
            } else return true;

        } else return true;

        return true;
    }

    public static Prace get(){
        return Prace.instance;
    }

    public String translate(String str){
        switch (str.toLowerCase()){
            case "builder":
                return "Budowniczy";
            case "woodcutter":
                return "Drwal";
            case "miner":
                return "Gornik";
            case "none":
                return "Brak";
        }
        return "";
    }

}
