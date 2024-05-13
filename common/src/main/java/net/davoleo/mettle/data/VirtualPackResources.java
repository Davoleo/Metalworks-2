package net.davoleo.mettle.data;

import com.google.common.base.Joiner;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.init.ModRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public class VirtualPackResources extends AbstractPackResources {

    private Path source;

    public VirtualPackResources() {
        super(new File("AWOWA"));
        this.source = Mettle.platformUtils.getResourcesPath().resolve("virtual");
        if (Mettle.platformUtils.isDevEnv()) {
            String newPath = source.toString().replace(Mettle.platformUtils.getModLoader().toString(), "common");
            this.source = Path.of(newPath);
        }
        System.out.println("VIRTUAL PACK source:" +  source);
    }

    private Optional<String> getMetalNameFromResource(String resourceName) {

        // / or \ or _ or .
        return Arrays.stream(resourceName.split("[\\\\/_.]"))
                .filter(ModRegistry.METALS::has)
                .findAny();
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
        //System.out.println("getResource(" + resourcePath + ")");

        var metalName = getMetalNameFromResource(resourcePath);
        if (metalName.isEmpty())
            throw new ResourcePackFileNotFoundException(new File("METTLE_VIRTUAL_PACK"), resourcePath);

        String templatePath = resourcePath
                .replace(metalName.get(), "§metal§")
                .replace(".json", ".template.json");
        var templateString = Files.readString(source.resolve(templatePath));
        templateString = templateString.replaceAll("§metal§", metalName.get());
        return new ByteArrayInputStream(templateString.getBytes());
    }

    @Override
    protected boolean hasResource(String resourcePath) {
        //System.out.println("hasResource(" + resourcePath + ")");

        var metalName = getMetalNameFromResource(resourcePath);
        if (metalName.isEmpty())
            return false;

        String templatePath = resourcePath
                .replace(metalName.get(), "§metal§")
                .replace(".json", ".template.json");
        return Files.exists(source.resolve(templatePath));
    }

    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String pathIn, int maxDepth, Predicate<String> filter) {
        //System.out.println("getResources(" + type + " " + namespace + " " + pathIn + ")");
        Path packRoot = source.resolve(type.getDirectory()).resolve(namespace);
        Path input = packRoot.getFileSystem().getPath(pathIn);

        try (var files = Files.walk(packRoot)) {
            return files
                    .map(packRoot::relativize)
                    .filter(path -> path.getNameCount() <= maxDepth && !path.toString().endsWith(".mcmeta") && path.startsWith(input))
                    .filter(path -> filter.test(path.toString()))
                    .map(path -> Joiner.on("/").join(path))
                    .flatMap(path -> {
                        Stream.Builder<String> resources = Stream.builder();
                        ModRegistry.METALS.forEach((name, metal) -> {
                            //TODO: dynamic blacklist check
                            if (metal.mod().equals("minecraft"))
                                return;

                            String replaced = path
                                    .replace("§metal§", name)
                                    .replace(".template", "");
                            resources.add(replaced);
                        });
                        return resources.build();
                    })
                    .map(path -> new ResourceLocation(namespace, path))
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            return Collections.emptyList();
        }
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
