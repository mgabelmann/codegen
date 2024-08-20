package ca.mikegabelmann.db.sqlite;

import ca.mikegabelmann.db.ColumnMatcher;
import ca.mikegabelmann.db.DatabaseFactory;
import ca.mikegabelmann.db.antlr.sqlite.SQLiteLexer;
import ca.mikegabelmann.db.antlr.sqlite.SQLiteParser;
import ca.mikegabelmann.db.mapping.Mapping;
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


public class SQLiteFactory implements DatabaseFactory {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(SQLiteFactory.class);

    private final SQLiteParserImpl sqliteParser;


    /** Constructor. */
    public SQLiteFactory(ColumnMatcher columnMatcher) {
        this.sqliteParser = new SQLiteParserImpl(columnMatcher);
    }

    @Override
    public void parseStream(CharStream cs) throws IOException {
        SQLiteLexer lexer = new SQLiteLexer(cs);
        SQLiteParser parser = new SQLiteParser(new CommonTokenStream(lexer));

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        parser.addParseListener(sqliteParser);

        parser.parse();
    }

    @Override
    public List<TableType> getTables() {
        return sqliteParser.getTables();
    }

    @Override
    public TableType getTable(final String tableName) {
        return sqliteParser.getTable(tableName);
    }

}
