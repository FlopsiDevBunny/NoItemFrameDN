package com.sylvcraft.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sylvcraft.NoItemFrameDN;

public class nodn implements CommandExecutor {
  NoItemFrameDN plugin;
  
  public nodn(NoItemFrameDN instance) {
    plugin = instance;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    try {
      if (!(sender instanceof Player)) {
        plugin.msg("player-only", null);
        return true;
      }

      if (!sender.hasPermission("noitemframedn.use")) {
        plugin.msg("access-denied", sender);
        return true;
      }

      if (args.length > 0) {
        ItemStack i = new ItemStack(Material.IRON_SWORD);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("Sandai Kitetsu");
        i.setItemMeta(im);
        ((Player)sender).getInventory().addItem(i);
        ((Player)sender).getInventory().addItem(new ItemStack(Material.ITEM_FRAME));
      }
      
      boolean result = plugin.toggleEnabled((Player)sender);
      plugin.msg("nodn-" + (result?"enabled":"disabled"), sender);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
