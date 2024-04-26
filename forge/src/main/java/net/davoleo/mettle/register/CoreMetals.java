package net.davoleo.mettle.register;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.MettleAPI;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.MetalProperties;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CoreMetals {

    public static final DeferredRegister<IMetal> BASE_REGISTER = DeferredRegister.create(MettleAPI.METAL_REGISTRY_ID, Mettle.MODID);
    public static final DeferredRegister<IMetal> VANILLA_REGISTER = DeferredRegister.create(MettleAPI.METAL_REGISTRY_ID, "minecraft");

    public static final Map<ResourceLocation, IMetal> LOCAL_METAL_CACHE = new HashMap<>();
    public static final Map<ResourceLocation, TagKey<Item>> METAL_TAG = new HashMap<>();

    static {
        BASE_REGISTER.makeRegistry(IMetal.class, RegistryBuilder::new);



        //DeferredRegister<IMetal> ITEM = DeferredRegister.create(REGISTRY_ID, Mettle.MODID);
    }


    public static void init() {
        BASE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        VANILLA_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Vanilla.init();
    }

    public static final class Vanilla {

        public static final RegistryObject<SimpleMetal> IRON = register(VANILLA_REGISTER, "iron", () -> new SimpleMetal(
                0xD8D8D8,
                new MetalProperties(1830, 0.43F, 7.9F, 230, 4.0F),
                null
        ));

        public static final RegistryObject<SimpleMetal> GOLD = register(VANILLA_REGISTER, "gold", () -> new SimpleMetal(
                0xFAD64A,
                new MetalProperties(1360, 0.93F, 19.3F, 100, 2.5F),
                null
        ));

        public static final RegistryObject<SimpleMetal> COPPER = register(VANILLA_REGISTER, "copper", () -> new SimpleMetal(
                0xE77C56,
                new MetalProperties(1380, 0.62F, 7.9F, 250, 3.0F),
                null
        ));

        public static void init() {}
    }

    public static final RegistryObject<SimpleMetal> TIN = register(BASE_REGISTER, "tin", () -> new SimpleMetal(
            0x00FFFF,
            new MetalProperties(530, 0.69F, 7.3F, 45, 1.5F),
            null
    ));

    public static final RegistryObject<SimpleMetal> ALUMINIUM = register(BASE_REGISTER, "aluminium", () -> new SimpleMetal(
            0x00FF00,
            new MetalProperties(960, 0.65F, 2.7F, 150, 2.75F),
            null
    ));

    private static <T extends IMetal> RegistryObject<T> register(DeferredRegister<IMetal> register, String name, Supplier<T> supplier)
    {
        RegistryObject<T> obj = register.register(name, supplier);
        LOCAL_METAL_CACHE.put(obj.getId(), supplier.get());
        METAL_TAG.put(obj.getId(), ItemTags.create(new ResourceLocation(obj.getId().getNamespace(), obj.getId().getPath() + "_chunk")));
        return obj;
    }
}
