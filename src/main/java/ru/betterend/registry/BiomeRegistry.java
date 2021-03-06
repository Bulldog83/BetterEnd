package ru.betterend.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import ru.betterend.world.biome.BiomeDefinition;
import ru.betterend.world.biome.EndBiome;
import ru.betterend.world.generator.BiomePicker;

public class BiomeRegistry {
	private static final Map<EndBiome, RegistryKey<Biome>> KEYS = Maps.newHashMap();
	public static final HashMap<Biome, EndBiome> MUTABLE = Maps.newHashMap();
	
	public static final EndBiome END = registerBiome(BuiltinBiomes.PLAINS);
	public static final EndBiome TEST = registerBiome(new EndBiome(new BiomeDefinition("test").setFogColor(255, 0, 0)));
	
	public static void register() {}
	
	private static EndBiome registerBiome(Biome biome) {
		EndBiome endBiome = new EndBiome(biome);
		BiomePicker.addBiome(endBiome);
		makeLink(endBiome);
		return endBiome;
	}
	
	private static EndBiome registerBiome(EndBiome biome) {
		BiomePicker.addBiome(biome);
		registerBiomeDirect(biome);
		return biome;
	}

	private static void registerBiomeDirect(EndBiome biome) {
		Registry.register(BuiltinRegistries.BIOME, biome.getID(), biome.getBiome());
		makeLink(biome);
	}
	
	private static void makeLink(EndBiome biome) {
		Optional<RegistryKey<Biome>> optional = BuiltinRegistries.BIOME.getKey(biome.getBiome());
		RegistryKey<Biome> key = optional.isPresent() ? optional.get() : RegistryKey.of(Registry.BIOME_KEY, biome.getID());
		KEYS.put(biome, key);
	}

	public static RegistryKey<Biome> getBiomeKey(EndBiome biome) {
		return KEYS.get(biome);
	}
}
