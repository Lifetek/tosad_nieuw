package hu.fatihyilmaz.tosad.model.rule;

import hu.fatihyilmaz.tosad.model.generator.Template;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BRType {
    //one to many met template

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int brtid;

    private String name;
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_categoryid")
    private Category category;

    @OneToMany(mappedBy = "brtype")
    private List<Template> templates = new ArrayList<Template>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "BRType_Operator",
    joinColumns = {@JoinColumn(name = "brtype_brtid") },
    inverseJoinColumns = {@JoinColumn(name = "operator_operatorid") }
    )
    private List<Operator>  operators = new ArrayList<Operator>();

    public BRType() {
    }

    public BRType(int brtId, String name, String code) {
        this.brtid = brtId;
        this.name = name;
        this.code = code;
    }

    public BRType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public int getBrtid() {
        return brtid;
    }

    public void setBrtid(int brtid) {
        this.brtid = brtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public void addTemplate(Template template){
        templates.add(template);
        template.setBrtype(this);
    }

    public void removeTemplate(Template template){
        templates.remove(template);
        template.setBrtype(null);
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public void addOperator(Operator operator){
        operators.add(operator);
        operator.getBrTypes().add(this);
    }

    public void removeOperator(Operator operator){
        operators.remove(operator);
        operator.getBrTypes().remove(this);
    }


    public boolean equals(Object otherObject) {
        boolean equalObjects = false;

        if (otherObject instanceof BRType){
            BRType otherBRType = (BRType) otherObject;

            if (this.brtid == otherBRType.brtid){
                equalObjects = true;
            }
        }
        return equalObjects;
    }
}
