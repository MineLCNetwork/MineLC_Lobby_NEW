package com.minelc.LOBBY.Controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Colorable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minelc.CORE.Utils.IconMenu;
import com.minelc.CORE.Utils.IconMenu.OptionClickEvent;
import com.minelc.CORE.Utils.IconMenu.OptionClickEventHandler;
import com.minelc.LOBBY.LobbyMain;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;


public class MobsController {
  private static HashMap<String, MobsController> Mob = new HashMap<String, MobsController>();
  private String name;
  private Location loc;
  private int InvSize;
  private String InvTitle;
  private IconMenu inv;
  private IconMenu invnj;
  private IconMenu inv2_0;
  public boolean customStyle = false;
  private LivingEntity ent;
  private static HashMap<LivingEntity, MobsController> Ents = new HashMap<LivingEntity, MobsController>();
  private ConfigurationSection config;
  private ConfigurationSection configServers;
  private String entity;
  private String MobID;
  private LinkedHashMap<Integer, ItemStack> slots = new LinkedHashMap<Integer, ItemStack>();
  private LinkedHashMap<ItemStack, Integer> SPlayers = new LinkedHashMap<ItemStack, Integer>();
  private LinkedHashMap<Integer, ItemStack> slotsNJ = new LinkedHashMap<Integer, ItemStack>();
  private Set<ConfigurationSection> confSect = Sets.newHashSet();
  private static HashMap<Entity,Location> locs = new HashMap<Entity,Location>();
  private LinkedHashMap<Integer, ConfigurationSection> ItemsSlots = new LinkedHashMap<Integer, ConfigurationSection>();
  private LinkedHashMap<Integer, ConfigurationSection> ItemsSlotsNJ = new LinkedHashMap<Integer, ConfigurationSection>();
  private String configName = "";

  public static MobsController getMob(String s) {
    String ss = s.toLowerCase();

    if(Mob.containsKey(ss))
      return Mob.get(ss);

    return new MobsController(s);
  }

  public MobsController(String s) {
    if(s.equalsIgnoreCase("lobbies")) {
      config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
      configName = s;
      configServers = config.getConfigurationSection("Servers");
      InvSize = InvSize(config.getInt("InventorySize"));
      InvTitle = Colorize(config.getString("InventoryTitle"));
      customStyle = config.getBoolean("CustomStyle");
      Mob.put(s.toLowerCase(), this);
      createInventory();
    } else {
      config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
      configName = s;
      configServers = config.getConfigurationSection("Servers");
      name = Colorize(config.getString("name"));
      loc = getLocation(config.getString("location"));
      entity = config.getString("mobtype");
      InvSize = InvSize(config.getInt("InventorySize"));
      InvTitle = Colorize(config.getString("InventoryTitle"));
      customStyle = config.getBoolean("CustomStyle");
      MobID = config.getString("MobID");
      Mob.put(s.toLowerCase(), this);

      createInventory();
      SpawnMob();
    }
  }

  public String getName() {
    return name;
  }

  public String getConfigName() {
    return configName;
  }

  public void SpawnMob() {
    if(!checkID()) {
      if(!loc.getChunk().isLoaded()) {
        loc.getChunk().load();
      }
      ent = (LivingEntity) getEntity(loc, entity);
      //ent.teleport(loc);
      ent.setCanPickupItems(false);
      ent.setCustomName(name);
      ent.setCustomNameVisible(true);
      ent.setRemoveWhenFarAway(false);
      config.set("MobID", ent.getUniqueId().toString());
      ConfigController.getInstance().saveMobsConfig();
    }
    Bukkit.getScheduler().runTaskLater(LobbyMain.getInstance(), new Runnable() {

      @Override
      public void run() {
        noAI(ent);
        ent.setCustomNameVisible(true);
      }
    }, 12L);
    //ent.teleport(loc);
    Ents.put(ent, this);
    locs.put(ent, loc);
    setContents();
  }

  public void noAI(final LivingEntity bukkitEntity) {

    EntityLiving nmsEntity = ((CraftLivingEntity) bukkitEntity).getHandle();
    NBTTagCompound tag = new NBTTagCompound();

    nmsEntity.c(tag);
    tag.setInt("NoAI", 1);
    tag.setInt("Silent", 1);
    tag.setInt("Invulnerable", 1);
    tag.setInt("CustomNameVisible", 1);
    nmsEntity.f(tag);
    //nmsEntity.f(tag);

  }

  public String getContents() {
    if(config.isSet("Contents"))
      return config.getString("Contents");

    return "0:true,0:true,0:true,0:true,0:true";
  }

  private void setContents() {
    if(config.isSet("Contents")) {
      String[] Armor = config.getString("Contents").split(",");
      String Sword = Armor[0];
      int SwordID = Integer.valueOf(Sword.split(":")[0]);
      boolean SwordEnchant = Boolean.valueOf(Sword.split(":")[1]);
      String hat = Armor[1];
      int hatID = Integer.valueOf(hat.split(":")[0]);
      boolean hatEnchant = Boolean.valueOf(hat.split(":")[1]);
      String Pech = Armor[2];
      int pechID = Integer.valueOf(Pech.split(":")[0]);
      boolean pechEnchant = Boolean.valueOf(Pech.split(":")[1]);
      String Pant = Armor[3];
      int pantID = Integer.valueOf(Pant.split(":")[0]);
      boolean pantEnchant = Boolean.valueOf(Pant.split(":")[1]);
      String Boots = Armor[4];
      int BootsID = Integer.valueOf(Boots.split(":")[0]);
      boolean BootsEnchant = Boolean.valueOf(Boots.split(":")[1]);

      if(SwordID != 0){
        ItemStack h = getitem(SwordID, 0, 1, "", getLore(""));
        if(SwordEnchant)
          addEnchantmentGlow(h);
        ent.getEquipment().setItemInHand(h);
      }
      if(hatID != 0){
        ItemStack h = getitem(hatID, 0, 1, "", getLore(""));
        if(hatEnchant)
          addEnchantmentGlow(h);
        ent.getEquipment().setHelmet(h);
      }
      if(pechID != 0){
        ItemStack h = getitem(pechID, 0, 1, "", getLore(""));
        if(pechEnchant)
          addEnchantmentGlow(h);
        ent.getEquipment().setChestplate(h);
      }
      if(pantID != 0) {
        ItemStack h = getitem(pantID, 0, 1, "", getLore(""));
        if(pantEnchant)
          addEnchantmentGlow(h);
        ent.getEquipment().setLeggings(h);
      }
      if(BootsID != 0){
        ItemStack h = getitem(BootsID, 0, 1, "", getLore(""));
        if(BootsEnchant)
          addEnchantmentGlow(h);
        ent.getEquipment().setBoots(h);
      }
    }
  }

  private boolean checkID() {
    boolean ret = false;
    for(LivingEntity le : loc.getWorld().getLivingEntities()) {
      if(le.getUniqueId().toString().equalsIgnoreCase(MobID)) {
        if(!le.isDead()) {
          ent = le;
          ret = true;
        }
        break;
      }
    }
    return ret;
  }

  public LivingEntity getMob() {
    return ent;
  }

  public Location getLocation() {
    return loc;
  }

  public String getMobType() {
    return entity;
  }

  public int getInventorySize() {
    return InvSize;
  }

  public String getInventoryTitle() {
    return InvTitle;
  }

  public Set<String> getServers() {
    return configServers.getKeys(false);
  }

  public void createInventory() {
    if(isCustom()) {
      inv = new IconMenu(getInventoryTitle(), getInventorySize(), new OptionClickEventHandler() {

        @Override
        public void onOptionClick(OptionClickEvent e) {
          e.setWillDestroy(false);
          e.setWillClose(false);
          sendToServer(e.getPosition(), e.getPlayer());
        }
      }, LobbyMain.getInstance());
    } else {
      invnj = new IconMenu(getInventoryTitle(), 54, new OptionClickEventHandler() {

        @Override
        public void onOptionClick(OptionClickEvent e) {
          e.setWillDestroy(false);
          e.setWillClose(false);
          if(e.getPosition() == 4) {
            openInventory(e.getPlayer());
          } else {
            sendToServerNJ(e.getPosition(), e.getPlayer());
          }
        }
      }, LobbyMain.getInstance());
      inv2_0 = new IconMenu(getInventoryTitle(), 54, new OptionClickEventHandler() {

        @Override
        public void onOptionClick(OptionClickEvent e) {
          e.setWillDestroy(false);
          e.setWillClose(false);
          if(e.getPosition() == 40) {
            openGamesInProgress(e.getPlayer());
          } else {
            sendToServer(e.getPosition(), e.getPlayer());
          }
        }
      }, LobbyMain.getInstance());
    }
    ConfigurationSection c = null;
    for(String items : getServers()) {
      c = configServers.getConfigurationSection(items);
      ItemsSlots.put(c.getInt("slot"), c);
      confSect.add(c);
    }
  }

  public void openInventory(Player p) {
    if(isCustom())
      inv.open(p);
    else {
      inv2_0.open(p);
    }
  }

  public void openGamesInProgress(Player p) {
    invnj.open(p);
  }

  public void updateInventory() {
    ConfigurationSection c = null;
    for(ConfigurationSection it : confSect) {
      try {
        c = it;
        if(it == null){
          Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No se pudo cargar el servidor.");
          return;
        }
        ServersController server = ServersController.getServer(it.getName());
        if(server == null){
          Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "No se pudo cargar el servidor: " + it.getName());
          return;
        }
        ItemStack item;
        if(server.isOnline()) {
          if(!(c.getBoolean("ItemNotJoinableIsFull") && server.getCurrentPlayers() >= server.getMaxPlayers()) && !checkMotdContain(server.getMotd(), c.getString("ItemNotJoinableIfMotdContains"))) {
            item = getitem(c.getString("ItemOnlineID"), c.getInt("ItemOnlineAmmount"), c.getString("ItemName"), c.getStringList("ItemOnlineLore"));
            List<String> lore = Lists.newArrayList();
            for(String s : item.getItemMeta().getLore()) {
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                      .replace("<Motd>", server.getMotd()));
            }
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);

            if(c.getBoolean("ItemOnlineGlow"))
              addEnchantmentGlow(item);

            inv.setOption(c.getInt("slot"), item);
          } else {
            item = getitem(c.getString("ItemNotJoinableID"), c.getInt("ItemNotJoinableAmmount"), c.getString("ItemName"), c.getStringList("ItemNotJoinableLore"));
            List<String> lore = Lists.newArrayList();
            for(String s : item.getItemMeta().getLore()) {
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                      .replace("<Motd>", server.getMotd()));
            }
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);

            if(c.getBoolean("ItemNotJoinableGlow"))
              addEnchantmentGlow(item);

            inv.setOption(c.getInt("slot"), item);

          }
        } else {
          item = getitem(c.getString("ItemOfflineID"), c.getInt("ItemOfflineAmmount"), c.getString("ItemName"), c.getStringList("ItemOfflineLore"));
          List<String> lore = Lists.newArrayList();
          for(String s : item.getItemMeta().getLore()) {
            lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
          }
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                  .replace("<Motd>", server.getMotd()));
          im.setLore(getLore(lore));
          item.setItemMeta(im);

          if(c.getBoolean("ItemOfflineGlow"))
            addEnchantmentGlow(item);

          inv.setOption(c.getInt("slot"), item);
        }
      } catch(Exception ex) {

      }
    }
  }

  public void updateInventory2_0() {
    ConfigurationSection c = null;
    slots.clear();
    slotsNJ.clear();
    SPlayers.clear();
    ItemsSlots.clear();
    ItemsSlotsNJ.clear();
    for(ConfigurationSection it : confSect) {
      try {
        c = it;
        ServersController server = ServersController.getServer(it.getName());
        ItemStack item;
        if(server.isOnline()) {
          if(!(c.getBoolean("ItemNotJoinableIsFull") && server.getCurrentPlayers() >= server.getMaxPlayers()) && !checkMotdContain(server.getMotd(), c.getString("ItemNotJoinableIfMotdContains"))) {
            item = getitem(c.getString("ItemOnlineID"), c.getInt("ItemOnlineAmmount"), c.getString("ItemName"), c.getStringList("ItemOnlineLore"));
            List<String> lore = Lists.newArrayList();
            for(String s : item.getItemMeta().getLore()) {
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                      .replace("<Motd>", server.getMotd()));
            }
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);
            item.setAmount(server.getCurrentPlayers());

            if(c.getBoolean("ItemOnlineGlow"))
              addEnchantmentGlow(item);

            addSlot(item, c, server.getCurrentPlayers());
          } else {
            item = getitem(c.getString("ItemNotJoinableID"), c.getInt("ItemNotJoinableAmmount"), c.getString("ItemName"), c.getStringList("ItemNotJoinableLore"));
            List<String> lore = Lists.newArrayList();
            for(String s : item.getItemMeta().getLore()) {
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                      .replace("<Motd>", server.getMotd()));
            }
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);
            item.setAmount(server.getCurrentPlayers());

            if(c.getBoolean("ItemNotJoinableGlow"))
              addEnchantmentGlow(item);

            addSlotNJ(item,9,c, server.getCurrentPlayers());
          }
        } else {
          item = getitem(c.getString("ItemOfflineID"), c.getInt("ItemOfflineAmmount"), c.getString("ItemName"), c.getStringList("ItemOfflineLore"));
          List<String> lore = Lists.newArrayList();
          for(String s : item.getItemMeta().getLore()) {
            lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                    .replace("<Motd>", server.getMotd()));
          }
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers()))
                  .replace("<Motd>", server.getMotd()));
          im.setLore(getLore(lore));
          item.setItemMeta(im);
          item.setAmount(server.getCurrentPlayers());

          if(c.getBoolean("ItemOfflineGlow"))
            addEnchantmentGlow(item);

          addSlotNJ(item,9, c, server.getCurrentPlayers());
        }
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }
/*
	public void addSlot(ItemStack item) {
		for(int slot = 9; slot < 44; slot++) {
			if(!isBad(slot) && !slots.containsKey(slot) && !slots.containsValue(item)) {
			slots.put(slot, item);
			break;
			}
		}
	}

		public void orderSlots() {
		HashMap<Integer, ItemStack> NEWslots = new HashMap<Integer, ItemStack>();
		NEWslots = (HashMap<Integer, ItemStack>) slots.clone();
		HashMap<Integer, ConfigurationSection> NEWItemsSlots = new HashMap<Integer, ConfigurationSection>();
		NEWItemsSlots = (HashMap<Integer, ConfigurationSection>) ItemsSlots.clone();

		slots.clear();
		ItemsSlots.clear();

		HashMap.sort(arr, Collections.reverseOrder());

		int slot = 0;
		int HighPlayers = 0;
		ConfigurationSection c = null;

		while(!NEWslots.isEmpty()) {
			for(Entry<Integer, ItemStack> sl : NEWslots.entrySet()) {
			int online = SPlayers.get(sl.getValue());

			if(online >= HighPlayers) {
			HighPlayers = online;
			slot = sl.getKey();
			c = NEWItemsSlots.get(slot);
			}
			}

			for(Entry<ItemStack, Integer> sl2 : SPlayers.entrySet()) {
			if(sl2.getValue() == HighPlayers) {
				addSlotOnly(sl2.getKey(), 19, c);
			}
			}
			NEWslots.remove(slot);
			HighPlayers = 0;
			slot = 0;
		}
	}
	*/

  public void orderSlots() {
    LinkedHashMap<Integer, ItemStack> NEWslots = (LinkedHashMap<Integer, ItemStack>) slots.clone();
    LinkedHashMap<Integer, ConfigurationSection> NEWItemsSlots = (LinkedHashMap<Integer, ConfigurationSection>) ItemsSlots.clone();
    LinkedHashMap<ItemStack, Integer> NEWSPlayers = (LinkedHashMap<ItemStack, Integer>) SPlayers.clone();
    slots.clear();
    ItemsSlots.clear();
    SPlayers.clear();

    int added = 0;
    int slot = 0;
    int HighPlayers = 0;
    ConfigurationSection c = null;
    ItemStack is = null;
    while(!NEWslots.isEmpty()) {
      for(Entry<Integer, ItemStack> sl : NEWslots.entrySet()) {
        int online = NEWSPlayers.get(sl.getValue());

        if(online >= HighPlayers) {
          HighPlayers = online;
          slot = sl.getKey();
          c = NEWItemsSlots.get(slot);
          is = sl.getValue();
        }
      }

      if(!ItemsSlots.values().contains(c)) { //nuevo
        addSlotOnly(is, 19, c);
        added++;
        if(added >= 7) {
          NEWslots.clear();
          break;
        }
      }
      NEWslots.remove(slot);
      HighPlayers = 0;
      slot = 0;

    }
  }
  public void addSlot(ItemStack item, ConfigurationSection c, int players) {
    int slot = 19;

    if(isBad(slot) || slots.containsKey(slot)) {
      addSlot(item, slot + 1, c, players);
      return;
    }
    SPlayers.put(item, players);
    slots.put(slot, item);
    ItemsSlots.put(slot, c);
  }

  public void addSlot(ItemStack item, int slot, ConfigurationSection c, int players) {
    if(slot > 25)
      return;

    if(isBad(slot) || slots.containsKey(slot)) {
      addSlot(item, slot + 1, c, players);
      return;
    }
    SPlayers.put(item, players);
    slots.put(slot, item);
    ItemsSlots.put(slot, c);
  }

  public void addSlotOnly(ItemStack item, int slot, ConfigurationSection c) {
    if(slot > 25)
      return;

    if(isBad(slot) || slots.containsKey(slot)) {
      addSlotOnly(item, slot + 1, c);
      return;
    }
    slots.put(slot, item);
    ItemsSlots.put(slot, c);
  }

  public void addSlotNJ(ItemStack item, int slot, ConfigurationSection c, int players) {
    if(slot > 53)
      return;

    if(slotsNJ.containsKey(slot) || slotsNJ.containsValue(slot)) {
      addSlotNJ(item, slot + 1, c, players);
      return;
    }

    slotsNJ.put(slot, item);
    ItemsSlotsNJ.put(slot, c);
  }

  public boolean isCustom() {
    return customStyle;
  }

  public void reloadMob() {
    String s = configName;
    config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
    configServers = config.getConfigurationSection("Servers");
    name = Colorize(config.getString("name"));
    loc = getLocation(config.getString("location"));
    entity = config.getString("mobtype");
    InvSize = InvSize(config.getInt("InventorySize"));
    InvTitle = Colorize(config.getString("InventoryTitle"));
    customStyle = config.getBoolean("CustomStyle");
    MobID = config.getString("MobID");
    Mob.put(s.toLowerCase(), this);

    ent.remove();

    createInventory();
    SpawnMob();
  }

  private boolean isBad(int n) {
    switch(n) {
      case 9: return true;
      case 17: return true;
      case 18: return true;
      case 26: return true;
      case 27: return true;
      case 35: return true;
      case 36: return true;
      default: return false;
    }
  }

  public void sendToServer(int i, Player p) {
    if(ItemsSlots.containsKey(i))
      LobbyMain.sendPlayerToServer(p, ServersController.getServer(ItemsSlots.get(i).getName()).getBungeeCordServerName(), ServersController.getServer(ItemsSlots.get(i).getName()).getName());
  }

  public void sendToServerNJ(int i, Player p) {
    if(ItemsSlotsNJ.containsKey(i))
      LobbyMain.sendPlayerToServer(p, ServersController.getServer(ItemsSlotsNJ.get(i).getName()).getBungeeCordServerName(), ServersController.getServer(ItemsSlotsNJ.get(i).getName()).getName());
  }

  public static void updateAllMobs() {
    for(Entry<String, MobsController> mbs : getAll().entrySet()) {
      try {
        MobsController mb = mbs.getValue();
        if(!mb.isCustom()) {
          mb.updateInventory2_0();
          mb.inv2_0.clear();
          mb.invnj.clear();
          mb.orderSlots();
          for(Entry<Integer, ItemStack> k : ((LinkedHashMap<Integer,ItemStack>) mb.slots.clone()).entrySet()) {
            try {
              mb.inv2_0.setOption(k.getKey(), k.getValue());
            } catch(Exception ex) {
              ex.printStackTrace();
            }
          }

          for(Entry<Integer, ItemStack> kNJ : ((LinkedHashMap<Integer, ItemStack>) mb.slotsNJ.clone()).entrySet()) {
            try {
              mb.invnj.setOption(kNJ.getKey(), kNJ.getValue());
            } catch(Exception ex) {
              ex.printStackTrace();
            }
          }
          List<String> list = Lists.newArrayList();
          int sl = mb.slotsNJ.size();
          if(sl <= 0)
            sl = 1;
          ItemStack item = getitem("35:4", sl, Colorize(ConfigController.getInstance().getDefaultConfig().getConfigurationSection("Messages").getString("GamesInProgress")), list);
          if(mb.ItemsSlots.containsKey(19))
            item = getitem(mb.ItemsSlots.get(19).getString("ItemNotJoinableID"), sl, Colorize(LobbyMain.getInstance().getConfig().getConfigurationSection("Messages").getString("GamesInProgress")), list);
          mb.inv2_0.setOption(40, item);

          ItemStack item2 = getitem(ConfigController.getInstance().getDefaultConfig().getString("ItemGoBack"), 1, Colorize(ConfigController.getInstance().getDefaultConfig().getConfigurationSection("Messages").getString("MenuGoBack")), list);
          mb.invnj.setOption(4, item2);
        } else {
          mb.updateInventory();
        }
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static HashMap<String, MobsController> getAll() {
    return Mob;
  }

  public static void onDisable() {
    for(LivingEntity ent : getAllEntities().keySet()) {
      ent.remove();
    }
  }

  public static HashMap<Entity,Location> getAllEntitiesLocations() {
    return locs;
  }

  public static HashMap<LivingEntity, MobsController> getAllEntities() {
    return Ents;
  }

  public LinkedHashMap<Integer, ConfigurationSection> getItemsSlots() {
    return ItemsSlots;
  }

  public LinkedHashMap<Integer, ConfigurationSection> getItemsSlotsNJ() {
    return ItemsSlotsNJ;
  }

  private static Location getLocation(String s) {
    String[] Locs = s.split(",");
    World w = Bukkit.getWorld(Locs[0]);
    double x = Double.valueOf(Locs[1].replace(" ", ""));
    double y = Double.valueOf(Locs[2].replace(" ", ""));
    double z = Double.valueOf(Locs[3].replace(" ", ""));
    float yaw = Float.valueOf(Locs[4].replace(" ", ""));
    float pitch = Float.valueOf(Locs[5].replace(" ", ""));

    return new Location(w, x, y, z, yaw, pitch);
  }

  private static int InvSize(Integer i) {
    switch(i) {
      case 9: break;
      case 18: break;
      case 27: break;
      case 36: break;
      case 45: break;
      case 54: break;
      default: i = 54;
    }
    return i;
  }

  private static Entity getEntity(Location loc, String s) {
    Entity ent = null;
    if(s.contains(":")) {
      String[] e = s.split(":");
      String entityType = e[0];

      ent = loc.getWorld().spawnEntity(loc, EntityType.fromName(entityType));

      if(ent.getType() == EntityType.HORSE) {
        if(s.toUpperCase().contains("DONKEY"))
          ((Horse) ent).setVariant(Variant.DONKEY);
        else if(s.toUpperCase().contains("MULE"))
          ((Horse) ent).setVariant(Variant.MULE);
        else if(s.toUpperCase().contains("SKELETON_HORSE"))
          ((Horse) ent).setVariant(Variant.SKELETON_HORSE);
        else if(s.toUpperCase().contains("UNDEAD_HORSE"))
          ((Horse) ent).setVariant(Variant.UNDEAD_HORSE);
      } else if(ent.getType() == EntityType.OCELOT) {
        if(s.toUpperCase().contains("BLACK_CAT"))
          ((Ocelot) ent).setCatType(Type.BLACK_CAT);
        else if(s.toUpperCase().contains("RED_CAT"))
          ((Ocelot) ent).setCatType(Type.RED_CAT);
        else if(s.toUpperCase().contains("SIAMESE_CAT"))
          ((Ocelot) ent).setCatType(Type.SIAMESE_CAT);
      } else if(ent.getType() == EntityType.SKELETON) {
        if(s.toUpperCase().contains("WITHER"))
          ((Skeleton) ent).setSkeletonType(SkeletonType.WITHER);
      } else if(ent.getType() == EntityType.SHEEP) {
        if(s.toUpperCase().contains("COLOR=")) {
          String color = "WHITE";
          if(e[1].toUpperCase().contains("COLOR="))
            color = e[1].split("LOR=")[1];
          else if(e[1].toUpperCase().contains("COLOR="))
            color = e[2].split("LOR=")[1];

          ((Colorable) ent).setColor(DyeColor.valueOf(color));
        }
      }

      if(s.toUpperCase().contains("SIZE") && (ent.getType() == EntityType.SLIME || ent.getType() == EntityType.MAGMA_CUBE)) {
        if(s.contains("4"))
          ((Slime) ent).setSize(4);
        else if(s.contains("10"))
          ((Slime) ent).setSize(10);
        else if(s.contains("16"))
          ((Slime) ent).setSize(16);

      }

      if(s.toUpperCase().contains("BABY"))
        ((Ageable) ent).setBaby();

    } else {
      ent = loc.getWorld().spawnEntity(loc, EntityType.fromName(s));
    }

    return ent;
  }

  private static ItemStack getitem(String s, int ammount, String DisplayName, List<String> Lore) {
    if(ammount <= 0)
      ammount = 1;
    String[] itemString = s.split(":");
    int ItemID = Integer.valueOf(itemString[0]);
    byte ItemByte = Byte.valueOf(itemString[1]);
    ItemStack item = new ItemStack(Material.getMaterial(ItemID), ammount, ItemByte);
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(Colorize(DisplayName));
    List<String> newLore = Lists.newArrayList();
    for(String str : Lore) {
      if(str.contains("==")) {
        for(String str2 : str.split("==")) {
          newLore.add(Colorize(str2));
        }
      } else {
        newLore.add(Colorize(str));
      }
    }
    im.setLore(Colorize(newLore));
    item.setItemMeta(im);
    return item;
  }

  private static ItemStack getitem(int id, int b, int ammount, String DisplayName, List<String> Lore) {
    if(ammount <= 0)
      ammount = 1;
    int ItemID = id;
    byte ItemByte = (byte) b;
    ItemStack item = new ItemStack(Material.getMaterial(ItemID), ammount, ItemByte);
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(Colorize(DisplayName));
    List<String> newLore = Lists.newArrayList();
    for(String str : Lore) {
      if(str.contains("==")) {
        for(String str2 : str.split("==")) {
          newLore.add(Colorize(str2));
        }
      } else {
        newLore.add(Colorize(str));
      }
    }
    im.setLore(Colorize(newLore));
    item.setItemMeta(im);
    return item;
  }

  private static List<String> getLore(String s) {
    List<String> lore = Lists.newArrayList();

    for(String t : s.split("==")) {
      lore.add(t);
    }

    return lore;
  }

  private static List<String> getLore(List<String> slore) {
    List<String> lore = Lists.newArrayList();

    for(String s : slore) {
      for(String t : s.split("==")) {
        lore.add(t);
      }
    }
    return lore;
  }

  private static boolean checkMotdContain(String motd, String contain) {
    if(motd.isEmpty())
      return false;
    for(String cont : contain.split(","))
      if(motd.toLowerCase().contains(cont.toLowerCase()))
        return true;

    return false;
  }

  private static String Colorize(String s) {
    if(s == null)
      return null;

    return ChatColor.translateAlternateColorCodes('&', s);
  }

  private static List<String> Colorize(List<String> s) {
    List<String> txt = Lists.newArrayList();
    for(String t : s) {
      txt.add(Colorize(t));
    }
    return txt;
  }

  public static void addEnchantmentGlow(ItemStack item) {
    Enchantment glow = EnchantGlow.getGlow();

    item.addEnchantment(glow , 1);
  }

  public static class EnchantGlow extends EnchantmentWrapper {
    private static Enchantment glow;

    public EnchantGlow(int id ) {
      super(id);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
      return true;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
      return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
      return null;
    }

    @Override
    public int getMaxLevel() {
      return 10;
    }

    @Override
    public String getName() {
      return "Glow";
    }

    @Override
    public int getStartLevel() {
      return 1;
    }

    public static Enchantment getGlow() {
      if ( glow != null )
        return glow;

      try {
        Field f = Enchantment.class.getDeclaredField("acceptingNew");
        f.setAccessible(true);
        f.set(null , true);
      } catch (Exception e) {
        e.printStackTrace();
      }

      glow = new EnchantGlow(255);
      Enchantment.registerEnchantment(glow);
      return glow;
    }

    public static void addGlow(ItemStack item) {
      Enchantment glow = getGlow();

      item.addEnchantment(glow , 1);
    }

  }

}