package ca.mikegabelmann.db;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.antlr.v4.runtime.CharStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;

import javax.xml.namespace.QName;
import java.io.StringWriter;


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

        //print XML tree
        JAXBContext context = JAXBContext.newInstance(TableType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        JAXBElement<TableType> je = new JAXBElement<TableType>(new QName("http://db.apache.org/torque/5.0/templates/database", "table"), TableType.class, factory.getTable());
        marshaller.marshal(je, sw);

        String xml = sw.toString();
        LOG.debug(xml);
    }

}
