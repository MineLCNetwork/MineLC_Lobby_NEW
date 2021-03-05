package com.minelc.LOBBY.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MineLCCmd implements CommandInterface {
    public MineLCCmd() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Comando no valido!");
        return true;
    }
}
