package hu.fatihyilmaz.tosad.model.rule;

import javax.persistence.*;

@Entity
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int valueid;

    private int value;
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessrule_brid")
    BusinessRule businessRule;

    public Value() {
    }

    public Value(int valueId, int value, int position) {
        this.valueid = valueId;
        this.value = value;
        this.position = position;
    }

    public Value(int value, int position) {
        this.value = value;
        this.position = position;
    }

    public int getValueid() {
        return valueid;
    }

    public void setValueid(int valueid) {
        this.valueid = valueid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BusinessRule getBusinessRule() {
        return businessRule;
    }

    public void setBusinessRule(BusinessRule businessRule) {
        this.businessRule = businessRule;
    }

    public boolean equals(Object otherObject) {
        boolean equalObjects = false;

        if (otherObject instanceof Value){
            Value otherValue = (Value) otherObject;

            if (this.valueid == otherValue.valueid){
                equalObjects = true;
            }
        }
        return equalObjects;
    }

    @Override
    public String toString() {
        return "Value{" +
                "valueid=" + valueid +
                ", value=" + value +
                ", position=" + position +
                ", businessRule=" + businessRule +
                '}';
    }
}
