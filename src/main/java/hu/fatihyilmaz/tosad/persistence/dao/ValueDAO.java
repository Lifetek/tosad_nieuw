package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.rule.Value;
import org.springframework.data.repository.CrudRepository;

public interface ValueDAO extends CrudRepository<Value, Integer> {

}