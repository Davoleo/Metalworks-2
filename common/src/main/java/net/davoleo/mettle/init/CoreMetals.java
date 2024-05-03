package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.MetalProperties;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.api.registry.AbstractRegistry;

public class CoreMetals {

    public static final AbstractRegistry<IMetal> BASE_REGISTER = new AbstractRegistry<>(Mettle.MODID);
    public static final AbstractRegistry<IMetal> VANILLA_REGISTER = new AbstractRegistry<>("minecraft");

    public static void init() {
        Vanilla.init();
    }

    public static final class Vanilla {

        public static final SimpleMetal IRON = VANILLA_REGISTER.register("iron", new SimpleMetal(

        ))

        public static final SimpleMetal IRON = register(VANILLA_REGISTER, "iron", () -> new SimpleMetal(
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

    private static <T extends IMetal> T register(AbstractRegistry<T> register, String name, T entry)
    {
        return register.register(name, entry);
    }
}
