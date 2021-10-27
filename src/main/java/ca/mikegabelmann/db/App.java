package ca.mikegabelmann.db;

import ca.mikegabelmann.db.freemarker.TableWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
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
        factory.parseFile(CharStreams.fromStream(App.class.getResourceAsStream("/example.sqlite")));

        //JAXB - print XML tree
        JAXBContext context = JAXBContext.newInstance(TableType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        JAXBElement<TableType> je = new JAXBElement<TableType>(new QName("http://db.apache.org/torque/5.0/templates/database", "table"), TableType.class, factory.getTable());
        marshaller.marshal(je, sw);

        String xml = sw.toString();
        LOG.debug(xml);

        Map<String, String> sqlMappings = App.getMappings("/sqldatatype.properties");

        //FreeMarker - process templates
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(App.class, "/templates");
        Version version = new Version(2, 3, 20);
        cfg.setIncompatibleImprovements(version);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        //base set of parameters inherited by all
        Map<String, Object> input = new HashMap<>();
        input.put("table", factory.getTable());
        input.put("basePackagePath", "ca.mikegabelmann.persistence");
        input.put("author", "codegenerator");
        input.put("version", "1.0.0");
        input.put("buildDtm", LocalDateTime.now());
        input.put("javadoc", Boolean.TRUE);
        //input.put("sqlMappings", sqlMappings);

        {
            Map<String, Object> inputTemplate = new HashMap<>();
            inputTemplate.putAll(input);

            Template dao = cfg.getTemplate("dao.ftl");
            Writer cw = new OutputStreamWriter(System.out);
            dao.process(inputTemplate, cw);
        }

        {
            Map<String, Object> inputTemplate = new HashMap<>();
            inputTemplate.putAll(input);
            inputTemplate.put("tableEntity", new TableWrapper(factory.getTable(), sqlMappings));

            Template entity = cfg.getTemplate("entity.ftl");
            Writer cw2 = new OutputStreamWriter(System.out);
            entity.process(inputTemplate, cw2);
        }
    }

    /**
     * Read property file.
     * @param filename
     * @return
     * @throws IOException
     */
    public static Map<String, String> getMappings(final String filename) throws IOException {
        Map<String, String> map = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(filename)))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] keyvalue = line.split("=");
                    map.put(keyvalue[0].trim(), keyvalue[1].trim());

                } else {
                    LOG.trace("skipping comment or empty line");
                }
            }
        }

        return map;
    }
}
