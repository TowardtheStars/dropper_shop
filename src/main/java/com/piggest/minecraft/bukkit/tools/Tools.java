package com.piggest.minecraft.bukkit.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.piggest.minecraft.bukkit.dropper_shop.Dropper_shop_plugin;
import com.piggest.minecraft.bukkit.material_ext.Custom_durability;
import com.piggest.minecraft.bukkit.material_ext.Material_ext;
import com.piggest.minecraft.bukkit.material_ext.Tool_material;

public class Tools implements Listener {
	private static final NamespacedKey pickaxe_recipe_namespace = Dropper_shop_plugin.get_key("pickaxe_recipe");
	private static final NamespacedKey axe_recipe_namespace = Dropper_shop_plugin.get_key("axe_recipe");
	private static final NamespacedKey shovel_recipe_namespace = Dropper_shop_plugin.get_key("shovel_recipe");
	private static final NamespacedKey hoe_recipe_namespace = Dropper_shop_plugin.get_key("hoe_recipe");
	private static final List<NamespacedKey> tools_namespace = Arrays.asList(new NamespacedKey[] {
			pickaxe_recipe_namespace, axe_recipe_namespace, shovel_recipe_namespace, hoe_recipe_namespace });
	public static final ArrayList<Tools> pickaxe_config = new ArrayList<>();
	public static final ArrayList<Tools> axe_config = new ArrayList<>();
	public static final ArrayList<Tools> shovel_config = new ArrayList<>();
	public static final ArrayList<Tools> hoe_config = new ArrayList<>();
	public static final int tool_model_offset = 100;

	private Tool_type tool_type;
	private Tool_material.Custom_material tool_material;
	private String id_name;
	private Material raw_material;

	public Tools(Tool_material.Custom_material tool_material, Tool_type tool_type) {
		this.tool_type = tool_type;
		this.tool_material = tool_material;
		this.id_name = tool_material.name().toLowerCase() + "_" + tool_type.name().toLowerCase();
		this.raw_material = Material.valueOf(tool_material.get_raw().name() + "_" + tool_type.name());
	}

	@EventHandler
	public void on_prepare_craft(PrepareItemCraftEvent event) {
		Recipe recipe = event.getRecipe();
		if (recipe == null) {
			return;
		}
		if (!(recipe instanceof ShapedRecipe)) {
			return;
		}
		ShapedRecipe sr = (ShapedRecipe) recipe;
		if (tools_namespace.contains(sr.getKey())) {
			CraftingInventory inventory = event.getInventory();
		}
	}

	public Tool_type get_tool_type() {
		return this.tool_type;
	}

	public Tool_material.Custom_material get_tool_material() {
		return this.tool_material;
	}

	public String get_display_name() {
		return this.tool_material.get_display_name() + this.tool_type.get_display_name();
	}

	public String get_id_name() {
		return this.id_name;
	}

	public Material get_raw_material() {
		return this.raw_material;
	}

	public static void init_recipe() {
		ShapedRecipe pickaxe_recipe = new ShapedRecipe(pickaxe_recipe_namespace, new ItemStack(Material.STONE_PICKAXE));
		pickaxe_recipe.shape("bbb", " s ", " s ");
		pickaxe_recipe.setIngredient('b', Material.BRICK);
		pickaxe_recipe.setIngredient('s', Material.STICK);
		Dropper_shop_plugin.instance.add_recipe(pickaxe_recipe);

		ShapedRecipe axe_recipe = new ShapedRecipe(axe_recipe_namespace, new ItemStack(Material.STONE_AXE));
		axe_recipe.shape("bb ", "bs ", " s ");
		axe_recipe.setIngredient('b', Material.BRICK);
		axe_recipe.setIngredient('s', Material.STICK);
		Dropper_shop_plugin.instance.add_recipe(axe_recipe);

		ShapedRecipe shovel_recipe = new ShapedRecipe(shovel_recipe_namespace, new ItemStack(Material.STONE_SHOVEL));
		shovel_recipe.shape(" b ", " s ", " s ");
		shovel_recipe.setIngredient('b', Material.BRICK);
		shovel_recipe.setIngredient('s', Material.STICK);
		Dropper_shop_plugin.instance.add_recipe(shovel_recipe);

		ShapedRecipe hoe_recipe = new ShapedRecipe(hoe_recipe_namespace, new ItemStack(Material.STONE_HOE));
		hoe_recipe.shape("bb ", " s ", " s ");
		hoe_recipe.setIngredient('b', Material.BRICK);
		hoe_recipe.setIngredient('s', Material.STICK);
		Dropper_shop_plugin.instance.add_recipe(hoe_recipe);

	}

	public static void init_tools_config() {
		init_config(pickaxe_config, Tool_type.PICKAXE);
		init_config(axe_config, Tool_type.AXE);
		init_config(hoe_config, Tool_type.HOE);
		init_config(shovel_config, Tool_type.SHOVEL);
	}

	public static void init_tools() {
		init(pickaxe_config, Tool_type.PICKAXE);
		init(axe_config, Tool_type.AXE);
		init(hoe_config, Tool_type.HOE);
		init(shovel_config, Tool_type.SHOVEL);
	}

	private static void init_config(ArrayList<Tools> config_list, Tool_type tool_type) {
		for (Tool_material.Custom_material material : Tool_material.Custom_material.values()) {
			config_list.add(new Tools(material, tool_type));
		}
	}

	private static void init(ArrayList<Tools> config_list, Tool_type tool_type) {
		init_config(config_list, tool_type);
		Dropper_shop_plugin.instance.getLogger().info("注册" + tool_type.get_display_name());
		for (int i = 0; i < config_list.size(); i++) {
			Tools tool = config_list.get(i);
			String id_name = tool.get_id_name();
			NamespacedKey key = Dropper_shop_plugin.get_key(id_name);
			ItemStack tool_item = Material_ext.register(key, tool.get_raw_material(), tool.get_display_name(),
					Dropper_shop_plugin.custom_model_data_offset + tool_model_offset + i);
			Custom_durability.init_custom_durability(tool_item);
		}
	}
}
