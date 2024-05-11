package net.davoleo.mettle;

import com.mojang.logging.LogUtils;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.davoleo.mettle.util.IPlatformUtils;
import org.slf4j.Logger;

public class Mettle {

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "mettle";
    public static final String MODNAME = MODID;

    public static IMettleRegistry registry;
    public static IPlatformUtils platformUtils;

}
