package com.minelc.LOBBY;

import com.google.common.collect.Maps;
import com.minelc.LOBBY.Commands.*;
import com.minelc.LOBBY.Controller.ConfigController;
import com.minelc.LOBBY.Controller.LobbyController;
import com.minelc.LOBBY.Controller.MobsController;
import com.minelc.LOBBY.Controller.ServersController;
import com.minelc.LOBBY.Listener.Events;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.minelc.LOBBY.blockcmd.ConfigHandler;
import com.minelc.LOBBY.blockcmd.PlayerCommandPreprocess;



import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;

public class LobbyMain extends JavaPlugin {
    public static Location minilobby;
    private static LobbyMain instance;
    private int countdown = 10;
    public static Map<String, Long> cooldown = Maps.newHashMap();
    public static Location spawnLocation;
    /*
    public void onLoad() {
        //lobby 1
        File world = new File(Bukkit.getServer().getWorldContainer()+File.separator + "world");
        if(world.exists()) {
            world.delete();
        }

        File worldcopy = new File(Bukkit.getServer().getWorldContainer()+File.separator + "worldcopy");
        try {
            Util.copyFolder(worldcopy, world);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.shutdown();
        }
        //lobby 2
        File world2 = new File(Bukkit.getServer().getWorldContainer()+File.separator + "world2");
        if(world2.exists()) {
            world2.delete();
        }

        File worldcopy2 = new File(Bukkit.getServer().getWorldContainer()+File.separator + "worldcopy2");
        try {
            Util.copyFolder(worldcopy2, world2);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }
    */
    public void onEnable() {
        try {
            instance = this;

            ConfigController cfg = new ConfigController();
            cfg.loadConfigFiles("config.yml", "servers.yml", "mobs.yml");
            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

            for(String s : cfg.getServersConfig().getKeys(false)) {
                ServersController.getServer(s);
            }

            for(String m : cfg.getMobsConfig().getKeys(false)) {
                MobsController.getMob(m);
            }

            registerCommands();
            UpdateServers();
            updateMobs();
            updateSelector();
            AutoRestart();
            getServer().getPluginManager().registerEvents(new Events(), this);
            AutoFlyJump();

            getServer().createWorld(new WorldCreator("world"));
            World world = Bukkit.getWorld("world");
            world.setAutoSave(false);
            world.setSpawnLocation(642, 30, -182);
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(500);
            World worldL = Bukkit.getWorld(getConfig().getString("Location.world"));
            double x = Double.parseDouble(getConfig().getString("Location.x"));
            double y = Double.parseDouble(getConfig().getString("Location.y"));
            double z = Double.parseDouble(getConfig().getString("Location.z"));
            float pitch = Float.parseFloat(getConfig().getString("Location.pitch"));
            float yaw = Float.parseFloat(getConfig().getString("Location.yaw"));
            spawnLocation = new Location(worldL, x,y,z, pitch, yaw);


            getServer().createWorld(new WorldCreator("world2"));
            World world2 = Bukkit.getWorld("world2");
            world2.setSpawnLocation(64, 26, 64);
            world2.setAutoSave(false);
            world2.getWorldBorder().setCenter( new Location(world2, 64,26,64));
            world2.getWorldBorder().setSize(1000);
            minilobby = new Location(world2, 64,26,64);

            for(Entity mb : world.getEntities()) {
                mb.remove();
            }

            for(Entity mb : world2.getEntities()) {
                mb.remove();
            }

            ScoreboardLib.setPluginInstance(this);

            // this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        } catch(Exception ex) {
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }

    private boolean loadWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generateStructures(false);
        worldCreator.generator(new ChunkGenerator() {
            @Override
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Arrays.asList(new BlockPopulator[0]);
            }

            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            @Override
            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];
            }

            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 64.0D, 0.0D);
            }
        });
        World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(false, false);
        world.setPVP(true);
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(0);
        world.setTicksPerMonsterSpawns(0);

        world.setGameRuleValue("mobGriefing", "true");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("spectatorsGenerateChunks", "false");
        boolean loaded = false;
        for(World w: getServer().getWorlds()) {
            if(w.getName().equals(world.getName())) {
                loaded = true;
                break;
            }
        }
        return loaded;
    }

    public void AutoFlyJump() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getAllowFlight()) {
                        //Location loc = p.getLocation();
                        //Block block = loc.getBlock().getRelative(BlockFace.DOWN);
                        //if (block.getType() != Material.AIR) {
                        p.setAllowFlight(true);
                        //}
                    }
                    //Location loc = p.getLocation();
                    //double y = loc.getY();
                    //if(y > 200 || y <= 0) {
                    //p.teleport(loc.getWorld().getSpawnLocation());
                    //}
                }

            }
        }, 40L, 30L);
    }

    private void AutoRestart() {
        Random random = new Random();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                if(countdown > 1) {
                    broadcast(ChatColor.RED+"El servidor sera reiniciado en "+countdown+" segundos!");
                } else if(countdown == 1) {
                    broadcast(ChatColor.RED+"El servidor sera reiniciado en "+countdown+" segundo!");
                } else if(countdown == 0) {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.kickPlayer(ChatColor.RED+"Reiniciando servidor!");
                    }
                    Bukkit.shutdown();
                }
                countdown--;
            }
        }, (43200 + random.nextInt(7500)) * 20L, 20L); //8 horas
    }

    public void broadcast(String msg) {
        for(Player Online : Bukkit.getOnlinePlayers()) {
            Online.sendMessage(msg);
        }
    }

    public void onDisable() {
        if(ConfigController.getInstance().getDefaultConfig().getBoolean("KillBungeeMobsOnStop")) {
            for(LivingEntity mb : MobsController.getAllEntities().keySet())
                mb.remove();
        }
    }

    private void registerCommands() {
        CommandHandler handler = new CommandHandler();

        handler.register("minelc", new MineLCCmd());

        handler.register("rank", new RankSubCMD());
        getCommand("tpworld").setExecutor(new tpworld(instance));
        getCommand("vippay").setExecutor(new VIPPayCMD());
        getCommand("minelc").setPermission("minelc.admin");

        getCommand("minelc").setPermissionMessage(ChatColor.RED+"No tienes permisos!");

        getCommand("minelc").setExecutor(handler);
    }

    private void UpdateServers() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                ServersController.pingAllServers();
            }
        }, 40L, 20L);
    }

    private void updateSelector() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    LobbyController.updateInvSelector(p);
                }
            }
        }, 40L, 25L);
    }

    private void updateMobs() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                MobsController.updateAllMobs();
            }
        }, 80L, 55L);
    }

    public static void sendPlayerToServer(Player player, String server, String svName) {
        try {
            if(hasCooldown(player)) {
                return;
            }
            cooldown.put(player.getName(), System.currentTimeMillis()+1500);
            player.sendMessage(ChatColor.GREEN+"Conectando al servidor "+ChatColor.YELLOW+svName+ChatColor.GREEN+"!");
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception ex) {
            player.sendMessage(ChatColor.RED+"No se pudo conectar con el servidor: "+ChatColor.YELLOW+svName+ChatColor.RED+"");
            player.sendMessage(ChatColor.RED+"Si el problema persiste puede reportarlo en: "+ChatColor.YELLOW+"www.minelc.net");
            ex.printStackTrace();
        }
    }

    private static boolean hasCooldown(Player p) {
        boolean ret = false;
        if(cooldown.containsKey(p.getName())) {
            if(cooldown.get(p.getName()) < System.currentTimeMillis()) {
                cooldown.remove(p.getName());
            } else {
                ret = true;
            }
        }

        return ret;
    }

    public static LobbyMain getInstance() {
        return instance;
    }

    public static String getIconVar(String s) {
        switch (s) {
            case "vip":
            case "vippoints":
                return "\u24cb";
            default:
                return "\u26c1";
        }
    }
}
