package net.davoleo.mettle.forge.init;

import net.davoleo.mettle.Mettle;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void setup(final FMLClientSetupEvent event) {
        Mettle.clientSetup();
    }

}
