package ca.mikegabelmann.codegen;

import org.jetbrains.annotations.NotNull;


public interface PrintFactory {

    /**
     * Output this item in a language or format specific manner. eg: Java, Kotlin, HTML, UML, etc.
     * @param printable
     * @return
     */
    String print(@NotNull final Printable printable);

}
