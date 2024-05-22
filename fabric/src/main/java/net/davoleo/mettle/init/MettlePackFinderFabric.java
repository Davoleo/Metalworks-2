package net.davoleo.mettle.init;

import net.davoleo.mettle.api.IMettleIntegration;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.util.List;

public class MettlePackFinderFabric {

    public static final String METTLE_PACK_ENTRYPOINT = "mettle_pack";

    public static void findPacks() {
        var fabricLoader = FabricLoader.getInstance();
        List<EntrypointContainer<IMettleIntegration>> packContainers =
                fabricLoader.getEntrypointContainers(METTLE_PACK_ENTRYPOINT, IMettleIntegration.class);

        packContainers.forEach(packContainer -> {
            String mod = packContainer.getProvider().getMetadata().getId();
            IMettleIntegration pack = packContainer.getEntrypoint();
            ModRegistry.registerPack(mod, pack);
        });
    }

}
