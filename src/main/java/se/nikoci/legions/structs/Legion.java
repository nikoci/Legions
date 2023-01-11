package se.nikoci.legions.structs;

import lombok.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Legion {

    private Integer id;
    @NonNull private String name;
    private String description;
    private String icon;
    @NonNull private OfflinePlayer leader;
    private List<OfflinePlayer> members;
    private Integer power = 10;
    private Location core;

}
