package todoapp.app.viewmodels.todoitem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.FatalException;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.value.Clob;

import lombok.Getter;

@Mixin
public class Dto_downloadSchema {

    private final Dto dto;

    final MimeType mimeTypeApplicationZip;

    public Dto_downloadSchema(final Dto dto) {
        this.dto = dto;
        try {
            mimeTypeApplicationZip = new MimeType("application", "zip");
        } catch (final MimeTypeParseException ex) {
            throw new FatalException(ex);
        }
    }

    public Object $$(final String fileName) throws JAXBException, IOException {

        final Class<? extends Dto> dtoClass = dto.getClass();
        final String dtoClassName = dtoClass.getName();

        final JAXBContext context = JAXBContext.newInstance(dtoClass);

        final CatalogingSchemaOutputResolver outputResolver = new CatalogingSchemaOutputResolver();
        context.generateSchema(outputResolver);

        final List<String> namespaceUris = outputResolver.getNamespaceUris();

        if(namespaceUris.isEmpty()) {
            container.warnUser("No schemas were generated for " + dtoClassName + "; programming error?");
            return null;
        }

        if(namespaceUris.size() == 1) {
            final String namespaceUri = namespaceUris.get(0);
            return new Clob(withSuffix(fileName, "xsd"), "text/xml", outputResolver.getSchemaTextFor(namespaceUri));
        }

        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(baos);
            final OutputStreamWriter writer = new OutputStreamWriter(zos);
            for (final String namespaceUri : namespaceUris) {
                zos.putNextEntry(new ZipEntry(zipEntryNameFor(namespaceUri)));
                writer.write(outputResolver.getSchemaTextFor(namespaceUri));
                writer.flush();
                zos.closeEntry();
            }
            writer.close();
            return new Blob(withSuffix(fileName, "zip"), mimeTypeApplicationZip, baos.toByteArray());
        } catch (final IOException ex) {
            throw new FatalException("Unable to create zip", ex);
        }
    }

    private static String withSuffix(String fileName, String suffix) {
        if(!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        if(!fileName.endsWith(suffix)) {
            fileName += suffix;
        }
        return fileName;
    }

    public String default0$$() {
        return withSuffix(dto.getClass().getName(), "xsd");
    }

    private static String zipEntryNameFor(final String namespaceUri) {
        final Iterable<String> parts = Splitter.on("/").split(namespaceUri);
        final String last = Iterables.getLast(parts);
//        return withSuffix(last, "xsd");
        return withSuffix(namespaceUri, "xsd");
    }


    @Inject
    DomainObjectContainer container;
}

/**
 * An implementation of {@link SchemaOutputResolver} that keeps track of all the schemas for which it has
 * {@link #createOutput(String, String) created} an output {@link StreamResult} containing the content of the schema.
 */
class CatalogingSchemaOutputResolver extends SchemaOutputResolver
{
    @Getter
    private List<String> namespaceUris = Lists.newArrayList();

    private Map<String, StreamResultWithWriter> schemaResultByNamespaceUri = Maps.newLinkedHashMap();

    public String getSchemaTextFor(final String namespaceUri) {
        final StreamResultWithWriter streamResult = schemaResultByNamespaceUri.get(namespaceUri);
        return streamResult != null? streamResult.asString(): null;
    }

    @Override
    public Result createOutput(
            final String namespaceUri, final String suggestedFileName) throws IOException {

        final StreamResultWithWriter result = new StreamResultWithWriter();

        result.setSystemId(namespaceUri);

        namespaceUris.add(namespaceUri);
        schemaResultByNamespaceUri.put(namespaceUri, result);

        return result;
    }
}

/**
 * A {@link StreamResult} that contains its own writer.
 *
 * <p>
 *     The point is that the writer is only ever queried lazily AFTER the result has been generated.
 * </p>
 */
class StreamResultWithWriter extends StreamResult {
    private final StringWriter writer;

    public StreamResultWithWriter() {
        this(new StringWriter());
    }

    private StreamResultWithWriter(StringWriter writer) {
        super(writer);
        this.writer = writer;
    }

    public String asString() {
        return writer.toString();
    }
}
