package io.github.katsumag.prace.SQL;

import io.github.katsumag.prace.Prace;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public abstract class Database {
    Prace plugin;
    Connection connection;
    // The name of the table we created back in SQLite class.
    public String table = "Prace";
    public int tokens = 0;
    public Database(Prace instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = connection;
        try{
            NewSQLite newSQLite = new NewSQLite(Prace.getPlugin(Prace.class), "Prace.db");
            newSQLite.load();
            if (! newSQLite.getConnection().isPresent()) return;
            connection = newSQLite.getConnection().get();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    public boolean isInDB(UUID string){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Prace Where UUID=?");
            statement.setString(1, string.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // These are the methods you can use to get things out of your database. You of course can make new ones to return different things in the database.
    // This returns the number of people the player killed.
    public Integer getMinerLevel(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MinerLevel FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("MinerLevel");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public Integer getMinerEXP(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MinerEXP FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("MinerEXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public Integer getWoodCutterLevel(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT WoodCutterLevel FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("WoodCutterLevel");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public Integer getWoodCutterEXP(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT WoodCutterEXP FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Wood_Cutter_EXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public Integer getBuilderLevel(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT BuilderLevel FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("BuilderLevel");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public Integer getBuilderEXP(UUID string) {
        System.out.println("getBuilderEXP");

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT BuilderEXP FROM Prace WHERE UUID=?;");
            ps.setString(1, string.toString());

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("BuilderEXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return 0;
    }

    public String getCurrentJob(UUID string) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT SelectedJob FROM " + table + " WHERE UUID = '"+string+"';");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getString("SelectedJob");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return null;
    }

    // Exact same method here, Except as mentioned above i am looking for total!

    // Now we need methods to save things to the database
    public void setInfo(Player player, Integer MinerLevel, Integer MinerEXP, Integer WoodCutterLevel, Integer WoodCutterEXP, Integer BuilderLevel, Integer BuilderEXP, String job) { //level, exp
        try {
            PreparedStatement ps = connection.prepareStatement("REPLACE INTO " + table + " (player,MinerLevel,MinerEXP, WoodCutterLevel, WoodCutterEXP, BuilderLevel, BuilderEXP, Selected_Job) VALUES(?,?,?,?,?,?,?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, player.getUniqueId().toString());
            ps.setInt(2, MinerLevel);
            ps.setInt(3, MinerEXP);
            ps.setInt(4, WoodCutterLevel);
            ps.setInt(5, WoodCutterEXP);
            ps.setInt(6, BuilderLevel);
            ps.setInt(7, BuilderEXP);
            ps.setString(8, job);
            // YOU MUST put these into this line!! And depending on how many
            // colums you put (say you made 5) All 5 need to be in the brackets
            // Seperated with comma's (,) AND there needs to be the same amount of
            // question marks in the VALUES brackets. Right now i only have 3 colums
            // So VALUES (?,?,?) If you had 5 colums VALUES(?,?,?,?,?)

            // This sets the value in the database. The colums go in order. Player is ID 1, kills is ID 2, Total would be 3 and so on. you can use
            // setInt, setString and so on. tokens and total are just variables sent in, You can manually send values in as well. p.setInt(2, 10) <-
            // This would set the players kills instantly to 10. Sorry about the variable names, It sets their kills to 10 i just have the variable called
            // Tokens from another plugin :/
            ps.executeUpdate();
            return;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        }
        return;
    }

    public void setCurrentJob(UUID player, String job){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET SelectedJob=? WHERE UUID=?");
            statement.setString(1, job);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setMinerLevel(UUID player, int level){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET MinerLevel=? WHERE UUID=?");
            statement.setInt(1, level);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMinerEXP(UUID player, int exp){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET MinerEXP=? WHERE UUID=?");
            statement.setInt(1, exp);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setWoodCutterLevel(UUID player, int level){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET WoodCutterLevel=? WHERE UUID=?");
            statement.setInt(1, level);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setWoodCutterEXP(UUID player, int exp){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET WoodCutterEXP=? WHERE UUID=?");
            statement.setInt(1, exp);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBuilderLevel(UUID player, int level){
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE Prace SET BuilderLevel=? WHERE UUID=?");
            statement.setInt(1, level);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBuilderEXP(UUID player, int exp) {

        /*
        if (!isInDB(player)) {
            try {
                statement = connection.prepareStatement("INSERT INTO Prace VALUES UUID, BuilderEXP VALUES (?, ?)");
                statement.setString(1, player.toString());
                statement.setInt(2, exp);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            try {
                statement = connection.prepareStatement("UPDATE Prace SET BuilderEXP=? WHERE UUID=?");
                statement.setInt(1, exp);
                statement.setString(2, player.toString());
                statement.executeUpdate();
                System.out.println("FINSIHED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        System.out.println("setBuilderEXP");

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `Prace`(`UUID`, `BuilderEXP`) VALUES (?, ?) ON CONFLICT(`UUID`) DO UPDATE SET `BuilderEXP`=excluded.`BuilderEXP`"))
        {
            statement.setString(1, player.toString());
            statement.setInt(2, exp);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }

    public void shutdown(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            Errors.sqlConnectionClose();
            e.printStackTrace();
        }
    }
}
