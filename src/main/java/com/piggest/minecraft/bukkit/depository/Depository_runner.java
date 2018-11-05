package com.piggest.minecraft.bukkit.depository;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.piggest.minecraft.bukkit.structure.Structure_manager;

import net.milkbowl.vault.economy.Economy;

public class Depository_runner extends BukkitRunnable {
	private Depository depository = null;

	public Depository_runner(Depository depository) {
		this.depository = depository;
	}

	public void run() {
		int price = depository.get_price();
		Economy economy = Structure_manager.plugin.get_economy();
		if (economy.has(depository.get_owner(), price)) {
			if (depository.get_owner().isOnline() == true) {
				((Player) depository.get_owner()).sendMessage("存储器已扣除" + price);
			}
			economy.withdrawPlayer(depository.get_owner(), price);
			depository.set_accessible(true);
		} else {
			depository.set_accessible(false);
		}
	}

}
