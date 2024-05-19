package net.davoleo.mettle.data.template;

import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.init.ModRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CodeTagTemplateProvider {

    public static void tagTemplates(Map<String, ITemplateInfo> templateInfoMap) {

        String tagPath = "data/minecraft/tags/";

        List<String> shovel = new LinkedList<>();
        List<String> pickaxe = new LinkedList<>();

        ModRegistry.METALS.forEach((name, metal) -> {
            if(metal.value().components().get(ComponentType.ORE))
                for (OreVariant oreVariant : metal.value().oreVariants())
                    if(oreVariant.getTool().equals("shovel"))
                        shovel.add(metal.value().name() + "_" + oreVariant + "_ore");
                    else
                        pickaxe.add(metal.value().name() + "_" + oreVariant + "_ore");
            if(metal.value().components().get(ComponentType.BLOCK))
                pickaxe.add(metal.value().name() + "_" + "block");
        });
        templateInfoMap.put(tagPath + "blocks/mineable/shovel.json", new SimpleTagTemplate(shovel));
        templateInfoMap.put(tagPath + "blocks/mineable/pickaxe.json", new SimpleTagTemplate(pickaxe));
    }
}
