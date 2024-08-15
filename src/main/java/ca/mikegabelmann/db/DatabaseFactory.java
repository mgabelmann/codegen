package ca.mikegabelmann.db;

import org.antlr.v4.runtime.CharStream;

import java.io.IOException;


public interface DatabaseFactory extends DatabaseParser {

    /**
     * Parse a stream of data.
     * @param cs character stream
     * @throws IOException error
     */
    void parseStream(CharStream cs) throws IOException;

}
