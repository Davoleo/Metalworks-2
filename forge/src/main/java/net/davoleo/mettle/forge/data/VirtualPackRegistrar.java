package net.davoleo.mettle.forge.data;

import com.google.common.base.Suppliers;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.data.VirtualPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mettle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VirtualPackRegistrar {

    @SubscribeEvent
    public static void registerVirtualPack(AddPackFindersEvent event) {

        event.addRepositorySource((infoConsumer, infoFactory) -> {
            var pack = Pack.create(
                    "mettle:virtual_pack", true,
                    Suppliers.memoize(VirtualPackResources::new),
                    infoFactory, Pack.Position.BOTTOM, PackSource.DEFAULT);

            if (pack != null)
                infoConsumer.accept(pack);
        });
    }

}
