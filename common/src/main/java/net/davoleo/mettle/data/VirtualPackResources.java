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
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public class VirtualPackResources extends AbstractPackResources {

    private Path source;

    private final Map<String, ComponentType> resourceConditions;
    private final Map<String, TemplateInfo> compiledToTemplates;

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
                    .flatMap(this::generateReplacedResources)
                    .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

            System.out.println(compiledToTemplates);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("VIRTUAL PACK source:" +  source);

    }

    private Stream<Pair<String, TemplateInfo>> generateReplacedResources(String templatePath) {
        Stream.Builder<Pair<String, TemplateInfo>> resources = Stream.builder();
        ModRegistry.METALS.forEach((name, metal) -> {

            var component = resourceConditions.get(templatePath);
            if (component != null && !metal.value().components().get(component)) {
                return;
            }

            Matcher matcher = TemplateVariable.PATTERN.matcher(templatePath);
            List<TemplateVariable> variables = new ArrayList<>();
            while(matcher.find())
                variables.add(Objects.requireNonNull(TemplateVariable.getTemplateVariable(matcher.group())));


            if(!variables.isEmpty()) {
                combineTemplateVarReplacements(variables, 0, new LinkedList<>(), replacements -> {
                    String instancedFile = templatePath.replace(".template", "");
                    int i = 0;
                    for (IReplacement replacement : replacements) {
                        instancedFile = instancedFile.replace(variables.get(i++).var(), replacement.pathName());
                    }

                    resources.add(Pair.of(instancedFile, new TemplateInfo(templatePath, replacements)));
                }, metal.value());
            }
        });
        return resources.build();
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

        Matcher matcher = TemplateVariable.PATTERN.matcher(template.template());
        int index = 0;
        while (matcher.find())
        {
            TemplateVariable templateVariables = TemplateVariable.getTemplateVariable(matcher.group());
            templateContent = templateContent.replaceAll(templateVariables.var(), template.replacements()[index].name());
            templateContent = templateContent.replaceAll("§path_" + templateVariables.varName() + '§', template.replacements()[index].pathName());
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
        return Set.of("mettle");
    }

    @Override
    public String getName() {
        return "mettle_virtual";
    }

    @Override
    public void close() {}
}
