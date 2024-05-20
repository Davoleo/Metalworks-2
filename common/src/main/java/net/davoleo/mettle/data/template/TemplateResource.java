package net.davoleo.mettle.data.template;

import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

public record TemplateResource(String template, IReplacement[] replacements) implements ITemplateResource {

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
}
