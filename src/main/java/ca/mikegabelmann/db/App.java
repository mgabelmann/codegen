package ca.mikegabelmann.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(App.class);


    /**
     * Entry point to application.
     * @param args command line arguments
     * @throws Exception error
     */
    public static void main(String[] args) throws Exception {
        SQLiteFactory factory = new SQLiteFactory();
        factory.parseFile(App.class.getResourceAsStream("/example.sqlite"));
    }

}
