package se.nikoci.legions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import se.nikoci.legions.structs.Legion;

import java.util.HashMap;
import java.util.Map;

public class Factory {

    @Getter @Setter(AccessLevel.PROTECTED)
    private static Map<Integer, Legion> legions = new HashMap<>(); // <id, legion>

    public static void load(Database db){
        legions = db.getLegions();

    }

}
