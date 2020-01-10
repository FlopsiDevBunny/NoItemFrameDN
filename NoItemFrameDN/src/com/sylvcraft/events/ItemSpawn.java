package com.sylvcraft.events;

import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.sylvcraft.NoItemFrameDN;

import net.minecraft.server.v1_14_R1.NBTTagCompound;

import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemSpawn implements Listener {
  NoItemFrameDN plugin;
  
  public ItemSpawn(NoItemFrameDN instance) {
    plugin = instance;
  }

  @EventHandler
  public void onItemSpawn(ItemSpawnEvent e) {
    if (e.getEntity().getType() != EntityType.DROPPED_ITEM) return;
    ItemStack i = e.getEntity().getItemStack();
    net.minecraft.server.v1_14_R1.ItemStack nms = CraftItemStack.asNMSCopy(i);
    NBTTagCompound tag;
    if (!nms.hasTag()) return;

    tag = nms.getTag();
    if (!tag.hasKey("origDN")) return;

    ItemMeta im = i.getItemMeta();
    im.setDisplayName(tag.getString("origDN"));
    i.setItemMeta(im);
    e.getEntity().setItemStack(i);
  }
}