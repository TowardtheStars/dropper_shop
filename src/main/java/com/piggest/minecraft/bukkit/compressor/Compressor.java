package com.piggest.minecraft.bukkit.compressor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.piggest.minecraft.bukkit.dropper_shop.Dropper_shop_plugin;
import com.piggest.minecraft.bukkit.grinder.Grinder_io_runner;
import com.piggest.minecraft.bukkit.grinder.Grinder_runner;
import com.piggest.minecraft.bukkit.structure.Auto_io;
import com.piggest.minecraft.bukkit.structure.HasRunner;
import com.piggest.minecraft.bukkit.structure.Multi_block_with_gui;
import com.piggest.minecraft.bukkit.structure.Structure_runner;

public class Compressor extends Multi_block_with_gui implements HasRunner, Auto_io {
	private Compressor_runner runner = new Compressor_runner(this);
	private Compressor_io_runner io_runner = new Compressor_io_runner(this);
	private int piston_storage = 0;

	public static final int raw_slot = 9;
	public static final int piston_slot = 11;
	public static final int product_slot = 13;

	private static final int[][] solid_reactant_hopper_check_list = { { 0, 1, 2 }, { 2, 1, 0 }, { 0, 1, -2 },
			{ -2, 1, 0 } }; // 注入原料
	private static final int[][] fuel_hopper_check_list = { { 0, -1, 2 }, { 2, -1, 0 }, { 0, -1, -2 }, { -2, -1, 0 } }; // 注入活塞单位
	private static final int[][] solid_product_check_list = { { 1, -1, 2 }, { 2, -1, 1 }, { -1, -1, 2 }, { 2, -1, -1 },
			{ 1, -1, -2 }, { -2, -1, 1 }, { -2, -1, -1 }, { -1, -1, -2 } };// 产品自动流出

	@Override
	public void on_button_pressed(Player player, int slot) {
		return;
	}

	@Override
	public boolean on_switch_pressed(Player player, int slot, boolean on) {
		return true;
	}

	@Override
	public boolean on_put_item(Player player, ItemStack cursor_item, int slot) {
		return true;
	}

	@Override
	public boolean on_take_item(Player player, ItemStack in_item, int slot) {
		return true;
	}

	@Override
	public boolean on_exchange_item(Player player, ItemStack in_item, ItemStack cursor_item, int slot) {
		return true;
	}

	@Override
	public boolean create_condition(Player player) {
		int price = Dropper_shop_plugin.instance.get_price_config().get_make_grinder_price();
		if (Dropper_shop_plugin.instance.cost_player_money(price, player)) {
			player.sendMessage("[压缩机]已扣除" + price);
			return true;
		} else {
			player.sendMessage("[压缩机]建立压缩机所需的钱不够，需要" + price);
			return false;
		}
	}

	@Override
	protected void init_after_set_location() {
		return;
	}

	@Override
	protected boolean on_break(Player player) {
		return true;
	}

	@Override
	public ItemStack[] get_drop_items() {
		return new ItemStack[] { this.get_raw(), this.get_piston(), this.get_product() };
	}

	public ItemStack get_product() {
		return this.gui.getItem(product_slot);
	}

	public ItemStack get_piston() {
		return this.gui.getItem(piston_slot);
	}

	public ItemStack get_raw() {
		return this.gui.getItem(raw_slot);
	}

	@Override
	public Structure_runner[] get_runner() {
		return new Structure_runner[] { this.runner, this.io_runner };
	}

	public int get_piston_storage() {
		return this.piston_storage;
	}

	public synchronized void set_piston_storage(int storage) {
		ItemStack flint_info = this.gui.getContents()[17];
		ItemMeta flint_info_meta = flint_info.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r" + storage + " 单位");
		flint_info_meta.setLore(lore);
		flint_info.setItemMeta(flint_info_meta);
		this.piston_storage = storage;
	}
	
	@Override
	protected void set_from_save(Map<?, ?> save) {
		this.set_piston_storage((Integer) save.get("piston-storage"));
		this.runner.working_ticks = (Integer) save.get("working-ticks");
		super.set_from_save(save);
	}
	
	@Override
	protected HashMap<String, Object> get_save() {
		HashMap<String, Object> save = super.get_save();
		save.put("piston-storage", this.get_piston_storage());
		save.put("working-ticks", this.runner.working_ticks);
		return save;
	}
}
