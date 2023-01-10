package se.nikoci.legions.structs;

import lombok.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Legion {

    @NonNull private Integer id;
    @NonNull private String name;
    @NonNull private String description;
    @NonNull private String icon;
    @NonNull private OfflinePlayer leader;
    @NonNull private List<OfflinePlayer> members; //JSON Array String[uuid...]
    @NonNull private Integer power;
    @NonNull private Integer maxPower;
    @NonNull private Location core; //JSON Array String[uuid, x, y, z]

}
