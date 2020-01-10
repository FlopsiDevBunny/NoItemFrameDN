package com.sylvcraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import com.sylvcraft.events.ItemSpawn;
import com.sylvcraft.events.PlayerInteractEntity;
import com.sylvcraft.verclasses.WipeDN_110;
import com.sylvcraft.verclasses.WipeDN_111;
import com.sylvcraft.verclasses.WipeDN_112;
import com.sylvcraft.verclasses.WipeDN_113;
import com.sylvcraft.verclasses.WipeDN_114;
import com.sylvcraft.verclasses.WipeDN_115;
import com.sylvcraft.verclasses.WipeDN_18;
import com.sylvcraft.verclasses.WipeDN_19;
import com.sylvcraft.commands.nodn;

public class NoItemFrameDN extends JavaPlugin {
  private List<String> enabledUsers = new ArrayList<>();
  
  @Override
  public void onEnable() {
    saveDefaultConfig();
    enabledUsers = getConfig().getStringList("enabled");

    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new PlayerInteractEntity(this), this);
    pm.registerEvents(new ItemSpawn(this), this);
    getCommand("nodn").setExecutor(new nodn(this));
  }

  public ItemStack wipeDisplayName(ItemStack item) {
    switch (getServerVersion().get("root")) {
    case "1.15":
      WipeDN_115 wipe115 = new WipeDN_115(this, item);
      return wipe115.getWipedItem();
    case "1.14":
      WipeDN_114 wipe114 = new WipeDN_114(this, item);
      return wipe114.getWipedItem();
    case "1.13":
      WipeDN_113 wipe113 = new WipeDN_113(this, item);
      return wipe113.getWipedItem();
    case "1.12":
      WipeDN_112 wipe112 = new WipeDN_112(this, item);
      return wipe112.getWipedItem();
    case "1.11":
      WipeDN_111 wipe111 = new WipeDN_111(this, item);
      return wipe111.getWipedItem();
    case "1.10":
      WipeDN_110 wipe110 = new WipeDN_110(this, item);
      return wipe110.getWipedItem();
    case "1.9":
      WipeDN_19 wipe19 = new WipeDN_19(this, item);
      return wipe19.getWipedItem();
    case "1.8":
      WipeDN_18 wipe18 = new WipeDN_18(this, item);
      return wipe18.getWipedItem();
    default:
      return null;
    }
  }
  
  public boolean isEnabled(Player p) {
    return enabledUsers.contains(p.getUniqueId().toString());
  }
  
  public boolean toggleEnabled(Player p) {
    if (enabledUsers.contains(p.getUniqueId().toString())) {
      enabledUsers.remove(p.getUniqueId().toString());
      updateConfig();
      return false;
    }
    
    enabledUsers.add(p.getUniqueId().toString());
    updateConfig();
    return true;
  }
  
  private void updateConfig() {
    getConfig().set("enabled", enabledUsers);
    saveConfig();
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