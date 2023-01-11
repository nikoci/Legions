package se.nikoci.legions.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.structs.Cmd;
import se.nikoci.legions.structs.CmdValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LegionCommand implements Cmd {
    @Override
    public String name() {
        return "legion";
    }

    @Override
    public String description() {
        return "base legion command";
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull List<CmdValue<?>> values) {
        var player = (Player) sender;
        player.sendMessage("This is the base legion command");
        return true;
    }

    @Override
    public Map<String, Cmd> subcommands(){
        return Map.of("create", new LegionCreateCommand());
    }


}
