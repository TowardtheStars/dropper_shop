package com.piggest.minecraft.bukkit.structure;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.piggest.minecraft.bukkit.dropper_shop.Dropper_shop_plugin;

public class Structure_listener implements Listener {
	@EventHandler
	public void on_break(BlockBreakEvent event) {
		if (event.isCancelled() == true) {
			return;
		}
		Player player = event.getPlayer();
		Block block = event.getBlock();
		HashMap<Class<? extends Structure>, Structure_manager> structure_manager = Dropper_shop_plugin.instance
				.get_structure_manager();
		for (Entry<Class<? extends Structure>, Structure_manager> entry : structure_manager.entrySet()) {
			Structure_manager manager = entry.getValue();
			Structure structure = manager.find(null, block.getLocation(), false);
			if (structure != null) {
				if (structure.on_break(player) == false) {
					event.setCancelled(true);
				} else {
					player.sendMessage(structure.getClass().getSimpleName() + "结构已被移除");
					structure.remove();
				}
			}
		}
	}

	@EventHandler
	public void on_right_click(PlayerInteractEvent event) {
		if (event.useItemInHand() == Result.DENY || event.useInteractedBlock() == Result.DENY) {
			return;
		}
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			HashMap<Class<? extends Structure>, Structure_manager> structure_manager = Dropper_shop_plugin.instance
					.get_structure_manager();
			for (Entry<Class<? extends Structure>, Structure_manager> entry : structure_manager.entrySet()) {
				Structure_manager manager = entry.getValue();
				Structure structure = manager.find(player.getName(), block.getLocation(), false);
				if (structure != null && player.isSneaking() == false) {
					if (structure instanceof Multi_block_structure) {
						((Multi_block_structure) structure).on_right_click(player);
						event.setCancelled(true);
					}
				}
			}
		}
	}
}