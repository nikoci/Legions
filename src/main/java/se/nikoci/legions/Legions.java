package se.nikoci.legions;

import org.bukkit.plugin.java.JavaPlugin;
import se.nikoci.legions.structs.EventHandler;

import java.util.logging.Logger;


public final class Legions extends JavaPlugin {

    public static Logger logger;
    public static Database db;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        db = new Database("legions.db").init();
    }

    @Override
    public void onDisable() {
    }

    public void subscribe(EventHandler... handlers){

    }

    public void unsubscribe(EventHandler... handlers){

    }

}
