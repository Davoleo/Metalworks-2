package net.davoleo.mettle.world;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.MetalComponents;
import net.davoleo.mettle.block.MettleOreBlock;
import net.davoleo.mettle.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;
import java.util.Objects;

public class MettleOreFeatures {

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TIN_ORE = configuredFeature("tin_ore", new OreConfiguration(
            targetBlockList(ModRegistry.METALS.get("tin").value(), ModRegistry.METAL_COMPONENTS.get("tin")),
            12
    ));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> NICKEL_ORE = configuredFeature("nickel_ore", new OreConfiguration(
            targetBlockList(ModRegistry.METALS.get("nickel").value(), ModRegistry.METAL_COMPONENTS.get("nickel")),
            7
    ));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> CHROMIUM_ORE = configuredFeature("chromium_ore", new OreConfiguration(
            targetBlockList(ModRegistry.METALS.get("chromium").value(), ModRegistry.METAL_COMPONENTS.get("chromium")),
            6
    ));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> TITANIUM_ORE = configuredFeature("titanium_ore", new OreConfiguration(
            targetBlockList(ModRegistry.METALS.get("titanium").value(), ModRegistry.METAL_COMPONENTS.get("titanium")),
            4
    ));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> WOLFRAM_ORE = configuredFeature("wolfram_ore", new OreConfiguration(
            targetBlockList(ModRegistry.METALS.get("wolfram").value(), ModRegistry.METAL_COMPONENTS.get("wolfram")),
            5
    ));

    private static Holder<ConfiguredFeature<OreConfiguration, ?>> configuredFeature(String name, OreConfiguration oreConfig) {
        return FeatureUtils.register(Mettle.MODID + ':' + name, Feature.ORE, oreConfig);
    }

    private static List<OreConfiguration.TargetBlockState> targetBlockList(IMetal metal, MetalComponents components) {
        return metal.oreVariants().stream()
                .map(oreVariant -> {
                    MettleOreBlock block = Objects.requireNonNull(components.ores().get(oreVariant)).get();
                    return OreConfiguration.target(oreVariant.getTargetReplaceRuleTest(), block.defaultBlockState());
                })
                .toList();
    }


}
