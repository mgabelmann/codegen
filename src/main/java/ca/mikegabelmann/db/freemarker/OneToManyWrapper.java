package ca.mikegabelmann.db.freemarker;

import ca.mikegabelmann.codegen.NamingType;
import ca.mikegabelmann.codegen.util.NameUtil;

import java.util.Set;

public class OneToManyWrapper extends AbstractWrapper {

    private final String name;
    private final String javaType;
    private final String mappedBy;

    public OneToManyWrapper(String javaType, String mappedBy, String name) {
        this.javaType = javaType;
        this.mappedBy = mappedBy;
        this.name = name;
    }

    public String getMappedBy() {
        return mappedBy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public Set<String> getAllImports() {
        return imports;
    }

    @Override
    public String getCanonicalName() {
        return javaType;
    }

    @Override
    public String getSimpleName() {
        String simpleName = javaType.lastIndexOf(".") > 0 ? javaType.substring(javaType.lastIndexOf(".") + 1) : javaType;
        return "List<" + simpleName + ">";
    }

    @Override
    public String getVariableName() {
        return NameUtil.getJavaName(NamingType.LOWER_CAMEL_CASE, name);
    }

    @Override
    public String toString() {
        return "OneToManyWrapper{" +
                "javaType='" + javaType + '\'' +
                ", name='" + name + '\'' +
                ", mappedBy='" + mappedBy + '\'' +
                '}';
    }

}
