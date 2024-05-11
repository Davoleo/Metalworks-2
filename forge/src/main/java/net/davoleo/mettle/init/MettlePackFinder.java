package net.davoleo.mettle.init;

import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.MettlePack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MettlePackFinder {

    private static final Class<MettlePack> METTLE_PACK_CLASS = MettlePack.class;

    public static void doTheStuff() {
        Type annotationType = Type.getType(METTLE_PACK_CLASS);
        List<ModFileScanData> scanData = ModList.get().getAllScanData();
        Map<String, String> mettlePackNames = new LinkedHashMap<>();
        for (ModFileScanData data : scanData) {
            for (ModFileScanData.AnnotationData annotation : data.getAnnotations()) {
                if (Objects.equals(annotation.annotationType(), annotationType)) {
                    String mod = (String) annotation.annotationData().get("modid");
                    mettlePackNames.put(mod, annotation.memberName());
                }
            }
        }

        mettlePackNames.forEach((mod, packName) -> {
            try {
                Class<? extends IMettleIntegration> klass = Class.forName(packName).asSubclass(IMettleIntegration.class);
                var constructor = klass.getDeclaredConstructor();
                IMettleIntegration integration = constructor.newInstance();
                ModRegistry.registerPack(mod, integration);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
