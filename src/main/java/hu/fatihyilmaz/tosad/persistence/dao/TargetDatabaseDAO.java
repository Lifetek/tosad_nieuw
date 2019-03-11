package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.targetschema.TargetDatabase;
import org.springframework.data.repository.CrudRepository;

public interface TargetDatabaseDAO extends CrudRepository<TargetDatabase, Integer> {

}
