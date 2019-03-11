package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.rule.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Integer> {

}
