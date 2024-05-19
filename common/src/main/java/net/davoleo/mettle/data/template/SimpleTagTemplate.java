package net.davoleo.mettle.data.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.davoleo.mettle.Mettle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public record SimpleTagTemplate(List<String> tags) implements ITemplateInfo{
    @Override
    public InputStream getResource(Path source) throws IOException {
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("replace", false);
        JsonArray array = new JsonArray();
        for (String value : tags)
            array.add(Mettle.MODID + ":" + value);
        jsonElement.add("values", array);
        return new ByteArrayInputStream(jsonElement.toString().getBytes());
    }

}
