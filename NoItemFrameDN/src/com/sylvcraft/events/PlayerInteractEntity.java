package com.sylvcraft.events;

import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.sylvcraft.NoItemFrameDN;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteractEntity implements Listener {
  NoItemFrameDN plugin;
  
  public PlayerInteractEntity(NoItemFrameDN instance) {
    plugin = instance;
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
    if (!(e.getRightClicked() instanceof ItemFrame)) return;
    if (!plugin.isEnabled(e.getPlayer())) return;

    ItemFrame frame = (ItemFrame)e.getRightClicked();
    new BukkitRunnable() {
      @Override
      public void run() {
        ItemStack stck = frame.getItem();
        if (!stck.hasItemMeta()) return;
        if (!stck.getItemMeta().hasDisplayName()) return;
        ItemStack wipedItem = plugin.wipeDisplayName(frame.getItem());
        if (wipedItem == null) this.cancel();
        frame.setItem(wipedItem);
      }
    }.runTaskLater(plugin, 1);
  }
}