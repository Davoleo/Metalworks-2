package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.MettlePack;
import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.api.registry.Registry;

@MettlePack(modid = Mettle.MODID)
public class CoreMetals implements IMettleIntegration {

    public static final IMetal TIN = SimpleMetal
            .wizard("tin")
            .color(0xBEBBAF)
            .oreVariants(OreVariant.STONE, OreVariant.DEEPSLATE)
            .component(ComponentType.ORE)
            .build();

    public static final IMetal NICKEL = SimpleMetal
            .wizard("nickel")
            .oreVariants(OreVariant.STONE, OreVariant.DEEPSLATE)
            .component(ComponentType.ORE)
            .build();

    public static final IMetal CHROMIUM = SimpleMetal
            .wizard("chromium")
            .oreVariants(OreVariant.DEEPSLATE, OreVariant.END_STONE)
            .component(ComponentType.ORE)
            .build();

    public static final IMetal TITANIUM = SimpleMetal
            .wizard("titanium")
            .color(0xFFFFFF)
            .oreVariants(OreVariant.DEEPSLATE)
            .component(ComponentType.ORE)
            .build();

    public static final IMetal WOLFRAM = SimpleMetal
            .wizard("wolfram")
            .oreVariants(OreVariant.NETHERRACK)
            .component(ComponentType.ORE)
            .build();


    //Alloys
    public static final IMetal BRONZE = SimpleMetal
            .wizard("bronze")
            .build();
    public static final IMetal INVAR = SimpleMetal
            .wizard("invar")
            .build();
    public static final IMetal ELINVAR = SimpleMetal
            .wizard("elinvar")
            .build();

    @Override
    public void registerMetals(Registry<IMetal> registry) {
        registry.register(TIN);
        registry.register(NICKEL);
        registry.register(CHROMIUM);
        registry.register(TITANIUM);
        registry.register(WOLFRAM);

        registry.register(BRONZE);
        registry.register(INVAR);
        registry.register(ELINVAR);
    }

    @Override
    public void registerAlloys(Registry<Alloy> registry) {
        registry.register(Alloy
                .create(BRONZE)
                .addMetal(VanillaMetals.COPPER, 3)
                .addMetal(TIN, 1)
                .build()
        );

        registry.register(Alloy
                .create(INVAR)
                .addMetal(VanillaMetals.IRON, 2)
                .addMetal(NICKEL, 1)
                .build()
        );

        registry.register(Alloy
                .create(ELINVAR)
                .addMetal(INVAR, 9)
                .addMetal(CHROMIUM, 1)
                .build()
        );

    }
}
