package io.github.katsumag.prace.PAPI;

import io.github.katsumag.prace.Prace;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;

public class payoutPlaceholder extends PlaceholderExpansion {

    private Prace prace;
    private DecimalFormat df2 = new DecimalFormat("##.##");

    public payoutPlaceholder(Prace main){
        if (main == null) this.prace = main;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if (! params.equalsIgnoreCase("wyplata")) return null;
        return String.valueOf(df2.format(prace.getPlayerManager().getMoney(p.getUniqueId())));
    }

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
}
