package com.piggest.minecraft.bukkit.grinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.piggest.minecraft.bukkit.structure.Multi_block_structure;

public class Grinder extends Multi_block_structure implements InventoryHolder {
	public static HashMap<Material, ItemStack> recipe = new HashMap<Material, ItemStack>();
	public static HashMap<Material, Integer> recipe_time = new HashMap<Material, Integer>();
	private Inventory gui = Bukkit.createInventory(this, 18, "磨粉机");
	private Grinder_runner runner = new Grinder_runner(this);
	private Grinder_io_runner io_runner = new Grinder_io_runner(this);

	public Grinder() {
		this.gui.setItem(10, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
		this.gui.setItem(12, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
		this.gui.setItem(14, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
		this.gui.setItem(16, new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
		ItemStack flint_info = new ItemStack(Material.FLINT);
		ItemMeta flint_info_meta = flint_info.getItemMeta();
		flint_info_meta.setDisplayName("§r燧石储量");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r0");
		flint_info_meta.setLore(lore);
		flint_info.setItemMeta(flint_info_meta);
		this.gui.setItem(17, flint_info);
	}

	public int get_flint_storage() {
		int storage = 0;
		ItemStack flint_info = this.gui.getContents()[17];
		ItemMeta flint_info_meta = flint_info.getItemMeta();
		List<String> lore = flint_info_meta.getLore();
		String line = lore.get(0);
		String pattern = "§r([1-9]\\d*|0)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if (m.find()) {
			storage = Integer.parseInt(m.group(1));
			return storage;
		}
		return 0;
	}

	public void set_flint_storge(int storage) {
		ItemStack flint_info = this.gui.getContents()[17];
		ItemMeta flint_info_meta = flint_info.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§r" + storage);
		flint_info_meta.setLore(lore);
		flint_info.setItemMeta(flint_info_meta);
	}

	public Inventory get_gui() {
		return this.gui;
	}

	public ItemStack get_raw() {
		return this.gui.getContents()[9];
	}

	public ItemStack get_flint() {
		return this.gui.getContents()[11];
	}

	public ItemStack get_product() {
		return this.gui.getContents()[13];
	}

	public ItemStack get_product_2() {
		return this.gui.getContents()[15];
	}

	public void set_product(ItemStack product_item) {
		this.gui.setItem(13, product_item);
	}

	private static void add_recipe(Material material, Material out, int num, int time) {
		Grinder.recipe.put(material, new ItemStack(out, num));
		Grinder.recipe_time.put(material, time);
	}

	private static void add_recipe(Material material, ItemStack item, int time) {
		Grinder.recipe.put(material, item);
		Grinder.recipe_time.put(material, time);
	}

	public static void init_recipe() {
		Grinder.add_recipe(Material.COBBLESTONE, Material.GRAVEL, 2, 600); // 圆石
		Grinder.add_recipe(Material.GRAVEL, Material.SAND, 2, 200); // 砂砾
		Grinder.add_recipe(Material.SANDSTONE, Material.SAND, 4, 400); // 砂石
		Grinder.add_recipe(Material.RED_SANDSTONE, Material.RED_SAND, 4, 400); // 红砂石
		Grinder.add_recipe(Material.STONE, Material.COBBLESTONE, 1, 300); // 石块
		Grinder.add_recipe(Material.STONE_BRICKS, Material.COBBLESTONE, 1, 300); // 石砖
		Grinder.add_recipe(Material.PRISMARINE, Material.PRISMARINE_SHARD, 4, 400); // 海晶石
		Grinder.add_recipe(Material.PRISMARINE_BRICKS, Material.PRISMARINE_SHARD, 8, 800); // 海晶石砖
		Grinder.add_recipe(Material.DARK_PRISMARINE, Material.PRISMARINE_SHARD, 8, 800); // 暗海晶石砖
		Grinder.add_recipe(Material.GLOWSTONE, Material.GLOWSTONE_DUST, 4, 400); // 荧石
		Grinder.add_recipe(Material.QUARTZ_BLOCK, Material.QUARTZ, 4, 400); // 石英
		Grinder.add_recipe(Material.REDSTONE_ORE, Material.REDSTONE, 8, 400); // 红石矿

		Grinder.add_recipe(Material.DIRT, Material.CLAY_BALL, 2, 300); // 泥土
		Grinder.add_recipe(Material.BRICK, Material.CLAY_BALL, 1, 100); // 红砖
		Grinder.add_recipe(Material.BRICKS, Material.CLAY_BALL, 4, 400); // 砖块
		Grinder.add_recipe(Material.CLAY, Material.CLAY_BALL, 4, 400); // 粘土块
		Grinder.add_recipe(Material.TERRACOTTA, Material.CLAY_BALL, 4, 500); // 陶瓦

		Grinder.add_recipe(Material.NETHERRACK, Material.GRAVEL, 2, 400); // 地狱岩
		Grinder.add_recipe(Material.NETHER_BRICK, Material.NETHERRACK, 1, 100); // 地狱砖
		Grinder.add_recipe(Material.NETHER_BRICKS, Material.NETHERRACK, 4, 400); // 地狱砖块
		Grinder.add_recipe(Material.MAGMA_BLOCK, Material.SOUL_SAND, 2, 400); // 岩浆块

		Grinder.add_recipe(Material.COBBLESTONE_SLAB, Material.GRAVEL, 2, 300); // 圆石半砖
		Grinder.add_recipe(Material.STONE_BRICK_SLAB, Material.COBBLESTONE_SLAB, 1, 150); // 石砖半砖
		Grinder.add_recipe(Material.STONE_SLAB, Material.COBBLESTONE_SLAB, 1, 150); // 石头半砖
		Grinder.add_recipe(Material.BRICK_SLAB, Material.CLAY_BALL, 2, 200); // 红砖半砖
		Grinder.add_recipe(Material.NETHER_BRICK_SLAB, Material.NETHERRACK, 2, 200); // 地狱砖半砖
		Grinder.add_recipe(Material.SANDSTONE_SLAB, Material.SAND, 2, 200); // 砂石半砖
		Grinder.add_recipe(Material.RED_SANDSTONE_SLAB, Material.RED_SAND, 2, 200); // 红砂石半砖
		Grinder.add_recipe(Material.PRISMARINE_SLAB, Material.PRISMARINE_SHARD, 2, 200); // 海晶石半砖
		Grinder.add_recipe(Material.PRISMARINE_BRICK_SLAB, Material.PRISMARINE_SHARD, 4, 400); // 海晶石砖半砖
		Grinder.add_recipe(Material.DARK_PRISMARINE_SLAB, Material.PRISMARINE_SHARD, 4, 400); // 暗海晶石砖半砖

		Grinder.add_recipe(Material.BONE, Material.BONE_MEAL, 4, 200); // 骨头
		Grinder.add_recipe(Material.BLAZE_ROD, Material.BLAZE_POWDER, 3, 200); // 烈焰粉
		Grinder.add_recipe(Material.MELON, Material.MELON_SEEDS, 9, 400); // 西瓜
		Grinder.add_recipe(Material.PUMPKIN, Material.PUMPKIN_SEEDS, 4, 200); // 南瓜
		Grinder.add_recipe(Material.CARVED_PUMPKIN, Material.PUMPKIN_SEEDS, 4, 200); // 被雕刻的南瓜

		Grinder.add_recipe(Material.ROSE_BUSH, Material.ROSE_RED, 3, 200); // 玫瑰红染料
		Grinder.add_recipe(Material.POPPY, Material.ROSE_RED, 2, 200); // 玫瑰红染料
		Grinder.add_recipe(Material.RED_TULIP, Material.ROSE_RED, 2, 200); // 玫瑰红染料
		Grinder.add_recipe(Material.BEETROOT, Material.ROSE_RED, 2, 200); // 玫瑰红染料

		Grinder.add_recipe(Material.AZURE_BLUET, Material.LIGHT_GRAY_DYE, 2, 200); // 淡灰色染料
		Grinder.add_recipe(Material.OXEYE_DAISY, Material.LIGHT_GRAY_DYE, 2, 200); // 淡灰色染料
		Grinder.add_recipe(Material.WHITE_TULIP, Material.LIGHT_GRAY_DYE, 2, 200); // 淡灰色染料

		Grinder.add_recipe(Material.PINK_TULIP, Material.PINK_DYE, 2, 200); // 粉红色染料
		Grinder.add_recipe(Material.PEONY, Material.PINK_DYE, 3, 200); // 粉红色染料

		Grinder.add_recipe(Material.BLUE_ORCHID, Material.LIGHT_BLUE_DYE, 2, 200); // 淡蓝色染料

		Grinder.add_recipe(Material.ALLIUM, Material.MAGENTA_DYE, 2, 200); // 品红色染料
		Grinder.add_recipe(Material.LILAC, Material.MAGENTA_DYE, 3, 200); // 品红色染料

		Grinder.add_recipe(Material.DANDELION, Material.DANDELION_YELLOW, 2, 200); // 蒲公英黄染料
		Grinder.add_recipe(Material.SUNFLOWER, Material.DANDELION_YELLOW, 3, 200); // 蒲公英黄染料

		Grinder.add_recipe(Material.ORANGE_TULIP, Material.ORANGE_DYE, 2, 200); // 橙色染料
	}

	public static boolean is_empty(ItemStack item) {
		if (item == null) {
			return true;
		}
		if (item.getType() == Material.AIR) {
			return true;
		}
		return false;
	}

	@Override
	public int completed() {
		if (this.get_block(0, 0, 0).getType() != Material.SMOOTH_STONE) {
			return 0;
		}
		if (this.get_block(0, -1, 0).getType() != Material.COBBLESTONE_WALL) {
			return 0;
		}
		if (this.get_block(1, 0, 0).getType() != Material.END_ROD) {
			return 0;
		}
		if (this.get_block(-1, 0, 0).getType() != Material.END_ROD) {
			return 0;
		}
		if (this.get_block(0, 0, 1).getType() != Material.END_ROD) {
			return 0;
		}
		if (this.get_block(0, 0, -1).getType() != Material.END_ROD) {
			return 0;
		}
		if (this.get_block(0, -2, 0).getType() != Material.IRON_BLOCK) {
			return 0;
		}
		return 1;
	}

	@Override
	public boolean in_structure(Location loc) {
		if (loc.equals(this.get_location())) {
			return true;
		}
		return false;
	}

	public Inventory getInventory() {
		return this.gui;
	}

	public Hopper get_hopper() {
		BlockState up_block = this.get_block(0, 1, 0).getState();
		if (up_block instanceof Hopper) {
			Hopper up_hopper = (Hopper) up_block;
			return up_hopper;
		}
		return null;
	}

	public Chest get_chest() {
		BlockState chest = this.get_block(1, -2, 0).getState();
		if (chest instanceof Chest) {
			return (Chest) chest;
		}
		chest = this.get_block(-1, -2, 0).getState();
		if (chest instanceof Chest) {
			return (Chest) chest;
		}
		chest = this.get_block(0, -2, 1).getState();
		if (chest instanceof Chest) {
			return (Chest) chest;
		}
		chest = this.get_block(0, -2, -1).getState();
		if (chest instanceof Chest) {
			return (Chest) chest;
		}
		return null;
	}

	public Grinder_runner get_runner() {
		return this.runner;
	}

	public BukkitRunnable get_io_runner() {
		return this.io_runner;
	}

	public boolean to_product() {
		if (!Grinder.is_empty(this.get_raw())) {
			ItemStack product_item = recipe.get(this.get_raw().getType());
			if (product_item != null) {
				if (Grinder.is_empty(this.get_product())) {
					this.set_product(product_item.clone());
					this.get_raw().setAmount(this.get_raw().getAmount() - 1);
					return true;
				} else if (this.get_product().getType() == product_item.getType()) {
					int new_num = this.get_product().getAmount() + product_item.getAmount();
					if (new_num <= product_item.getMaxStackSize()) {
						this.get_product().setAmount(new_num);
						this.get_raw().setAmount(this.get_raw().getAmount() - 1);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void set_process(int process) {
		int n = process * 9 / 100;
		int i = 0;
		ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemStack white = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		for (i = 0; i < n; i++) {
			this.gui.setItem(i, red.clone());
		}
		for (i = n; i < 9; i++) {
			this.gui.setItem(i, white.clone());
		}
	}
}