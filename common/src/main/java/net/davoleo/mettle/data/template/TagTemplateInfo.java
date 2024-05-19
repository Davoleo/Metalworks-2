package net.davoleo.mettle.data.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import net.davoleo.mettle.data.template.ITemplateInfo;
import net.davoleo.mettle.init.ModRegistry;
import net.davoleo.mettle.registry.InternalRegistry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

public record TagTemplateInfo(String template,String[] values) implements ITemplateInfo {


    @Override
    public InputStream getResource(Path source) throws IOException {
        var templateContent = Files.readString(source.resolve(template));
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("replace", false);
        JsonArray array = new JsonArray();
        for (String value : values)
            array.add(value);
        jsonElement.add("values", array);
        return new ByteArrayInputStream(jsonElement.toString().getBytes());
    }
}
