package se.nikoci.legions.structs;

import se.nikoci.legions.events.LegionCreateEvent;

public interface EventHandler {

    default void onLegionsCreate(LegionCreateEvent e) {}

}
