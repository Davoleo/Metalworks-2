package net.davoleo.mettle.data.template;

import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.data.IReplacement;
import net.davoleo.mettle.data.TemplateVariable;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * given a template file location define the template infos used to virtualize resource files
 */
public interface ITemplateProvider {
    /**
     *
     * @param componentType the component type defined in the conditions file, can be null
     * @param templateFile the path to the template file
     * @param builder here you add the templateInfos used to inside getResource
     * @return
     */
    boolean getTemplates(Path source, ComponentType componentType, String templateFile, Stream.Builder<Pair<String, ITemplateInfo>> builder);


    static void combineTemplateVarReplacements(List<TemplateVariable> templateVariables, int index, LinkedList<IReplacement> replacements, Consumer<IReplacement[]> callback, IMetal metal)
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
