package net.davoleo.mettle.data.template;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import net.davoleo.mettle.init.ModRegistry;
import net.davoleo.mettle.tag.ITagMember;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class VirtualResourceProviders {

    public static void generateTemplateResources(Stream.Builder<Pair<String, ITemplateResource>> stream, String templatePath, @Nullable ComponentType resourceCondition) {
        ModRegistry.METALS.forEach((metal) -> {

            if (resourceCondition != null && !metal.value().components().get(resourceCondition))
                return;

            Matcher matcher = TemplateVariable.PATTERN.matcher(templatePath);
            List<TemplateVariable> variables = new ArrayList<>();
            while(matcher.find())
                variables.add(Objects.requireNonNull(TemplateVariable.getTemplateVariable(matcher.group())));


            if(!variables.isEmpty()) {
                combineTemplateVarReplacements(variables, 0, new LinkedList<>(), replacements -> {
                    String instancedFile = templatePath.replace(".template", "");
                    int i = 0;
                    for (IReplacement replacement : replacements) {
                        instancedFile = instancedFile.replace(variables.get(i++).var(), replacement.pathName());
                    }

                    stream.add(Pair.of(instancedFile, new TemplateResource(templatePath, replacements)));
                }, metal.value());
            }
        });
    }

    private static void combineTemplateVarReplacements(List<TemplateVariable> templateVariables, int index, LinkedList<IReplacement> replacements, Consumer<IReplacement[]> callback, IMetal metal)
    {
        //already backtracked through all template variables
        if(index >= templateVariables.size())
        {
            //Replacement combination is complete callback to output
            callback.accept(replacements.toArray(IReplacement[]::new));
            return;
        }
        TemplateVariable variable = templateVariables.get(index);
        for(var iterateEveryVariation : variable.getReplacements(metal)) {
            replacements.add(iterateEveryVariation);
            combineTemplateVarReplacements(templateVariables, index + 1, replacements, callback, metal);
            replacements.removeLast();
        }
    }

    @SuppressWarnings("unchecked")
    public static void tagMembers(Map<String, ITemplateResource> resourceMap) {

        Multimap<String, ResourceLocation> tagMembers = Multimaps.newListMultimap(Maps.newHashMap(), Lists::newArrayList);

        ModRegistry.BLOCKS.forEach(reg -> {
            Block block = reg.entry().get();
            if (block instanceof ITagMember) {
                var tagMember = (ITagMember<Block>) block;
                tagMember.getTags().forEach(tag -> {
                    String location = "data/%s/tags/blocks/%s.json"
                            .formatted(tag.location().getNamespace(), tag.location().getPath());
                    tagMembers.put(location, tagMember.getResourceLocation());
                });

            }
        });

        tagMembers.asMap().forEach((path, members) -> resourceMap.put(path, new TagResource(members)));
    }

}
