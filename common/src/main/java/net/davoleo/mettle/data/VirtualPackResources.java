package net.davoleo.mettle.data;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
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
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public class VirtualPackResources extends AbstractPackResources {

    private Path source;

    private final Map<String, ComponentType> resourceConditions;
    private final Map<String, TemplateInfo> compiledToTemplates;

    private final Pattern variablePattern = Pattern.compile("§.*?§");

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
                    .flatMap(path -> {
                        Stream.Builder<Pair<String, TemplateInfo>> resources = Stream.builder();
                        ModRegistry.METALS.forEach((name, metal) -> {

                            var component = resourceConditions.get(path);
                            if (component != null && !metal.value().components().get(component)) {
                                return;
                            }


                            List<TemplateVariables> foundVariables = new ArrayList<>();
                            Matcher matcher = variablePattern.matcher(path);
                            while(matcher.find())
                                foundVariables.add(TemplateVariables.getTemplateVariable(matcher.group()));

                            if(!foundVariables.isEmpty())
                                backtracking(instance -> {
                                    String instancedFile = path.replace(".template", "");
                                    for (int i = 0;i < instance.length;i++)
                                        instancedFile = instancedFile.replace(foundVariables.get(i).varName(), instance[i]);
                                    resources.add(Pair.of(instancedFile, new TemplateInfo(path, instance)));
                                },foundVariables,0,new LinkedList<>(), metal.value());

                        });
                        return resources.build();
                    })
                    .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

            System.out.println(compiledToTemplates);
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

        TemplateInfo template = compiledToTemplates.get(resourcePath);

        if (template == null)
            throw new ResourcePackFileNotFoundException(new File("METTLE_VIRTUAL_PACK"), resourcePath);

        var templateContent = Files.readString(source.resolve(template.template()));

        Matcher matcher = variablePattern.matcher(template.template());
        int index = 0;
        while (matcher.find())
        {
            TemplateVariables templateVariables = TemplateVariables.getTemplateVariable(matcher.group());
            templateContent = templateContent.replaceAll(templateVariables.varName(), template.replaces()[index]);
            index++;
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
        String prefix = type.getDirectory() + '/' + namespace + '/' + pathIn + "/";

        Collection<ResourceLocation> a = compiledToTemplates
                .keySet()
                .stream()
                .filter(path -> path.startsWith(prefix) && (path.split("/").length - 2) <= maxDepth) //TODO: maxDepth condition : Verify
                .filter(filter)
                .map(string -> new ResourceLocation(Mettle.MODID, string.replace(prefix, "")))
                .toList();
        return a;
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

    public static void backtracking(Consumer<String[]> fun, List<TemplateVariables> templateVariables, int index, LinkedList<String> replaces, IMetal metal)
    {
        if(index >= templateVariables.size())
        {
            //REPLACES IS COMPLETE ADD NEW TEMPLATE INFO
            fun.accept(replaces.toArray(String[]::new));
            return;
        }
        TemplateVariables variable = templateVariables.get(index);
        for(String iterateEveryVariation : variable.getResult(metal)) {
            replaces.add(iterateEveryVariation);
            backtracking(fun, templateVariables, index + 1, replaces, metal);
            replaces.removeLast();
        }
    }
}
