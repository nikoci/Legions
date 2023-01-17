package se.nikoci.legions.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.structs.Cmd;
import se.nikoci.legions.structs.ParsablePair;

import java.util.List;

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
    public List<ParsablePair<?>> values() {
        return List.of(
                ParsablePair.of("name", ""),
                ParsablePair.of("description", "").setRequired(true).setSingle(true));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull List<ParsablePair<?>> values) {
        sender.sendMessage("legion create command");
        sender.sendMessage(values.toString());
        return true;
    }
}
