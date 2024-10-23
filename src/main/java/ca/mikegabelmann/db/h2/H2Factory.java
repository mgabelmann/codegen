package ca.mikegabelmann.db.h2;

import ca.mikegabelmann.db.ColumnMatcher;
import ca.mikegabelmann.db.DatabaseFactory;
import ca.mikegabelmann.db.antlr.h2.H2Lexer;
import ca.mikegabelmann.db.antlr.h2.H2Parser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;

import java.io.IOException;
import java.util.List;


public class H2Factory implements DatabaseFactory {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(H2Factory.class);

    private final H2ParserImpl h2Parser;


    /** Constructor. */
    public H2Factory(final ColumnMatcher columnMatcher) {
        this.h2Parser = new H2ParserImpl(columnMatcher);
    }

    @Override
    public void parseStream(CharStream cs) throws IOException {
        H2Lexer lexer = new H2Lexer(cs);
        H2Parser parser = new H2Parser(new CommonTokenStream(lexer));

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        parser.addParseListener(h2Parser);

        parser.sql_script();
    }

    @Override
    public List<TableType> getTables() {
        return h2Parser.getTables();
    }

    @Override
    public TableType getTable(String tableName) {
        return h2Parser.getTable(tableName);
    }
}
