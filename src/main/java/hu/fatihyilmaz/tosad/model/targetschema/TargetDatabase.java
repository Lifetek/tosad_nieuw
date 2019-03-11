package hu.fatihyilmaz.tosad.model.targetschema;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Entity
public class TargetDatabase {
    //moet dit niet in application.properties?
    //moet nog een oracle driver in maven dependency?? (Oracle JDBC Driver) ojdbc14.jar?

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tdid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "databasetype_dtid")
    private DatabaseType databaseType;

    private static TargetDatabase instance = new TargetDatabase();

    private static final String DB_DRIV = "oracle.jdbc.driver.OracleDriver"; //niet aangepast nog.
    private static final String DB_URL = "jdbc:oracle:thin:@//DESKTOP-IPA9DH5:1521/xe";
    private static final String DB_USER = "system";
    private static final String DB_PASS = "yilmaz";
    private static Connection conn;

    public TargetDatabase() {
    }

    public static TargetDatabase getInstance() {
        return instance;
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

    @Override
    public String toString() {
        return "TargetDatabase{" +
                "tdid=" + tdid +
                ", databaseType=" + databaseType +
                '}';
    }
}
