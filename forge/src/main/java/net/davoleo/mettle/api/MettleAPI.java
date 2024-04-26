package net.davoleo.mettle.api;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.registry.AlloyRegistry;
import net.minecraft.resources.ResourceLocation;

public class MettleAPI {

    public static final String MODID = "mettle";

    public static final ResourceLocation METAL_REGISTRY_ID = new ResourceLocation(Mettle.MODID, "metal_registry");

    public static final AlloyRegistry ALLOY_REGISTRY = AlloyRegistry.INSTANCE;

}
