package net.davoleo.mettle.data.template;

import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import net.davoleo.mettle.data.template.ITemplateInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;

public record TemplateInfo(String template, IReplacement[] replacements) implements ITemplateInfo {

    @Override
    public InputStream getResource(Path source) throws IOException {
        var templateContent = Files.readString(source.resolve(this.template()));

        Matcher matcher = TemplateVariable.PATTERN.matcher(this.template());
        int index = 0;
        while (matcher.find())
        {
            TemplateVariable templateVariables = TemplateVariable.getTemplateVariable(matcher.group());
            templateContent = templateContent.replaceAll(templateVariables.var(), this.replacements()[index].name());
            templateContent = templateContent.replaceAll("§path_" + templateVariables.varName() + '§', this.replacements()[index].pathName());
            index++;
        }
        return new ByteArrayInputStream(templateContent.getBytes());
    }

    private void combineTemplateVarReplacements(List<TemplateVariable> templateVariables, int index, LinkedList<IReplacement> replacements, Consumer<IReplacement[]> callback, IMetal metal)
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
}
