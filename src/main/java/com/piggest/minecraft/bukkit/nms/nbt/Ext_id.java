package com.piggest.minecraft.bukkit.nms.nbt;

import org.bukkit.inventory.ItemStack;

public interface Ext_id {
	public String get_ext_id(ItemStack item);

	public boolean has_ext_id(ItemStack item);

	public ItemStack set_ext_id(ItemStack item, String ext_id);
}
