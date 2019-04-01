package hu.fatihyilmaz.tosad.model.targetschema;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Entity
public class TargetDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tdid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "databasetype_dtid")
    private DatabaseType databaseType;

    public String db_user;
    public String db_pass;
    public String db_db;

    public TargetDatabase() {
    }

    public TargetDatabase(String db_user, String db_pass, String db_db) {
        this.db_user = db_user;
        this.db_pass = db_pass;
        this.db_db = db_db;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_pass() {
        return db_pass;
    }

    public void setDb_pass(String db_pass) {
        this.db_pass = db_pass;
    }

    public String getDb_db() {
        return db_db;
    }

    public void setDb_db(String db_db) {
        this.db_db = db_db;
    }

    public int getTdid() {
        return tdid;
    }

    public void setTdid(int tdid) {
        this.tdid = tdid;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

}
