package io.github.katsumag.prace.SQL;

import io.github.katsumag.prace.Prace;

import java.util.logging.Level;

public class Error {
    public static void execute(Prace plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(Prace plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
