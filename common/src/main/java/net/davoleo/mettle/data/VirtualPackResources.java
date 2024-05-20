package net.davoleo.mettle.data;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.data.template.ITemplateResource;
import net.davoleo.mettle.data.template.VirtualResourceProviders;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public class VirtualPackResources extends AbstractPackResources {

    private Path source;

    private final Map<String, ComponentType> resourceConditions;
    private final Map<String, ITemplateResource> compiledToTemplates;

    public VirtualPackResources() {
        super(new File("AWOWA"));
        this.source = Mettle.platformUtils.getResourcesPath().resolve("virtual");
        if (Mettle.platformUtils.isDevEnv()) {
            String newPath = source.toString().replace(Mettle.platformUtils.getModLoader().toString(), "common");
            this.source = Path.of(newPath);
        }


        try (var files = Files.walk(source);
             JsonReader conditionsReader = new JsonReader(new InputStreamReader(getRootResource("conditions.json")))
        ) {
            Type type = new TypeToken<Map<String, ComponentType>>(){}.getType();
            resourceConditions = new Gson().fromJson(conditionsReader, type);

            compiledToTemplates = files
                    .map(source::relativize)
                    .filter(path -> path.toString().endsWith(".template.json"))
                    .map(path -> Joiner.on("/").join(path))
                    .flatMap(templatePath -> {
                        Stream.Builder<Pair<String, ITemplateResource>> stream = Stream.builder();
                        VirtualResourceProviders.generateTemplateResources(stream, templatePath, resourceConditions.get(templatePath));
                        return stream.build();
                    })
                    .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        VirtualResourceProviders.tagMembers(compiledToTemplates);
    }


    @NotNull
    @Override
    public InputStream getRootResource(String fileName) throws IOException {
        if (fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
        return new FileInputStream(source.resolve(fileName).toFile());
    }

    @Override
    protected InputStream getResource(String resourcePath) throws IOException {

        ITemplateResource template = compiledToTemplates.get(resourcePath);

        if (template == null)
            throw new ResourcePackFileNotFoundException(new File("METTLE_VIRTUAL_PACK"), resourcePath);
        return template.getResource(source);
    }

    @Override
    protected boolean hasResource(String resourcePath) {
        //System.out.println("hasResource(" + resourcePath + ")");
        return compiledToTemplates.containsKey(resourcePath);
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String pathIn, int maxDepth, Predicate<String> filter) {
        //System.out.println("getResources(" + type + " " + namespace + " " + pathIn + ")");
        String root = type.getDirectory() + '/' + namespace + '/';
        String entirePath = root + pathIn + '/';

        return compiledToTemplates
                .keySet()
                .stream()
                .filter(path -> path.startsWith(entirePath) && (entirePath.split("/").length - 2) <= maxDepth) //TODO: maxDepth condition : Verify
                .filter(filter)
                .map(string -> new ResourceLocation(namespace, string.replace(root, "")))
                .toList();
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        try (InputStream inputStream = this.getRootResource("pack.mcmeta")){
            return AbstractPackResources.getMetadataFromStream(deserializer, inputStream);
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return Set.of("mettle","minecraft");
    }

    @Override
    public String getName() {
        return "mettle_virtual";
    }

    @Override
    public void close() {}
}
