package hu.fatihyilmaz.tosad.persistence.dao;

import hu.fatihyilmaz.tosad.model.generator.Template;
import org.springframework.data.repository.CrudRepository;

public interface TemplateDAO extends CrudRepository<Template, Integer> {

}