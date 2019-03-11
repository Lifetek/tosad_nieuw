package hu.fatihyilmaz.tosad.model.targetschema;

import hu.fatihyilmaz.tosad.model.generator.Template;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DatabaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dtid;

    private String name;

    @OneToMany(mappedBy = "databaseType")
    private List<Template> templates = new ArrayList<Template>();

    public DatabaseType() {
    }

    public DatabaseType(int dtid, String name) {
        this.dtid = dtid;
        this.name = name;
    }

    public DatabaseType(String name) {
        this.name = name;
    }

    public int getDtid() {
        return dtid;
    }

    public void setDtid(int dtid) {
        this.dtid = dtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public void addTemplate(Template template){
        templates.add(template);
        template.setDatabaseType(this);
    }

    public void removeTemplate(Template template){
        templates.remove(template);
        template.setDatabaseType(null);
    }

    @Override
    public String toString() {
        return "DatabaseType{" +
                "dtid=" + dtid +
                ", name='" + name + '\'' +
                ", templates=" + templates +
                '}';
    }
}
