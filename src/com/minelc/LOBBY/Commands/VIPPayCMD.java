package com.minelc.LOBBY.Commands;

import com.minelc.LOBBY.Controller.LobbyController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VIPPayCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Sólo los usuarios pueden usar esto.");
            return false;
        }

        Player p = (Player) commandSender;

        if (args.length < 2) {
            p.sendMessage(ChatColor.YELLOW + "Envia VIP-Points a alguien conectado usando: /vippay <usuario> <cantidad>");
            return false;
        }

        Player toSend = Bukkit.getPlayer(args[0]);

        if (toSend == null) {
            p.sendMessage(ChatColor.RED + "¡Jugador no encontrado!");
            return true;
        }

        LobbyController.sendVIPPoints(p, toSend, args[1]);
        return true;
    }
}
