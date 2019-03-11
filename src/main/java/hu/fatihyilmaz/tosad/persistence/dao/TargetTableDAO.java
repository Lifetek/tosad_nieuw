package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.targetschema.TargetTable;
import org.springframework.data.repository.CrudRepository;

public interface TargetTableDAO extends CrudRepository<TargetTable, Integer> {

}