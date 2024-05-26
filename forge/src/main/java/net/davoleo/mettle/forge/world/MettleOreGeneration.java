package net.davoleo.mettle.forge.world;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.world.MettleOrePlacements;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Mettle.MODID)
public class MettleOreGeneration {

    @SubscribeEvent
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> features = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        features.addAll(MettleOrePlacements.ALL);
    }

}
