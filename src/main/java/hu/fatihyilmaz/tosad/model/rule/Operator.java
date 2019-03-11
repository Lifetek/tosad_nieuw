package hu.fatihyilmaz.tosad.model.rule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Operator {
    //many to many met BRType

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int operatorid;

    private String operator;

    @ManyToMany(fetch = FetchType.LAZY,
    mappedBy = "operators")

    private List<BRType> brTypes = new ArrayList<BRType>();

    public Operator() {
    }

    public Operator(int operatorId, String operator) {
        this.operatorid = operatorId;
        this.operator = operator;
    }

    public Operator(String operator) {
        this.operator = operator;
    }

    public int getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(int operatorid) {
        this.operatorid = operatorid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<BRType> getBrTypes() {
        return brTypes;
    }

    public void setBrTypes(List<BRType> brTypes) {
        this.brTypes = brTypes;
    }

    //add remove brtypes

    public boolean equals(Object otherObject) {
        boolean equalObjects = false;

        if (otherObject instanceof Operator){
            Operator otherOperator = (Operator) otherObject;

            if (this.operatorid == otherOperator.operatorid){
                equalObjects = true;
            }
        }
        return equalObjects;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "operatorid=" + operatorid +
                ", operator='" + operator + '\'' +
//                ", brTypes=" + brTypes +
                '}';
    }
}
