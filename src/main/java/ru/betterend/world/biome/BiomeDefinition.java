package ru.betterend.world.biome;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.Precipitation;
import net.minecraft.world.biome.BiomeEffects.Builder;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import ru.betterend.BetterEnd;
import ru.betterend.MHelper;

public class BiomeDefinition
{
	private final List<ConfiguredStructureFeature<?, ?>> structures = Lists.newArrayList();
	private final List<FeatureInfo> features = Lists.newArrayList();
	private final List<SpawnInfo> mobs = Lists.newArrayList();
	
	private BiomeParticleConfig particleConfig;
	private BiomeAdditionsSound additions;
	private BiomeMoodSound mood;
	private SoundEvent music;
	private SoundEvent loop;
	
	private int waterFogColor = 329011;
	private int waterColor = 4159204;
	private int fogColor = 3344392;
	
	private boolean stalactites = true;
	private boolean bnStructures = true;
	
	private final Identifier id;
	
	public BiomeDefinition(String name)
	{
		this.id = new Identifier(BetterEnd.MOD_ID, name);
	}
	
	public BiomeDefinition setStalactites(boolean value)
	{
		stalactites = value;
		return this;
	}
	
	public BiomeDefinition setBNStructures(boolean value)
	{
		bnStructures = value;
		return this;
	}
	
	/**
	 * Set default ores generation
	 * @param value - if true (default) then default ores will be generated
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultOres(boolean value)
	{
		return this;
	}
	
	/**
	 * Set default nether structure features to be added
	 * @param value - if true (default) then default structure features (nether fortresses, caves, etc.) will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultStructureFeatures(boolean value)
	{
		return this;
	}
	
	/**
	 * Set default nether features to be added
	 * @param value - if true (default) then default features (small structures) will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultFeatures(boolean value)
	{
		return this;
	}
	
	/**
	 * Set default Nether Wastes mobs to be added
	 * @param value - if true (default) then default mobs will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultMobs(boolean value)
	{
		return this;
	}
	
	public BiomeDefinition setParticleConfig(BiomeParticleConfig config)
	{
		this.particleConfig = config;
		return this;
	}
	
	/**
	 * Adds mob into biome
	 * @param type - {@link EntityType}
	 * @param group - {@link SpawnGroup}
	 * @param weight - cumulative spawning weight
	 * @param minGroupSize - minimum count of mobs in the group
	 * @param maxGroupSize - maximum count of mobs in the group
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition addMobSpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize)
	{
		Identifier eID = Registry.ENTITY_TYPE.getId(type);
		if (eID != Registry.ENTITY_TYPE.getDefaultId())
		{
			SpawnInfo info = new SpawnInfo();
			info.type = type;
			info.weight = weight;
			info.minGroupSize = minGroupSize;
			info.maxGroupSize = maxGroupSize;
			mobs.add(info);
		}
		return this;
	}
	
	/**
	 * Adds feature (small structure) into biome - plants, ores, small buildings, etc.
	 * @param feature - {@link ConfiguredStructureFeature} to add
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition addStructureFeature(ConfiguredStructureFeature<?, ?> feature)
	{
		System.out.println("Structure " + feature);
		structures.add(feature);
		return this;
	}
	
	public BiomeDefinition addFeature(Feature featureStep, ConfiguredFeature<?, ?> feature)
	{
		FeatureInfo info = new FeatureInfo();
		info.featureStep = featureStep;
		info.feature = feature;
		features.add(info);
		return this;
	}
	
	/**
	 * Sets biome fog color
	 * @param r - Red [0 - 255]
	 * @param g - Green [0 - 255]
	 * @param b - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setFogColor(int r, int g, int b)
	{
		r = MathHelper.clamp(r, 0, 255);
		g = MathHelper.clamp(g, 0, 255);
		b = MathHelper.clamp(b, 0, 255);
		this.fogColor = MHelper.color(r, g, b);
		return this;
	}
	
	/**
	 * Sets biome water color
	 * @param r - Red [0 - 255]
	 * @param g - Green [0 - 255]
	 * @param b - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setWaterColor(int r, int g, int b)
	{
		r = MathHelper.clamp(r, 0, 255);
		g = MathHelper.clamp(g, 0, 255);
		b = MathHelper.clamp(b, 0, 255);
		this.waterColor = MHelper.color(r, g, b);
		return this;
	}
	
	/**
	 * Sets biome underwater fog color
	 * @param r - Red [0 - 255]
	 * @param g - Green [0 - 255]
	 * @param b - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setWaterFogColor(int r, int g, int b)
	{
		r = MathHelper.clamp(r, 0, 255);
		g = MathHelper.clamp(g, 0, 255);
		b = MathHelper.clamp(b, 0, 255);
		this.waterFogColor = MHelper.color(r, g, b);
		return this;
	}
	
	/**
	 * Plays in never-ending loop for as long as player is in the biome
	 * @param loop - SoundEvent
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setLoop(SoundEvent loop)
	{
		this.loop = loop;
		return this;
	}
	
	/**
	 * Plays commonly while the player is in the biome
	 * @param mood - SoundEvent
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setMood(SoundEvent mood)
	{
		this.mood = new BiomeMoodSound(mood, 6000, 8, 2.0D);
		return this;
	}
	
	/**
	 * Set additional sounds. They plays once every 6000-17999 ticks while the player is in the biome
	 * @param additions - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefinition setAdditions(SoundEvent additions)
	{
		this.additions = new BiomeAdditionsSound(additions, 0.0111);
		return this;
	}

	/**
	 * Set background music for biome
	 * @param music
	 * @return
	 */
	public BiomeDefinition setMusic(SoundEvent music)
	{
		this.music = music;
		return this;
	}

	public Biome build()
	{
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		Builder effects = new Builder();
		
		mobs.forEach((spawn) -> { spawnSettings.spawn(spawn.type.getSpawnGroup(), new SpawnSettings.SpawnEntry(spawn.type, spawn.weight, spawn.minGroupSize, spawn.maxGroupSize)); });
		
		generationSettings.surfaceBuilder(ConfiguredSurfaceBuilders.END);
		structures.forEach((structure) -> generationSettings.structureFeature(structure));
		features.forEach((info) -> generationSettings.feature(info.featureStep, info.feature));
		
		effects.skyColor(fogColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor);
		if (loop != null) effects.loopSound(loop);
		if (mood != null) effects.moodSound(mood);
		if (additions != null) effects.additionsSound(additions);
		if (particleConfig != null) effects.particleConfig(particleConfig);
		effects.music(MusicType.createIngameMusic(music != null ? music : SoundEvents.MUSIC_END));
		
		return new Biome.Builder()
				.precipitation(Precipitation.NONE)
				.category(Category.THEEND)
				.depth(0.1F)
				.scale(0.2F)
				.temperature(2.0F)
				.downfall(0.0F)
				.effects(effects.build())
				.spawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build())
				.build();
	}

	private static final class SpawnInfo
	{
		EntityType<?> type;
		int weight;
		int minGroupSize;
		int maxGroupSize;
	}

	private static final class FeatureInfo
	{
		Feature featureStep;
		ConfiguredFeature<?, ?> feature;
	}
	
	public Identifier getID()
	{
		return id;
	}

	public boolean hasStalactites()
	{
		return stalactites;
	}
	
	public boolean hasBNStructures()
	{
		return bnStructures;
	}
}