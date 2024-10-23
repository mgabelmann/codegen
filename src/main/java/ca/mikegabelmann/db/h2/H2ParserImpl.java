package ca.mikegabelmann.db.h2;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.db.ColumnMatcher;
import ca.mikegabelmann.db.DatabaseParser;
import ca.mikegabelmann.db.antlr.h2.H2ParserBaseListener;
import ca.mikegabelmann.db.antlr.h2.H2Parser;
import ca.mikegabelmann.db.mapping.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
import org.apache.torque.SqlDataType;
import org.apache.torque.TableType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class H2ParserImpl extends H2ParserBaseListener implements DatabaseParser {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(H2ParserImpl.class);

    /** List of parsed tables. */
    private final List<TableType> tableTypes;

    private final ColumnMatcher columnMatcher;

    /** Current table, not exposed. */
    private TableType table;

    public H2ParserImpl(final ColumnMatcher columnMatcher) {
        this.tableTypes = new ArrayList<>();
        this.columnMatcher = columnMatcher;
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
    public void enterSql_script(H2Parser.Sql_scriptContext ctx) {
        LOG.debug("parse - start");
    }

    @Override
    public void exitSql_script(H2Parser.Sql_scriptContext ctx) {
        LOG.debug("parse - end");
    }

    @Override
    public void enterCreate_table(H2Parser.Create_tableContext ctx) {
        LOG.debug("enterCreate_table");
        this.table = new TableType();
    }

    @Override
    public void exitCreate_table(H2Parser.Create_tableContext ctx) {
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

    @Override
    public void exitColumn_definition(H2Parser.Column_definitionContext ctx) {
        String columnName = ctx.column_name().getText();
        String typeName = ctx.datatype().native_datatype_element().getText();

        LOG.debug("exitColumn_definition: {}, {}", columnName, typeName);

        ColumnType column = new ColumnType();
        column.setName(columnName);
        column.setPrimaryKey(false);
        column.setRequired(false);
        column.setAutoIncrement(Boolean.FALSE);

        H2Parser.Precision_partContext precisionTmp = ctx.datatype().precision_part();
        if (precisionTmp!= null) {
            BigDecimal size = new BigDecimal(precisionTmp.numeric(0).getText());
            column.setSize(size);
        }

        column.setJavaName(NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, columnName));
        column.setDescription("");

        Integer length = column.getSize() == null ? null : column.getSize().intValue();
        //TODO: precision
        Integer precision = null;
        Integer scale = null;
        Mapping mapping = columnMatcher.matchMapping(typeName, length, precision, scale, columnName);

        if (mapping != null) {
            column.setType(SqlDataType.valueOf(mapping.getJdbcType()));
            column.setDbSqlType(typeName);
            column.setJavaSqlType(mapping.getJavaType());

        } else {
            LOG.warn("unable to find mapping for column '{}' and type '{}'", columnName, typeName);
            //FIXME: use fallback from sqldatatype.properties, is this necessary?!? could be defined as ALL
        }

        for (H2Parser.Inline_constraintContext constraint : ctx.inline_constraint()) {
            LOG.debug("constraint: {}", constraint.getText());

            if ("NOTNULL".equalsIgnoreCase(constraint.getText())) {
                column.setRequired(true);
            }

            //TODO: handle other types (unique, check, etc.)

        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("columnName={}, type={}, typeName={}", columnName, column.getType().name(), typeName);
        }

        table.getColumn().add(column);
    }

//    @Override
//    public void exitPrimary_key_clause(H2Parser.Primary_key_clauseContext ctx) {
//        LOG.debug("exitPrimary_key_clause, PRIMARY KEY={}", ctx.getText());
//    }
//
//    @Override
//    public void exitKey_clause(H2Parser.Key_clauseContext ctx) {
//        LOG.debug("exitKey_clause, PRIMARY KEY={}", ctx.getText());
//    }

    @Override
    public void exitOut_of_line_constraint(H2Parser.Out_of_line_constraintContext ctx) {
        if (table != null && ctx.PRIMARY() != null) {
            String columnName = ctx.column_name(0).getText();

            ColumnType column = table.getColumn().stream().filter(col -> col.getName().equals(columnName)).findFirst().orElse(null);
            if (column != null) {
                column.setPrimaryKey(Boolean.TRUE);
                column.setRequired(Boolean.TRUE);
                column.setAutoIncrement(Boolean.FALSE);


            } else {
                LOG.debug("primary key not found for id={}", columnName);
            }
        }

        LOG.debug("exitOut_of_line_constraint {}", ctx.getText());
    }

}
