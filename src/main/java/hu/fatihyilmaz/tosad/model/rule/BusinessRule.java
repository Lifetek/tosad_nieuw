package hu.fatihyilmaz.tosad.model.rule;

import hu.fatihyilmaz.tosad.model.targetschema.Attribute;
import hu.fatihyilmaz.tosad.model.targetschema.TargetTable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BusinessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int brid;

    private String generatedName;
    private String description;
    private String errorMessage;
    private String name;
    private String customCode;
    private String triggerType;
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brtype_brtid")
    private BRType brType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_tableid") //bi directioneel: BusinessRule <> TargetTable
    private TargetTable table;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_operatorid")
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributeid1")
    private Attribute attributeId1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributeid2")
    private Attribute attributeId2;

    //bi directional: BusinessRule <> Value, vandaar mapped. Uni directioneel met @OneToMany is niet veilig: https://thoughts-on-java.org/best-practices-many-one-one-many-associations-mappings/
    @OneToMany(mappedBy = "businessRule")
    private List<Value> values = new ArrayList<Value>();

    public BusinessRule() {
    }

    public BusinessRule(int brid, String generatedName, String description, String errorMessage, String name, String customCode, String triggerType, String state) {
        this.brid = brid;
        this.generatedName = generatedName;
        this.description = description;
        this.errorMessage = errorMessage;
        this.name = name;
        this.customCode = customCode;
        this.triggerType = triggerType;
        this.state = state;
    }

    public BusinessRule(String generatedName, String description, String errorMessage, String name, String customCode, String triggerType, String state) {
        this.generatedName = generatedName;
        this.description = description;
        this.errorMessage = errorMessage;
        this.name = name;
        this.customCode = customCode;
        this.triggerType = triggerType;
        this.state = state;
    }

    public int getBrid() {
        return brid;
    }

    public void setBrid(int brid) {
        this.brid = brid;
    }

    public String getGeneratedName() {
        return generatedName;
    }

    public void setGeneratedName(String generatedName) {
        this.generatedName = generatedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BRType getBrType() {
        return brType;
    }

    public void setBrType(BRType brType) {
        this.brType = brType;
    }

    public TargetTable getTable() {
        return table;
    }

    public void setTable(TargetTable table) {
        this.table = table;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Attribute getAttributeId1() {
        return attributeId1;
    }

    public void setAttributeId1(Attribute attributeId1) {
        this.attributeId1 = attributeId1;
    }

    public Attribute getAttributeId2() {
        return attributeId2;
    }

    public void setAttributeId2(Attribute attributeId2) {
        this.attributeId2 = attributeId2;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public void addValue(Value value){
        values.add(value);
        value.setBusinessRule(this);
    }

    public void removeValue(Value value){
        values.remove(value);
        value.setBusinessRule(null);
    }

    public boolean equals(Object otherObject) {
        boolean equalObjects = false;

        if (otherObject instanceof BusinessRule){
            BusinessRule otherBusinessRule = (BusinessRule) otherObject;

            if (this.brid == otherBusinessRule.brid){
                equalObjects = true;
            }
        }
        return equalObjects;
    }

}
