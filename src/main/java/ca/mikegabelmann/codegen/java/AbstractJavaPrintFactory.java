package ca.mikegabelmann.codegen.java;

import ca.mikegabelmann.codegen.PrintFactory;
import ca.mikegabelmann.codegen.Printable;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaAnnotation;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaArgument;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaClass;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaConstructor;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaField;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaImport;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaMethod;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaPackage;
import ca.mikegabelmann.codegen.java.lang.classbody.JavaReturnType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractJavaPrintFactory implements PrintFactory {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(AbstractJavaPrintFactory.class);

    @Override
    public String print(@NotNull final Printable printable) {
        if (printable instanceof JavaAnnotation) {
            return printAnnotation((JavaAnnotation) printable);

        } else if (printable instanceof JavaArgument) {
            return printArgument((JavaArgument) printable);

        } else if (printable instanceof JavaClass) {
            return printClass((JavaClass) printable);

        } else if (printable instanceof JavaConstructor) {
            return printConstructor((JavaConstructor) printable);

        } else if (printable instanceof JavaField) {
            return printField((JavaField) printable);

        } else if (printable instanceof JavaImport) {
            return printImport((JavaImport) printable);

        } else if (printable instanceof JavaMethod) {
            return printMethod((JavaMethod) printable);

        } else if (printable instanceof JavaPackage) {
            return printPackage((JavaPackage) printable);

        } else if (printable instanceof JavaReturnType) {
            return printReturn((JavaReturnType) printable);

        } else {
            //odd behaviour may ensue...
            logger.warn("unknown printable type {}", printable.getClass().getName());
            return "";
        }
    }

    abstract String printAnnotation(@NotNull final JavaAnnotation annotation);

    abstract String printArgument(@NotNull final JavaArgument argument);

    abstract String printClass(@NotNull final JavaClass javaClass);

    abstract String printConstructor(@NotNull final JavaConstructor javaConstructor);

    abstract String printField(@NotNull final JavaField field);

    abstract String printImport(@NotNull final JavaImport javaImport);

    abstract String printMethod(@NotNull final JavaMethod method);

    abstract String printPackage(@NotNull final JavaPackage javaPackage);

    abstract String printReturn(final JavaReturnType javaReturnType);

}
