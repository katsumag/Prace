package io.github.katsumag.prace.PAPI;

import io.github.katsumag.prace.Prace;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class payoutPlaceholder extends PlaceholderExpansion {

    private final Prace prace = Prace.get();
    private final DecimalFormat df2 = new DecimalFormat("##.##");

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        df2.applyPattern("##.##");
        df2.setRoundingMode(RoundingMode.HALF_EVEN);
        if (! params.equalsIgnoreCase("wyplata")) return null;
        return format(prace.getPlayerManager().getMoney(p.getUniqueId()));
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

    @Override
    public boolean persist() {
        return true;
    }

    private String format(double amount) {
        final NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(amount);
    }

}
