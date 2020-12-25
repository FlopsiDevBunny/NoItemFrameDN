package com.sylvcraft.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import com.sylvcraft.NoItemFrameDN;

public class ItemSpawn implements Listener {
  NoItemFrameDN plugin;
  
  public ItemSpawn(NoItemFrameDN instance) {
    plugin = instance;
  }

  @EventHandler
  public void onItemSpawn(ItemSpawnEvent e) {
    if (e.getEntity().getType() != EntityType.DROPPED_ITEM) return;
    
    ItemStack i = plugin.recallname(e.getEntity().getItemStack());
    if (i == null) return;
    
    e.getEntity().setItemStack(i);
  }
}