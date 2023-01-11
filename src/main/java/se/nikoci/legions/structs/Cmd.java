package se.nikoci.legions.structs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Cmd {

    String name();
    String description();
    default boolean allowConsole() { return false; }
    default List<Permission> permissions(){ return List.of(); }
    default List<Cmd> subcommands(){ return List.of(); }

    void execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
