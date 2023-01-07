package se.nikoci.legions.events;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.bukkit.OfflinePlayer;
import se.nikoci.legions.structs.Legion;

@Getter
@SuperBuilder(setterPrefix = "set")
public class LegionCreateEvent extends Event {
    private Legion legion;
}
