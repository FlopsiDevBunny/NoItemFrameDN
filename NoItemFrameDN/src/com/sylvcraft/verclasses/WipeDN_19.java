package com.sylvcraft.verclasses;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.sylvcraft.NoItemFrameDN;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;

public class WipeDN_19 {
	NoItemFrameDN plugin;
	ItemStack wipedItem;
	
	public WipeDN_19(NoItemFrameDN plugin, ItemStack item) {
		this.plugin = plugin;
		wipeDisplayName(item);
	}
	
	public ItemStack getWipedItem() {
		return wipedItem;
	}
	 
  public void wipeDisplayName(ItemStack i) {
	  net.minecraft.server.v1_9_R1.ItemStack nms = CraftItemStack.asNMSCopy(i);
	  NBTTagCompound tag;
	  if (nms.hasTag()) {
	    tag = nms.getTag();
	  } else {
	    nms.setTag(new NBTTagCompound());
	    tag = nms.getTag();
	  }
	  tag.setString("origDN", i.getItemMeta().getDisplayName());
	  wipedItem = CraftItemStack.asCraftMirror(nms);
	  ItemMeta im = wipedItem.getItemMeta();
	  im.setDisplayName(null);
	  wipedItem.setItemMeta(im);
  }
	
	public Map<String, String> getServerVersion() {
		Map<String, String> retval = new HashMap<String, String>();
		String[] tmp = Bukkit.getVersion().replace(")", "").split("MC: ");
		if (tmp.length != 2) return retval;
		String[] verdata = tmp[1].split("\\.");
		retval.put("root", verdata[0] + "." + verdata[1]);
		retval.put("full", StringUtils.join(verdata, " ", 0, verdata.length));
		return retval;
	}
}
