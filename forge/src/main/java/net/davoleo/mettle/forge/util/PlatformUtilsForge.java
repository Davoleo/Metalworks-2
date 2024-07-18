package net.davoleo.mettle.forge.util;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.forge.MettleForge;
import net.davoleo.mettle.util.IPlatformUtils;
import net.davoleo.mettle.util.Platform;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import java.nio.file.Path;

public class PlatformUtilsForge implements IPlatformUtils {

    @Override
    public CreativeModeTab getTab() {
        return MettleForge.CREATIVE_TAB;
    }

    @Override
    public Path getResourcesPath() {
        return ModList.get().getModFileById(Mettle.MODID).getFile().getFilePath();
    }

    @Override
    public boolean isDevEnv() {
        return !FMLLoader.isProduction();
    }

    @Override
    public Platform getModLoader() {
        return Platform.FORGE;
    }

    @Override
    public Side getPhysicalSide() {
        return FMLEnvironment.dist == Dist.CLIENT ? Side.CLIENT : Side.SERVER;
    }

}
