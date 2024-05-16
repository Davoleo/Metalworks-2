package net.davoleo.mettle.data;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.init.ModRegistry;
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
    private final Map<String, String> compiledToTemplates;

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
                    .filter(path -> !path.toString().endsWith(".mcmeta") && !path.toString().endsWith("conditions.json"))
                    .map(path -> Joiner.on("/").join(path))
                    .flatMap(path -> {
                        Stream.Builder<Pair<String, String>> resources = Stream.builder();
                        ModRegistry.METALS.forEach((name, metal) -> {

                            var component = resourceConditions.get(path);
                            if (component != null && !metal.value().components().get(component)) {
                                return;
                            }



                            //TODO : Generic Replace? replace more? more loops? when?
                            String replaced = path
                                    .replace("§metal§", name)
                                    .replace(".template", "");

                            resources.add(Pair.of(replaced, path));
                        });
                        return resources.build();
                    })
                    .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("VIRTUAL PACK source:" +  source);

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

        String template = compiledToTemplates.get(resourcePath);

        if (template == null)
            throw new ResourcePackFileNotFoundException(new File("METTLE_VIRTUAL_PACK"), resourcePath);

        var templateContent = Files.readString(source.resolve(template));
        for (var variable : TemplateVariables.getVariables(resourcePath, template)) {
            templateContent = templateContent.replaceAll(variable.getA(), variable.getB());
        }
        return new ByteArrayInputStream(templateContent.getBytes());
    }

    @Override
    protected boolean hasResource(String resourcePath) {
        //System.out.println("hasResource(" + resourcePath + ")");
        return compiledToTemplates.containsKey(resourcePath);
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String pathIn, int maxDepth, Predicate<String> filter) {
        //System.out.println("getResources(" + type + " " + namespace + " " + pathIn + ")");
        String prefix = type.getDirectory() + '/' + namespace + '/' + pathIn;

        return compiledToTemplates
                .keySet()
                .stream()
                .filter(path -> path.startsWith(prefix) && (path.split("/").length - 2) <= maxDepth) //TODO: maxDepth condition : Verify
                .filter(filter)
                .map(string -> new ResourceLocation(Mettle.MODID, string.replace(prefix, "")))
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
        return Set.of("mettle");
    }

    @Override
    public String getName() {
        return "mettle_virtual";
    }

    @Override
    public void close() {}
}
