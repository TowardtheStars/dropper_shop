package com.piggest.minecraft.bukkit.grinder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.piggest.minecraft.bukkit.material_ext.Material_ext;

public class Powder {
	public static void init_powder() {
		Powder.register_powder("铁粉");
		Powder.register_powder("金粉");
		Powder.register_powder("煤粉");
		Powder.register_powder("青金石粉");
		Powder.register_powder("铜粉");
		Powder.register_powder("铝粉");
		Powder.register_powder("锡粉");
		Powder.register_powder("银粉");
		Powder.register_powder("青铜粉");
		Powder.register_powder("绿宝石粉");
		Powder.register_powder("面粉");
	}

	public static void register_powder(String name) {
		ItemStack item = new ItemStack(Material.SUGAR);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("§r" + name);
		item.setItemMeta(itemmeta);
		Material_ext.register(item);
	}

	public static boolean is_powder(ItemStack item, String name) {
		if (item.getType() != Material.SUGAR) {
			return false;
		}
		return Material_ext.get_name(item).equals(name);
	}
}
