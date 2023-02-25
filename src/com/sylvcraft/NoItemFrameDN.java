package com.sylvcraft;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.sylvcraft.commands.nodn;
import com.sylvcraft.events.ItemSpawn;
import com.sylvcraft.events.PlayerInteractEntity;
import com.sylvcraft.verclasses.Functions_110;
import com.sylvcraft.verclasses.Functions_111;
import com.sylvcraft.verclasses.Functions_112;
import com.sylvcraft.verclasses.Functions_113;
import com.sylvcraft.verclasses.Functions_18;
import com.sylvcraft.verclasses.Functions_19;

public class NoItemFrameDN extends JavaPlugin {
  @Override
  public void onEnable() {
    saveDefaultConfig();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new PlayerInteractEntity(this), this);
    pm.registerEvents(new ItemSpawn(this), this);
    getCommand("nodn").setExecutor(new nodn(this));
  }

  public ItemStack recallname(ItemStack item) {
    switch (getServerVersion().get("root")) {
    case "1.13":
      Functions_113 f113 = new Functions_113(this, item);
      f113.renameItem();
      return f113.getItemStack();
    case "1.12":
      Functions_112 f112 = new Functions_112(this, item);
      f112.renameItem();
      return f112.getItemStack();
    case "1.11":
      Functions_111 f111 = new Functions_111(this, item);
      f111.renameItem();
      return f111.getItemStack();
    case "1.10":
      Functions_110 f110 = new Functions_110(this, item);
      f110.renameItem();
      return f110.getItemStack();
    case "1.9":
      Functions_19 f19 = new Functions_19(this, item);
      f19.renameItem();
      return f19.getItemStack();
    case "1.8":
      Functions_18 f18 = new Functions_18(this, item);
      f18.renameItem();
      return f18.getItemStack();
    default:
      ItemMeta im = item.getItemMeta();
      if (!im.hasDisplayName()) return item;
      
      NamespacedKey origDN = new NamespacedKey(this, "origDN");
      im.getPersistentDataContainer().set(origDN, PersistentDataType.STRING, im.getDisplayName());
      im.setDisplayName("");
      item.setItemMeta(im);
      return item;
    }
  }
  
  public ItemStack wipeDisplayName(ItemStack item) {
    if (!item.hasItemMeta()) return item;
    
    ItemMeta im = item.getItemMeta();
    if (!im.hasDisplayName()) return item;
    
    switch (getServerVersion().get("root")) {
    case "1.13":
      Functions_113 f113 = new Functions_113(this, item);
      f113.wipeDisplayName();
      return f113.getItemStack();
    case "1.12":
      Functions_112 f112 = new Functions_112(this, item);
      f112.wipeDisplayName();
      return f112.getItemStack();
    case "1.11":
      Functions_111 f111 = new Functions_111(this, item);
      f111.wipeDisplayName();
      return f111.getItemStack();
    case "1.10":
      Functions_110 f110 = new Functions_110(this, item);
      f110.wipeDisplayName();
      return f110.getItemStack();
    case "1.9":
      Functions_19 f19 = new Functions_19(this, item);
      f19.wipeDisplayName();
      return f19.getItemStack();
    case "1.8":
      Functions_18 f18 = new Functions_18(this, item);
      f18.wipeDisplayName();
      return f18.getItemStack();
    default:
      NamespacedKey origDN = new NamespacedKey(this, "origDN");
      im.getPersistentDataContainer().set(origDN, PersistentDataType.STRING, im.getDisplayName());
      im.setDisplayName("");
      item.setItemMeta(im);
      return item;
    }
  }
  
  private Map<String, String> getServerVersion() {
    Map<String, String> retval = new HashMap<String, String>();
    String[] tmp = Bukkit.getVersion().replace(")", "").split("MC: ");
    if (tmp.length != 2) return retval;
    String[] verdata = tmp[1].split("\\.");
    retval.put("root", verdata[0] + "." + verdata[1]);
    retval.put("full", StringUtils.join(verdata, " ", 0, verdata.length));
    return retval;
  }
  
  public boolean isEnabled(Player p) {
    return getConfig().getBoolean("players." + p.getUniqueId() + ".status", getConfig().getBoolean("config.default", false));
  }
  
  public boolean toggleEnabled(Player p) {
    if (isEnabled(p)) {
      getConfig().set("players." + p.getUniqueId() + ".status", false);
      saveConfig();
      return false;
    }

    getConfig().set("players." + p.getUniqueId() + ".status", true);
    saveConfig();
    return true;
  }
  
  public void msg(String msgCode, CommandSender sender) {
    if (getConfig().getString("messages." + msgCode) == null) return;
    msgTransmit(getConfig().getString("messages." + msgCode), sender);
  }

  public void msg(String msgCode, CommandSender sender, Map<String, String> data) {
    if (getConfig().getString("messages." + msgCode) == null) return;
    String tmp = getConfig().getString("messages." + msgCode, msgCode);
    for (Map.Entry<String, String> mapData : data.entrySet()) {
      tmp = tmp.replace(mapData.getKey(), mapData.getValue());
    }
    msgTransmit(tmp, sender);
  }
  
  public void msgTransmit(String msg, CommandSender sender) {
    for (String m : (msg + " ").split("%br%")) {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }
  }
}
