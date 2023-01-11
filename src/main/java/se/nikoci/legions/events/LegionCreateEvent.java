package se.nikoci.legions.events;

import lombok.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.Legions;
import se.nikoci.legions.structs.*;


@Getter
@RequiredArgsConstructor
public class LegionCreateEvent implements Event {

    @NonNull private OfflinePlayer trigger;
    @NonNull private Legion legion;

    @Setter
    private boolean doDefault;

    @Override
    public @NotNull OfflinePlayer getTrigger() {
        return trigger;
    }

    @Override
    public void fire() {
        Legions.eventHandlers.forEach(eh -> eh.onLegionCreate(this));

        if (doDefault) {
            if (trigger.isOnline()){
                ((Player) trigger).sendMessage("doing defaults...");
            }
        }
    }

}
