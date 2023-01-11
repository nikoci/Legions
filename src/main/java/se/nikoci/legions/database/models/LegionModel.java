package se.nikoci.legions.database.models;

import com.google.gson.annotations.Expose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.json.JSONArray;
import se.nikoci.legions.structs.Legion;

import java.util.ArrayList;
import java.util.UUID;

public class LegionModel {

    @Expose private Integer id;
    @Expose private String name;
    @Expose private String description;
    @Expose private String icon;
    @Expose private String leader;
    @Expose private String members; //JSON Array String[uuid...]
    @Expose private Integer power;
    @Expose private String core; //JSON Array String[uuid, x, y, z]

    public Legion toLegion() {
        var members = new ArrayList<OfflinePlayer>();
        new JSONArray(this.members)
                .forEach(i -> members.add(Bukkit.getOfflinePlayer(UUID.fromString(i.toString()))));

        UUID cWorld = null;
        float x = 0, y = 0, z = 0;

        var locArr = new JSONArray(this.core);
        for (int i = 0; i < locArr.length(); i++) {
            switch (i) {
                case 0: cWorld = UUID.fromString(locArr.getString(i)); continue;
                case 1: x = locArr.getFloat(i); continue;
                case 2: y = locArr.getFloat(i); continue;
                case 3: z = locArr.getFloat(i); continue;
                default: break;
            }
        }

        if (cWorld == null) {
            System.out.println("Hook error");
            return null;
        }

        return new Legion(
                this.id,
                this.name,
                this.description,
                this.icon,
                Bukkit.getOfflinePlayer(UUID.fromString(this.leader)),
                members,
                power,
                new Location(Bukkit.getWorld(cWorld), x, y, z)
        );
    }
}
