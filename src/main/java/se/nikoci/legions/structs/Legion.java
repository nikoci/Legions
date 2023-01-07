package se.nikoci.legions.structs;

import lombok.*;
import org.bukkit.Location;
import se.nikoci.legions.Factory;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Legion {
    private int id;
    private String name;
    private String description;
    private String icon;
    private UUID leader;
    private UUID[] members;
    private int power;
    private Location core;

    public static Legion getLegion(int id) {
        return Factory.getLegions().get(id); }

    public static Legion getLegion(String name) {
        for (var legion : Factory.getLegions().values()) {
            if (legion.getName().equalsIgnoreCase(name)) return legion;
        }
        return null;
    }
}
