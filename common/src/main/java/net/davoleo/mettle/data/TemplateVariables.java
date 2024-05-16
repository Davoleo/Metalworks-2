package net.davoleo.mettle.data;

import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum TemplateVariables {
    METAL("§metal§", iMetal -> true, iMetal -> new String[]{ iMetal.name() }),
    FILLER_BLOCK("§filler§",
            iMetal -> iMetal.components().get(ComponentType.ORE),
            iMetal -> Arrays.stream(iMetal.oreVariantsTextures())
                    .map(ResourceLocation::getPath)
                    .toArray(String[]::new)
    ),
    ;

    private final String varName;
    private final Predicate<IMetal> shouldReplace;
    private final Function<IMetal, String[]> result;

    TemplateVariables(String varName, Predicate<IMetal> shouldReplace, Function<IMetal, String[]> result) {
        this.varName = varName;
        this.shouldReplace = shouldReplace;
        this.result = result;
    }

    public String varName() {
        return varName;
    }

//    public static String replaceAll(String input, IMetal metal) {
//        String result = input;
//        for (TemplateVariables var : TemplateVariables.values()) {
//            if (var.shouldReplace.test(metal)) {
//                result = input.replaceAll(var.varName(), var.result.apply(metal));
//            }
//        }
//        return result;
//    }

    public static Iterable<Tuple<String, String>> getVariables(String resourceName, String template) {
        Stream.Builder<Tuple<String, String>> stream = Stream.builder();

        Pattern pattern = Pattern.compile("§.*?§");


        var splitTemplate = template.split("§");
        assert splitTemplate.length % 2 != 0;

        String s = resourceName;
        //amoma_tin_aboba_stone
        //amoma_§metal§_aboba_§filler§
        for (int i = 0; i < splitTemplate.length; i += 2) {
            int index = s.indexOf(splitTemplate[i]);
            if (index > 0) {
                String var = s.substring(0, index);
                stream.add(new Tuple<>('§' + splitTemplate[i-1] + '§',  var));
                s = s.replace(var + splitTemplate[i], "");
            }
            else if (index == 0) {
                s = s.replace(splitTemplate[i], "");
            }
        }

        return () -> stream.build().iterator();
        // / or \ or _ or .     Arrays.stream(resourceName.split("[\\\\/_.]"))
    }
}
