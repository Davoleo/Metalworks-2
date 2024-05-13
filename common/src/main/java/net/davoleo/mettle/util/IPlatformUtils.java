package net.davoleo.mettle.util;

import net.minecraft.world.item.CreativeModeTab;

import java.nio.file.Path;

public interface IPlatformUtils {

    CreativeModeTab getTab();

    Path getResourcesPath();

    boolean isDevEnv();

    Platform getModLoader();
}
