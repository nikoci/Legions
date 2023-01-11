package se.nikoci.legions.structs;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Cmd {

    String name();
    String description();
    default List<CmdValue<?>> values() { return null; }
    default CmdType type() { return CmdType.Player; }
    default List<Permission> permissions(){ return null; }
    default Map<String, Cmd> subcommands(){ return null; }

    boolean execute(@NotNull CommandSender sender, @NotNull List<CmdValue<?>> values);


}
