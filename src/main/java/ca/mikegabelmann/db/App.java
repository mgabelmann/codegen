package ca.mikegabelmann.db;

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
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


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


        //FreeMarker - process templates
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(App.class, "/templates");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<>();
        input.put("table", factory.getTable());
        input.put("basePackagePath", "ca.mikegabelmann.persistence");
        input.put("author", "codegenerator");
        input.put("version", "1.0.0");
        input.put("buildDtm", LocalDateTime.now());

        //dump output to console
        Template dao = cfg.getTemplate("dao.ftl");
        Writer cw = new OutputStreamWriter(System.out);
        dao.process(input, cw);

        Template entity = cfg.getTemplate("entity.ftl");
        Writer cw2 = new OutputStreamWriter(System.out);
        entity.process(input, cw2);
    }

}
