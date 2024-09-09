package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.torque.ColumnType;
import org.apache.torque.ForeignKeyType;
import org.apache.torque.ReferenceType;
import org.apache.torque.TableType;
import org.apache.torque.UniqueColumnType;
import org.apache.torque.UniqueType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author mgabe
 */
public class TableWrapper extends AbstractWrapper {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(TableWrapper.class);

    /**  */
    private final TableType tableType;

    /**  */
    private final Map<String, ForeignKeyWrapper> columnsFk;

    /**  */
    private final Map<String, ColumnWrapper> columns;

//    /**  */
//    private final Map<String, AbstractWrapper> keys;

    /**  */
    private final LocalKeyWrapper localKey;


    /**
     * Constructor.
     * @param tableType
     */
    public TableWrapper(
        @NotNull TableType tableType) {

        this.tableType = tableType;
        this.columnsFk = new TreeMap<>();
        this.columns = new TreeMap<>();
//        this.keys = new TreeMap<>();

        Map<String, ColumnWrapper> allCols = new TreeMap<>();
        Map<String, AbstractWrapper> keys = new TreeMap<>();

        //collection of foreign keys by column name (not part of key)
        Map<String, ForeignKeyWrapper> foreignKeysTmp = tableType.getForeignKeyOrIndexOrUnique().stream()
                .filter(c -> c instanceof ForeignKeyType)
                .map(c -> (ForeignKeyType) c)
                .map(ForeignKeyWrapper::new)
                .collect(Collectors.toMap(c -> c.getForeignKeyType().getForeignTable(), Function.identity()));

        //collection of columns that are not part of primary or composite key
        Map<String, ColumnWrapper> columnsNonKeyTmp = tableType.getColumn().stream()
                .filter(c -> !c.isPrimaryKey())
                .map(ColumnWrapper::new)
                .collect(Collectors.toMap(ColumnWrapper::getName, Function.identity()));

        //collection of all keys (primary or composite) that identify this table
        Map<String, ColumnWrapper> columnsKeyTmp = tableType.getColumn().stream()
                .filter(ColumnType::isPrimaryKey)
                .map(ColumnWrapper::new)
                .collect(Collectors.toMap(ColumnWrapper::getName, Function.identity()));

        //keep a record of all columns
        allCols.putAll(columnsNonKeyTmp);
        allCols.putAll(columnsKeyTmp);

        //remove columns that are a fk
        for (ForeignKeyWrapper fkw : foreignKeysTmp.values()) {
            for (ReferenceType rt : fkw.getForeignKeyType().getReference()) {
                String localKey = rt.getLocal();

                if (columnsNonKeyTmp.containsKey(localKey)) {
                    ColumnWrapper cw = columnsNonKeyTmp.remove(localKey);
                    fkw.addColumn(cw);
                    columnsFk.put(fkw.getForeignKeyType().getForeignTable(), fkw);

                } else if (columnsKeyTmp.containsKey(localKey)) {
                    ColumnWrapper cw = columnsKeyTmp.remove(localKey);
                    fkw.addColumn(cw);
                    keys.put(fkw.getForeignKeyType().getForeignTable(), fkw);
                }
            }
        }

        keys.putAll(columnsKeyTmp);

        this.columns.putAll(columnsNonKeyTmp);
        this.localKey = new LocalKeyWrapper(this.getName(), keys);

        for (Object o : tableType.getForeignKeyOrIndexOrUnique()) {
            if (o instanceof UniqueType ut) {
                for (UniqueColumnType uct : ut.getUniqueColumn()) {
                    if (allCols.containsKey(uct.getName())) {
                        ColumnWrapper cw = allCols.get(uct.getName());
                        cw.setUnique(true);
                    }
                }
            }
        }

        LOG.debug("setup {} complete", getName());

        /*
        //TODO: process index
        //TODO: process unique

        if (localKey.isCompositeKey()) {
            //TODO:
        } else {
            //TODO:
        }

        if (!columnsNonKey.isEmpty()) {
            //TODO:
        }

        if (!foreignKeys.isEmpty()) {
            //TODO: detect OneToOne, OneToMany, ManyToOne, ManyToMany
        }
        */

    }

    public TableType getTableType() {
        return tableType;
    }

    public LocalKeyWrapper getLocalKey() {
        return localKey;
    }

//    public Map<String, AbstractWrapper> getKeys() {
//        return keys;
//    }

    public Map<String, ForeignKeyWrapper> getColumnsFk() {
        return columnsFk;
    }

    public Map<String, ColumnWrapper> getColumns() {
        return columns;
    }

    public List<AbstractWrapper> getNonFkColumns() {
        List<AbstractWrapper> cols = new ArrayList<>();

        if (localKey.isCompositeKey()) {
            cols.add(localKey);
        } else {
            cols.addAll(localKey.getColumnValues());
        }

        cols.addAll(columns.values());

        return cols;
    }

    public List<AbstractWrapper> getAllColumns() {
        List<AbstractWrapper> cols = new ArrayList<>(this.getNonFkColumns());
        cols.addAll(columnsFk.values());
        return cols;
    }

    @Override
    public Set<String> getAllImports() {
        this.imports.addAll(columnsFk.values().stream().map(ForeignKeyWrapper::getAllImports).flatMap(Collection::stream).toList());
        this.imports.addAll(columns.values().stream().map(ColumnWrapper::getAllImports).flatMap(Collection::stream).toList());
        this.imports.addAll(localKey.getAllImports());
        return this.imports;
    }

    @Override
    public final String getCanonicalName() {
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getSimpleName() {
        return NameUtil.getJavaName(NamingType.UPPER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getVariableName() {
        return NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, tableType.getName());
    }

    @Override
    public final String getName() {
        return tableType.getName();
    }

    @Override
    public String getId() {
        return tableType.getName();
    }

    @Override
    public boolean isRequired() {
        return false;
    }

}
