package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.registry.RegistryEntry;
import net.davoleo.mettle.block.entity.ForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MettleBEs extends ModRegistry {

    public static RegistryEntry<BlockEntityType<ForgeBlockEntity>> FORGE_BE_TYPE;

    public static void init() {
        FORGE_BE_TYPE = rentry("forge", () -> Mettle.REGISTRY.newBlockEntityType(ForgeBlockEntity::new, MettleBlocks.FORGE.get()));
        Mettle.REGISTRY.registerBlockEntity(FORGE_BE_TYPE.name(), FORGE_BE_TYPE.entry());


    }
}
