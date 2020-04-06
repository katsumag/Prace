package io.github.katsumag.prace.SQL;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class NewSQLite
{

    Plugin main;

    private final File file;
    private final AtomicReference<Connection> conn = new AtomicReference<>();
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS Prace (" +
            "`UUID` varchar(32) NOT NULL DEFAULT ''," +
            "`minerLevel` INTEGER NOT NULL DEFAULT 0," +
            "`minerEXP` INTEGER NOT NULL DEFAULT 0," +
            "`woodCutterLevel` INTEGER NOT NULL DEFAULT 0," +
            "`woodCutterEXP` INTEGER NOT NULL DEFAULT 0," +
            "`builderLevel` INTEGER NOT NULL DEFAULT 0," +
            "`builderEXP` INTEGER NOT NULL DEFAULT 0," +
            "`selectedJob` text NOT NULL DEFAULT ''," +
            "PRIMARY KEY (`UUID`)" +
            ");";


    public NewSQLite(final Plugin plugin, final String fileName)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.main = plugin;
    }

    public void load()
    {
        try
        {
            file.getParentFile().mkdirs();
            file.createNewFile();

            Class.forName("org.sqlite.JDBC");
            conn.set(DriverManager.getConnection(String.format("jdbc:sqlite:%s", file)));

            if (getConnection().isPresent()){
                Statement s = getConnection().get().createStatement();
                s.executeUpdate(SQLiteCreateTokensTable);
                s.close();
            } else {
                this.main.getServer().getPluginManager().disablePlugin(this.main);
                throw new NullPointerException("Database Connection Failed!");
            }

        }
        catch (final SQLException | ClassNotFoundException | IOException ex)
        {
            ex.printStackTrace();
            kill();
        }
    }

    public void kill()
    {
        try
        {
            conn.get().close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public Optional<Connection> getConnection()
    {
        return Optional.ofNullable(conn.get());
    }

}
