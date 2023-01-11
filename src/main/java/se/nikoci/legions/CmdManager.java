package se.nikoci.legions;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import se.nikoci.legions.structs.Cmd;
import se.nikoci.legions.structs.CmdType;
import se.nikoci.legions.structs.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CmdManager implements CommandExecutor {

    @Getter
    private final Map<String, Cmd> commands = new HashMap<>();

    private final JavaPlugin plugin;

    public CmdManager(JavaPlugin plugin) {
        this.plugin = plugin; }

    public void registerCommand(Cmd cmd) {
        if (commands.containsKey(cmd.name())) {
            Legions.logger.severe("Cannot register duplicate commands!");
            return;
        }
        var pluginCommand = plugin.getCommand(cmd.name());

        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            commands.put(cmd.name(), cmd);
        } else {
            Legions.logger.severe("Could not register command '"+cmd.name()+"', it is not recognized by the server. Consider defining it in plugin.yml!");
        }
    }

    public void unregisterCommand(Cmd command) {
        commands.remove(command.name(), command); }

    public void unregisterCommand(String name) {
        commands.remove(name); }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var cmdPair = getCommand(addElementStart(args, label), null);
        var cmd = cmdPair.getFirst();
        var cmdArgs = cmdPair.getSecond();

        if (cmd == null) return true;

        if (cmd.permissions() != null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(cmd.name() + " command can only be executed by a Player!");
                return true;
            }

            for (var perm : cmd.permissions()) {
                if (!sender.hasPermission(perm)) {
                    sender.sendMessage("You do not have permission for this command!");
                    return true;
                }
            }
        } else if (cmd.type() == CmdType.Console && (sender instanceof Player)) {
            sender.sendMessage(cmd.name() + " command can only be executed by Console!");
            return true;
        } else  if (cmd.type() == CmdType.Player && !(sender instanceof Player)) {
            sender.sendMessage(cmd.name() + " command can only be executed by a Player!");
            return true;
        }

        //Check CmdValue List
        if (cmd.values() != null) {
            for (var cmdVal : cmd.values()){
                for (var a : removeFirstElement(cmdArgs)) {
                    cmdVal
                }
            }
        }

        return cmd.execute(sender, cmdArgs);
    }

    public Pair<Cmd, String[]> getCommand(String[] args, Cmd cmd){
        var potentialCmd = commands.get(args[0]);
        if (potentialCmd == null && cmd == null) return null;
        if (cmd == null) cmd = potentialCmd;

        for (String arg : args) {
            if (cmd.subcommands() != null && cmd.subcommands().get(arg) != null) {
                return getCommand(removeFirstElement(args), cmd.subcommands().get(arg));
            }
        }

        return new Pair<>(cmd, args);
    }

    public static <T> T[] removeFirstElement(T[] arr) {
        return Arrays.copyOfRange(arr, 1, arr.length);
    }

    public static <T> T[] addElementStart(T[] elements, T element) {
        T[] newArray = Arrays.copyOf(elements, elements.length + 1);
        newArray[0] = element;
        System.arraycopy(elements, 0, newArray, 1, elements.length);
        return newArray;
    }
}
