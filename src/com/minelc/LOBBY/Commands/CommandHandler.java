package com.minelc.LOBBY.Commands;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    public static HashMap<String, CommandInterface> commands = new HashMap();

    public CommandHandler() {
    }

    public void register(String name, CommandInterface cmd) {
        commands.put(name.toLowerCase(), cmd);
    }

    public boolean exists(String name) {
        return commands.containsKey(name.toLowerCase());
    }

    public CommandInterface getExecutor(String name) {
        return (CommandInterface)commands.get(name.toLowerCase());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            this.getExecutor("minelc").onCommand(sender, cmd, commandLabel, args);
            return true;
        } else if (args.length > 0) {
            if (this.exists(args[0])) {
                this.getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Error!, This command doesn't exist!");
                return true;
            }
        } else {
            return false;
        }
    }
}
