package ca.mikegabelmann.db;

import ca.mikegabelmann.db.freemarker.LocalKeyWrapper;
import ca.mikegabelmann.db.freemarker.TableWrapper;
import ca.mikegabelmann.db.mapping.Database;
import ca.mikegabelmann.db.mapping.ReverseEngineering;
import ca.mikegabelmann.db.sqlite.SQLiteFactory;
import com.google.googlejavaformat.java.Formatter;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;
import freemarker.template.Version;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.antlr.v4.runtime.CharStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

        /*{
            //How to write a XML file using JAXB
            JAXBContext context = JAXBContext.newInstance(ReverseEngineering.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ReverseEngineering reverseEngineering = new ReverseEngineering();
            Databases databases = new Databases();
            Database database = new Database();
            database.setName("TEST");
            databases.getDatabase().add(database);
            reverseEngineering.setDatabases(databases);
            Mapping mapping = new Mapping();
            mapping.setName("COLUMN");
            mapping.setJdbcType("VARCHAR");
            mapping.setDatabaseType("VARCHAR2");
            mapping.setJavaType("java.lang.String");
            database.getMapping().add(mapping);

            StringWriter sw = new StringWriter();
            JAXBElement<ReverseEngineering> je = new JAXBElement<>(new QName("", "reverse-engineering"), ReverseEngineering.class, reverseEngineering);
            marshaller.marshal(je, sw);

            String xml = sw.toString();
            LOG.debug(xml);
        }*/

        ColumnMatcher columnMatcher = new ColumnMatcher();
        {
            JAXBContext jc = JAXBContext.newInstance(ReverseEngineering.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            ReverseEngineering re = (ReverseEngineering) unmarshaller.unmarshal(App.class.getResourceAsStream("/dbmapping.xml"));

            //TODO: add user custom mappings
            for (Database db : re.getDatabases().getDatabase()) {
                if ("CUSTOM".equalsIgnoreCase(db.getName())) {
                    LOG.trace("adding CUSTOM mappings");
                    columnMatcher.addMappings(db.getMapping());
                }
                if ("SQLITE".equalsIgnoreCase(db.getName())) {
                    LOG.trace("adding SQLITE mappings");
                    columnMatcher.addMappings(db.getMapping());
                }
                if ("ANY".equalsIgnoreCase(db.getName())) {
                    LOG.trace("adding ALL mappings");
                    columnMatcher.addMappings(db.getMapping());
                }
            }

            LOG.debug("added all database mappings");
        }

        //ANTR parse file
        //Parse SQLITE DB statements
        SQLiteFactory factory = new SQLiteFactory(columnMatcher);
        factory.parseStream(CharStreams.fromStream(App.class.getResourceAsStream("/example_sqlite_3.sql")));
        //factory.parseStream(CharStreams.fromStream(App.class.getResourceAsStream("/example_oracle_1.sql")));

        //Parse ORACLE DB statements
        //OracleFactory factory = new OracleFactory();
        //factory.parseStream(CharStreams.fromStream(App.class.getResourceAsStream("/example_oracle_1.sql")));

        List<TableType> tables = factory.getTables();
        if (tables.isEmpty()) {
            LOG.error("No tables found");
            return;
        }

        //TableType table = tables.get(0);

        for (TableType table : tables) {
            {
                //JAXB - print XML tree
                JAXBContext context = JAXBContext.newInstance(TableType.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                StringWriter sw = new StringWriter();
                JAXBElement<TableType> je = new JAXBElement<>(new QName("http://db.apache.org/torque/5.0/templates/database", "table"), TableType.class, table);
                marshaller.marshal(je, sw);

                String xml = sw.toString();
                LOG.debug(xml);
            }

//        Map<String, String> sqlMappings = App.getMappings("/sqldatatype.properties");

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
            //input.put("basePackagePath", "ca.mgabelmann.persistence");
            input.put("author", "codegenerator");
            input.put("version", "1.0.0");
            input.put("buildDtm", LocalDateTime.now());
            input.put("javadoc", Boolean.TRUE);

            {
                //Java JPA DAO/Repository
                Map<String, Object> inputTemplate = new HashMap<>(input);

                TableWrapper tw = new TableWrapper(table);
                tw.setPackageName("ca.mgabelmann.persistence.dao");
                inputTemplate.put("tableWrapper", tw);
                inputTemplate.put("entityPackageName", "ca.mgabelmann.persistence.model");
                tw.getImports().add("ca.mgabelmann.persistence.model" + "." + tw.getSimpleName());

                if (tw.getLocalKey().isCompositeKey()) {
                    tw.getImports().add("ca.mgabelmann.persistence.model" + "." + tw.getLocalKey().getSimpleName());
                }

                BeansWrapperBuilder wrapper = new BeansWrapperBuilder(version);
                TemplateHashModel staticModels = wrapper.build().getStaticModels();
                TemplateHashModel staticObjectUtil2 = (TemplateHashModel) staticModels.get("ca.mikegabelmann.db.freemarker.Entity");

                inputTemplate.put("Entity", staticObjectUtil2);

                OutputStream baos = new ByteArrayOutputStream();
                Writer cw2 = new OutputStreamWriter(baos);

                Template dao = cfg.getTemplate("dao.ftl");
                dao.process(inputTemplate, cw2);

                //Use Google formatter to pretty print the class file
                String formattedSource = new Formatter().formatSource(baos.toString());
                LOG.debug(formattedSource);

                writeFile("target/generated-sources/", tw.getPackageName(), tw.getSimpleName() + "Repository", formattedSource);
            }

            {
                //Java JPA Table/Entity
                Map<String, Object> inputTemplate = new HashMap<>(input);

                TableWrapper tw = new TableWrapper(table);
                tw.setPackageName("ca.mgabelmann.persistence.model");
                inputTemplate.put("tableWrapper", tw);

                //specialized properties
                //inputTemplate.put("schema", "SCHEMA");

                //allow template to access static classes
                BeansWrapperBuilder wrapper = new BeansWrapperBuilder(version);
                TemplateHashModel staticModels = wrapper.build().getStaticModels();
                TemplateHashModel staticObjectUtil2 = (TemplateHashModel) staticModels.get("ca.mikegabelmann.db.freemarker.Entity");

                inputTemplate.put("Entity", staticObjectUtil2);

                OutputStream baos = new ByteArrayOutputStream();
                Writer cw2 = new OutputStreamWriter(baos);

                Template entity = cfg.getTemplate("entity.ftl");
                entity.process(inputTemplate, cw2);

                //Use Google formatter to pretty print the class file
                String formattedSource = new Formatter().formatSource(baos.toString());
                //String formattedSource = baos.toString();
                LOG.debug(formattedSource);

                writeFile("target/generated-sources/", tw.getPackageName(), tw.getSimpleName(), formattedSource);

                //does this table require a composite id?

                if (tw.getLocalKey().isCompositeKey()) {
                    LocalKeyWrapper lkw = new LocalKeyWrapper(tw.getLocalKey().getTableName(), tw.getLocalKey().getColumns());
                    lkw.setPackageName("ca.mgabelmann.persistence.model");

                    //allow template to access static classes
                    BeansWrapperBuilder wrapper2 = new BeansWrapperBuilder(version);
                    TemplateHashModel staticModels2 = wrapper2.build().getStaticModels();
                    TemplateHashModel staticObjectUtil3 = (TemplateHashModel) staticModels2.get("ca.mikegabelmann.db.freemarker.Id");

                    Map<String, Object> input2Template = new HashMap<>(input);
                    input2Template.put("keyWrapper", lkw);
                    input2Template.put("tableName", tw.getTableType().getName());
                    input2Template.put("tableSimpleName", tw.getSimpleName());
                    input2Template.put("Id", staticObjectUtil3);

                    Template id = cfg.getTemplate("id.ftl");

                    OutputStream baos2 = new ByteArrayOutputStream();
                    Writer cw3 = new OutputStreamWriter(baos2);

                    id.process(input2Template, cw3);

                    //Use Google formatter to pretty print the class file
                    //String source = baos2.toString();
                    //LOG.debug(source);
                    String formattedSource2 = new Formatter().formatSource(baos2.toString());
                    LOG.debug(formattedSource2);

                    writeFile("target/generated-sources/", lkw.getPackageName(), lkw.getSimpleName(), formattedSource2);
                }
            }
        }
    }

    private static void writeFile(String basePath, String packageName, String className, String contents) throws IOException {
        String fullClassName = packageName + "." + className;
        String fileName = basePath + fullClassName.replaceAll("\\.", "/") + ".java";

        Path path = Paths.get(fileName);
        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(contents);
            LOG.debug("created file: {}", fileName);
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
                    String[] keyValue = line.split("=");
                    map.put(keyValue[0].trim(), keyValue[1].trim());

                } else if (LOG.isTraceEnabled()) {
                    LOG.trace("skipping comment or empty line");
                }
            }
        }

        return map;
    }

}
