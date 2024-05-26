package net.davoleo.mettle.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;

public class MettleOreGeneration {
    public static void generate() {
        MettleOrePlacements.ALL.forEach(placedFeature ->
                BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Decoration.UNDERGROUND_ORES, placedFeature.unwrapKey().orElseThrow())
        );
    }
}
