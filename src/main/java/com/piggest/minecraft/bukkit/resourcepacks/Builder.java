package com.piggest.minecraft.bukkit.resourcepacks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.piggest.minecraft.bukkit.dropper_shop.Dropper_shop_plugin;
import com.piggest.minecraft.bukkit.resourcepacks.dropper_shop.Element;
import com.piggest.minecraft.bukkit.resourcepacks.minecraft.Brick;
import com.piggest.minecraft.bukkit.resourcepacks.minecraft.Sugar;
import com.piggest.minecraft.bukkit.resourcepacks.minecraft.Vanilla_model;
import com.piggest.minecraft.bukkit.resourcepacks.minecraft.tools.Iron_pickaxe;

public class Builder {
	public final static Gson gson = new Gson();
	public final static String project_dir = System.getProperty("user.dir").replace("\\", "/") + "/";
	public final static String resourcepack_target_dir = project_dir + "target/resourcepacks/dropper_shop/";
	public final static String dropper_shop_models_dir = resourcepack_target_dir + "assets/dropper_shop/models/";
	public final static String dropper_shop_textures_dir = resourcepack_target_dir + "assets/dropper_shop/textures/";
	public final static String minecraft_models_dir = resourcepack_target_dir + "assets/minecraft/models/";
	public final static String minecraft_item_models_dir = minecraft_models_dir + "item/";

	public static void write_json(String path, String json) {
		FileWriter fw;
		File json_file = new File(path);

		try {
			if (json_file.exists()) {
				json_file.delete();
			} else {
				json_file.createNewFile();
			}
			fw = new FileWriter(json_file);
			fw.write(json);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void build_elements(Sugar sugar) {
		File file = new File(dropper_shop_models_dir + "elements");
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 1; i < 95; i++) {
			String file_path = dropper_shop_models_dir + "elements/element_" + i + ".json";
			String js = new Element(i).to_json();
			write_json(file_path, js);
			sugar.add_custom_model_override(Dropper_shop_plugin.custom_model_data_offset + i,
					"dropper_shop:elements/element_" + i);
		}
	}

	private static void build_powder(Sugar clay_ball) {
		File file = new File(dropper_shop_models_dir + "powder");
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 1; i < com.piggest.minecraft.bukkit.grinder.Powder.powder_config.size(); i++) {
			String material_name = com.piggest.minecraft.bukkit.grinder.Powder.powder_config.get(i).get_material_name();
			String file_path = dropper_shop_models_dir + "powder/" + material_name + ".json";
			String js = new com.piggest.minecraft.bukkit.resourcepacks.dropper_shop.Powder(material_name).to_json();
			write_json(file_path, js);
			clay_ball.add_custom_model_override(
					Dropper_shop_plugin.custom_model_data_offset
							+ com.piggest.minecraft.bukkit.grinder.Powder.powder_model_offset + i,
					"dropper_shop:powder/" + material_name);
		}
	}

	private static void build_ingot(Brick iron_ingot) {
		File file = new File(dropper_shop_models_dir + "ingots");
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 1; i < com.piggest.minecraft.bukkit.grinder.Ingot.ingot_config.size(); i++) {
			String material_name = com.piggest.minecraft.bukkit.grinder.Ingot.ingot_config.get(i).get_material_name();
			String file_path = dropper_shop_models_dir + "ingots/" + material_name + ".json";
			String js = new com.piggest.minecraft.bukkit.resourcepacks.dropper_shop.Ingot(material_name).to_json();
			write_json(file_path, js);
			iron_ingot.add_custom_model_override(
					Dropper_shop_plugin.custom_model_data_offset
							+ com.piggest.minecraft.bukkit.grinder.Ingot.ingot_model_offset + i,
					"dropper_shop:ingots/" + material_name);
		}
	}

	private static void build_wrench(Iron_pickaxe iron_pickaxe) {
		File file = new File(dropper_shop_models_dir + "tools");
		if (!file.exists()) {
			file.mkdirs();
		}
		String file_path = dropper_shop_models_dir + "tools/wrench.json";
		String js = new com.piggest.minecraft.bukkit.resourcepacks.dropper_shop.Wrench().to_json();
		write_json(file_path, js);
		iron_pickaxe.add_custom_model_override(Dropper_shop_plugin.custom_model_data_offset + 1,
				"dropper_shop:tools/wrench");
	}
	
	private static void build_vanilla(Vanilla_model model) {
		File file = new File(minecraft_item_models_dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = minecraft_models_dir + model.getTextures().layer0 + ".json";
		write_json(path, model.to_json());
	}

	public static void main(String[] args) {
		System.out.println("欢迎使用Dropper shop插件");
		System.out.println("dropper_shop_models_dir:" + dropper_shop_models_dir);
		Brick brick = new Brick();
		Sugar sugar = new Sugar();
		Iron_pickaxe iron_pickaxe = new Iron_pickaxe();
		com.piggest.minecraft.bukkit.grinder.Powder.init_powder_config();
		com.piggest.minecraft.bukkit.grinder.Ingot.init_ingot_config();
		com.piggest.minecraft.bukkit.tools.Tools.init_tools_config();
		
		// dropper_shop文件夹
		build_elements(sugar);
		build_powder(sugar);
		build_ingot(brick);
		build_wrench(iron_pickaxe);
		// minecraft文件夹
		build_vanilla(sugar);
		build_vanilla(brick);
		build_vanilla(iron_pickaxe);
		/*
		 * System.out.println("Java 运行时环境版本:"+System.getProperty("java.version"));
		 * System.out.println("Java 运行时环境供应商:"+System.getProperty("java.vendor"));
		 * System.out.println("Java 供应商的URL:"+System.getProperty("java.vendor.url"));
		 * System.out.println("Java 安装目录:"+System.getProperty("java.home"));
		 * System.out.println("Java 虚拟机规范版本:"+System.getProperty(
		 * "java.vm.specification.version"));
		 * System.out.println("Java 类格式版本号:"+System.getProperty("java.class.version"));
		 * System.out.println("Java 类路径："+System.getProperty("java.class.path"));
		 * System.out.println("加载库时搜索的路径列表:"+System.getProperty("java.library.path"));
		 * System.out.println("默认的临时文件路径:"+System.getProperty("java.io.tmpdir"));
		 * System.out.println("要使用的 JIT 编译器的名称:"+System.getProperty("java.compiler"));
		 * System.out.println("一个或多个扩展目录的路径:"+System.getProperty("java.ext.dirs"));
		 * System.out.println("操作系统的名称:"+System.getProperty("os.name"));
		 * System.out.println("操作系统的架构:"+System.getProperty("os.arch"));
		 * System.out.println("操作系统的版本:"+System.getProperty("os.version"));
		 * System.out.println("文件分隔符（在 UNIX 系统中是“/”）:"+System.getProperty(
		 * "file.separator"));
		 * System.out.println("路径分隔符（在 UNIX 系统中是“:”）:"+System.getProperty(
		 * "path.separator"));
		 * System.out.println("行分隔符（在 UNIX 系统中是“/n”）:"+System.getProperty(
		 * "line.separator"));
		 * System.out.println("用户的账户名称:"+System.getProperty("user.name"));
		 * System.out.println("用户的主目录:"+System.getProperty("user.home"));
		 * System.out.println("用户的当前工作目录:"+System.getProperty("user.dir"));
		 * System.out.println("当前的classpath的绝对路径的URI表示法:" +
		 * Thread.currentThread().getContextClassLoader().getResource(""));
		 * System.out.println("得到的是当前的classpath的绝对URI路径:"+
		 * Builder.class.getResource("/"));
		 * System.out.println("得到的是当前类Builder.class文件的URI目录:"+Builder.class.getResource(
		 * ""));
		 */
	}

}
