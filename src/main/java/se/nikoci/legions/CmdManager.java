package se.nikoci.legions;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.structs.Cmd;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CmdManager implements CommandExecutor {

    @Getter
    private final List<Cmd> cmds = new ArrayList<>();

    public void registerCommand(Cmd command) {
        cmds.add(command); }

    public void unregisterCommand(Cmd command) {
        cmds.remove(command); }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (var cmd : cmds) {
            if (!cmd.name().equalsIgnoreCase(label)) continue;

            if (!cmd.allowConsole() && !(sender instanceof Player)) {
                sender.sendMessage(cmd.name() + " can only be executed by a player!");
                return true;
            }

            cmd.execute(sender, command, label, args);
        }
        return true;
    }
}
