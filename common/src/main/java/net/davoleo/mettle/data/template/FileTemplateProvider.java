package net.davoleo.mettle.data.template;

import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import net.davoleo.mettle.init.ModRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class FileTemplateProvider implements ITemplateProvider{
    @Override
    public boolean getTemplates(Path source, ComponentType componentType, String templatePath, Stream.Builder<Pair<String, ITemplateInfo>> builder) {
        ModRegistry.METALS.forEach((name, metal) -> {

            if (componentType != null && !metal.value().components().get(componentType))
                return;

            Matcher matcher = TemplateVariable.PATTERN.matcher(templatePath);
            List<TemplateVariable> variables = new ArrayList<>();
            while(matcher.find())
                variables.add(Objects.requireNonNull(TemplateVariable.getTemplateVariable(matcher.group())));


            if(!variables.isEmpty()) {
                ITemplateProvider.combineTemplateVarReplacements(variables, 0, new LinkedList<>(), replacements -> {
                    String instancedFile = templatePath.replace(".template", "");
                    int i = 0;
                    for (IReplacement replacement : replacements) {
                        instancedFile = instancedFile.replace(variables.get(i++).var(), replacement.pathName());
                    }

                    builder.add(Pair.of(instancedFile, new TemplateInfo(templatePath, replacements)));
                }, metal.value());
            }
        });
        return true;
    }

}
