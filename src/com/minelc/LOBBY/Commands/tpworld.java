
package com.minelc.LOBBY.Commands;

import com.minelc.LOBBY.LobbyMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpworld implements CommandExecutor {
    public tpworld(LobbyMain plugin) {
    }

    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        Player p = (Player)arg0;
        p.teleport(Bukkit.getWorld(arg3[0]).getSpawnLocation());
        return false;
    }
}
