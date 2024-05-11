package net.davoleo.mettle.forge.util;

import net.davoleo.mettle.forge.MettleForge;
import net.davoleo.mettle.util.IPlatformUtils;
import net.minecraft.world.item.CreativeModeTab;

public class PlatformUtilsForge implements IPlatformUtils {

    @Override
    public CreativeModeTab getTab() {
        return MettleForge.CREATIVE_TAB;
    }
}
