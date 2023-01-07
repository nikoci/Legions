package se.nikoci.legions.events;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.bukkit.OfflinePlayer;

import java.util.Date;

@Data
@SuperBuilder(setterPrefix = "set")
public class Event {
    private OfflinePlayer trigger;
    private Date sent;
}
