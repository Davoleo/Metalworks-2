package net.davoleo.mettle.data.template;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


/**
 * Generates resource's input stream given the instantiated path of the resource.<br>
 * Template instances generation is handled by {@link net.davoleo.mettle.data.VirtualPackResources} during game startup
 */
public interface ITemplateResource {

    InputStream getResource(Path source) throws IOException;

}
