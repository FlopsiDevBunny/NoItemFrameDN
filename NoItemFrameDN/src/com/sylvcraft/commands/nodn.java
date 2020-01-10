package com.sylvcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
      
      boolean result = plugin.toggleEnabled((Player)sender);
      plugin.msg("nodn-" + (result?"enabled":"disabled"), sender);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
