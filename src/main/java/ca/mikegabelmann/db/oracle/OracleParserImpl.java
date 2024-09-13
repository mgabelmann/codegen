package ca.mikegabelmann.db.oracle;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.db.DatabaseParser;
import ca.mikegabelmann.db.antlr.oracle.PlSqlParserBaseListener;
import ca.mikegabelmann.db.antlr.oracle.PlSqlParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.TableType;

import java.util.ArrayList;
import java.util.List;


public class OracleParserImpl extends PlSqlParserBaseListener implements DatabaseParser {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(OracleParserImpl.class);

    /** List of parsed tables. */
    private final List<TableType> tableTypes;

    /** Current table, not exposed. */
    private TableType table;

    public OracleParserImpl() {
        this.tableTypes = new ArrayList<>();
    }


    @Override
    public List<TableType> getTables() {
        return tableTypes;
    }

    @Override
    public TableType getTable(final String tableName) {
        return tableTypes.stream().filter(tt -> tt.getName().equals(tableName)).findFirst().orElse(null);
    }


    @Override
    public void enterSql_script(PlSqlParser.Sql_scriptContext ctx) {
        LOG.debug("parse - start");
    }

    @Override
    public void exitSql_script(PlSqlParser.Sql_scriptContext ctx) {
        LOG.debug("parse - end");
    }

    @Override
    public void enterCreate_table(PlSqlParser.Create_tableContext ctx) {
        LOG.debug("enterCreate_table");
        this.table = new TableType();
    }

    @Override
    public void exitCreate_table(PlSqlParser.Create_tableContext ctx) {
        String tableName = ctx.table_name().getText();

        table.setName(tableName);
        table.setJavaName(NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, tableName));
        table.setDescription("");
        table.setBaseClass("");
        table.setAbstract(Boolean.FALSE);

        this.tableTypes.add(table);

        if (LOG.isDebugEnabled()) {
            LOG.debug("table={}", tableName);
        }
    }
}
