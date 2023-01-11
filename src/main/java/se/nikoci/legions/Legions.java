package se.nikoci.legions;

import org.bukkit.plugin.java.JavaPlugin;
import se.nikoci.legions.commands.LegionCommand;
import se.nikoci.legions.database.Database;
import se.nikoci.legions.structs.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public final class Legions extends JavaPlugin {

    public static List<EventHandler> eventHandlers;
    public static CmdManager commandManager;

    public static Logger logger;
    public static Database db;

    @Override
    public void onEnable() {
        eventHandlers = new ArrayList<>();
        commandManager = new CmdManager();
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

        /*db.selectParse("legions", LegionModel.class)
                .forEach(lh  -> System.out.println(lh.hook().getMembers()));*/

        commandManager.registerCommand(new LegionCommand());
    }

    @Override
    public void onDisable() {
    }

    public static void subscribe(EventHandler... handlers){
        eventHandlers.addAll(List.of(handlers)); }

    public static void unsubscribe(EventHandler... handlers){
        eventHandlers.removeAll(List.of(handlers)); }

}
