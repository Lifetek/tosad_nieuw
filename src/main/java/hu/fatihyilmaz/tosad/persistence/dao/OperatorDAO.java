package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.rule.Operator;
import org.springframework.data.repository.CrudRepository;

public interface OperatorDAO extends CrudRepository<Operator, Integer> {

}
