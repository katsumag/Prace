package io.github.katsumag.prace.PAPI;

import io.github.katsumag.prace.Prace;
import io.github.katsumag.prace.WrappedPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.concurrent.ExecutionException;

public class levelPlaceholder extends PlaceholderExpansion {

    private final Prace prace = Prace.get();

    @Override
    public String getIdentifier() {
        return "prace";
    }

    @Override
    public String getAuthor() {
        return "katsumag";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {

        WrappedPlayer player = new WrappedPlayer(p.getUniqueId());
        try {
            return String.valueOf(player.getLevel(player.getCurrentJob().get()).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "0";

    }
}
