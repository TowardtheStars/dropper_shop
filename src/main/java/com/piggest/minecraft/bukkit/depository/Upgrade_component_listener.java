package com.piggest.minecraft.bukkit.depository;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class Upgrade_component_listener implements Listener {
	@EventHandler
	public void on_update(CraftItemEvent event) {
		if (event.isCancelled() == true) {
			return;
		}
		CraftingInventory inventory = event.getInventory();
		ItemStack[] res_list = inventory.getContents();
		for (int i = 1; i < res_list.length; i++) {
			if (Reader.is_reader(res_list[i])) {
				event.getWhoClicked().sendMessage(i + ":不允许使用读取器代替原物品合成");
				event.setCancelled(true);
				return;
			}
		}
		ItemStack item = event.getRecipe().getResult();
		if (Upgrade_component.is_component(item)) { // 是升级组件
			int level = Upgrade_component.get_level(item);
			if (level == 1) {
				return;
			}
			ItemStack basis = res_list[5];
			if (!Upgrade_component.is_component(basis)) {
				event.getWhoClicked().sendMessage("必须使用升级组件合成，而你使用的是" + basis.getType().name());
				event.setCancelled(true);
				return;
			}
			if (Upgrade_component.get_level(basis) != level - 1) {
				event.getWhoClicked().sendMessage("必须使用" + (level - 1) + "级升级组件合成" + level + "级升级组件");
				event.setCancelled(true);
				return;
			}
		}
	}
}
