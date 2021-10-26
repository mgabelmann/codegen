package ${basePackagePath}.dao;

import javax.annotation.Generated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ${basePackagePath}.model.${table.javaName};

/**
 * <p>Data Access Object (DAO) for ${table.javaName}.</p>
 * <p><b>NOTE:</b>if this class is re-generated any changes will be lost, extend this interface if you wish to
 * add functionality</p>
 *
 * @author ${author}
 * @version ${version}
 * @see ${basePackagePath}.model.${table.javaName}
 */
@Generated(
    value = "${author}",
    date = "${buildDtm}",
    comments = "This code was generated by tools for use with Java applications using Hibernate and Spring Data."
)
@Repository
@Transactional
public interface ${table.javaName}Repository extends JpaRepository<${table.javaName}, Integer> {

}