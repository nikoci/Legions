package se.nikoci.legions.structs;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public interface Event {
    OfflinePlayer getTrigger();
    default Date getSent() { return new Date(); }

    void fire();
}
