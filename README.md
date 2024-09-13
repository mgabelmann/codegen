# codegen
Generates Java code based on database SQL scripts or existing database tables similar to 
Hibernate Tools.

## Build

## Runtime
When using Java 16 or newer you must add the following switches to the JRE command

    --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
  
For your IDE you need to add VM Options.

### IntelliJ
1. when running App.java click "Edit Configurations..." under the main menu
2. Select "Modify Options"
3. Select "Add VM Options"
4. Paste options above into the text box
5. Run App.java

