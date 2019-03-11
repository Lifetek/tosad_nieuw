package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.targetschema.DatabaseType;
import org.springframework.data.repository.CrudRepository;

public interface DatabaseTypeDAO extends CrudRepository<DatabaseType, Integer> {

}
