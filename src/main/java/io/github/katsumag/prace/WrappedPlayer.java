package io.github.katsumag.prace;

import io.github.katsumag.prace.Jobs.JobType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class WrappedPlayer {

    private static HashMap<UUID, WrappedPlayer> wrappedPlayers = new HashMap<>();
    private final BarManager manager = new BarManager();
    private String job = "NONE";
    private HashMap<JobType, Integer> levels = new HashMap<>();
    private HashMap<JobType, Integer> xp = new HashMap<>();


    private final UUID player;
    private final Prace main = Prace.get();

    public WrappedPlayer(UUID player) {
        this.player = player;
        wrappedPlayers.put(player, this);
    }


    public UUID getPlayer() {
        return player;
    }

    public CompletableFuture<Boolean> isInDb() {

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        new BukkitRunnable(){

            @Override
            public void run() {
                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT * FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, player.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        future.complete(true);
                    } else future.complete(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(main);

        return future;
    }


    public static boolean isWrapped(UUID id) {
        return wrappedPlayers.containsKey(id);
    }

    public static WrappedPlayer getWrappedPlayer(UUID id) {

        if (isWrapped(id)) {
            return wrappedPlayers.get(id);
        }

        return new WrappedPlayer(id);

    }

    public void setCurrentJob(JobType type) {

        new BukkitRunnable() {
            @Override
            public void run() {
            isInDb().thenAccept(aBoolean -> {
                if (aBoolean) {
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `selectedJob`=? WHERE `UUID`=?")) {
                        ps.setString(1, type.getName());
                        ps.setString(2, player.toString());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `selectedJob`) VALUES(?, ?)")) {
                        ps.setString(1, player.toString());
                        ps.setString(2, type.getName());
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            job = type.getName();

            }
        }.runTaskAsynchronously(main);
    }

    public CompletableFuture<JobType> getCurrentJob() {

        CompletableFuture<JobType> future = new CompletableFuture<>();

        new BukkitRunnable(){
            @Override
            public void run() {

                if (job != null) {
                    future.complete(JobType.fromString(job));
                }

                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `selectedJob` FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, player.toString());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            future.complete(JobType.fromString(rs.getString("selectedJob")));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(main);
        return future;
    }

    public void setLevel(int level, JobType type) {

        new BukkitRunnable() {

            @Override
            public void run() {

            isInDb().thenAccept(aBoolean -> {
                if (aBoolean) {
                    if (type == JobType.MINER) {
                        try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `minerLevel`=? WHERE `UUID`=?;")) {

                            ps.setInt(1, level);
                            ps.setString(2, player.toString());
                            ps.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (type == JobType.BUILDER) {

                            try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `builderLevel`=? WHERE `UUID`=?;")) {

                                ps.setInt(1, level);
                                ps.setString(2, player.toString());
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `woodCutterLevel`=? WHERE `UUID`=?;");) {

                                ps.setInt(1, level);
                                ps.setString(2, player.toString());
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    if (type == JobType.MINER) {
                        try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `minerLevel`) VALUES(?, ?);")) {

                            ps.setString(1, player.toString());
                            ps.setInt(2, level);
                            ps.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        if (type == JobType.BUILDER) {

                            try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `builderLevel`) VALUES(?, ?);")) {

                                ps.setString(1, player.toString());
                                ps.setInt(2, level);
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `woodCutterLevel`) VALUES(?, ?);");) {

                                ps.setString(1, player.toString());
                                ps.setInt(2, level);
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            if (levels.containsKey(type)){
                levels.replace(type, level);
            } else levels.put(type, level);

            }
        }.runTaskAsynchronously(main);

    }

    public CompletableFuture<Integer> getLevel(JobType type) {

        CompletableFuture<Integer> future = new CompletableFuture<>();

        new BukkitRunnable(){

            @Override
            public void run() {

                if (levels.containsKey(type)){
                    future.complete(levels.get(type));
                    cancel();
                }

                if (type == JobType.BUILDER) {

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `builderLevel` FROM `Prace` WHERE `UUID`=?")) {
                        ps.setString(1, player.toString());

                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                future.complete(rs.getInt("builderLevel"));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {

                    if (type == JobType.MINER) {

                        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `minerLevel` FROM `Prace` WHERE `UUID`=?")) {
                            ps.setString(1, player.toString());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    future.complete(rs.getInt("minerLevel"));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `woodCutterLevel` FROM `Prace` WHERE `UUID`=?")) {
                            ps.setString(1, player.toString());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    future.complete(rs.getInt("woodCutterLevel"));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.runTaskAsynchronously(main);
        return future;
    }

    public void setEXP(int exp, JobType type) {

        new BukkitRunnable() {

            @Override
            public void run() {
                isInDb().thenAccept(aBoolean -> {
                    if (aBoolean) {
                        if (type == JobType.MINER) {
                            try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `minerEXP`=? WHERE `UUID`=?;")) {

                                ps.setInt(1, exp);
                                ps.setString(2, player.toString());
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        } else {
                            if (type == JobType.BUILDER) {

                                try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `builderEXP`=? WHERE `UUID`=?;")) {

                                    ps.setInt(1, exp);
                                    ps.setString(2, player.toString());
                                    ps.executeUpdate();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `woodCutterEXP`=? WHERE `UUID`=?;");) {

                                    ps.setInt(1, exp);
                                    ps.setString(2, player.toString());
                                    ps.executeUpdate();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        if (type == JobType.MINER) {
                            try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `minerEXP`) VALUES(?, ?);")) {

                                ps.setString(1, player.toString());
                                ps.setInt(2, exp);
                                ps.executeUpdate();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        } else {
                            if (type == JobType.BUILDER) {

                                try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `builderEXP`) VALUES(?, ?);")) {

                                    ps.setString(1, player.toString());
                                    ps.setInt(2, exp);
                                    ps.executeUpdate();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `woodCutterEXP`) VALUES(?, ?);");) {

                                    ps.setString(1, player.toString());
                                    ps.setInt(2, exp);
                                    ps.executeUpdate();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

                getLevel(type).thenAccept(level -> {
                    getEXP(type).thenAccept(xp -> {
                        if (xp >= (level*200)+ 400) {
                            setLevel(level + 1, type);
                            Player p = Bukkit.getPlayer(getPlayer());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bEagle&7Craft&8] &e>> Udalo ci sie osiagnac nowy poziom. Obecny poziom wynosi &a%next-level%&e.").replaceAll("%next-level%", String.valueOf(level)));
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 200, 60);
                        }
                    });
                });

                updateBar(type);

                if (xp.containsKey(type)) {
                    xp.replace(type, exp);
                } else xp.put(type, exp);

            }
        }.runTaskAsynchronously(main);
    }

    public CompletableFuture<Integer> getEXP(JobType type){

        CompletableFuture<Integer> future = new CompletableFuture<>();

        new BukkitRunnable(){

            @Override
            public void run() {
                if (xp.containsKey(type)) {
                    future.complete(xp.get(type));
                    cancel();
                }

                if (type == JobType.BUILDER){

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `builderEXP` FROM `Prace` WHERE `UUID`=?")){
                        ps.setString(1, player.toString());

                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()){
                                future.complete(rs.getInt("builderEXP"));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{

                    if (type == JobType.MINER){

                        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `minerEXP` FROM `Prace` WHERE `UUID`=?")) {
                            ps.setString(1, player.toString());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()){
                                    future.complete(rs.getInt("minerEXP"));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else{

                        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `woodCutterEXP` FROM `Prace` WHERE `UUID`=?")) {
                            ps.setString(1, player.toString());

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()){
                                    future.complete(rs.getInt("woodCutterEXP"));
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.runTaskAsynchronously(main);


        return future;
    }

    private void updateBar(JobType type){

        getEXP(type).thenAccept(xp -> {
           getLevel(type).thenAccept(level -> {
               StringBuilder builder = new StringBuilder();
               builder.append("EXP: ");
               builder.append((level > 1) ? (xp - ((level-1) *400) + 400) : xp);
               builder.append(" / ");
               builder.append((level > 1) ? 200 : 400);

               Bukkit.getPlayer(player).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(builder.toString()));
           });
        });
    }

    public String getCachedJobName(){
        return job;
    }

}
