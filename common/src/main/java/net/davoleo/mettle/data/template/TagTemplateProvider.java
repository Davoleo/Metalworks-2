package net.davoleo.mettle.data.template;

import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import net.davoleo.mettle.init.ModRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class TagTemplateProvider implements ITemplateProvider{

    @Override
    public boolean getTemplates(Path source,ComponentType componentType, String templatePath, Stream.Builder<Pair<String, ITemplateInfo>> builder) {


        if(!templatePath.endsWith(".tag.template.json"))
            return false;

        ModRegistry.METALS.forEach((name, metal) -> {

            if (componentType != null && !metal.value().components().get(componentType))
                return;
            try {
                List<String> blocks = Files.readAllLines(source.resolve(templatePath));
                List<String> totalElements = new LinkedList<>();
                String tagFile = templatePath.replace(".tag.template", "");
                for (String fileTag : blocks) {
                    Matcher matcher = TemplateVariable.PATTERN.matcher(fileTag);
                    List<TemplateVariable> variables = new ArrayList<>();
                    while(matcher.find())
                        variables.add(Objects.requireNonNull(TemplateVariable.getTemplateVariable(matcher.group())));

                    if(!variables.isEmpty()) {


                        ITemplateProvider.combineTemplateVarReplacements(variables, 0, new LinkedList<>(), replacements -> {
                            String location = fileTag;
                            int i = 0;
                            for (IReplacement replacement : replacements)
                                location = location.replace(variables.get(i++).var(), replacement.pathName());
                            totalElements.add(location);
                        }, metal.value());
                    }
                }

                builder.add(Pair.of(tagFile, new TagTemplateInfo(templatePath, totalElements.toArray(String[]::new))));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }


}
