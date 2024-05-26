package net.davoleo.mettle.data;

import com.google.common.collect.Sets;
import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.IMetal;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum TemplateVariable {
    METAL("metal", (iMetal) -> Set.of(IReplacement.joint(iMetal.name()))),
    FILLER_BLOCK("filler", (iMetal) -> {
        Set<IReplacement> replacements = Sets.newHashSet();
        for (OreVariant variant : iMetal.oreVariants()) {
            replacements.add(IReplacement.split(variant.toString(), variant.getTextureLocation().toString()));
        }
        return replacements;
    }),

    ;
    public static final Pattern PATTERN = Pattern.compile("§.*?§");

    private final String varName;
    private final Function<IMetal, Set<IReplacement>> getReplacementsFun;


    TemplateVariable(String varName, Function<IMetal, Set<IReplacement>> result) {
        this.varName = varName;
        this.getReplacementsFun = result;
    }

    public String varName() {
        return varName;
    }

    public String var() {
        return '§' + varName + '§';
    }


    @Deprecated
    public static Iterable<Tuple<String, String>> getVariables(String resourceName, String template) {
        Stream.Builder<Tuple<String, String>> stream = Stream.builder();

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

    @Nullable
    public static TemplateVariable getTemplateVariable(String template)
    {
        for (TemplateVariable value : TemplateVariable.values()) {
            if(value.var().equals(template))
                return value;
        }
        return null;
    }

    public Set<IReplacement> getReplacements(IMetal iMetal) {
        return this.getReplacementsFun.apply(iMetal);
    }
}
