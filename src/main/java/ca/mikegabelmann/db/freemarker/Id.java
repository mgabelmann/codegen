package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.java.AbstractJavaPrintFactory;
import ca.mikegabelmann.codegen.java.JavaClassPrintFactory;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaImport;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaPackage;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaConstructorModifier;
import ca.mikegabelmann.codegen.java.lang.modifiers.JavaFieldModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Id {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(Id.class);

    private static final AbstractJavaPrintFactory factory = new JavaClassPrintFactory();

    /** Do not instantiate this class. */
    private Id() {}


    public static String constructorNoArgs(@NotNull final LocalKeyWrapper lkw) {
        JavaConstructor con = new JavaConstructor(lkw.getCanonicalName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        return factory.print(con);
    }

    public static String constructorArgs(@NotNull final LocalKeyWrapper lkw) {
        JavaConstructor con = new JavaConstructor(lkw.getCanonicalName());
        con.addModifier(JavaConstructorModifier.PUBLIC);

        for (AbstractWrapper column : lkw.getColumnValues()) {
            //LOG.trace("constructor arg type={}", column.getCanonicalName());
            con.addArgument(new JavaArgument(column.getSimpleName(), column.getVariableName(), true));

            //do not add keys or foreign key imports as they are in same package
            //FIXME: for FKs this is adding class, but not package. Do we need this? Probably only for non-Fks
            //lkw.addImport(column.getCanonicalName());
        }

        return factory.print(con);
    }

    public static String field(@NotNull final AbstractWrapper column) {
        JavaField field = new JavaField(column.getSimpleName(), column.getVariableName(), true);
        field.addModifier(JavaFieldModifier.PRIVATE);

        if (column instanceof ColumnWrapper cw) {
            field.addAnnotation(Entity.getColumnAnnotation(cw));

        } else if (column instanceof ForeignKeyWrapper fkw) {
            field.addAnnotation(new JavaAnnotation("jakarta.persistence.ManyToOne"));
            field.addAnnotation(Entity.getJoinColumn(fkw));

        } else {
            LOG.warn("something odd happened, received a {}", column.getClass().getCanonicalName());
        }

        return factory.print(field);
    }

    public static String getter(@NotNull final AbstractWrapper wrapper) {
        return Entity.getter(wrapper);
    }

    public static String setter(@NotNull final AbstractWrapper wrapper) {
        return Entity.setter(wrapper);
    }

    public static String toStringGenerator(@NotNull final LocalKeyWrapper lkw) {
        return Entity.toStringGenerator(lkw.getColumnValues(), lkw.getSimpleName());
    }

    public static String equalsGenerator(@NotNull final LocalKeyWrapper lkw) {
        lkw.addImport("java.util.Objects");
        return Entity.equalsGenerator(lkw.getColumnValues(), lkw.getSimpleName(), "that");
    }

    public static String hashCodeGenerator(@NotNull final LocalKeyWrapper lkw) {
        lkw.addImport("java.util.Objects");
        return Entity.hashCodeGenerator(lkw.getColumnValues());
    }

    public static String printPackage(@NotNull final LocalKeyWrapper lkw) {
        return factory.print(new JavaPackage(lkw.getPackageName()));
    }

    public static String printImports(@NotNull final LocalKeyWrapper lkw) {
        Set<String> imports = lkw.getAllImports();

        return imports.stream().map(a -> factory.print(new JavaImport(a))).collect(Collectors.joining(System.lineSeparator()));
    }

}
