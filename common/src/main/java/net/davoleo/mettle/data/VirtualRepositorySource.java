package net.davoleo.mettle.data;

import com.google.common.base.Suppliers;
import net.davoleo.mettle.Mettle;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.function.Consumer;

public class VirtualRepositorySource implements RepositorySource {

    private VirtualRepositorySource() {}

    private static VirtualRepositorySource instance = null;
    public static VirtualRepositorySource get() {
        if (instance == null)
            instance = new VirtualRepositorySource();
        return instance;
    }

    @Override
    public void loadPacks(Consumer<Pack> infoConsumer, Pack.PackConstructor infoFactory) {
        var pack = Pack.create(
                Mettle.MODID + ":virtual_pack", true,
                Suppliers.memoize(VirtualPackResources::new),
                infoFactory, Pack.Position.BOTTOM, PackSource.DEFAULT);

        if (pack != null)
            infoConsumer.accept(pack);
    }
}
