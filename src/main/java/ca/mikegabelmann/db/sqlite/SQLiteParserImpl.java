package ca.mikegabelmann.db.sqlite;

import ca.mikegabelmann.codegen.java.JavaNamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import ca.mikegabelmann.db.ColumnMatcher;
import ca.mikegabelmann.db.DatabaseParser;
import ca.mikegabelmann.db.antlr.sqlite.SQLiteParser;
import ca.mikegabelmann.db.antlr.sqlite.SQLiteParserBaseListener;
import ca.mikegabelmann.db.mapping.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
import org.apache.torque.ForeignKeyType;
import org.apache.torque.ReferenceType;
import org.apache.torque.SqlDataType;
import org.apache.torque.TableType;
import org.apache.torque.UniqueColumnType;
import org.apache.torque.UniqueType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SQLiteParserImpl extends SQLiteParserBaseListener implements DatabaseParser {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(SQLiteFactory.class);

    /** List of parsed tables. */
    private final List<TableType> tableTypes;

    private final ColumnMatcher columnMatcher;

    /** Current table, not exposed. */
    private TableType table;


    /**
     * Constructor.
     * @param columnMatcher
     */
    public SQLiteParserImpl(final ColumnMatcher columnMatcher) {
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

    //ANTLR method overrides
    @Override
    public void enterParse(SQLiteParser.ParseContext ctx) {
        LOG.debug("parse - start");
    }

    @Override
    public void exitParse(SQLiteParser.ParseContext ctx) {
        LOG.debug("parse - end");
    }

    @Override
    public void enterCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        LOG.debug("enterCreate_table_stmt");
        this.table = new TableType();
    }

    @Override
    public void exitCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        String tableName = ctx.table_name().getText();

        table.setName(tableName);
        table.setJavaName(NameUtil.getJavaName(JavaNamingType.UPPER_CAMEL_CASE, tableName));
        table.setDescription("");
        table.setBaseClass("");
        table.setAbstract(Boolean.FALSE);

        this.tableTypes.add(table);

        if (LOG.isDebugEnabled()) {
            LOG.debug("table={}", tableName);
        }
    }

    @Override
    public void exitColumn_def(SQLiteParser.Column_defContext ctx) {
        String columnName = ctx.column_name().getText();
        String typeName = ctx.type_name().name(0).getText().toUpperCase();

        ColumnType column = new ColumnType();
        column.setName(columnName);
        column.setPrimaryKey(false);
        column.setRequired(false);
        column.setAutoIncrement(Boolean.FALSE);
        //column.setJavaType(JavaReturnType.OBJECT);

        SQLiteParser.Signed_numberContext sn = ctx.type_name().signed_number(0);

        if (sn != null) {
            column.setSize(new BigDecimal(sn.getText()));
        }

        column.setJavaName(NameUtil.getJavaName(JavaNamingType.LOWER_CAMEL_CASE, columnName));
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

        if (LOG.isDebugEnabled()) {
            LOG.debug("columnName={}, type={}, typeName={}", columnName, column.getType().name(), typeName);
        }

        for (SQLiteParser.Column_constraintContext constraint : ctx.column_constraint()) {
            String key = constraint.getText();

            //LOG.debug("\tconstraint={}", key);

            if ("PRIMARYKEY".equals(key)) {
                column.setPrimaryKey(Boolean.TRUE);
                column.setRequired(Boolean.TRUE);
                column.setAutoIncrement(Boolean.FALSE);

            } else if ("REQUIRED".equals(key)) {
                column.setRequired(true);

            } else if ("UNIQUE".equals(key)) {
                UniqueColumnType uct = new UniqueColumnType();
                uct.setName(columnName);

                UniqueType ut = new UniqueType();
                ut.getUniqueColumn().add(uct);
                //NOTE: the default constraint name is tableName.columnName if not set explicitly
                //ut.setName("");
                table.getForeignKeyOrIndexOrUnique().add(ut);

            } else if ("NOTNULL".equals(key)) {
                column.setRequired(Boolean.TRUE);

            } else if (constraint.DEFAULT_() != null) {
                if (constraint.signed_number() != null) {
                    column.setDefault(constraint.signed_number().getText());

                } else if (constraint.literal_value() != null) {
                    column.setDefault(constraint.literal_value().getText());

                } else if (constraint.expr().getText() != null) {
                    column.setDefault(constraint.expr().getText());
                }

            } else {
                LOG.warn("unable to parse {}", key);
            }
        }

        table.getColumn().add(column);
    }

    @Override
    public void exitTable_constraint(SQLiteParser.Table_constraintContext ctx) {
        //LOG.trace("Table_constraintContext, text={}", ctx.getText());

        //primary key columns
        if (!ctx.indexed_column().isEmpty()) {
            LOG.debug("primary key");

            for (SQLiteParser.Indexed_columnContext record : ctx.indexed_column()) {
                LOG.debug("\tcolumn={}", record.getText());
            }
        }

        if (ctx.foreign_key_clause() != null) {

/* examples
    <foreign-key foreignTable="INCIDENT_CAUSE_STATUS_CODE" name="ICSCD_INCC_FK">
        <reference local="INCIDENT_CAUSE_STATUS_CODE" foreign="INCIDENT_CAUSE_STATUS_CODE"/>
    </foreign-key>

    <foreign-key foreignTable="MOBILE_SUPPORT_RSRC_PRODUCT" name="MSDT_MSRPC_FK">
        <reference local="MS_PRODUCT_TYPE_CODE" foreign="MS_PRODUCT_TYPE_CODE"/>
        <reference local="MOBILE_SUPPORT_RSRC_TYPE_CODE" foreign="MOBILE_SUPPORT_RSRC_TYPE_CODE"/>
    </foreign-key>
*/

            if (LOG.isDebugEnabled()) {
                LOG.debug("foreign key, table={}", ctx.foreign_key_clause().foreign_table().getText());
            }

            ForeignKeyType fkt = new ForeignKeyType();
            fkt.setForeignTable(ctx.foreign_key_clause().foreign_table().getText());
            //fkt.setName(""); //not sure what it uses for the name

            if (!ctx.foreign_key_clause().DELETE_().isEmpty()) {
                //TODO: there is a delete clause, but how to get it?
                //fkt.setOnDelete(CascadeType.);
            }

            if (!ctx.foreign_key_clause().UPDATE_().isEmpty()) {
                //TODO: there is an update clause, but how to get it?
                //fkt.setOnUpdate(CascadeType.);
            }

            for (int i=0; i < ctx.column_name().size(); i++) {
                SQLiteParser.Column_nameContext local = ctx.column_name(i);
                SQLiteParser.Column_nameContext foreign = ctx.foreign_key_clause().column_name(i);

                ReferenceType rt = new ReferenceType();
                rt.setLocal(local.getText());
                rt.setForeign(foreign.getText());

                fkt.getReference().add(rt);
            }

            table.getForeignKeyOrIndexOrUnique().add(fkt);
        }
    }

}


