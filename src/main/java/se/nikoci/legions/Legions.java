package se.nikoci.legions;

import org.bukkit.plugin.java.JavaPlugin;
import se.nikoci.legions.structs.EventHandler;
import se.nikoci.legions.structs.LegionHook;

import java.util.logging.Logger;


public final class Legions extends JavaPlugin {

    public static Logger logger;
    public static Database db;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        db = new Database("legions.db").init();

        /*db.insert("legions", Map.of(
                "name", "Dynasty",
                "description", "We rule it all",
                "icon", "null",
                "leader", "ad4b9f63-cf28-4fea-883c-474e6eec6253",
                "members", "['1537e30c-a660-42ce-9a7d-92d3a2d6704f', '88125a23-7600-412f-b524-6a2301935324']",
                "power", 10,
                "core", "['fbc0e65a-7bc9-48f4-bdc5-4d2b2f5e1b3b', 1078, 70, 123]"
        ));*/

        db.selectParse("legions", LegionHook.class)
                .forEach(lh  -> System.out.println(lh.hook().getMembers()));
    }

    @Override
    public void onDisable() {
    }

    public void subscribe(EventHandler... handlers){

    }

    public void unsubscribe(EventHandler... handlers){

    }

}
