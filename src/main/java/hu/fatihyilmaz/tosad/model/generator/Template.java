package hu.fatihyilmaz.tosad.model.generator;

import hu.fatihyilmaz.tosad.model.rule.BRType;
import hu.fatihyilmaz.tosad.model.targetschema.DatabaseType;

import javax.persistence.*;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int templateid;

    private String brCode;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brtype_brtid") //bi directioneel: Template <> BRType
    private BRType brtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "databasetype_dtid") //bi directioneel: Template <> DatabaseType
    private DatabaseType databaseType;

    public Template() {

    }

    public Template(int templateid, String brCode, String description) {
        this.templateid = templateid;
        this.brCode = brCode;
        this.description = description;
    }

    public Template(String brCode, String description) {
        this.brCode = brCode;
        this.description = description;
    }

    public int getTemplateid() {
        return templateid;
    }

    public void setTemplateid(int templateid) {
        this.templateid = templateid;
    }

    public String getBrCode() {
        return brCode;
    }

    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BRType getBrtype() {
        return brtype;
    }

    public void setBrtype(BRType brtype) {
        this.brtype = brtype;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public boolean equals(Object otherObject) {
        boolean equalObjects = false;

        if (otherObject instanceof Template){
            Template otherTemplate = (Template) otherObject;

            if (this.templateid== otherTemplate.templateid){
                equalObjects = true;
            }
        }
        return equalObjects;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateid=" + templateid +
                ", brCode='" + brCode + '\'' +
                ", description='" + description + '\'' +
                ", brtype=" + brtype +
                ", databaseType=" + databaseType +
                '}';
    }
}
