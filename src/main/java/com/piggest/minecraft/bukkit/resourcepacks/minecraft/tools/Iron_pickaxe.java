package com.piggest.minecraft.bukkit.resourcepacks.minecraft.tools;

import org.bukkit.Material;

import com.piggest.minecraft.bukkit.resourcepacks.minecraft.Vanilla_model;

public class Iron_pickaxe extends Vanilla_model {
	public Iron_pickaxe() {
		super(Material.IRON_PICKAXE);
		this.parent = "item/handheld";
	}
}
