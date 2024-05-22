package net.davoleo.mettle.data.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;

public record TagResource(Collection<ResourceLocation> members) implements ITemplateResource {
    @Override
    public InputStream getResource(Path source) {
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("replace", false);
        JsonArray array = new JsonArray();
        for (var value : members)
            array.add(value.toString());
        jsonElement.add("values", array);
        return new ByteArrayInputStream(jsonElement.toString().getBytes());
    }

}
