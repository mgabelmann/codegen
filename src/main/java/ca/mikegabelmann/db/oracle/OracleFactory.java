package ca.mikegabelmann.db.oracle;

import ca.mikegabelmann.db.DatabaseFactory;
import ca.mikegabelmann.db.antlr.oracle.PlSqlLexer;
import ca.mikegabelmann.db.antlr.oracle.PlSqlParser;
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


public class OracleFactory implements DatabaseFactory {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(OracleFactory.class);

    private final OracleParserImpl oracleParser;


    /** Constructor. */
    public OracleFactory() {
        this.oracleParser = new OracleParserImpl();
    }

    @Override
    public void parseStream(CharStream cs) throws IOException {
        PlSqlLexer lexer = new PlSqlLexer(cs);
        PlSqlParser parser = new PlSqlParser(new CommonTokenStream(lexer));


        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        parser.addParseListener(oracleParser);

        parser.sql_script();
    }

    @Override
    public List<TableType> getTables() {
        return oracleParser.getTables();
    }

    @Override
    public TableType getTable(String tableName) {
        return oracleParser.getTable(tableName);
    }

}
