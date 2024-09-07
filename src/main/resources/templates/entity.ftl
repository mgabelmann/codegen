<#assign classbody>

<#if javadoc>
/**
 * <p>Entity for <em>${tableWrapper.tableType.name}</em>.</p>
 * <p><b>NOTE:</b>if this class is re-generated any changes will be lost, extend this class if you wish to
 * add functionality</p>
 *
 * @author ${author}
 * @version ${version}
 * @see ${tableWrapper.packageName}.${tableWrapper.getSimpleName()}
 */
</#if>
@${tableWrapper.addTypedImport("javax.annotation.processing.Generated")}(
    value = "${author}",
    date = "${buildDtm}",
    comments = "This code was generated by tools for use with Java applications using Hibernate and Spring Data."
)
@${tableWrapper.addTypedImport("jakarta.persistence.Entity")}
${Entity.tableAnnotation(tableWrapper, "jakarta.persistence.Table", schema)}
<#--alternative way to print complex statement ${Entity.annotation(Entity.getTableAnnotation(tableWrapper, "jakarta.persistence.Table", schema))} -->
public class ${tableWrapper.getSimpleName()} implements ${tableWrapper.addTypedImport("java.io.Serializable")} {
    <#if javadoc>/** UID. */</#if>
    @${tableWrapper.addTypedImport("java.io.Serial")}
    private static final long serialVersionUID = 1L;

    //PROPERTIES
<#list tableWrapper.getAllColumns() as column>
    <#if javadoc>/** Column ${column.id}. */</#if>
    ${Entity.field(column)}

</#list>

    //CONSTRUCTORS
    <#if javadoc>/** Default constructor. Required by JPA. */</#if>
    ${Entity.constructorNoArgs(tableWrapper)}

    <#if javadoc>/** Required args constructor. */</#if>
    ${Entity.constructorArgs(tableWrapper, false)}

    <#if javadoc>/** All args constructor. */</#if>
    ${Entity.constructorArgs(tableWrapper, true)}

    //TODO: associations, collections

    //GETTERS & SETTERS
<#list tableWrapper.getAllColumns() as column>
    <#if javadoc>/** Set ${column.id}. */</#if>
    ${Entity.setter(column)}

    <#if javadoc>/** Get ${column.id}. */</#if>
    ${Entity.getter(column)}

</#list>
    //OBJECT overrides
    ${Entity.toStringGenerator(tableWrapper)}

    ${Entity.equalsGenerator(tableWrapper)}

    ${Entity.hashCodeGenerator(tableWrapper)}
}
</#assign>


<#--package ${basePackagePath}.model-->
${Entity.printPackage(tableWrapper)}

${Entity.printImports(tableWrapper)}

${classbody}
