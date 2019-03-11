package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.rule.BusinessRule;
import org.springframework.data.repository.CrudRepository;

public interface BusinessRuleDAO extends CrudRepository<BusinessRule, Integer> {

}