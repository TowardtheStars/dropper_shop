package com.piggest.minecraft.bukkit.dropper_shop;

import org.bukkit.scheduler.BukkitRunnable;

public class Config_auto_saver extends BukkitRunnable {
	Dropper_shop_plugin plugin = null;
	
	public Config_auto_saver(Dropper_shop_plugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.save_structure();
	}
}
