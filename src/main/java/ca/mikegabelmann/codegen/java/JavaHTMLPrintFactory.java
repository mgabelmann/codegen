package ca.mikegabelmann.codegen.java;

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


public class JavaHTMLPrintFactory extends AbstractJavaPrintFactory {
    /** Logger. */
    private static final Logger logger = LogManager.getLogger(JavaHTMLPrintFactory.class);


    @Override
    String printAnnotation(@NotNull JavaAnnotation annotation) {
        //TODO: implement me
        return "";
    }

    @Override
    String printArgument(@NotNull JavaArgument argument) {
        //TODO: implement me
        return "";
    }

    @Override
    String printClass(@NotNull JavaClass javaClass) {
        //TODO: implement me
        return "";
    }

    @Override
    String printConstructor(@NotNull JavaConstructor javaConstructor) {
        //TODO: implement me
        return "";
    }

    @Override
    String printField(@NotNull JavaField field) {
        //TODO: implement me
        return "";
    }

    @Override
    String printImport(@NotNull JavaImport javaImport) {
        //TODO: implement me
        return "";
    }

    @Override
    String printMethod(@NotNull JavaMethod method) {
        //TODO: implement me
        return "";
    }

    @Override
    String printPackage(@NotNull JavaPackage javaPackage) {
        //TODO: implement me
        return "";
    }

    @Override
    String printReturn(JavaReturnType javaReturnType) {
        //TODO: implement me
        return "";
    }

}
