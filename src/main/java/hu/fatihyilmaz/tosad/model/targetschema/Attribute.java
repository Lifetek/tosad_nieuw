package hu.fatihyilmaz.tosad.model.targetschema;

import javax.persistence.*;

@Entity
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int attributeid;

    private String name;
    private String dataType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_tableid")
    private TargetTable targetTable;

    public Attribute() {
    }

    public Attribute(int attributeId, String name, String dataType) {
        this.attributeid = attributeId;
        this.name = name;
        this.dataType = dataType;
    }

    public Attribute(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public int getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(int attributeid) {
        this.attributeid = attributeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public TargetTable getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(TargetTable targetTable) {
        this.targetTable = targetTable;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attributeid=" + attributeid +
                ", name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", targetTable=" + targetTable +
                '}';
    }
}
