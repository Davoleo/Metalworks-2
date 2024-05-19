package net.davoleo.mettle.data.template;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


/**
 * This interface is used to describe what is the virtual json to generate given a template file content
 */
public interface ITemplateInfo {
    InputStream getResource(Path source) throws IOException;

}
