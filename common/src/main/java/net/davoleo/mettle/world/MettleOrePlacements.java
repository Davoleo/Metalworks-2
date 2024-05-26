package net.davoleo.mettle.world;

import net.davoleo.mettle.Mettle;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class MettleOrePlacements {

    public static final Holder<PlacedFeature> TIN_ORE = placedFeature("tin_ore_placed", MettleOreFeatures.TIN_ORE,
            commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))
    );

    public static final Holder<PlacedFeature> NICKEL_ORE = placedFeature("nickel_ore_placed", MettleOreFeatures.NICKEL_ORE,
            commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(16)))
    );

    public static final Holder<PlacedFeature> CHROMIUM_ORE = placedFeature("chromium_ore_placed", MettleOreFeatures.CHROMIUM_ORE,
            commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(12)))
    );

    public static final Holder<PlacedFeature> TITANIUM_ORE = placedFeature("titanium_ore_placed", MettleOreFeatures.TITANIUM_ORE,
            commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0)))
    );

    public static final Holder<PlacedFeature> WOLFRAM_ORE = placedFeature("wolfram_ore_placed", MettleOreFeatures.WOLFRAM_ORE,
            commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)))
    );

    public static final List<Holder<PlacedFeature>> ALL = List.of(
            TIN_ORE,
            NICKEL_ORE,
            CHROMIUM_ORE,
            TITANIUM_ORE,
            WOLFRAM_ORE
    );

    private static Holder<PlacedFeature> placedFeature(String name, Holder<ConfiguredFeature<OreConfiguration, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        return PlacementUtils.register(
                Mettle.MODID + ':' + name,
                configuredFeature,
                placementModifiers
        );
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    private static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier heightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange);
    }
}
