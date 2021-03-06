package ca.mikegabelmann.db;

import ca.mikegabelmann.db.freemarker.TableWrapper;
import ca.mikegabelmann.db.sqlite.SQLiteFactory;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.antlr.v4.runtime.CharStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;

import javax.xml.namespace.QName;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author mgabe
 */
public class App {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(App.class);


    /**
     * Entry point to application.
     * @param args command line arguments
     * @throws Exception error
     */
    public static void main(final String[] args) throws Exception {

        SQLiteFactory factory = new SQLiteFactory();
        factory.parseFile(CharStreams.fromStream(App.class.getResourceAsStream("/example4.sqlite")));

        //JAXB - print XML tree
        JAXBContext context = JAXBContext.newInstance(TableType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        JAXBElement<TableType> je = new JAXBElement<>(new QName("http://db.apache.org/torque/5.0/templates/database", "table"), TableType.class, factory.getTable());
        marshaller.marshal(je, sw);

        String xml = sw.toString();
        LOG.debug(xml);

        Map<String, String> sqlMappings = App.getMappings("/sqldatatype.properties");

        //FreeMarker - process templates
        Version version = new Version(2, 3, 20);
        Configuration cfg = new Configuration(version);
        cfg.setClassForTemplateLoading(App.class, "/templates");
        cfg.setIncompatibleImprovements(version);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //base set of parameters inherited by all
        Map<String, Object> input = new HashMap<>();
        input.put("basePackagePath", "ca.mikegabelmann.persistence");
        input.put("author", "codegenerator");
        input.put("version", "1.0.0");
        input.put("buildDtm", LocalDateTime.now());
        input.put("javadoc", Boolean.FALSE);

        {
            Map<String, Object> inputTemplate = new HashMap<>();
            inputTemplate.putAll(input);
            inputTemplate.put("tableWrapper", new TableWrapper(sqlMappings, factory.getTable()));

            Template dao = cfg.getTemplate("dao.ftl");
            Writer cw = new OutputStreamWriter(System.out);
            dao.process(inputTemplate, cw);
        }

        {
            Map<String, Object> inputTemplate = new HashMap<>();
            inputTemplate.putAll(input);
            inputTemplate.put("tableWrapper", new TableWrapper(sqlMappings, factory.getTable()));

            //specialized properties
            //inputTemplate.put("schema", "SCHEMA");

            //allow template to access static classes
            BeansWrapperBuilder wrapper = new BeansWrapperBuilder(version);
            TemplateHashModel staticModels = wrapper.build().getStaticModels();
            //TemplateHashModel staticObjectUtil1 = (TemplateHashModel) staticModels.get("ca.mikegabelmann.codegen.util.ObjectUtil");
            TemplateHashModel staticObjectUtil2 = (TemplateHashModel) staticModels.get("ca.mikegabelmann.db.freemarker.Entity");

            //inputTemplate.put("ObjectUtil", staticObjectUtil1);
            inputTemplate.put("Entity", staticObjectUtil2);

            Template entity = cfg.getTemplate("entity.ftl");
            Writer cw2 = new OutputStreamWriter(System.out);
            entity.process(inputTemplate, cw2);
        }
    }

    /**
     * Read property file with SqlDataType to Java SQL types.
     * @param filename file to load
     * @return key/value pairs
     * @throws IOException error
     */
    public static Map<String, String> getMappings(final String filename) throws IOException {
        Map<String, String> map = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(filename)))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] keyvalue = line.split("=");
                    map.put(keyvalue[0].trim(), keyvalue[1].trim());

                } else if (LOG.isTraceEnabled()) {
                    LOG.trace("skipping comment or empty line");
                }
            }
        }

        return map;
    }

}
