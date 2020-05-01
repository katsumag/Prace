package io.github.katsumag.prace.Events;

import io.github.katsumag.prace.Jobs.JobType;
import io.github.katsumag.prace.WrappedPlayer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class registerOnJoin implements Listener {

    public void onJoin(AsyncPlayerPreLoginEvent e){
        WrappedPlayer p = WrappedPlayer.getWrappedPlayer(e.getUniqueId());

        p.isInDb().thenAccept(aBoolean -> {
           if (aBoolean) return;
           p.setCurrentJob(JobType.NONE);
        });
    }
}
