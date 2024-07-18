package net.davoleo.mettle.util;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.MettleFabric;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.world.item.CreativeModeTab;

import java.nio.file.Path;

public class PlatformUtilsFabric implements IPlatformUtils {

    private final ModContainer mod;

    public PlatformUtilsFabric() {
        this.mod = FabricLoader.getInstance().getModContainer(Mettle.MODID).orElseThrow();
    }

    @Override
    public CreativeModeTab getTab() {
        return MettleFabric.CREATIVE_TAB;
    }

    @Override
    public Path getResourcesPath() {
        return mod.getRootPaths().stream().findFirst().orElseThrow();
    }

    @Override
    public boolean isDevEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Platform getModLoader() {
        return Platform.FABRIC;
    }

    @Override
    public Side getPhysicalSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? Side.CLIENT : Side.SERVER;
    }
}
