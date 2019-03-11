package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.targetschema.Attribute;
import org.springframework.data.repository.CrudRepository;

public interface TargetAttributeDAO extends CrudRepository<Attribute, Integer> {

}
