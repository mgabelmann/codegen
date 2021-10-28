package ${basePackagePath}.dao;

import javax.annotation.Generated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ${basePackagePath}.model.${tableWrapper.tableType.javaName};

/**
 * <p>Data Access Object (DAO) for ${tableWrapper.tableType.javaName}.</p>
 * <p><b>NOTE:</b>if this class is re-generated any changes will be lost, extend this interface if you wish to
 * add functionality</p>
 *
 * @author ${author}
 * @version ${version}
 * @see ${basePackagePath}.model.${tableWrapper.tableType.javaName}
 */
@Generated(
    value = "${author}",
    date = "${buildDtm}",
    comments = "This code was generated by tools for use with Java applications using Hibernate and Spring Data."
)
@Repository
@Transactional
public interface ${tableWrapper.tableType.javaName}Repository extends JpaRepository<${tableWrapper.tableType.javaName}, SqlDataType_2_JavaType> {

}
