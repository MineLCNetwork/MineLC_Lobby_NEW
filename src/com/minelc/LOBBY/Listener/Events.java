package com.minelc.LOBBY.Listener;

import com.minelc.CORE.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.minelc.CORE.Controller.Database;
import com.minelc.CORE.Controller.Jugador;
import com.minelc.CORE.Controller.Ranks;
import com.minelc.LOBBY.LobbyMain;
import com.minelc.LOBBY.Controller.LobbyController;
import com.minelc.LOBBY.Controller.MobsController;

import java.util.List;

import static com.minelc.LOBBY.Controller.LobbyController.formatoScoreboard;
import static com.minelc.LOBBY.Controller.LobbyController.scoreboardsLibs;

public class Events implements Listener {

    @EventHandler
    public void whenPlayerInteractWithEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof LivingEntity) {
            LivingEntity npc = (LivingEntity) e.getRightClicked();
            String name = npc.getName();

            if (e.getRightClicked() instanceof Player) {
                Player p = e.getPlayer();
                Player toStack = (Player) e.getRightClicked();
                if (p.isSneaking()) {
                    LobbyController.MenuOpciones(p, toStack).open(p);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        Jugador j = Jugador.getJugador(p);
        if (!j.is_VIP()) {
            p.setFlying(false);
            Location loc = p.getLocation().clone();
            //loc.setPitch(0.0F);
            Vector vel = p.getVelocity().clone();

            Vector jump = vel.multiply(0.3D);
            Vector look = loc.getDirection().normalize().multiply(1.25D);

            p.setVelocity(look.add(jump));
            p.setAllowFlight(false);
            p.playSound(loc, Sound.ENDERDRAGON_WINGS, 0.7F, (float) (0.5F+Math.random()));
            //p.playSound(loc, Sound.ENTITY_ENDERDRAGON_SHOOT, 0.7F, (float) (0.5F+Math.random()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent e) {
        Jugador jug = Jugador.getJugador(e.getName());

        Bukkit.getScheduler().runTaskAsynchronously(LobbyMain.getInstance(), () -> {
            Database.loadPlayerRank_SYNC(jug);
            Database.loadPlayerCoins_SYNC(jug);
            Database.loadPlayerOpciones_SVS_SYNC(jug);
            Database.loadPlayerBungee_SYNC(jug);
        });
    }

    @EventHandler
    public void closeInv(InventoryCloseEvent e){
        // LobbyController.updateScoreboardPreGame();
    }

    @EventHandler
    public void CloseInteract(InventoryClickEvent e){
        if(e.getInventory().getType() == InventoryType.CHEST){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        Player p = e.getPlayer();
        Jugador jug = Jugador.getJugador(p);
        jug.setBukkitPlayer(p);
        LobbyController.onJoin(p, jug);
        /* if(jug.isOpciones_SVS_Enabled_Minilobby()){
            p.teleport(LobbyMain.minilobby);
        } else {
            p.teleport(LobbyMain.spawnLocation);
        } */
        p.teleport(LobbyMain.spawnLocation);

        for(Player Online : Bukkit.getOnlinePlayers()) {
            Jugador j2 = Jugador.getJugador(Online);

            if(!j2.isOpciones_SVS_Enable_Players()) {
                Online.hidePlayer(p);
            }
        }
        jug.checkRankduration();
        onJoin(e.getPlayer());
        addPermissionDefault(e.getPlayer());

        for (Player pp : LobbyController.scoreboardsLibs.keySet()) {
            LobbyController.scoreboardsLibs.get(pp).updatePublic();
        }
    }

    private void onJoin(Player p) {
	    Jugador j = Jugador.getJugador(p);

	    addPermission(j.getRank(), p);
    }

    @EventHandler
 	public void ArmorStandGenerador(PlayerArmorStandManipulateEvent e) {
 		e.setCancelled(true);
 	}

    @EventHandler
    public void Spawn(CreatureSpawnEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getCause() == DamageCause.VOID) {
            Bukkit.getScheduler().runTaskLater(LobbyMain.getInstance(), new Runnable() {
                public void run() {
                    e.getEntity().teleport(e.getEntity().getWorld().getSpawnLocation());
                }
            }, 1L);
        }

    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockMove(BlockFromToEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) {
            ItemStack is = e.getPlayer().getItemInHand();
            if (is != null && is.getType() != Material.AIR) {
                Player p = e.getPlayer();
                if (!p.isOp()) {
                    e.setCancelled(true);
                }

                switch(is.getTypeId()) {
                    case 130:
                        LobbyController.getInvRangos(p).open(p);
                        break;
                    case 339:
                        LobbyController.getInvStats_MAIN().open(p);
                        break;
                    case 345:
                        LobbyController.getInvSelector().open(p);
                        break;
                    case 397:
                        LobbyController.getInvPerfil(p).open(p);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithEntity(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof LivingEntity) {
            final Player p = e.getPlayer();
            if (MobsController.getAllEntities().containsKey(e.getRightClicked())) {
                e.setCancelled(true);
                if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
                    ((MobsController)MobsController.getAllEntities().get(e.getRightClicked())).openInventory(p);
                } else {
                    Bukkit.getScheduler().runTaskLater(LobbyMain.getInstance(), new Runnable() {
                        public void run() {
                            ((MobsController)MobsController.getAllEntities().get(e.getRightClicked())).openInventory(p);
                        }
                    }, 2L);
                }

            } else if (e.getRightClicked() instanceof Player) {
           
                    Player toStack = (Player)e.getRightClicked();
                    if (p != toStack) {
                        if (p.getOpenInventory() == null) {
                            if (toStack.getOpenInventory() == null) {
                                if (toStack.getVehicle() == e.getPlayer()) {
                                    e.setCancelled(true);
                                } else if (toStack.getPassenger() == e.getPlayer()) {
                                    e.setCancelled(true);
                                } else if (toStack.getPassenger() == null || toStack.getVehicle() == null) {
                                    p.setPassenger(toStack);
                                    e.getPlayer().sendMessage(ChatColor.GREEN + "Has apilado a " + ChatColor.YELLOW + e.getRightClicked().getName() + ChatColor.GREEN + "!");
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                 
            }
        }
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR||
           (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            Player p = event.getPlayer();
            Entity entity = p.getPassenger();

            if (entity != null) {
                entity.leaveVehicle();
                this.multiplyVelocity(entity, 1.8D, 1.3D, 1.8D, p.getLocation().getDirection(), 0.3D);
            }
        }

    }

    private void multiplyVelocity(Entity e, double strength, double yVel, double yVelMaximum, Vector prevVector, double yMultiply) {
        if (prevVector.length() != 0.0D) {
            prevVector.normalize();
            prevVector.multiply(strength);
            prevVector.setY(prevVector.getY() + yVel);
            if (prevVector.getY() > yVelMaximum) {
                prevVector.setY(yVelMaximum);
            }

            if (e.isOnGround()) {
                prevVector.setY(prevVector.getY() + yMultiply);
            }

            e.setVelocity(prevVector);
        }

    }

    public void addPermission(Ranks rank, Player p) {
        List<String> rows;
        switch (rank) {
            case VIP:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.vip");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                break;
            case SVIP:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.svip");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                break;
            case ELITE:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.elite");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                break;
            case RUBY:
            case BUILDER:
            case MINIYT:
            case YOUTUBER:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.ruby");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.elite"));
                break;
            case AYUDANTE:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.ayudante");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.elite"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ruby"));
                break;
            case MOD:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.mod");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.elite"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ruby"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ayudante"));
                break;
            case ADMIN:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.admin");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.elite"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ruby"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ayudante"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.mod"));
                break;
            case OWNER:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.owner");
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.default"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.vip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.svip"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.elite"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ruby"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.ayudante"));
                rows.addAll(LobbyMain.getInstance().getConfig().getStringList("permisos.mod"));
                break;
            default:
                rows = LobbyMain.getInstance().getConfig().getStringList("permisos.default");
                break;
        }

        for(String row : rows) {
            if(row.startsWith("-")) {
                StringBuilder sb = new StringBuilder(row);
                String rowdos = sb.deleteCharAt(0).toString();
                p.addAttachment(CoreMain.getInstance(), rowdos, false);
            } else {
                p.addAttachment(CoreMain.getInstance(), row, true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(e.isCancelled()) return;

        Player p = e.getPlayer();
        Jugador j = Jugador.getJugador(p);

        if(!j.isOpciones_SVS_Enable_Chat()) {
            p.sendMessage(ChatColor.RED+"Tienes el chat desactivado, activalo para poder escribir.");
            e.setCancelled(true);
            return;
        }

        String msg = e.getMessage().replace("%", "");
        try {
            for(Player rc : Bukkit.getOnlinePlayers()) {
                if(!Jugador.getJugador(rc).isOpciones_SVS_Enable_Chat())
                    e.getRecipients().remove(rc);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        if(j.isHideRank()) {
            e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
        } else if(j.is_Owner())
            e.setFormat(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+ChatColor.DARK_GRAY+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_Admin())
            e.setFormat(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_MODERADOR())
            e.setFormat(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_AYUDANTE())
            e.setFormat(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_YOUTUBER())
            e.setFormat(ChatColor.RED+""+ChatColor.BOLD+"You"+ChatColor.WHITE+""+ChatColor.BOLD+"Tuber "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_MiniYT())
            e.setFormat(ChatColor.WHITE+""+ChatColor.BOLD+"Mini"+ChatColor.RED+""+ChatColor.BOLD+"YT "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_BUILDER())
            e.setFormat(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_RUBY())
            e.setFormat(ChatColor.AQUA + "★ " + ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+j.getNameTagColor() + p.getName() +ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_ELITE())
            e.setFormat(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_SVIP())
            e.setFormat(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_VIP())
            e.setFormat(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+ChatColor.translateAlternateColorCodes('&', msg));
        else if(j.is_Premium())
            e.setFormat(ChatColor.BLUE+""+ChatColor.BOLD+Ranks.PREMIUM.name()+" "+j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
        else
            e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY+" » " + ChatColor.GRAY+msg);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        LobbyController.onQuit(e.getPlayer());

    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.setLeaveMessage(null);
    }
    
    
         public void addPermissionDefault(Player p){
        	p.addAttachment(CoreMain.getInstance(), "interactivebooks.command.open.examplebook", true);
            p.addAttachment(CoreMain.getInstance(), "chestcommands.open", true);
            p.addAttachment(CoreMain.getInstance(), "chestcommands.open.example.yml", true);
            p.addAttachment(CoreMain.getInstance(), "chestcommands.open.cosmeticos.yml", true);
            
            p.addAttachment(CoreMain.getInstance(), "settings.speed", true);
            p.addAttachment(CoreMain.getInstance(), "settings.chat", true);
            p.addAttachment(CoreMain.getInstance(), "settings.doublejump", true);
            
            p.addAttachment(CoreMain.getInstance(), "playeroptions.use", true);
            p.addAttachment(CoreMain.getInstance(), "playeroptions.speed", true);
            p.addAttachment(CoreMain.getInstance(), "playeroptions.chat", true);
            p.addAttachment(CoreMain.getInstance(), "playeroptions.doublejump", true);    
            p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility", true);  
            
            p.addAttachment(CoreMain.getInstance(), "music.command", true);
            p.addAttachment(CoreMain.getInstance(), "jumppads.use", true);
            p.addAttachment(CoreMain.getInstance(), "trail.allow", true);
            p.addAttachment(CoreMain.getInstance(), "trail.heart", true);
            p.addAttachment(CoreMain.getInstance(), "trail.angry", true);
            p.addAttachment(CoreMain.getInstance(), "trail.magic", true);
            p.addAttachment(CoreMain.getInstance(), "trail.fun", true);
            
            
            //p.addAttachment(CoreMain.getInstance(), "chestcommands.command.menu", true);
   
        }
         public void addPermissionPREMIUM(Player p){
             
        }
         public void addPermissionVIP(Player p){
        	p.addAttachment(CoreMain.getInstance(), "prodigy.particle.*", true);

        	 
            p.addAttachment(CoreMain.getInstance(), "settings.jump", true);
            p.addAttachment(CoreMain.getInstance(), "settings.stacker", true);
            p.addAttachment(CoreMain.getInstance(), "settings.chat", true);
            
            p.addAttachment(CoreMain.getInstance(), "playeroptions.jump", true);
            p.addAttachment(CoreMain.getInstance(), "playeroptions.stacker", true);
            p.addAttachment(CoreMain.getInstance(), "playeroptions.chat", true);
            
            
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.name", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.ride.*", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.hat.*", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.remove", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.rabbit", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.bat", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.pig", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.sheep", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.cow", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.chicken", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.zombie", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.creeper", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.skeleton", true);
            p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.spider", true);
            
            p.addAttachment(CoreMain.getInstance(), "trail.cloud", true);
            p.addAttachment(CoreMain.getInstance(), "trail.wmagic", true);
            p.addAttachment(CoreMain.getInstance(), "trail.ender", true);
            p.addAttachment(CoreMain.getInstance(), "trail.green", true);
            p.addAttachment(CoreMain.getInstance(), "trail.spark", true);
            p.addAttachment(CoreMain.getInstance(), "trail.flame", true);
            p.addAttachment(CoreMain.getInstance(), "trail.white", true);
            p.addAttachment(CoreMain.getInstance(), "trail.rain.tear", true);
            
             
        }
         public void addPermissionSVIP(Player p){
         	p.addAttachment(CoreMain.getInstance(), "prodigy.gadget.*", true);
        	p.addAttachment(CoreMain.getInstance(), "prodigy.pet.*", true);
             p.addAttachment(CoreMain.getInstance(), "settings.fly", true);
             
             p.addAttachment(CoreMain.getInstance(), "playeroptions.fly", true);
             
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.slime", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.cavespider", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.silverfish", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.witch", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.endermite", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.wolf", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.ocelot", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.squid", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.villager", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.mushroomcow", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.pigzombie", true);
             
             p.addAttachment(CoreMain.getInstance(), "trail.water", true);
             p.addAttachment(CoreMain.getInstance(), "trail.note", true);
             p.addAttachment(CoreMain.getInstance(), "trail.snow", true);
             p.addAttachment(CoreMain.getInstance(), "trail.lava", true);
             p.addAttachment(CoreMain.getInstance(), "trail.crit", true);
             p.addAttachment(CoreMain.getInstance(), "trail.smoke", true);
             p.addAttachment(CoreMain.getInstance(), "trail.spell", true);
             p.addAttachment(CoreMain.getInstance(), "trail.enchant", true);
             p.addAttachment(CoreMain.getInstance(), "trail.splash", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.rainbow", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.carpet", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.redstone", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.blood", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.dia", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.builder", true);
             p.addAttachment(CoreMain.getInstance(), "trail.limit.vip", true);
             p.addAttachment(CoreMain.getInstance(), "trail.limit.bypass", true);
        }
         public void addPermissionELITE(Player p){
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.morph.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.mood.*", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.magmacube", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.guardian", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.Blaze", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.enderman", true);
             
             p.addAttachment(CoreMain.getInstance(), "trail.slime", true);
             p.addAttachment(CoreMain.getInstance(), "trail.snowball", true);
             p.addAttachment(CoreMain.getInstance(), "trail.void", true);
             p.addAttachment(CoreMain.getInstance(), "trail.pop", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rainbow", true);
             p.addAttachment(CoreMain.getInstance(), "trail.breath", true);
             p.addAttachment(CoreMain.getInstance(), "trail.endrod", true);
             p.addAttachment(CoreMain.getInstance(), "trail.damage", true);
             p.addAttachment(CoreMain.getInstance(), "trail.sand", true);
             p.addAttachment(CoreMain.getInstance(), "trail.totem", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.sea", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.wheat", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.ore", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.clay", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.wart", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.gold", true);
             p.addAttachment(CoreMain.getInstance(), "trail.rain.builder", true);
             p.addAttachment(CoreMain.getInstance(), "trail.limit.vip", true);
             p.addAttachment(CoreMain.getInstance(), "trail.wing.bf", true);
             p.addAttachment(CoreMain.getInstance(), "trail.limit.bypass", true);
             p.addAttachment(CoreMain.getInstance(), "trail.wings", true);
             p.addAttachment(CoreMain.getInstance(), "trail.wing.angel", true);
             
             
             
        }
         public void addPermissionYOUTUBER(Player p){
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.gadget.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.pet.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.particle.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.suit.*", true); 
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.morph.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.minion.*", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
             
        }
         public void addPermissionBUILDER(Player p){
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.gadget.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.pet.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.particle.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.suit.*", true); 
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.morph.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.minion.*", true);
        }
         public void addPermissionAYUDANTE(Player p){
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.gadget.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.pet.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.particle.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.suit.*", true); 
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.morph.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.minion.*", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
        }
         public void addPermissionMOD(Player p){
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.snowman", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.horse", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.command", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.wing.BloodHound", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.wing.SoulShadow", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
        }
         public void addPermissionADMIN(Player p){
             p.addAttachment(CoreMain.getInstance(), "jumppads.maxpower.bypass", true);
             p.addAttachment(CoreMain.getInstance(), "jumppads.create", true);
             p.addAttachment(CoreMain.getInstance(), "jumppads.delete", true);
             p.addAttachment(CoreMain.getInstance(), "jumppads.set", true);
             p.addAttachment(CoreMain.getInstance(), "jumppads.delete", true);
             p.addAttachment(CoreMain.getInstance(), "jumppads.reload", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.wither", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.irongolem", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.wing.Frost", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
        }
         public void addPermissionOWNER(Player p){
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.*", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.Wisdom", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.reload", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.setwing", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
             p.addAttachment(CoreMain.getInstance(), "bukkit.*", true);
             p.addAttachment(CoreMain.getInstance(), "minecraft.*", true);
             p.addAttachment(CoreMain.getInstance(), "*", true);
        }
         public void addPermissionRUBY(Player p){
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.suit.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.morph.*", true);
        	 p.addAttachment(CoreMain.getInstance(), "prodigy.minion.*", true);
             p.addAttachment(CoreMain.getInstance(), "trail.block.flower", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.irongolem", true);
             p.addAttachment(CoreMain.getInstance(), "echopet.pet.type.wither", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.command", true);
             p.addAttachment(CoreMain.getInstance(), "customwings.wing.SoulShadow", true);
             p.addAttachment(CoreMain.getInstance(), "playeroptions.visibility.vip", true);
             
        }
}
