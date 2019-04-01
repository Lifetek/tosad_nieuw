package hu.fatihyilmaz.tosad.model.targetschema;

import hu.fatihyilmaz.tosad.model.rule.BusinessRule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TargetTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tableid;

    private String name;

    @OneToMany(mappedBy = "table")
    private List<BusinessRule> businessRules = new ArrayList<BusinessRule>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetdatabase_tdid")
    private TargetDatabase targetDatabase;

    public TargetTable() {
    }

    public TargetTable(int tableId, String name) {
        this.tableid = tableId;
        this.name = name;
    }

    public TargetTable(String name) {
        this.name = name;
    }

    public int getTableid() {
        return tableid;
    }



    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<BusinessRule> getBusinessRules() {
        return businessRules;
    }

    public void setBusinessRules(List<BusinessRule> businessRules) {
        this.businessRules = businessRules;
    }

    public void addBusinessRule(BusinessRule businessRule){
        businessRules.add(businessRule);
        businessRule.setTable(this);
    }

    public void removeBusinessRule(BusinessRule businessRule){
        businessRules.remove(businessRule);
        businessRule.setTable(null);
    }
    public TargetDatabase getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(TargetDatabase targetDatabase) {
        this.targetDatabase = targetDatabase;
    }
    @Override
    public String toString() {
        return "TargetTable{" +
                "tableid=" + tableid +
                ", name='" + name + '\'' +
                ", businessRules="  +
                ", targetDatabase=" +
                '}';
    }
}
