package net.davoleo.mettle.forge.data;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.data.VirtualRepositorySource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mettle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VirtualPackRegistrar {

    @SubscribeEvent
    public static void registerVirtualPack(AddPackFindersEvent event) {
        event.addRepositorySource(VirtualRepositorySource.get());
    }

}
