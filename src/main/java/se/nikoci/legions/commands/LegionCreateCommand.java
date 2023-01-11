package se.nikoci.legions.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.structs.Cmd;
import se.nikoci.legions.structs.CmdValue;

import java.util.List;
import java.util.Map;

public class LegionCreateCommand implements Cmd {
    @Override
    public String name() {
        return "create";
    }

    @Override
    public String description() {
        return "create a legion";
    }

    @Override
    public List<CmdValue<?>> values() {
        return List.of(
                CmdValue.of("name", ""),
                CmdValue.of("description", "", false));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull List<CmdValue<?>> values) {
        sender.sendMessage("legion create command");
        sender.sendMessage(values.toString());
        return true;
    }
}
