package net.davoleo.mettle;

import net.fabricmc.api.ClientModInitializer;

public class MettleFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Mettle.clientSetup();
    }
}
