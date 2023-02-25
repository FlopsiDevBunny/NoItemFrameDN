package com.sylvcraft.verclasses;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.sylvcraft.NoItemFrameDN;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Functions_18 {
  NoItemFrameDN plugin;
  ItemStack item;
    
  public Functions_18(NoItemFrameDN plugin, ItemStack item) {
    this.plugin = plugin;
    this.item = item;
  }
    
  public ItemStack getItemStack() {
    return item;
  }

  public ItemStack renameItem() {
    net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag;
    if (!nms.hasTag()) return null;

    tag = nms.getTag();
    if (!tag.hasKey("origDN")) return null;

    String itemDN = tag.getString("origDN");
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(itemDN);
    item.setItemMeta(im);
    return item;
  }
  
  public void wipeDisplayName() {
    net.minecraft.server.v1_8_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag;
    if (nms.hasTag()) {
      tag = nms.getTag();
    } else {
      nms.setTag(new NBTTagCompound());
      tag = nms.getTag();
    }

    if (!item.hasItemMeta()) plugin.getLogger().info("No itemmeta");
    if (!item.getItemMeta().hasDisplayName()) plugin.getLogger().info("No displayname");
    tag.setString("origDN", item.getItemMeta().getDisplayName());
    item = CraftItemStack.asCraftMirror(nms);
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(null);
    item.setItemMeta(im);
  }
}
