package se.nikoci.legions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.events.LegionCreateEvent;
import se.nikoci.legions.structs.Cmd;
import se.nikoci.legions.structs.Legion;

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
    public void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        new LegionCreateEvent((Player) sender, Legion.builder().name(args[0]).leader((Player) sender).build()).fire();
    }


}
