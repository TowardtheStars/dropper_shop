package com.piggest.minecraft.bukkit.grinder;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.piggest.minecraft.bukkit.dropper_shop.Dropper_shop_plugin;
import com.piggest.minecraft.bukkit.material_ext.Material_ext;

public class Powder {
	public final static int powder_model_offset = 100;
	private String material_name;
	private String chinese_name;

	public String get_material_name() {
		return this.material_name;
	}

	public String get_chinese_name() {
		return this.chinese_name;
	}

	public Powder(String material_name, String chinese_name) {
		this.material_name = material_name;
		this.chinese_name = chinese_name;
	}

	public final static ArrayList<Powder> powder_config = new ArrayList<>();

	public static void init_powder_config() {
		powder_config.add(null);
		powder_config.add(new Powder("iron", "铁粉"));
		powder_config.add(new Powder("gold", "金粉"));
		powder_config.add(new Powder("coal", "煤粉"));
		powder_config.add(new Powder("lapis", "青金石粉"));
		powder_config.add(new Powder("copper", "铜粉"));
		powder_config.add(new Powder("aluminium", "铝粉"));
		powder_config.add(new Powder("tin", "锡粉"));
		powder_config.add(new Powder("silver", "银粉"));
		powder_config.add(new Powder("bronze", "青铜粉"));
		powder_config.add(new Powder("emerald", "绿宝石粉"));
		powder_config.add(new Powder("flour", "面粉"));
	}

	public static void init_powder() {
		init_powder_config();
		for (int i = 1; i < powder_config.size(); i++) {
			Powder.register_powder(powder_config.get(i).material_name + "_powder", powder_config.get(i).chinese_name,
					i);
		}
	}

	public static void register_powder(String id_name, String name, int powder_id) {
		ItemStack item = new ItemStack(Material.SUGAR);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("§r" + name);
		itemmeta.setCustomModelData(Dropper_shop_plugin.custom_model_data_offset + powder_model_offset + powder_id);
		item.setItemMeta(itemmeta);
		Material_ext.register(id_name, item);
	}
	
	public static boolean is_powder(ItemStack item, String id_name) {
		if (item.getType() != Material.SUGAR) {
			return false;
		}
		return Material_ext.get_id_name(item).equalsIgnoreCase(id_name);
	}
}
