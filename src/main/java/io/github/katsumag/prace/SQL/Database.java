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
        connection = getSQLConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    // These are the methods you can use to get things out of your database. You of course can make new ones to return different things in the database.
    // This returns the number of people the player killed.
    public Integer getMinerLevel(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT Miner_Level FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Miner_Level");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getMinerEXP(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT Miner_EXP FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Miner_EXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getWoodCutterLevel(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT WoodCutter_Level FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("WoodCutter_Level");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getWoodCutterEXP(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT WoodCutter_EXP FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Wood_Cutter_EXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getBuilderLevel(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT Builder_Level FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Builder_Level");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public Integer getBuilderEXP(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT Builder_EXP FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getInt("Builder_EXP");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return 0;
    }

    public String getCurrentJob(UUID string) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("SELECT Selected_Job FROM " + table + " WHERE UUID = '"+string+"';");

            rs = ps.executeQuery();
            while(rs.next()){// Tell database to search for the player you sent into the method. e.g getTokens(sam) It will look for sam.
                return rs.getString("Selected_Job");  // Return the players ammount of kills. If you wanted to get total (just a random number for an example for you guys) You would change this to total!
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return null;
    }

    // Exact same method here, Except as mentioned above i am looking for total!

    // Now we need methods to save things to the database
    public void setInfo(Player player, Integer miner_level, Integer miner_exp, Integer woodcutter_level, Integer woodcutter_exp, Integer builder_level, Integer builder_exp, String job) { //level, exp
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement("REPLACE INTO " + table + " (player,Miner_Level,Miner_EXP, WoodCutter_Level, WoodCutter_EXP, Builder_Level, Builder_EXP, Selected_Job) VALUES(?,?,?,?,?,?,?,?)"); // IMPORTANT. In SQLite class, We made 3 colums. player, Kills, Total.
            ps.setString(1, player.getUniqueId().toString());
            ps.setInt(2, miner_level);
            ps.setInt(3, miner_exp);
            ps.setInt(4, woodcutter_level);
            ps.setInt(5, woodcutter_exp);
            ps.setInt(6, builder_level);
            ps.setInt(7, builder_exp);
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
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
            }
        }
        return;
    }

    public void setCurrentJob(UUID player, String job){
        PreparedStatement statement = null;
        try {
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET Selected_Job=? WHERE UUID=?");
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
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET Miner_Level=? WHERE UUID=?");
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
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET Miner_EXP=? WHERE UUID=?");
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
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET WoodCutter_Level=? WHERE UUID=?");
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
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET WoodCutter_EXP=? WHERE UUID=?");
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
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET Builder_Level=? WHERE UUID=?");
            statement.setInt(1, level);
            statement.setString(2, player.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBuilderEXP(UUID player, int exp){
        PreparedStatement statement = null;
        try {
            statement = getSQLConnection().prepareStatement("UPDATE Prace SET Builder_EXP=? WHERE UUID=?");
            statement.setInt(1, exp);
            statement.setString(2, player.toString());
            statement.executeUpdate();
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
