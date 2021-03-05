package com.minelc.LOBBY.blockcmd;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {

	@EventHandler
	public void onDispatch(PlayerCommandPreprocessEvent e) {
		if(e.getPlayer().hasPermission("bc.bypass")) return;
		List<String> l = ConfigHandler.getInstance().getList();
		
		

		for (String i : l) {
			if (e.getMessage().toLowerCase().contains(i)) {
				
				e.setCancelled(true);
				if(!ConfigHandler.getInstance().err_noPermission().equals("none")){
					e.getPlayer().sendMessage(ConfigHandler.getInstance().getPrefix().replace("&", "§") + " " + ConfigHandler.getInstance().err_noPermission().replace("&", "§"));
				break;
				}
			}
		}
	}

}
