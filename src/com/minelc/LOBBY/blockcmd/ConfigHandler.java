package com.minelc.LOBBY.blockcmd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;


public class ConfigHandler {
	
	public static ConfigHandler instance = null;
	public static ConfigHandler getInstance(){
		if(ConfigHandler.instance == null)
			ConfigHandler.instance = new ConfigHandler();
		
		return ConfigHandler.instance;
	}
	
	public void createFile(){
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		
		if(!file.exists()){
                         
                        c.createSection("#----Evista usar /me, /op---#");
			c.set("prefix", "&eLobby &8» &7");
			c.set("err_noPermission", "No tines permisos para usar este comando");
			c.set("blocked", Arrays.asList("/ver","/minecraft:deop","/minecraft:op","/kill","/deop","/say","/bukkit","/help", "/hey", "/?", "/plugins", "/bukkit:plugins", "/pl", "/bukkit:pl"));
			
			try {
				c.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String err_noPermission(){
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		return c.getString("err_noPermission");
	}
	
	public boolean exist(String command){
		

		List<String> l = getList();
		
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		
		if(l.contains(command))
			
			return true;
		else
			return false;
	}
	
	public void add(String command) throws IOException{
		
		List<String> l = getList();
		
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		
		if(l.contains(command)){
			
			return;
		}
		
		l.add(command);
		
		c.set("blocked", l);
		
		c.save(file);
		
		
	}
	
	public List<String> getList(){
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		
		return c.getStringList("blocked");
	}
	public String getPrefix(){
		File file = new File("plugins//MineLC_Lobby//", "comandoslocked.yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(file);
		return c.getString("prefix");
	}

}
