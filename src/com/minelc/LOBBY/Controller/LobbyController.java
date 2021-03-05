package com.minelc.LOBBY.Controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.minelc.CORE.Utils.Util;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Controller.Ranks;
import com.minelc.CORE.Utils.IconMenu;
import com.minelc.CORE.Utils.ItemUtils;
import com.minelc.LOBBY.LobbyMain;
import com.minelc.LOBBY.Listener.Events;

public class LobbyController{
	private static Scoreboard scoreboard=null;
	private static IconMenu invColorSelector=null;
	private static IconMenu invSelector=null;
	private static IconMenu invVanidad=null;
	private static IconMenu invStats_MAIN=null;

	public static HashMap<String, Team> TEAMS=new HashMap<>();
	private static IconMenu invStats_HG=null;
	private static IconMenu invStats_HG_kills=null;
	private static IconMenu invStats_HG_deaths=null;
	private static IconMenu invStats_HG_part_ganadas=null;
	private static IconMenu invStats_HG_part_jugadas=null;

	private static IconMenu invStats_CHG=null;
	private static IconMenu invStats_CHG_kills=null;
	private static IconMenu invStats_CHG_deaths=null;
	private static IconMenu invStats_CHG_part_ganadas=null;
	private static IconMenu invStats_CHG_part_jugadas=null;

	private static IconMenu invStats_EW=null;
	private static IconMenu invStats_EW_kills=null;
	private static IconMenu invStats_EW_deaths=null;
	private static IconMenu invStats_EW_part_ganadas=null;
	private static IconMenu invStats_EW_part_jugadas=null;
	private static ItemStack Glass;
	private static IconMenu invStats_SW=null;
	private static IconMenu invStats_SW_kills=null;
	private static IconMenu invStats_SW_deaths=null;
	private static IconMenu invStats_SW_part_ganadas=null;
	private static IconMenu invStats_SW_part_jugadas=null;

	private static IconMenu invStats_KITPVP=null;
	private static IconMenu invStats_KITPVP_kills=null;
	private static IconMenu invStats_KITPVP_deaths=null;

	private static IconMenu invStats_POTPVP=null;
	private static IconMenu invStats_POTPVP_kills=null;
	private static IconMenu invStats_POTPVP_deaths=null;

	private static IconMenu invStats_PVPG=null;
	private static IconMenu invStats_PVPG_kills=null;
	private static IconMenu invStats_PVPG_deaths=null;
	private static IconMenu invStats_PVPG_part_ganadas=null;
	private static IconMenu invStats_PVPG_part_jugadas=null;
	private static IconMenu invStats_PVPG_nucleos=null;
	private static IconMenu invStats_PVPG_monumentos=null;
	private static IconMenu invStats_PVPG_lanas=null;

	private static IconMenu invStats_PRAC = null;
	private static IconMenu invStats_PRAC_kills = null;
	private static IconMenu invStats_PRAC_deaths = null;
	private static IconMenu invStats_PRAC_global_elo = null;

	private static ItemUtils rangos;

	private static ItemUtils stats;
	private static IconMenu invRedes=null;
	private static ItemUtils vanidad;

	private static ItemStack PVPGamesIS;


	private static ItemStack tnttag;

	private static ItemStack puentes;

	private static ItemStack buildbattle;

	private static ItemStack facebook;

	private static ItemStack twitter;

	private static ItemStack foro;

	private static ItemStack discord;

	private static Player prueba;
	private static ItemUtils selector;
	static {
		selector=new ItemUtils(Material.COMPASS,Short.valueOf((short)0),Integer.valueOf(1),ChatColor.GREEN+"Selector De Servidores",ChatColor.GRAY+"Click derecho para abrir el selector de servidores");
		rangos=new ItemUtils(Material.ENDER_CHEST,Short.valueOf((short)0),Integer.valueOf(1),ChatColor.GREEN+"Rangos",ChatColor.GRAY+"Click derecho para abrir el mende rangos");
		stats=new ItemUtils(Material.PAPER,Short.valueOf((short)0),Integer.valueOf(1),ChatColor.GREEN+"TOP Jugadores",ChatColor.GRAY+"Click derecho para abrir el mende estadisticas");
		vanidad=new ItemUtils(Material.TRAPPED_CHEST,Short.valueOf((short)0),Integer.valueOf(1),ChatColor.GREEN+"Vanidad",ChatColor.GRAY+"Click derecho para abrir el mende vanidad");
		PVPGamesIS=new ItemStack(Material.WOOL,1,(short)14);
		Glass=new ItemStack(Material.STAINED_GLASS_PANE,1,(short)8);
		facebook=new ItemStack(Material.INK_SACK,1,(short)6);
		twitter=new ItemStack(Material.INK_SACK,1,(short)12);
		discord=new ItemStack(Material.INK_SACK,1,(short)5);
		foro=new ItemStack(Material.INK_SACK,1,(short)8);
		ItemMeta meta=Glass.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY,2,true);
		meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
		List<String> lore=Lists.newArrayList();
		lore.add(ChatColor.YELLOW+"www.minelc.net");
		meta.setLore(lore);
		Glass.setItemMeta(meta);
		tnttag=new ItemStack(Material.TNT,1);
		ItemMeta tagmeta=tnttag.getItemMeta();
		tagmeta.addEnchant(Enchantment.DURABILITY,2,true);
		tagmeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
		tnttag.setItemMeta(tagmeta);
		puentes=new ItemStack(Material.BRICK,1);
		ItemMeta tagpuentes=tnttag.getItemMeta();
		tagpuentes.addEnchant(Enchantment.DURABILITY,2,true);
		tagpuentes.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
		puentes.setItemMeta(tagpuentes);
		buildbattle=new ItemStack(Material.CAKE,1);
		ItemMeta tagbuild=tnttag.getItemMeta();
		tagbuild.addEnchant(Enchantment.DURABILITY,2,true);
		tagbuild.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
		buildbattle.setItemMeta(tagpuentes);
	}


	public static void onQuit(Player p){
		if(LobbyMain.cooldown.containsKey(p.getName())){
			LobbyMain.cooldown.remove(p.getName());
		}
		Jugador.removeJugador(p.getName());

		if(scoreboard.getTeams().contains(scoreboard.getTeam(p.getName())))
			scoreboard.getTeam(p.getName()).unregister();
	}
	public static void updateScoreboardPreGame(){
		Collection<?extends Player> onlineplayers=Bukkit.getOnlinePlayers();
		for(Player Online:onlineplayers){
			Scoreboard sb=Online.getScoreboard();

			if(sb==null){
				sb=Bukkit.getScoreboardManager().getNewScoreboard();
				Online.setScoreboard(sb);

				if(TEAMS.containsKey("LCoins"+Online.getName())){
					TEAMS.remove("LCoins"+Online.getName());
				}
				if(TEAMS.containsKey("rango"+Online.getName())){
					TEAMS.remove("rango"+Online.getName());
				}
				if(TEAMS.containsKey("vipoints"+Online.getName())){
					TEAMS.remove("vipoints"+Online.getName());
				}
				if(TEAMS.containsKey("lcbox"+Online.getName())){
					TEAMS.remove("lcbox"+Online.getName());
				}
			}

			if(!TEAMS.containsKey("LCoins"+Online.getName())){
				Team refill=sb.registerNewTeam(ChatColor.GOLD+" ");
				refill.addEntry(ChatColor.GOLD+" ");
				TEAMS.put("LCoins"+Online.getName(),refill);
			}
			if(!TEAMS.containsKey("rango"+Online.getName())){
				Team comienza=sb.registerNewTeam(ChatColor.RED+" ");
				comienza.addEntry(ChatColor.RED+" ");
				TEAMS.put("rango"+Online.getName(),comienza);
			}
			if(!TEAMS.containsKey("vipoints"+Online.getName())){
				Team jugadores=sb.registerNewTeam(ChatColor.AQUA+" ");
				jugadores.addEntry(ChatColor.AQUA+" ");
				TEAMS.put("vipoints"+Online.getName(),jugadores);
			}
			if(!TEAMS.containsKey("lcbox"+Online.getName())){
				Team jugadores=sb.registerNewTeam(ChatColor.BLUE+" ");
				jugadores.addEntry(ChatColor.BLUE+" ");
				TEAMS.put("lcbox"+Online.getName(),jugadores);
			}
			Objective objGame=sb.getObjective("Lobby");
			Jugador jugOnline=Jugador.getJugador(Online);
			Database.loadPlayerCoins_SYNC(jugOnline);

			if(objGame==null){
				objGame=sb.registerNewObjective("Lobby","dummy");

				objGame.setDisplaySlot(DisplaySlot.SIDEBAR);

				objGame.setDisplayName(formatoScoreboard(LobbyMain.getInstance().getConfig().getString("scoreboard.title", "&6&lMineLC &3&lNetwork"), Online));

				List<String> rows = LobbyMain.getInstance().getConfig().getStringList("scoreboard.content");
				int x = rows.size();

				for (String row : rows) {
					objGame.getScore(formatoScoreboard(row, Online)).setScore(x);
					x--;
				}

				/*
				objGame.getScore(ChatColor.GRAY + strDate).setScore(12);
				objGame.getScore("    ").setScore(11);
				objGame.getScore(ChatColor.translateAlternateColorCodes('&',"Nick: &7"+Online.getName())).setScore(10);
				objGame.getScore("  ").setScore(9);
				objGame.getScore(ChatColor.translateAlternateColorCodes('&',"LCoins:")).setScore(8);
				objGame.getScore(ChatColor.YELLOW +"" +jugOnline.getLcoins()).setScore(7);
				objGame.getScore("        ").setScore(6);
				objGame.getScore(ChatColor.translateAlternateColorCodes('&',"Rango: &b"+jugOnline.getRank())).setScore(5);
				objGame.getScore(ChatColor.translateAlternateColorCodes('&', "Duración: &a" + returnduration(jugOnline))).setScore(4);

				objGame.getScore(ChatColor.translateAlternateColorCodes('&',"Vip-Points: &a"+jugOnline.getRankpoints())).setScore(3);

				objGame.getScore("     ").setScore(2);
				objGame.getScore(ChatColor.translateAlternateColorCodes('&',"&eplay.minelc.net")).setScore(1); */
			}

			TEAMS.get("LCoins"+Online.getName()).setPrefix(ChatColor.GOLD+""+jugOnline.getLcoins());
			TEAMS.get("rango"+Online.getName()).setPrefix(ChatColor.RED+""+jugOnline.getRank());
			TEAMS.get("vipoints"+Online.getName()).setPrefix(ChatColor.AQUA+""+jugOnline.getRankpoints());
			TEAMS.get("lcbox"+Online.getName()).setPrefix(ChatColor.BLUE+""+"0");
			for(Player tmOnline:onlineplayers){
				Jugador jugTM=Jugador.getJugador(tmOnline);

				try{
					Team tm=sb.getTeam(jugTM.getBukkitPlayer().getName());

					if(tm!=null){
						continue;
					}

					tm=sb.registerNewTeam(jugTM.getBukkitPlayer().getName());

					if(jugTM.isHideRank())
						tm.setPrefix(ChatColor.GRAY.toString());
					else if(jugTM.is_Owner())
						tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_Admin())
						tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_MODERADOR())
						tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_AYUDANTE())
						tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_YOUTUBER()){
						String youtuber=ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
						tm.setPrefix(youtuber+jugTM.getNameTagColor());
					} else if(jugTM.is_MiniYT()){
						String miniyt= ChatColor.RED+"" + ChatColor.BOLD + "MiniYT ";
						tm.setPrefix(miniyt+jugTM.getNameTagColor());
					}
					else if(jugTM.is_BUILDER())
						tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+jugTM.getNameTagColor());
					else if(jugTM.is_RUBY())
						tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+jugTM.getNameTagColor());
					else if(jugTM.is_ELITE())
						tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_SVIP())
						tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_VIP())
						tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "
								+jugTM.getNameTagColor());
					else if(jugTM.is_Premium())
						tm.setPrefix(ChatColor.YELLOW.toString());
					else
						tm.setPrefix(ChatColor.GRAY.toString());

					tm.addPlayer(tmOnline);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}

		}
	}

	private static String returnduration(Jugador jugOnline) {
		String duration;
		if(jugOnline.getRank() == Ranks.OWNER ||
				jugOnline.getRank() == Ranks.ADMIN ||
				jugOnline.getRank() == Ranks.MOD ||
				jugOnline.getRank() == Ranks.AYUDANTE ||
				jugOnline.getRank() == Ranks.YOUTUBER ||
				jugOnline.getRank() == Ranks.BUILDER){
			duration = "Permanente";
		} else {
			duration = jugOnline.getRankduration() / 86400000 + " días.";
		}

		return duration;
	}

	public static void onJoin(Player p,Jugador j){
		Jugador jugg= Jugador.getJugador(p);
		jugg.setBukkitPlayer(p);
		//updateScoreboard(p);
		ItemUtils perfil=new ItemUtils(p.getName(),1,ChatColor.GREEN+"Perfil",ChatColor.GRAY+"Click derecho para abrir tu perfil");
		p.getInventory().clear();
		p.getInventory().setItem(4,selector);
		p.getInventory().setItem(5,rangos);
		p.getInventory().setItem(8,stats);
		p.getInventory().setItem(0,perfil);

		//potions
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,Integer.MAX_VALUE,0));
		p.setWalkSpeed(0.3f);

		//welcome old
		/*
		p.sendMessage(ChatColor.GOLD+""+ChatColor.STRIKETHROUGH+"= = = = = = = = = = = = = = = = = = = = = = = =");
		p.sendMessage("");
		p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"                      Website: ");
		p.sendMessage("                      "+ChatColor.AQUA+"http://minelc.com");
		p.sendMessage("");
		p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"                      Tienda: ");
		p.sendMessage("                 "+ChatColor.AQUA+"http://tienda.minelc.com/");
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD+""+ChatColor.STRIKETHROUGH+"= = = = = = = = = = = = = = = = = = = = = = = =");
		 */

		//welcome
		for (String s : LobbyMain.getInstance().getConfig().getStringList("welcome")) {
			p.sendMessage(formatoScoreboard(s, p));
		}
		/*
		p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"================================");
		p.sendMessage("");
		p.sendMessage(ChatColor.YELLOW+"        Bienvenido "+ChatColor.GOLD+p.getName()+ChatColor.YELLOW+" a");
		p.sendMessage("              "+ChatColor.DARK_AQUA+ChatColor.BOLD+"MineLC"+ChatColor.GOLD+ChatColor.BOLD+" Network");
		p.sendMessage("");
		p.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"                  Sitio Web:");
		p.sendMessage("              "+ChatColor.AQUA+"https://www.minelc.net/");
		p.sendMessage("");
		p.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+"                  Discord:");
		p.sendMessage(ChatColor.BLUE+"              discord.minelc.net");
		p.sendMessage("");
		p.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+" Compra los mejores rangos en:");
		p.sendMessage("           "+ChatColor.AQUA+"tienda.minelc.net");
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"================================"); */

		if(!j.isOpciones_SVS_Enable_Players()){
			for(Player Online:Bukkit.getOnlinePlayers()){
				j.getBukkitPlayer().hidePlayer(Online);
			}
		}

		if(j.isOpciones_SVS_Enabled_Minilobby()){
			p.sendMessage(ChatColor.GREEN+"Mini-Lobby (Rapido) esta activado, puedes desactivarlo desde el menu de servidores!");
		}
		/*
		int onlineCount = Bukkit.getOnlinePlayers().size();
		if(onlineCount > 60) {
			for(Player Online : Bukkit.getOnlinePlayers()) {
				j.getBukkitPlayer().hidePlayer(Online);
				if(onlineCount-- < 60) {
					break;
				}
			}
		}
		*/
		try{
			Team tm=null;
			scoreboard=Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj=scoreboard.getObjective("lobby");

			if(obj==null){
				obj=scoreboard.registerNewObjective("lobby","dummy");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);

				obj.setDisplayName(formatoScoreboard(LobbyMain.getInstance().getConfig().getString("scoreboard.title", "&6&lMineLC &3&lNetwork"), p));

				List<String> rows = LobbyMain.getInstance().getConfig().getStringList("scoreboard.content");
				int x = rows.size();

				for (String row : rows) {
					obj.getScore(formatoScoreboard(row, p)).setScore(x);
					x--;
				}
			}

			if(scoreboard.getTeams().contains(scoreboard.getTeam(p.getName()))){
				tm=scoreboard.getTeam(p.getName());
			}else{
				tm=scoreboard.registerNewTeam(p.getName());
			}
			if(j.isHideRank())
				tm.setPrefix(ChatColor.GRAY.toString());
			else if(j.is_Owner())
				tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+j.getNameTagColor());
			else if(j.is_Admin())
				tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+j.getNameTagColor());
			else if(j.is_MODERADOR())
				tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+j.getNameTagColor());
			else if(j.is_AYUDANTE())
				tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+j.getNameTagColor());
			else if(j.is_YOUTUBER()){
				String youtuber=ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
				tm.setPrefix(youtuber+j.getNameTagColor());
			}
			else if(j.is_MiniYT()){
				String miniyt= ChatColor.RED +""+ ChatColor.BOLD + "MiniYT ";
				tm.setPrefix(miniyt+j.getNameTagColor());
			}
			else if(j.is_BUILDER())
				tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+j.getNameTagColor());
			else if(j.is_RUBY())
				tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+j.getNameTagColor());
			else if(j.is_ELITE())
				tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+j.getNameTagColor());
			else if(j.is_SVIP())
				tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+j.getNameTagColor());
			else if(j.is_VIP())
				tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+j.getNameTagColor());
			else if(j.is_Premium())
				tm.setPrefix(ChatColor.YELLOW.toString());
			else
				tm.setPrefix(ChatColor.GRAY.toString());

			tm.addPlayer(p);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		if(!j.isOpciones_SVS_Enable_Players()){
			Scoreboard SBPlayer=Bukkit.getScoreboardManager().getNewScoreboard();
			try{
				Objective obj=SBPlayer.registerNewObjective("lobby","dummy");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				Team tm=SBPlayer.registerNewTeam(p.getName());
				if(j.isHideRank())
					tm.setPrefix(ChatColor.GRAY.toString());
				else if(j.is_Owner())
					tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+j.getNameTagColor());
				else if(j.is_Admin())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+j.getNameTagColor());
				else if(j.is_MODERADOR())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+j.getNameTagColor());
				else if(j.is_AYUDANTE())
					tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+j.getNameTagColor());
				else if(j.is_YOUTUBER()){
					String youtuber=ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
					tm.setPrefix(youtuber+j.getNameTagColor());
				}else if(j.is_MiniYT()){
					String miniyt= ChatColor.RED +""+ ChatColor.BOLD + "MiniYT ";
					tm.setPrefix(miniyt+j.getNameTagColor());
				}
				else if(j.is_BUILDER())
					tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+j.getNameTagColor());
				else if(j.is_RUBY())
					tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+j.getNameTagColor());
				else if(j.is_ELITE())
					tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+j.getNameTagColor());
				else if(j.is_SVIP())
					tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+j.getNameTagColor());
				else if(j.is_VIP())
					tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+j.getNameTagColor());
				else if(j.is_Premium())
					tm.setPrefix(ChatColor.YELLOW.toString());
				else
					tm.setPrefix(ChatColor.GRAY.toString());

				tm.addPlayer(p);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			j.getBukkitPlayer().setScoreboard(SBPlayer);
		}else{
			j.getBukkitPlayer().setScoreboard(scoreboard);
		}
	}

	private static IconMenu getInvStats_HG_kills(){
		if(invStats_HG_kills==null){
			invStats_HG_kills=new IconMenu("TOP Asesinatos - HG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_HG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_HG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_HG_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_HG_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_HG_kills;
	}

	private static IconMenu getInvStats_HG_partidas_ganadas(){
		if(invStats_HG_part_ganadas==null){
			invStats_HG_part_ganadas=new IconMenu("TOP Partidas Ganadas - HG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_HG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_ganadas","SV_HG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_HG_part_ganadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas ganadas"));
			}

			invStats_HG_part_ganadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_HG_part_ganadas;
	}

	private static IconMenu getInvStats_HG_partidas_jugadas(){
		if(invStats_HG_part_jugadas==null){
			invStats_HG_part_jugadas=new IconMenu("TOP Partidas Jugadas - HG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_HG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_jugadas","SV_HG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_HG_part_jugadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas jugadas"));
			}

			invStats_HG_part_jugadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_HG_part_jugadas;
	}

	private static IconMenu getInvStats_HG_deaths(){
		if(invStats_HG_deaths==null){
			invStats_HG_deaths=new IconMenu("TOP Muertes - HG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_HG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_HG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_HG_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_HG_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_HG_deaths;
	}

	private static IconMenu getInvStats_HG(){
		if(invStats_HG==null){
			invStats_HG=new IconMenu("TOP Jugadores - HG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_HG_kills().open(e.getPlayer());break;
						case 12:getInvStats_HG_partidas_ganadas().open(e.getPlayer());break;
						case 14:getInvStats_HG_partidas_jugadas().open(e.getPlayer());break;
						case 16:getInvStats_HG_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_HG.setOption(10,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s asesinatos");

			invStats_HG.setOption(12,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Ganadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s partidas ganadas");

			invStats_HG.setOption(14,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Jugadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s partidas jugadas");

			invStats_HG.setOption(16,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s muertes");

			invStats_HG.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_HG;
	}

	private static IconMenu getInvStats_CHG_kills(){
		if(invStats_CHG_kills==null){
			invStats_CHG_kills=new IconMenu("TOP Asesinatos - CHG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_CHG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_CHG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_CHG_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_CHG_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_CHG_kills;
	}

	private static IconMenu getInvStats_CHG_partidas_ganadas(){
		if(invStats_CHG_part_ganadas==null){
			invStats_CHG_part_ganadas=new IconMenu("TOP Partidas Ganadas - CHG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_CHG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_ganadas","SV_CHG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_CHG_part_ganadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas ganadas"));
			}

			invStats_CHG_part_ganadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_CHG_part_ganadas;
	}

	private static IconMenu getInvStats_CHG_partidas_jugadas(){
		if(invStats_CHG_part_jugadas==null){
			invStats_CHG_part_jugadas=new IconMenu("TOP Partidas Jugadas - CHG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_CHG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_jugadas","SV_CHG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_CHG_part_jugadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas jugadas"));
			}

			invStats_CHG_part_jugadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_CHG_part_jugadas;
	}

	private static IconMenu getInvStats_CHG_deaths(){
		if(invStats_CHG_deaths==null){
			invStats_CHG_deaths=new IconMenu("TOP Muertes - CHG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_CHG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_CHG");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_CHG_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_CHG_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_CHG_deaths;
	}

	private static IconMenu getInvStats_CHG(){
		if(invStats_CHG==null){
			invStats_CHG=new IconMenu("TOP Jugadores - CHG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_CHG_kills().open(e.getPlayer());break;
						case 12:getInvStats_CHG_partidas_ganadas().open(e.getPlayer());break;
						case 14:getInvStats_CHG_partidas_jugadas().open(e.getPlayer());break;
						case 16:getInvStats_CHG_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_CHG.setOption(10,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s asesinatos");

			invStats_CHG.setOption(12,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Ganadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s partidas ganadas");

			invStats_CHG.setOption(14,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Jugadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s partidas jugadas");

			invStats_CHG.setOption(16,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"m�s muertes");

			invStats_CHG.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_CHG;
	}

	private static IconMenu getInvStats_SW_kills(){
		if(invStats_SW_kills==null){
			invStats_SW_kills=new IconMenu("TOP Asesinatos - SW",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_SW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_SKYWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_SW_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_SW_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_SW_kills;
	}

	private static IconMenu getInvStats_SW_partidas_ganadas(){
		if(invStats_SW_part_ganadas==null){
			invStats_SW_part_ganadas=new IconMenu("TOP Partidas Ganadas - SW",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_SW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_ganadas","SV_SKYWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_SW_part_ganadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas ganadas"));
			}

			invStats_SW_part_ganadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_SW_part_ganadas;
	}

	private static IconMenu getInvStats_SW_partidas_jugadas(){
		if(invStats_SW_part_jugadas==null){
			invStats_SW_part_jugadas=new IconMenu("TOP Partidas Jugadas - SW",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_SW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_jugadas","SV_SKYWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_SW_part_jugadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas jugadas"));
			}

			invStats_SW_part_jugadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_SW_part_jugadas;
	}

	private static IconMenu getInvStats_SW_deaths(){
		if(invStats_SW_deaths==null){
			invStats_SW_deaths=new IconMenu("TOP Muertes - SW",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_SW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_SKYWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_SW_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_SW_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_SW_deaths;
	}

	private static IconMenu getInvStats_SW(){
		if(invStats_SW==null){
			invStats_SW=new IconMenu("TOP Jugadores - SW",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_SW_kills().open(e.getPlayer());break;
						case 12:getInvStats_SW_partidas_ganadas().open(e.getPlayer());break;
						case 14:getInvStats_SW_partidas_jugadas().open(e.getPlayer());break;
						case 16:getInvStats_SW_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_SW.setOption(10,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas asesinatos");

			invStats_SW.setOption(12,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Ganadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas ganadas");

			invStats_SW.setOption(14,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Jugadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas jugadas");

			invStats_SW.setOption(16,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas muertes");

			invStats_SW.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_SW;
	}

	private static IconMenu getInvStats_KITPVP_kills(){
		if(invStats_KITPVP_kills==null){
			invStats_KITPVP_kills=new IconMenu("TOP Asesinatos - KITPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_KITPVP().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_KITPVP");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_KITPVP_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_KITPVP_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_KITPVP_kills;
	}

	private static IconMenu getInvStats_KITPVP_deaths(){
		if(invStats_KITPVP_deaths==null){
			invStats_KITPVP_deaths=new IconMenu("TOP Muertes - KITPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_KITPVP().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_KITPVP");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_KITPVP_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_KITPVP_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_KITPVP_deaths;
	}

	private static IconMenu getInvStats_POTPVP_kills(){
		if(invStats_POTPVP_kills==null){
			invStats_POTPVP_kills=new IconMenu("TOP Asesinatos - PotPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_POTPVP().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_POTPVP");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_POTPVP_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_POTPVP_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_POTPVP_kills;
	}

	private static IconMenu getInvStats_POTPVP_deaths(){
		if(invStats_POTPVP_deaths==null){
			invStats_POTPVP_deaths=new IconMenu("TOP Muertes - PotPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_POTPVP().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_POTPVP");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_POTPVP_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_POTPVP_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_POTPVP_deaths;
	}

	private static IconMenu getInvStats_POTPVP(){
		if(invStats_POTPVP==null){
			invStats_POTPVP=new IconMenu("TOP Jugadores - PotPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 11:getInvStats_POTPVP_kills().open(e.getPlayer());break;
						case 15:getInvStats_POTPVP_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_POTPVP.setOption(11,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas asesinatos");

			invStats_POTPVP.setOption(15,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas muertes");

			invStats_POTPVP.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_POTPVP;
	}

	private static IconMenu getInvStats_KITPVP(){
		if(invStats_KITPVP==null){
			invStats_KITPVP=new IconMenu("TOP Jugadores - KITPVP",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 11:getInvStats_KITPVP_kills().open(e.getPlayer());break;
						case 15:getInvStats_KITPVP_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_KITPVP.setOption(11,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas asesinatos");

			invStats_KITPVP.setOption(15,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas muertes");

			invStats_KITPVP.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_KITPVP;
	}

	private static IconMenu getInvStats_PVPG_kills(){
		if(invStats_PVPG_kills==null){
			invStats_PVPG_kills=new IconMenu("TOP Asesinatos - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_PVPG_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_kills;
	}

	private static IconMenu getInvStats_PVPG_partidas_ganadas(){
		if(invStats_PVPG_part_ganadas==null){
			invStats_PVPG_part_ganadas=new IconMenu("TOP Partidas Ganadas - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_ganadas","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_part_ganadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas ganadas"));
			}

			invStats_PVPG_part_ganadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_part_ganadas;
	}

	private static IconMenu getInvStats_PVPG_partidas_jugadas(){
		if(invStats_PVPG_part_jugadas==null){
			invStats_PVPG_part_jugadas=new IconMenu("TOP Partidas Jugadas - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_jugadas","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_part_jugadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas jugadas"));
			}

			invStats_PVPG_part_jugadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_part_jugadas;
	}

	private static IconMenu getInvStats_PVPG_deaths(){
		if(invStats_PVPG_deaths==null){
			invStats_PVPG_deaths=new IconMenu("TOP Muertes - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_PVPG_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_deaths;
	}

	private static IconMenu getInvStats_PVPG_Monumentos(){
		if(invStats_PVPG_monumentos==null){
			invStats_PVPG_monumentos=new IconMenu("TOP Monumentos - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_monuments_destroyed","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_monumentos.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" monumentos"));
			}

			invStats_PVPG_monumentos.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_monumentos;
	}

	private static IconMenu getInvStats_PVPG_Nucleos(){
		if(invStats_PVPG_nucleos==null){
			invStats_PVPG_nucleos=new IconMenu("TOP Nucleos - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_cores_leakeds","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_nucleos.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" Nucleos"));
			}

			invStats_PVPG_nucleos.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_nucleos;
	}

	private static IconMenu getInvStats_PVPG_Lanas(){
		if(invStats_PVPG_lanas==null){
			invStats_PVPG_lanas=new IconMenu("TOP Lanas - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_PVPG().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_wools_placed","SV_PVPGAMES");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_PVPG_lanas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" Lanas"));
			}

			invStats_PVPG_lanas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG_lanas;
	}

	private static IconMenu getInvStats_PVPG(){
		if(invStats_PVPG==null){
			invStats_PVPG=new IconMenu("TOP Jugadores - PVPG",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_PVPG_kills().open(e.getPlayer());break;
						case 11:getInvStats_PVPG_partidas_ganadas().open(e.getPlayer());break;
						case 12:getInvStats_PVPG_partidas_jugadas().open(e.getPlayer());break;
						case 13:getInvStats_PVPG_deaths().open(e.getPlayer());break;
						case 14:getInvStats_PVPG_Monumentos().open(e.getPlayer());break;
						case 15:getInvStats_PVPG_Nucleos().open(e.getPlayer());break;
						case 16:getInvStats_PVPG_Lanas().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

				}
			},LobbyMain.getInstance());

			invStats_PVPG.setOption(10,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas asesinatos");

			invStats_PVPG.setOption(11,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Ganadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas ganadas");

			invStats_PVPG.setOption(12,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Jugadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas jugadas");

			invStats_PVPG.setOption(13,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas muertes");

			invStats_PVPG.setOption(14,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Monumentos Destruidos  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas monumentos destruidos");

			invStats_PVPG.setOption(15,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Nucleos Derramados ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas nucleos derramados");

			invStats_PVPG.setOption(16,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Lanas  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas lanas colocadas");

			invStats_PVPG.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_PVPG;
	}

	// Asesinatos
	private static IconMenu getInvStats_PRAC_kills() {
		if (invStats_PRAC_kills == null) {
			invStats_PRAC_kills = new IconMenu("TOP Asesinatos - Practice", 45, new IconMenu.OptionClickEventHandler() {
				public void onOptionClick(IconMenu.OptionClickEvent e) {
					e.setWillClose(false);
					e.setWillDestroy(false);
					if (e.getPosition() == 31) {
						getInvStats_PRAC().open(e.getPlayer());
					}

				}
			}, LobbyMain.getInstance());
			LinkedHashMap<String, Integer> top = Database.getTop(18, "username", "kills", "stats");
			int slot = 0;
			Iterator var3 = top.entrySet().iterator();

			while(var3.hasNext()) {
				Map.Entry<String, Integer> es = (Map.Entry)var3.next();
				invStats_PRAC_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
			}

			invStats_PRAC_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
		}

		return invStats_PRAC_kills;
	}

	private static IconMenu invStats_PRAC_global_elo() {
		if (invStats_PRAC_global_elo == null) {
			invStats_PRAC_global_elo = new IconMenu("TOP ELO global - Practice", 45, new IconMenu.OptionClickEventHandler() {
				public void onOptionClick(IconMenu.OptionClickEvent e) {
					e.setWillClose(false);
					e.setWillDestroy(false);
					if (e.getPosition() == 31) {
						getInvStats_PRAC().open(e.getPlayer());
					}

				}
			}, LobbyMain.getInstance());
			LinkedHashMap<String, Integer> top = Database.getTop(18, "username", "global_elo", "stats");
			int slot = 0;
			Iterator var3 = top.entrySet().iterator();

			while(var3.hasNext()) {
				Map.Entry<String, Integer> es = (Map.Entry)var3.next();
				invStats_PRAC_global_elo.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(),  ChatColor.GRAY + "ELO " + es.getValue()));
			}

			invStats_PRAC_global_elo.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
		}

		return invStats_PRAC_global_elo;
	}

	private static IconMenu getInvStats_PRAC_deaths() {
		if (invStats_PRAC_deaths == null) {
			invStats_PRAC_deaths = new IconMenu("TOP Muertes - Practice", 45, new IconMenu.OptionClickEventHandler() {
				public void onOptionClick(IconMenu.OptionClickEvent e) {
					e.setWillClose(false);
					e.setWillDestroy(false);
					if (e.getPosition() == 31) {
						getInvStats_PRAC().open(e.getPlayer());
					}

				}
			}, LobbyMain.getInstance());
			LinkedHashMap<String, Integer> top = Database.getTop(18, "username", "deaths", "stats");
			int slot = 0;
			Iterator var3 = top.entrySet().iterator();

			while(var3.hasNext()) {
				Map.Entry<String, Integer> es = (Map.Entry)var3.next();
				invStats_PRAC_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
			}

			invStats_PRAC_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
		}

		return invStats_PRAC_deaths;
	}

	public static IconMenu getInvStats_PRAC() {
		if (invStats_PRAC == null) {
			invStats_PRAC = new IconMenu("TOP Jugadores - Practice", 45, new IconMenu.OptionClickEventHandler() {
				public void onOptionClick(IconMenu.OptionClickEvent e) {
					e.setWillDestroy(false);
					switch(e.getPosition()) {
						case 10:
							e.setWillClose(false);
							getInvStats_PRAC_kills().open(e.getPlayer());
							break;
						case 12:
							e.setWillClose(false);
							getInvStats_PRAC_deaths().open(e.getPlayer());
							// LobbyController.getInvStats_EW_partidas_ganadas().open(e.getPlayer());
							break;
						case 14:
							e.setWillClose(false);
							invStats_PRAC_global_elo().open(e.getPlayer());
							break;
						case 16:
							e.setWillClose(true);
							e.getPlayer().sendMessage(ChatColor.YELLOW + "Ingresa a la modalidad para ver más estadísticas.");
							break;
						case 31:
							e.setWillClose(false);
							getInvStats_MAIN().open(e.getPlayer());
							// LobbyController.getInvStats_MAIN().open(e.getPlayer());
					}

				}
			}, LobbyMain.getInstance());
			invStats_PRAC.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos", ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "más asesinatos");
			invStats_PRAC.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "más muertes"});
			invStats_PRAC.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "ELO global", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "los mejores ELO globales"});
			invStats_PRAC.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.BLUE + ChatColor.BOLD + "Otras estadísticas", new String[]{ChatColor.GRAY + "Click para mostrar estadísticas", ChatColor.GRAY + "mas avanzadas"});
			invStats_PRAC.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar");
		}

		return invStats_PRAC;
	}

	public static IconMenu getInvStats_MAIN(){
		if(invStats_MAIN==null){
			invStats_MAIN=new IconMenu("TOP Jugadores",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_HG().open(e.getPlayer());break;
						case 12:getInvStats_SW().open(e.getPlayer());break;
						case 14:getInvStats_PVPG().open(e.getPlayer());break;
						case 16:getInvStats_KITPVP().open(e.getPlayer());break;
						case 28:getInvStats_PRAC().open(e.getPlayer());break;
						case 30:getInvStats_EW().open(e.getPlayer());break;
						case 32:getInvStats_CHG().open(e.getPlayer());break;
						default:break;
					}
				}
			},LobbyMain.getInstance());

			invStats_MAIN.setOption(10,new ItemStack(Material.BOW),ChatColor.GREEN+""+ChatColor.BOLD+"Hunger Games   ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(12,new ItemStack(Material.FISHING_ROD),ChatColor.GREEN+""+ChatColor.BOLD+"SkyWars   ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(14,new ItemStack(Material.IRON_CHESTPLATE),ChatColor.GREEN+""+ChatColor.BOLD+"PVPGames "," ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(16,new ItemStack(Material.IRON_SWORD),ChatColor.GREEN+""+ChatColor.BOLD+"KitPVP   ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(28,new ItemStack(Material.ANVIL),ChatColor.GREEN+""+ChatColor.BOLD+"Practice   ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(30,new ItemStack(Material.DRAGON_EGG),ChatColor.GREEN+""+ChatColor.BOLD+"EggWars   ",
					ChatColor.GRAY+"Click para abrir el menu");

			invStats_MAIN.setOption(32,new ItemStack(Material.BOW),ChatColor.GREEN+""+ChatColor.BOLD+"Hunger Games Clasicos   ",
					ChatColor.GRAY+"Click para abrir el menu");
		}
		return invStats_MAIN;
	}

	private static IconMenu getInvStats_EW_kills(){
		if(invStats_EW_kills==null){
			invStats_EW_kills=new IconMenu("TOP Asesinatos - EggWars",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_EW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_kills","SV_BEDWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_EW_kills.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" asesinatos"));
			}

			invStats_EW_kills.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_EW_kills;
	}

	private static IconMenu getInvStats_EW_partidas_ganadas(){
		if(invStats_EW_part_ganadas==null){
			invStats_EW_part_ganadas=new IconMenu("TOP Partidas Ganadas - EggWars",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_EW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_ganadas","SV_BEDWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_EW_part_ganadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas ganadas"));
			}

			invStats_EW_part_ganadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_EW_part_ganadas;
	}

	private static IconMenu getInvStats_EW_partidas_jugadas(){
		if(invStats_EW_part_jugadas==null){
			invStats_EW_part_jugadas=new IconMenu("TOP Partidas Jugadas - EggWars",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_EW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_partidas_jugadas","SV_BEDWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_EW_part_jugadas.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" partidas jugadas"));
			}

			invStats_EW_part_jugadas.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_EW_part_jugadas;
	}

	private static IconMenu getInvStats_EW_deaths(){
		if(invStats_EW_deaths==null){
			invStats_EW_deaths=new IconMenu("TOP Muertes - EggWars",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					if(e.getPosition()==31){
						getInvStats_EW().open(e.getPlayer());
					}
				}
			},LobbyMain.getInstance());

			LinkedHashMap<String,Integer>top=Database.getTop(18,"stats_deaths","SV_BEDWARS");
			int slot=0;
			for(Entry<String,Integer>es:top.entrySet()){
				invStats_EW_deaths.setOption(slot++,new ItemUtils(es.getKey(),1,ChatColor.GOLD+""+ChatColor.BOLD+"#"+slot+ChatColor.DARK_GRAY+" - "+ChatColor.RED+es.getKey(),""+ChatColor.GRAY+es.getValue()+" muertes"));
			}

			invStats_EW_deaths.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_EW_deaths;
	}

	private static IconMenu getInvStats_EW(){
		if(invStats_EW==null){
			invStats_EW=new IconMenu("TOP Jugadores - EggWars",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);

					switch(e.getPosition()){
						case 10:getInvStats_EW_kills().open(e.getPlayer());break;
						case 12:getInvStats_EW_partidas_ganadas().open(e.getPlayer());break;
						case 14:getInvStats_EW_partidas_jugadas().open(e.getPlayer());break;
						case 16:getInvStats_EW_deaths().open(e.getPlayer());break;
						case 31:getInvStats_MAIN().open(e.getPlayer());
						default:break;
					}

					//getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			},LobbyMain.getInstance());

			invStats_EW.setOption(10,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Asesinatos   ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas asesinatos");

			invStats_EW.setOption(12,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Ganadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas ganadas");

			invStats_EW.setOption(14,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Partidas Jugadas",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas partidas jugadas");

			invStats_EW.setOption(16,new ItemStack(Material.SIGN),ChatColor.GREEN+""+ChatColor.BOLD+"Muertes  ",
					ChatColor.GRAY+"Click para mostrar a los usuarios con",ChatColor.GRAY+"mas muertes");

			invStats_EW.setOption(31,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invStats_EW;
	}

	public static IconMenu getInvSelector(){
		if(invSelector==null){
			ItemStack glassBlue = new ItemStack(Material.getMaterial(160),1,(short) 9);
			ItemStack glassWhite = new ItemStack(Material.getMaterial(160),1,(short) 0);
			invSelector=new IconMenu("Menu De Servidores",54,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);


					switch(e.getPosition()){
						case 13:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"survival","survival");
							break;
						case 18:
							/* minilobby*/  LobbyController.changeMiniLobby(e.getPlayer());
							break;
						case 20:
							/* creativo */ LobbyMain.sendPlayerToServer(e.getPlayer(), "creativo", "creativo");
							break;

						case 24:
							/*skywars */ MobsController.getMob("mob10").openInventory(e.getPlayer());
							break;
						case 26:
							/* tienda */
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fCompra Los mejores rangos y a un bajo"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fprecio, obten nuevos kits, coins y más"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&etienda.minelc.net"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							break;
						case 27:
							/* selector de lobby*/  MobsController.getAll().get("lobbies").openInventory(e.getPlayer());
							break;
						case 29:
							/* eggwars */ LobbyMain.sendPlayerToServer(e.getPlayer(),"lobbyew","lobbyew");
							break;
						case 31:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"kitpvp","kitpvp");
							break;
						case 33:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"practice","practice");
							break;
						case 35:
							redesSociales().open(e.getPlayer());
							break;
						case 38:
							/* chg*/ MobsController.getMob("mob8").openInventory(e.getPlayer());
							break;
						case 40:
							/* pvgm */ MobsController.getMob("mob7").openInventory(e.getPlayer());
							break;
					}
				}
			},LobbyMain.getInstance())

					.setOption(13,new ItemStack(Material.DIAMOND_PICKAXE,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aSurvival&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Un survival con parcelas, economia ,",ChatColor.WHITE+"Mundo PvP, Tiendas, Eventos y",ChatColor.WHITE+"muchas otras cosas para asegurar tu diversion!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("survival")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"})
					.setOption(18,new ItemStack(Material.FIREWORK_CHARGE,1),""+ChatColor.GREEN+ChatColor.BOLD+"MiniLobby",new String[]{"",ChatColor.YELLOW+"Click para activar o desactivar la minilobby"})
					.setOption(20,new ItemStack(Material.STAINED_GLASS,1,(short)3),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aCreativo&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Aqui puedes demostrar tus habilidades",ChatColor.WHITE+"como constructor o solo construir por diversión","",ChatColor.GOLD+"Hay "+GetPlayersInServer("creativo")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"})
					.setOption(24,new ItemStack(Material.BOW,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aSkyWars&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Apareceras en una isla. Para conseguir",ChatColor.WHITE+"la victoria sobrevive y ataca a los",ChatColor.WHITE+"enemigos de las otras islas!",ChatColor.WHITE+"Recibe una recompenza",ChatColor.WHITE+"al cumplir los retos!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("sw")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"})
					.setOption(38,new ItemStack(Material.PORK,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aHG Clasicos&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Juegos del hambre clásicos con kits. Para",ChatColor.WHITE+"conseguir la victoria tienes que",ChatColor.WHITE+"eliminar a los demas jugadores!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("chg")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menú de arenas!"})
					.setOption(26,new ItemStack(Material.BLAZE_ROD,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aTienda&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"¿Quieres ganar más coins y obtener nuevos kits?",ChatColor.WHITE+"Visita nuestra tienda y compra rangos al mejor precio!!",ChatColor.YELLOW+"tienda.minelc.net"})

					.setOption(27,new ItemStack(Material.ENCHANTED_BOOK,1),""+ChatColor.GREEN+ChatColor.BOLD+"Selector De Lobby",new String[]{"",ChatColor.YELLOW+"Click para abrir el selector de lobby"})
					.setOption(29,new ItemStack(Material.DRAGON_EGG,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aEggWars&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Destruye los huevos enemigos para",ChatColor.WHITE+"ganar, pero no olvides proteger el tuyo",ChatColor.WHITE+"para poder reaparecer si mueres!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("ew")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"})
					.setOption(31,new ItemStack(Material.IRON_SWORD,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aKitPvP&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Selecciona un kit y demustra tus",ChatColor.WHITE+"habilidades asesinando a los demás",ChatColor.WHITE+"jugadores!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("kitpvp")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"})
					.setOption(33,new ItemStack(Material.ANVIL,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aPractice&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Crea tu kit y demustra tus",ChatColor.WHITE+"habilidades en pvp a travez de",ChatColor.WHITE+"duelos!","",ChatColor.GOLD+"Hay "+countPlayersInServer("practice")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"})
					.setOption(35,new ItemStack(Material.BEACON,1),""+ChatColor.GREEN+ChatColor.BOLD+"Redes Sociales",new String[]{"",ChatColor.YELLOW+"Click para ver nuestras redes sociales!"})

					.setOption(42,new ItemStack(Material.GRASS,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&ASkyBlock&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.RED+"Modalidad Beta ",ChatColor.GRAY+"Aún no disponible."})
					.setOption(40,new ItemStack(Material.CAKE,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aPvPGames&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Entra y disfruta de varios",ChatColor.WHITE+"minijuegos, juega en modo",ChatColor.YELLOW+"coperativo y lucha con tu equipo!","",ChatColor.GRAY+"Hay "+GetPlayersInServer("pvpg")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"})



					.setOption(0,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(1,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(7,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(8,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(9,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(17,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(36,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(44,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(45,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(46,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(52,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(53,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})

					.setOption(2,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(3,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(4,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(5,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(6,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})

					.setOption(47,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(48,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(49,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(50,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"})
					.setOption(51,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});


		}
		return invSelector;
	}

	private static void changeMiniLobby(Player p){
		Jugador jug=Jugador.getJugador(p);
		jug.setOpciones_SVS_Enabled_Minilobby(!jug.isOpciones_SVS_Enabled_Minilobby());
		Database.savePlayerOpciones_SVS(jug);
		if(jug.isOpciones_SVS_Enabled_Minilobby()){
			p.teleport(LobbyMain.minilobby);
		} else {
			p.teleport(LobbyMain.spawnLocation);
		}
	}

	public static IconMenu updateInvSelector(){
		if(invSelector==null){
			invSelector=new IconMenu(ChatColor.translateAlternateColorCodes('&',"&a&lServidores"),54,new IconMenu.OptionClickEventHandler(){
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					switch(e.getPosition()){
						case 13:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"survival","survival");
							break;
						case 18:
							/* minilobby*/  LobbyController.changeMiniLobby(e.getPlayer());
							break;
						case 20:
							/* creativo */ LobbyMain.sendPlayerToServer(e.getPlayer(), "creativo", "creativo");
							break;

						case 24:
							/*skywars */ MobsController.getMob("mob10").openInventory(e.getPlayer());
							break;
						case 26:
							/* tienda */
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fCompra Los mejores rangos y a un bajo"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fprecio, obten nuevos kits, coins y más"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&etienda.minelc.net"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							break;
						case 27:
							/* selector de lobby*/  MobsController.getAll().get("lobbies").openInventory(e.getPlayer());
							break;
						case 29:
							/* eggwars */ LobbyMain.sendPlayerToServer(e.getPlayer(),"lobbyew","lobbyew");
							break;
						case 31:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"kitpvp","kitpvp");
							break;
						case 33:
							LobbyMain.sendPlayerToServer(e.getPlayer(),"practice","practice");
							break;
						case 35:
							redesSociales().open(e.getPlayer());
							break;
						case 38:
							/* chg*/ MobsController.getMob("mob8").openInventory(e.getPlayer());
							break;
						case 40:
							/* pvgm */ MobsController.getMob("mob7").openInventory(e.getPlayer());
							break;


					}

				}
			},LobbyMain.getInstance());
		}

		invSelector.setOption(13,new ItemStack(Material.DIAMOND_PICKAXE,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aSurvival&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Un survival con parcelas, economia ,",ChatColor.WHITE+"Mundo PvP, Tiendas, Eventos y",ChatColor.WHITE+"muchas otras cosas para asegurar tu diversion!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("survival")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"});
		invSelector.setOption(18,new ItemStack(Material.FIREWORK_CHARGE,1),""+ChatColor.GREEN+ChatColor.BOLD+"MiniLobby",new String[]{"",ChatColor.YELLOW+"Click para activar o desactivar la minilobby"});
		invSelector.setOption(20,new ItemStack(Material.STAINED_GLASS,1,(short)3),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aCreativo&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Aqui puedes demostrar tus habilidades",ChatColor.WHITE+"como constructor o solo construir por diversión","",ChatColor.GOLD+"Hay "+GetPlayersInServer("creativo")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"});
		invSelector.setOption(24,new ItemStack(Material.BOW,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aSkyWars&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Apareceras en una isla. Para conseguir",ChatColor.WHITE+"la victoria sobrevive y ataca a los",ChatColor.WHITE+"enemigos de las otras islas!",ChatColor.WHITE+"Recibe una recompenza",ChatColor.WHITE+"al cumplir los retos!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("sw")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"});
		invSelector.setOption(38,new ItemStack(Material.PORK,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aHG Clasicos&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Juegos del hambre clásicos con kits. Para",ChatColor.WHITE+"conseguir la victoria tienes que",ChatColor.WHITE+"eliminar a los demas jugadores!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("chg")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menú de arenas!"});
		invSelector.setOption(26,new ItemStack(Material.BLAZE_ROD,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aTienda&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"¿Quieres ganar más coins y obtener nuevos kits?",ChatColor.WHITE+"Visita nuestra tienda y compra rangos al mejor precio!!",ChatColor.YELLOW+"tienda.minelc.net"});

		invSelector.setOption(27,new ItemStack(Material.ENCHANTED_BOOK,1),""+ChatColor.GREEN+ChatColor.BOLD+"Selector De Lobby",new String[]{"",ChatColor.YELLOW+"Click para abrir el selector de lobby"});
		invSelector.setOption(29,new ItemStack(Material.DRAGON_EGG,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aEggWars&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Destruye los huevos enemigos para",ChatColor.WHITE+"ganar, pero no olvides proteger el tuyo",ChatColor.WHITE+"para poder reaparecer si mueres!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("ew")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"});
		invSelector.setOption(31,new ItemStack(Material.IRON_SWORD,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aKitPvP&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Selecciona un kit y demustra tus",ChatColor.WHITE+"habilidades asesinando a los demás",ChatColor.WHITE+"jugadores!","",ChatColor.GOLD+"Hay "+GetPlayersInServer("kitpvp")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"});
		invSelector.setOption(33,new ItemStack(Material.ANVIL,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aPractice&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Crea tu kit y demustra tus",ChatColor.WHITE+"habilidades en pvp a travez de",ChatColor.WHITE+"duelos!","",ChatColor.GOLD+"Hay "+countPlayersInServer("practice")+" jugadores!","",ChatColor.YELLOW+"Click para abrir el menu de arenas!"});
		invSelector.setOption(35,new ItemStack(Material.BEACON,1),""+ChatColor.GREEN+ChatColor.BOLD+"Redes Sociales",new String[]{"",ChatColor.YELLOW+"Click para ver nuestras redes sociales!"});

		invSelector.setOption(42,new ItemStack(Material.GRASS,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&ASkyBlock&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.RED+"Modalidad Beta ",ChatColor.GRAY+"Aún no disponible."});
		invSelector.setOption(40,new ItemStack(Material.CAKE,1),""+ChatColor.translateAlternateColorCodes('&',"&6&kii&aPvPGames&6&kii"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Entra y disfruta de varios",ChatColor.WHITE+"minijuegos, juega en modo",ChatColor.YELLOW+"coperativo y lucha con tu equipo!","",ChatColor.GRAY+"Hay "+GetPlayersInServer("pvpg")+" jugadores!","",ChatColor.YELLOW+"Click para ingresar!"});


		ItemStack glassBlue = new ItemStack(Material.getMaterial(160),1,(short) 9);
		ItemStack glassWhite = new ItemStack(Material.getMaterial(160),1,(short) 0);
		invSelector.setOption(0,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(1,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(7,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(8,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(9,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(17,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(36,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(44,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(45,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(46,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(52,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(53,glassBlue,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});

		invSelector.setOption(2,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(3,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(4,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(5,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(6,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});

		invSelector.setOption(47,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(48,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(49,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(50,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});
		invSelector.setOption(51,glassWhite,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{ChatColor.YELLOW+"www.minelc.net"});


		return invSelector;
	}


	public static int GetPlayersInServer(String svname){
		int count=0;
		Iterator var3=ServersController.getAllServers().iterator();

		while(var3.hasNext()){
			ServersController svs=(ServersController)var3.next();

			try{
				if(svs.getName().toLowerCase().startsWith(svname)){
					count+=svs.getCurrentPlayers();
				}
			}catch(Exception var5){
				var5.printStackTrace();
			}
		}

		return count;
	}
	public static IconMenu redesSociales(){
		if(invRedes==null){
			invRedes=new IconMenu(ChatColor.BOLD+""+ChatColor.GREEN+"Redes Sociales",54,new IconMenu.OptionClickEventHandler(){
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					switch(e.getPosition()){
                   /* case 11:
             puentes  ((MobsController)MobsController.getAll().get("mob6")).openInventory(e.getPlayer());
                        break; */
						case 20:
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fComunicate con nosotros vía Facebook"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fEnterate de las novedades, información y publicaciones"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&frelacionadas con el servidor en:"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&Esto estará muy pronto."));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));

							break;
						case 22:
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fSiguenos en nuestro twitter oficial"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fParticipa de sorteos, eventos y"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fenterate de nuevas modalidades"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fsiguenos en @MineLCNetwork"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));

							break;
						case 24:
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fForma parte de nuestro servidor de discord"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fpuedes hablar con la comunidad, realizar reportes"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fescuchar música y jugar en llamada!!"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&ediscord.minelc.net"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));

							break;
						case 31:
							e.getPlayer().closeInventory();
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fVisita nuestro foro oficial"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fReporta usuarios ilegales, comparte tus ideas"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&fe informate de las novedades del servidor en:"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&ewww.minelc.net"));
							e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&m----------------------------------"));

							break;

					}

				}
			},LobbyMain.getInstance());
		}

		// invSelector.setOption(11, new ItemStack(Material.BRICK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Los Puentes", new String[]{"", ChatColor.GRAY + "Equipos de 1vs1, 2vs2 y 4vs4.", ChatColor.GRAY + "Para ganar debes tirarte en el portal enemigo ", ChatColor.GRAY + "pero cuidado, no olvides proteger el tuyo!", "", ChatColor.BOLD +"" +ChatColor.DARK_RED + "!Modalidad Beta! ","",ChatColor.YELLOW + "Hay " + countPlayersInServer("puentes") + " Jugadores.","", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
		invRedes.setOption(20,facebook,""+ChatColor.translateAlternateColorCodes('&',"&9&lFacebook"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Siguenos en Facebook :","",ChatColor.GOLD+"www.facebook.com/MinelcServer"});
		invRedes.setOption(22,twitter,""+ChatColor.translateAlternateColorCodes('&',"&b&lTwitter"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Buscanos en Twitter como:","",ChatColor.GOLD+"@MineLCNetwork"});
		invRedes.setOption(24,discord,""+ChatColor.translateAlternateColorCodes('&',"&5&lDiscord"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Se parte de nuestro Discord: ","",ChatColor.GOLD+"https://discord.gg/K7JPM8E"});
		invRedes.setOption(31,foro,""+ChatColor.translateAlternateColorCodes('&',"&8&lForo"),new String[]{"",ChatColor.translateAlternateColorCodes('&',"&6&m-------------------------------"),ChatColor.WHITE+"Visita nuestro foro:","",ChatColor.GOLD+"www.minelc.net"});


		//glass
		int i;
		for(i=0;i<=54;i++){
			if(i>=0&&i<=8||i>=45&&i<=53||i==9||i==18||i==27||i==36||i==17||i==26||i==35||i==44){
				invRedes.setOption(i,Glass,""+ChatColor.translateAlternateColorCodes('&',"&3&lMineLC &6&lNetwork"),new String[]{"",ChatColor.YELLOW+"www.minelc.net"});
			}
		}


		return invRedes;
	}

	private static int countPlayersInServer(String svname){
		int count=0;
		for(ServersController svs:ServersController.getAllServers()){
			try{
				if(svs.getName().toLowerCase().startsWith(svname)){
					count+=svs.getCurrentPlayers();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return count;
	}

	public static IconMenu getInvRangos(Player p){
		Jugador j=Jugador.getJugador(p);

		IconMenu invRangos=new IconMenu("Men� De Rangos",45,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				e.setWillClose(false);
				e.setWillDestroy(true);
				if(e.getPosition()==11){
					getInvRangoVIP(e.getPlayer()).open(e.getPlayer());
				}else if(e.getPosition()==13){
					getInvRangoSVIP(e.getPlayer()).open(e.getPlayer());
				}else if(e.getPosition()==15){
					getInvRangoElite(e.getPlayer()).open(e.getPlayer());
				}else if(e.getPosition()==22){
					getInvRangoRuby(e.getPlayer()).open(e.getPlayer());
				}else{
					e.setWillClose(true);
					e.getPlayer().sendMessage(ChatColor.GREEN+"M�s informaci�n sobre los rangos en "+ChatColor.GOLD+"tienda.minelc.net");
				}
			}
		},LobbyMain.getInstance(),true)
				.setOption(11,new ItemStack(Material.IRON_INGOT,1),ChatColor.AQUA+""+ChatColor.BOLD+ChatColor.UNDERLINE+"Rango VIP","",ChatColor.GREEN+""+ChatColor.BOLD+"Beneficios:",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Prefijo "+ChatColor.AQUA+ChatColor.BOLD+"VIP",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso en todos los servidores a todos los",ChatColor.YELLOW+"  articulos que contengan \"solo para VIP\".",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso prioritario al servidor.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Y mas..","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �")
				.setOption(13,new ItemStack(Material.GOLD_INGOT,1),ChatColor.GREEN+""+ChatColor.BOLD+ChatColor.UNDERLINE+"Rango SVIP","",ChatColor.GREEN+""+ChatColor.BOLD+"Beneficios:",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Todos los beneficios del rango "+ChatColor.AQUA+ChatColor.BOLD+"VIP.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Prefijo "+ChatColor.GREEN+ChatColor.BOLD+"SVIP.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso en todos los servidores a todos los",ChatColor.YELLOW+"  articulos que contengan \"solo para SVIP\".",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso prioritario al servidor.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Y mas..","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �")
				.setOption(15,new ItemStack(Material.DIAMOND,1),ChatColor.GOLD+""+ChatColor.BOLD+ChatColor.UNDERLINE+"Rango ELITE","",ChatColor.GREEN+""+ChatColor.BOLD+"Beneficios:",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Todos los beneficios del rango "+ChatColor.GREEN+ChatColor.BOLD+"SVIP.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Prefijo "+ChatColor.GOLD+ChatColor.BOLD+"ELITE.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso en todos los servidores a todos los",ChatColor.YELLOW+"  articulos que contengan \"solo para ELITE\".",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso prioritario al servidor.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Y mas..","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �")
				.setOption(22,new ItemStack(Material.EMERALD,1),ChatColor.RED+""+ChatColor.BOLD+ChatColor.UNDERLINE+"Rango RUBY","",ChatColor.GREEN+""+ChatColor.BOLD+"Beneficios:",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Todos los beneficios del rango "+ChatColor.GOLD+ChatColor.BOLD+"ELITE.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Prefijo "+ChatColor.RED+ChatColor.BOLD+"RUBY.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso en todos los servidores a todos los",ChatColor.YELLOW+"  articulos que contengan \"solo para RUBY\".",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Acceso prioritario al servidor.",ChatColor.DARK_GRAY+"- "+ChatColor.YELLOW+"Y mas..","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");

		if((j.getRank()==Ranks.VIP||j.getRank()==Ranks.SVIP||j.getRank()==Ranks.ELITE)||j.getRank()==Ranks.RUBY){
			invRangos.setOption(31,new ItemUtils(p.getName(),1),ChatColor.GREEN+"Informaci�n Del Rango",ChatColor.GRAY+"Rango: "+ChatColor.GOLD+j.getRank().name(),ChatColor.GRAY+"Duraci�n: "+ChatColor.GOLD+getDuration(j.getRankduration()),ChatColor.GRAY+"VIP-Points: "+ChatColor.GOLD+j.getRankpoints());
		}else{
			invRangos.setOption(31,new ItemUtils(p.getName(),1),ChatColor.GREEN+"Informaci�n Del Rango",ChatColor.GRAY+"Rango: "+ChatColor.GOLD+j.getRank().toString(),ChatColor.GRAY+"VIP-Points: "+ChatColor.GOLD+j.getRankpoints());
		}

		return invRangos;
	}

	private static String getDuration(long time){
		String duracion="";
		if(time<=0)
			return"";
		time=(time-System.currentTimeMillis())/1000/60;
		float time2=time;

		if(time2>2880){
			time2=time2/24/60;
			duracion=(int)time2+" dias, ";
			time2=(time2-(int)time2)*24*60;
		}else if(time2>1440){
			time2=time2/24/60;
			duracion=(int)time2+" dia, ";
			time2=(time2-(int)time2)*24*60;
		}

		if(time2>120){
			time2=time2/60;
			duracion+=(int)time2+" horas y ";
			time2=(time2-(int)time2)*60;
		}else if(time2>60){
			time2=time2/60;
			duracion+=(int)time2+" hora y ";
			time2=(time2-(int)time2)*60;
		}else{
			duracion=duracion.replace(", ","");
		}

		if(time2==1){
			duracion+=(int)time2+" minuto";
		}else if(time2>1){
			duracion+=(int)time2+" minutos";
		}else{
			duracion=duracion.replace(" y ","");
		}

		return duracion;
	}

	private static IconMenu getInvRangoVIP(Player p){
		final Jugador j=Jugador.getJugador(p);

		IconMenu invRangoVIP=new IconMenu("Comprar Rango VIP",27,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				if(e.getPosition()==11){
					if(j.getRankpoints()>=150){
						j.removeRankpoints(150);
						if(j.getRank()==Ranks.VIP){
							j.addRankduration(604800000);
						}else{
							j.setRank(Ranks.VIP);
							j.setRankduration(604800000);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==13){
					if(j.getRankpoints()>=300){
						j.removeRankpoints(300);
						if(j.getRank()==Ranks.VIP){
							j.addRankduration(1296000000);
						}else{
							j.setRank(Ranks.VIP);
							j.setRankduration(1296000000);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==15){
					if(j.getRankpoints()>=500){
						j.removeRankpoints(500);
						if(j.getRank()==Ranks.VIP){
							j.addRankduration(2592000000L);
						}else{
							j.setRank(Ranks.VIP);
							j.setRankduration(2592000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}
				e.setWillDestroy(true);
			}
		},LobbyMain.getInstance(),true);

		if(j.getRankpoints()>=150){
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$1.5 EUR � 150 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$1.5 EUR � 150 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=300){
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$3 EUR � 300 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$3 EUR � 300 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=500){
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$5 EUR � 500 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.AQUA+""+ChatColor.BOLD+"Rango VIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$5 EUR � 500 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		return invRangoVIP;
	}

	private static IconMenu getInvRangoSVIP(Player p){
		final Jugador j=Jugador.getJugador(p);

		IconMenu invRangoVIP=new IconMenu("Comprar Rango SVIP",27,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				if(e.getPosition()==11){
					if(j.getRankpoints()>=300){
						j.removeRankpoints(300);
						if(j.getRank()==Ranks.SVIP){
							j.addRankduration(604800000);
						}else{
							j.setRank(Ranks.SVIP);
							j.setRankduration(604800000);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==13){
					if(j.getRankpoints()>=600){
						j.removeRankpoints(600);
						if(j.getRank()==Ranks.SVIP){
							j.addRankduration(1296000000);
						}else{
							j.setRank(Ranks.SVIP);
							j.setRankduration(1296000000);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==15){
					if(j.getRankpoints()>=1000){
						j.removeRankpoints(1000);
						if(j.getRank()==Ranks.SVIP){
							j.addRankduration(2592000000L);
						}else{
							j.setRank(Ranks.SVIP);
							j.setRankduration(2592000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}
				e.setWillDestroy(true);
			}
		},LobbyMain.getInstance(),true);

		if(j.getRankpoints()>=300){
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$3 EUR � 300 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$3 EUR � 300 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=600){
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$6 EUR � 600 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$6 EUR � 600 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=1000){
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$10 EUR � 1000 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GREEN+""+ChatColor.BOLD+"Rango SVIP"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$10 EUR � 1000 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		return invRangoVIP;
	}

	public static IconMenu getInvRangoRuby(Player p){
		final Jugador j=Jugador.getJugador(p);
		IconMenu invRangoRuby=new IconMenu("Comprar rango Ruby",27,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				if(e.getPosition()==11){
					if(j.getRankpoints()>=1000){
						j.removeRankpoints(1000);
						if(j.getRank()==Ranks.RUBY){
							j.addRankduration(604800000L);
						}else{
							j.setRank(Ranks.RUBY);
							j.setRankduration(604800000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==13){
					if(j.getRankpoints()>=1700){
						j.removeRankpoints(1700);
						if(j.getRank()==Ranks.RUBY){
							j.addRankduration(1296000000L);
						}else{
							j.setRank(Ranks.RUBY);
							j.setRankduration(1296000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==15){
					if(j.getRankpoints()>=2300){
						j.removeRankpoints(2300);
						if(j.getRank()==Ranks.RUBY){
							j.addRankduration(2592000000L);
						}else{
							j.setRank(Ranks.RUBY);
							j.setRankduration(2592000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}
				e.setWillDestroy(true);
			}
		},LobbyMain.getInstance(),true);


		if(j.getRankpoints()>=1000){
			invRangoRuby.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"1000 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoRuby.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"1000 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=1700){
			invRangoRuby.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"1700 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoRuby.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"1700 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=2300){
			invRangoRuby.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"2300 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoRuby.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GOLD+""+ChatColor.BOLD+"Rango RUBY"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"2300 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		return invRangoRuby;
	}

	private static IconMenu getInvRangoElite(Player p){
		final Jugador j=Jugador.getJugador(p);

		IconMenu invRangoVIP=new IconMenu("Comprar Rango Elite",27,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				if(e.getPosition()==11){
					if(j.getRankpoints()>=450){
						j.removeRankpoints(450);
						if(j.getRank()==Ranks.ELITE){
							j.addRankduration(604800000L);
						}else{
							j.setRank(Ranks.ELITE);
							j.setRankduration(604800000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==13){
					if(j.getRankpoints()>=900){
						j.removeRankpoints(900);
						if(j.getRank()==Ranks.ELITE){
							j.addRankduration(1296000000L);
						}else{
							j.setRank(Ranks.ELITE);
							j.setRankduration(1296000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}else if(e.getPosition()==15){
					if(j.getRankpoints()>=1500){
						j.removeRankpoints(1500);
						if(j.getRank()==Ranks.ELITE){
							j.addRankduration(2592000000L);
						}else{
							j.setRank(Ranks.ELITE);
							j.setRankduration(2592000000L);
						}
						Database.savePlayerRank(j);
						e.getPlayer().sendMessage(ChatColor.GREEN+"Rango comprado con exito!");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_YES,1.2F,1.2F);
					}else{
						e.getPlayer().sendMessage(ChatColor.RED+"No tienes suficientes "+ChatColor.AQUA+"VIP-Points "+ChatColor.RED+"para comprar este rango. Visita "+ChatColor.GOLD+"http://tienda.minelc.com"+ChatColor.RED+" para que puedas obtener este rango.");
						e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.VILLAGER_NO,1.2F,1.2F);
					}
				}
				e.setWillDestroy(true);
			}
		},LobbyMain.getInstance(),true);

		if(j.getRankpoints()>=450){
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$4.5 EUR � 450 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(11,new ItemStack(Material.WATCH,7),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"7 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"7 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$4.5 EUR � 450 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=900){
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$9 EUR � 900 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(13,new ItemStack(Material.WATCH,15),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"15 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"15 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$9 EUR � 900 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		if(j.getRankpoints()>=1500){
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$15 EUR � 1500 VIP-Points","",ChatColor.BLUE+""+ChatColor.BOLD+"� Click Para Comprar �");
		}else{
			invRangoVIP.setOption(15,new ItemStack(Material.WATCH,30),ChatColor.GOLD+""+ChatColor.BOLD+"Rango ELITE"+ChatColor.DARK_GRAY+" - "+ChatColor.GRAY+ChatColor.BOLD+"30 Dias","",ChatColor.GREEN+"Duracion: "+ChatColor.GOLD+"30 Dias.","",ChatColor.GREEN+"Precio: "+ChatColor.GOLD+"$15 EUR � 1500 VIP-Points","",ChatColor.RED+"Actualmente tienes "+j.getRankpoints()+" VIP-Points, Visita",ChatColor.GOLD+"http://tienda.minelc.com/"+ChatColor.RED+" para comprar mas!");
		}

		return invRangoVIP;
	}

	public static IconMenu getInvVanidad(Player player){
		if(invVanidad==null){
			invVanidad=new IconMenu("Vanidad",45,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					Player player=e.getPlayer();
					switch(e.getPosition()){
						case 11:break;
						case 13:break;
						case 15:break;
						case 28:break;
						case 30:player.performCommand("wings");break;
						case 32:break;
						case 34:break;
						default:break;
					}


				}
			},LobbyMain.getInstance())
					.setOption(11,new ItemStack(Material.BEACON,1),ChatColor.GREEN+"Opciones",ChatColor.GRAY+"Abre el men� de opciones.")
					.setOption(13,new ItemStack(Material.REDSTONE,1),ChatColor.GREEN+"Particulas",ChatColor.GRAY+"Abre el men� de particulas.")
					.setOption(15,new ItemStack(Material.EGG,1),ChatColor.GREEN+"Cosm�ticos",ChatColor.GRAY+"Abre el men� de cosm�ticos.")
					.setOption(28,new ItemStack(Material.JUKEBOX,1,(short)2),ChatColor.GREEN+"M�sica",ChatColor.GRAY+"Abre el men� de m�sica.")
					.setOption(30,new ItemStack(Material.FEATHER),ChatColor.GREEN+"Alas",ChatColor.GRAY+"Abre el men� de alas.");
//        .setOption(32, new ItemStack(Material.DIAMOND_BARDING), ChatColor.GREEN+"Monturas", ChatColor.GRAY+"Click para seleccionar una montura!")
//        .setOption(34, new ItemStack(Material.JUKEBOX), ChatColor.GREEN+"Musica", ChatColor.GRAY+"Click para seleccionar musica!");
		}
		return invVanidad;
	}

	public static IconMenu getInvPerfil(Player p){
		//Jugador j = Jugador.getJugador(p);
		IconMenu invPerfil=new IconMenu("Perfil de "+p.getName(),36,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				e.setWillClose(false);
				e.setWillDestroy(true);

				if(e.getPosition()==11){
					getInvStats(e.getPlayer()).open(e.getPlayer());
				}else if(e.getPosition()==15){
					getInvSettings(e.getPlayer()).open(e.getPlayer());
				}
			}
		},LobbyMain.getInstance(),true)
				.setOption(11,new ItemStack(Material.PAPER,1),ChatColor.GREEN+"Estadisticas",ChatColor.GRAY+"Muestra tus estadisticas de cada",ChatColor.GRAY+"juego y un resumen de todas!")
				.setOption(15,new ItemStack(Material.REDSTONE_COMPARATOR,1),ChatColor.GREEN+"Opciones",ChatColor.GRAY+"Permite cambiar algunas opciones en ",ChatColor.GRAY+"la lobby.");
		//.setOption(22, new ItemUtils(p.getName(), 1), ChatColor.GREEN+"Informacion Del Jugador", ChatColor.GRAY+"Rango: "+ChatColor.GOLD+j.getRank().toString(), ChatColor.GRAY+"Nivel: "+ChatColor.GOLD+"0",ChatColor.GRAY+"LCoins: "+ChatColor.GOLD+j.getLcoins(),ChatColor.GRAY+"VIP-Points: "+ChatColor.GOLD+j.getRankpoints());

		return invPerfil;
	}

	public static IconMenu getInvStats(Player p){
		final Jugador j=Jugador.getJugador(p);
		IconMenu invStats=new IconMenu("Estadísticas de "+p.getName(),54,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				e.setWillClose(false);
				e.setWillDestroy(false);

				//getInvSettings(e.getPlayer()).open(e.getPlayer());
			}
		},LobbyMain.getInstance(),true);
		updateInvStats(invStats,j);
		return invStats;
	}

	private static void updateInvStats(final IconMenu invStats,final Jugador jug){
		Bukkit.getScheduler().runTaskAsynchronously(LobbyMain.getInstance(),new Runnable(){

			@Override
			public void run(){
				//ASYNC E INV HASHMAP  Y FALSE ICONMENU
				Database.loadPlayerSV_HG_SYNC(jug);
				invStats.setOption(10,new ItemStack(Material.BOW),ChatColor.GREEN+""+ChatColor.BOLD+"Hunger Games   "," ",
						ChatColor.YELLOW+"Victorias"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getHG_Stats_Partidas_ganadas(),
						ChatColor.YELLOW+"Derrotas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+(jug.getHG_Stats_Partidas_jugadas()-jug.getHG_Stats_Partidas_ganadas()),
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getHG_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getHG_Stats_deaths()
								+" ");

				Database.loadPlayerSV_SKYWARS_SYNC(jug);
				invStats.setOption(12,new ItemStack(Material.FISHING_ROD),ChatColor.GREEN+""+ChatColor.BOLD+"SkyWars   "," ",
						ChatColor.YELLOW+"Victorias"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getSKYW_Stats_Partidas_ganadas(),
						ChatColor.YELLOW+"Derrotas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+(jug.getSKYW_Stats_Partidas_jugadas()-jug.getSKYW_Stats_Partidas_ganadas()),
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getSKYW_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getSKYW_Stats_deaths()
								+" ");

				Database.loadPlayerSV_PVPGAMES_SYNC(jug);
				invStats.setOption(14,new ItemStack(Material.IRON_CHESTPLATE),ChatColor.GREEN+""+ChatColor.BOLD+"PVPGames "," ",
						ChatColor.YELLOW+"Victorias"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_Partidas_ganadas(),
						ChatColor.YELLOW+"Derrotas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+(jug.getPVPG_Stats_Partidas_jugadas()-jug.getPVPG_Stats_Partidas_ganadas()),
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_deaths(),
						ChatColor.YELLOW+"Núcleos filtrados"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_cores_leakeds(),
						ChatColor.YELLOW+"Monumentos destruidos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_monuments_destroyed(),
						ChatColor.YELLOW+"Lanas colocadas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getPVPG_Stats_wools_placed()
								+" ");

				Database.loadPlayerSV_KITPVP_SYNC(jug);
				invStats.setOption(16,new ItemStack(Material.IRON_SWORD),ChatColor.GREEN+""+ChatColor.BOLD+"KitPVP   "," ",
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getKitPVP_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getKitPVP_Stats_deaths()
								+" ");

				Database.loadPlayerSV_PRACTICE_SYNC(jug);
				invStats.setOption(28,new ItemStack(Material.ANVIL),ChatColor.GREEN+""+ChatColor.BOLD+"Practice   "," ",
						ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_kills(),
						ChatColor.YELLOW + "Muertes"  + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_deaths(),
						ChatColor.YELLOW + "ELO Global"  + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_global_elo(),
						ChatColor.YELLOW + "Victorias (en Party)"  + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_victorias_party(),
						ChatColor.YELLOW + "Victorias (en LMS)"  + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_victorias_lms(),
						ChatColor.YELLOW + "Victorias (en Brackets)"  + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPRAC_Stats_victorias_brackets());

				Database.loadPlayerSV_BEDWARS_SYNC(jug);
				invStats.setOption(30,new ItemStack(Material.DRAGON_EGG),ChatColor.GREEN+""+ChatColor.BOLD+"EggWars   "," ",
						ChatColor.YELLOW+"Victorias"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getBEDW_Stats_Partidas_ganadas(),
						ChatColor.YELLOW+"Derrotas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+(jug.getBEDW_Stats_Partidas_jugadas()-jug.getBEDW_Stats_Partidas_ganadas()),
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getBEDW_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getBEDW_Stats_deaths()
								+" ");

				Database.loadPlayerSV_CHG_SYNC(jug);
				invStats.setOption(32,new ItemStack(Material.BOW),ChatColor.GREEN+""+ChatColor.BOLD+"Hunger Games Clasicos  "," ",
						ChatColor.YELLOW+"Victorias"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getCHG_Stats_Partidas_ganadas(),
						ChatColor.YELLOW+"Derrotas"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+(jug.getCHG_Stats_Partidas_jugadas()-jug.getCHG_Stats_Partidas_ganadas()),
						ChatColor.YELLOW+"Asesinatos"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getCHG_Stats_kills(),
						ChatColor.YELLOW+"Muertes"+ChatColor.DARK_GRAY+": "+ChatColor.GRAY+jug.getCHG_Stats_deaths()
								+" ");

			}
		});
	}

	public static IconMenu getInvSettings(Player p){
		final Jugador j=Jugador.getJugador(p);
		IconMenu invSettings=new IconMenu("Opciones",54,new IconMenu.OptionClickEventHandler(){
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent e){
				switch(e.getPosition()){
					case 10:
					case 19:
						j.setOpciones_SVS_Enable_Players(!j.isOpciones_SVS_Enable_Players());
						if(j.isOpciones_SVS_Enable_Players()){
							for(Player Online:Bukkit.getOnlinePlayers()){
								e.getPlayer().showPlayer(Online);
							}
						}else{
							for(Player Online:Bukkit.getOnlinePlayers()){
								e.getPlayer().hidePlayer(Online);
							}
						}
						Database.savePlayerOpciones_SVS(j);
						break;
					case 12:
					case 21:
						j.setOpciones_SVS_Enable_Chat(!j.isOpciones_SVS_Enable_Chat());
						Database.savePlayerOpciones_SVS(j);
						break;
					case 14:
					case 23:
						j.setOpciones_SVS_Enable_Stacker(!j.isOpciones_SVS_Enable_Stacker());
						Database.savePlayerOpciones_SVS(j);
						break;
					case 16:
					case 25:
						j.setOnlineMode(!j.isOnlineMode());
						//j.getBukkitPlayer().sendMessage(ChatColor.RED+"La opcion de cuenta premium solo funciona en usuarios premium (Minecraft Comprado) si no eres premium te quedaras sin acceso al servidor!");
						//j.getBukkitPlayer().playSound(j.getBukkitPlayer().getLocation(), Sound.NOTE_BASS, 0.7F, 0.7F);
						Database.savePlayerBungee(j);
						j.getBukkitPlayer().kickPlayer(ChatColor.GREEN+"Cuenta premium activada!");
						e.setWillClose(false);
						e.setWillDestroy(true);
						return;
					case 29:
					case 38:
						if(j.is_VIP()){
							j.setHideRank(!j.isHideRank());
							Database.savePlayerRank(j);
						}
						break;
					case 31:
					case 40:
						e.setWillClose(false);
						e.setWillDestroy(true);
						getinvColors().open(e.getPlayer());
						return;
					default:break;
				}
				//poner cooldown???
				e.setWillClose(false);
				e.setWillDestroy(true);

				getInvSettings(e.getPlayer()).open(e.getPlayer());
			}
		},LobbyMain.getInstance(),true);
		updateInvSettings(invSettings,j);
		return invSettings;
	}

	private static IconMenu getinvColors(){
		if(invColorSelector==null){
			invColorSelector=new IconMenu("Colores",54,new IconMenu.OptionClickEventHandler(){
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent e){
					e.setWillClose(false);
					e.setWillDestroy(false);
					Jugador jug=Jugador.getJugador(e.getPlayer());
					switch(e.getPosition()){
						case 10:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.DARK_AQUA);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 11:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.YELLOW);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 12:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.GREEN);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 13:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.LIGHT_PURPLE);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 14:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.GRAY);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 15:if(jug.is_VIP()){
							jug.setNameTagColor(ChatColor.DARK_PURPLE);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 16:if(jug.is_SVIP()){
							jug.setNameTagColor(ChatColor.WHITE);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 19:if(jug.is_SVIP()){
							jug.setNameTagColor(ChatColor.DARK_GREEN);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 20:if(jug.is_SVIP()){
							jug.setNameTagColor(ChatColor.RED);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 21:if(jug.is_SVIP()){
							jug.setNameTagColor(ChatColor.DARK_GRAY);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 22:if(jug.is_SVIP()){
							jug.setNameTagColor(ChatColor.GOLD);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 23:if(jug.is_ELITE()){
							jug.setNameTagColor(ChatColor.AQUA);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 24:if(jug.is_ELITE()){
							jug.setNameTagColor(ChatColor.BLUE);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
							break;
						case 25:if(jug.is_ELITE()){
							jug.setNameTagColor(ChatColor.DARK_RED);
							Database.savePlayerRank(jug);
							e.getPlayer().sendMessage(ChatColor.GREEN+"Tu color de nombre fue cambiado correctamente!");
							getInvSettings(e.getPlayer()).open(e.getPlayer());
						}else{
							e.getPlayer().sendMessage(ChatColor.RED+"No tienes rango suficiente para elegir color, visita: http://tienda.minelc.com/ para ver informacion de rangos!");
						}
						case 40:getInvSettings(e.getPlayer()).open(e.getPlayer());
							break;
						default:break;
					}
				}
			},LobbyMain.getInstance());
			invColorSelector.setOption(10,new ItemStack(Material.WOOL,1,(short)3),ChatColor.DARK_AQUA+"Cian oscuro",ChatColor.GRAY+"Requiere rango VIP o superior.");
			invColorSelector.setOption(11,new ItemStack(Material.WOOL,1,(short)4),ChatColor.YELLOW+"Amarillo",ChatColor.GRAY+"Requiere rango VIP o superior.");
			invColorSelector.setOption(12,new ItemStack(Material.WOOL,1,(short)5),ChatColor.GREEN+"Lima",ChatColor.GRAY+"Requiere rango VIP o superior.");
			invColorSelector.setOption(13,new ItemStack(Material.WOOL,1,(short)6),ChatColor.LIGHT_PURPLE+"Rosa",ChatColor.GRAY+"Requiere rango VIP o superior.");
			invColorSelector.setOption(14,new ItemStack(Material.WOOL,1,(short)8),ChatColor.GRAY+"Gris",ChatColor.GRAY+"Requiere rango VIP o superior.");
			invColorSelector.setOption(15,new ItemStack(Material.WOOL,1,(short)10),ChatColor.DARK_PURPLE+"Purpura",ChatColor.GRAY+"Requiere rango VIP o superior.");

			invColorSelector.setOption(16,new ItemStack(Material.WOOL,1,(short)0),ChatColor.WHITE+"Blanco",ChatColor.GRAY+"Requiere rango SVIP o superior.");
			invColorSelector.setOption(19,new ItemStack(Material.WOOL,1,(short)13),ChatColor.DARK_GREEN+"Verde",ChatColor.GRAY+"Requiere rango SVIP o superior.");
			invColorSelector.setOption(20,new ItemStack(Material.WOOL,1,(short)14),ChatColor.RED+"Rojo",ChatColor.GRAY+"Requiere rango SVIP o superior.");
			invColorSelector.setOption(21,new ItemStack(Material.WOOL,1,(short)7),ChatColor.DARK_GRAY+"Gris Oscuro",ChatColor.GRAY+"Requiere rango SVIP o superior.");
			invColorSelector.setOption(22,new ItemStack(Material.WOOL,1,(short)1),ChatColor.GOLD+"Dorado",ChatColor.GRAY+"Requiere rango SVIP o superior.");

			invColorSelector.setOption(23,new ItemStack(Material.WOOL,1,(short)9),ChatColor.AQUA+"Cian",ChatColor.GRAY+"Requiere rango Elite.");
			invColorSelector.setOption(24,new ItemStack(Material.WOOL,1,(short)11),ChatColor.BLUE+"Azul",ChatColor.GRAY+"Requiere rango Elite.");
			invColorSelector.setOption(25,new ItemStack(Material.REDSTONE_BLOCK,1),ChatColor.DARK_RED+"Rojo oscuro",ChatColor.GRAY+"Requiere rango Elite.");
			invColorSelector.setOption(40,new ItemStack(Material.MAP),ChatColor.GRAY+""+ChatColor.BOLD+"Regresar");
		}
		return invColorSelector;
	}

	private static void updateInvSettings(IconMenu invPerfil,Jugador j){
		//Players
		if(j.isOpciones_SVS_Enable_Players()){
			invPerfil.setOption(10,new ItemStack(Material.WATCH),ChatColor.GREEN+"Visibilidad De Jugadores",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"a los jugadores conectados");
			invPerfil.setOption(19,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.GREEN+"Activado",ChatColor.GRAY+"Click para desactivar");
		}else{
			invPerfil.setOption(10,new ItemStack(Material.WATCH),ChatColor.RED+"Visibilidad De Jugadores",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"a los jugadores conectados");
			invPerfil.setOption(19,new ItemStack(Material.INK_SACK,1,(short)8),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
		}
		//Chat
		if(j.isOpciones_SVS_Enable_Chat()){
			invPerfil.setOption(12,new ItemStack(Material.PAPER),ChatColor.GREEN+"Chat De Jugadores",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"a el chat de jugadores");
			invPerfil.setOption(21,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.GREEN+"Activado",ChatColor.GRAY+"Click para desactivar");
		}else{
			invPerfil.setOption(12,new ItemStack(Material.PAPER),ChatColor.RED+"Chat De Jugadores",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"a el chat de jugadores");
			invPerfil.setOption(21,new ItemStack(Material.INK_SACK,1,(short)8),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
		}
		//Stacker
		if(j.isOpciones_SVS_Enable_Stacker()){
			invPerfil.setOption(14,new ItemStack(Material.SADDLE),ChatColor.GREEN+"Apilador De Jugadores",ChatColor.GRAY+"Permite apilar a los jugadores");
			invPerfil.setOption(23,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.GREEN+"Activado",ChatColor.GRAY+"Click para desactivar");
		}else{
			invPerfil.setOption(14,new ItemStack(Material.SADDLE),ChatColor.RED+"Apilador De Jugadores",ChatColor.GRAY+"Permite apilar a los jugadores");
			invPerfil.setOption(23,new ItemStack(Material.INK_SACK,1,(short)8),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
		}
		//Premium
		if(j.isOnlineMode()){
			invPerfil.setOption(16,new ItemUtils(j.getBukkitPlayer().getName(),1),ChatColor.GREEN+"Cuenta Premium",ChatColor.GRAY+"Esta opcion te pertmitira acceder al",ChatColor.GRAY+"servidor sin ingresar tu contrase�a","",ChatColor.DARK_RED+"Atencion: "+ChatColor.RED+"Esta opcion solo funciona en",ChatColor.RED+"usuarios premium (Minecraft Comprado)",ChatColor.RED+"si no eres premium te quedaras sin",ChatColor.RED+"acceso al servidor!");
			invPerfil.setOption(25,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.GREEN+"Activado",ChatColor.GRAY+"Click para desactivar");
		}else{
			invPerfil.setOption(16,new ItemUtils(j.getBukkitPlayer().getName(),1),ChatColor.RED+"Cuenta Premium",ChatColor.GRAY+"Esta opcion te pertmitira acceder al",ChatColor.GRAY+"servidor sin ingresar tu contrase�a","",ChatColor.DARK_RED+"Atencion: "+ChatColor.RED+"Esta opcion solo funciona en",ChatColor.RED+"usuarios premium (Minecraft Comprado)",ChatColor.RED+"si no eres premium te quedaras sin",ChatColor.RED+"acceso al servidor!");
			invPerfil.setOption(25,new ItemStack(Material.INK_SACK,1,(short)8),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
		}

		if(!j.isHideRank()){
			invPerfil.setOption(29,new ItemUtils(Material.ENDER_CHEST,1),ChatColor.GREEN+"Visibilidad De Rango",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"tu rango");
			invPerfil.setOption(38,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.GREEN+"Activado",ChatColor.GRAY+"Click para desactivar");
		}else{
			invPerfil.setOption(29,new ItemUtils(Material.ENDER_CHEST,1),ChatColor.RED+"Visibilidad De Rango",ChatColor.GRAY+"Permite ocultar o mostrar",ChatColor.GRAY+"tu rango");
			invPerfil.setOption(38,new ItemStack(Material.INK_SACK,1,(short)8),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
		}
		invPerfil.setOption(31,new ItemUtils(Material.WOOL,1),ChatColor.RED+"Color de nombre",ChatColor.GRAY+"Selecciona el color de tu nombre");
		invPerfil.setOption(40,new ItemStack(Material.INK_SACK,1,(short)10),ChatColor.RED+"Desactivado",ChatColor.GRAY+"Click para activar");
	}

	private static String getDurationSmall(long time) {
		String duracion = "";
		if (time <= 0L) {
			return "";
		} else {
			time = (time - System.currentTimeMillis()) / 1000L / 60L;
			float time2 = (float)time;
			if (time2 > 2880.0F) {
				time2 = time2 / 24.0F / 60.0F;
				duracion = (int)time2 + "d, ";
				time2 = (time2 - (float)((int)time2)) * 24.0F * 60.0F;
			} else if (time2 > 1440.0F) {
				time2 = time2 / 24.0F / 60.0F;
				duracion = (int)time2 + "d, ";
				time2 = (time2 - (float)((int)time2)) * 24.0F * 60.0F;
			}

			if (time2 > 120.0F) {
				time2 /= 60.0F;
				duracion = duracion + (int)time2 + "h y ";
				time2 = (time2 - (float)((int)time2)) * 60.0F;
			} else if (time2 > 60.0F) {
				time2 /= 60.0F;
				duracion = duracion + (int)time2 + "h y ";
				time2 = (time2 - (float)((int)time2)) * 60.0F;
			} else {
				duracion = duracion.replace(", ", "");
			}

			if (time2 == 1.0F) {
				duracion = duracion + (int)time2 + "m";
			} else if (time2 > 1.0F) {
				duracion = duracion + (int)time2 + "m";
			} else {
				duracion = duracion.replace(" y ", "");
			}

			return duracion;
		}
	}

	public static String formatoScoreboard(String s, Player p) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate= formatter.format(date);

		Jugador jugador = Jugador.getJugador(p);
		String dur;
		if (getDurationSmall(jugador.getRankduration()).isEmpty()) {
			dur = "Permanente";
		} else {
			dur = getDurationSmall(jugador.getRankduration());
		}

		s = s.replace("$player$", p.getName());
		s = s.replace("$duration$", dur);
		s = s.replace("$fecha$", strDate);

		s = PlaceholderAPI.setPlaceholders(p, s);
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}