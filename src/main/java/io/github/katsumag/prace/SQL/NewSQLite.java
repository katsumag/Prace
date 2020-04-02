package io.github.katsumag.prace.SQL;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class NewSQLite
{

    private final File file;
    private final AtomicReference<Connection> conn = new AtomicReference<>();


    public NewSQLite(final Plugin plugin, final String fileName)
    {
        this.file = new File(plugin.getDataFolder(), fileName);
    }

    public void load()
    {
        try
        {
            file.getParentFile().mkdirs();
            file.createNewFile();

            Class.forName("org.sqlite.JDBC");
            conn.set(DriverManager.getConnection(String.format("jdbc:sqlite:%s", file)));
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
