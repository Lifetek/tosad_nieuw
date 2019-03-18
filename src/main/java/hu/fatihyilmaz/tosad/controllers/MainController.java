package hu.fatihyilmaz.tosad.controllers;

import hu.fatihyilmaz.tosad.model.generator.Template;
import hu.fatihyilmaz.tosad.model.rule.BRType;
import hu.fatihyilmaz.tosad.model.rule.BusinessRule;
import hu.fatihyilmaz.tosad.model.rule.Operator;
import hu.fatihyilmaz.tosad.model.rule.Value;
import hu.fatihyilmaz.tosad.model.targetschema.Attribute;
import hu.fatihyilmaz.tosad.model.targetschema.DatabaseType;
import hu.fatihyilmaz.tosad.model.targetschema.TargetDatabase;
import hu.fatihyilmaz.tosad.model.targetschema.TargetTable;
import hu.fatihyilmaz.tosad.persistence.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


@RestController
public class MainController {

    @Autowired
    private BusinessRuleDAO businessRuleDAO;

    @Autowired
    private TargetTableDAO targetTableDAO;

    @Autowired
    private TemplateDAO templateDAO;

    @Autowired
    private BRTypeDAO brTypeDAO;

    @Autowired
    private DatabaseTypeDAO databaseTypeDAO;

    //stap 1: input BusinessRule ID
    @RequestMapping("/{id}")
    public String infoBR(@PathVariable("id") String id) throws IOException {
        int brid = Integer.parseInt(id);



    //stap 2: Lees BusinessRule met geralateerde objecten uit tooldb
        //BusinessRule Object:
        BusinessRule foundBR = businessRuleDAO.findById(brid).get();
        String brName = foundBR.getName();

        //BRType object:
        BRType brType = foundBR.getBrType();
        String brtCode = brType.getCode();

        //Table object
        TargetTable table = foundBR.getTable();
        String tableName = table.getName();

        //2 Attribute objects
        Attribute attribute1 = foundBR.getAttributeId1();
        String attribute1Name = attribute1.getName();

//        Attribute attribute2 = foundBR.getAttributeId2();
//        String attribute2Name = attribute2.getName();

        //Operator object
        Operator operator = foundBR.getOperator();
        String operatorName = operator.getOperator();

        //Value object
        List<Value> values = foundBR.getValues();
        int value1 = values.get(0).getValue();
        int value2 = -1;
        if (values.size() > 1) {
            value2 = values.get(1).getValue();
        }

    //stap3: Welke DBType?
        DatabaseType dbType = foundBR.getTable().getTargetDatabase().getDatabaseType();
        String dbTypeName = dbType.getName();



//        String templateARNG = "DELIMITER $$ " +
//                "CREATE DEFINER=`root`@`localhost` TRIGGER %s " +
//                "BEFORE INSERT ON %s " +
//                "FOR EACH ROW " +
//                "BEGIN " +
//                "IF NEW.%s %s %s AND %s " +
//                "THEN signal sqlstate '20000' set message_text = 'Insert is denied, not between the requested values';  " +
//                "END IF; " +
//                "END$$ DELIMITER ; ";

        String templateARNG =
                "CREATE DEFINER=`root`@`localhost` TRIGGER %s " +
                "BEFORE INSERT ON %s " +
                "FOR EACH ROW " +
                "BEGIN " +
                "IF NEW.%s %s %s AND %s " +
                "THEN signal sqlstate '20000' set message_text = 'Insert is denied, not between the requested values';  " +
                "END IF; " +
                "END; ";

        if(foundBR.getBrid() == 1) {
            saveTemplateBrCode(templateARNG, foundBR.getBrid());
        }

        for (Integer templateId : getTemplateIdsByBrId(foundBR.getBrid())) {
            this.generateTemplateARNG(templateId, brid);
            writeTriggerCode(templateDAO.findById(templateId).get().getBrCode());
        }


        TargetDatabase targetDatabase = new TargetDatabase("root", "yilmaz52", "classicmodels");
        writeTriggerToTargetDb("trigger.sql", targetDatabase);


        return "The template(s) with id "+getTemplateIdsByBrId(foundBR.getBrid())+" are linked to the Business Rule "+foundBR.getBrid()+". The template code has been saved.";
    }


    private List<Integer> getTemplateIdsByBrId(int brid) {
        List<Integer> foundBRTypeTemplatesIDs = new ArrayList<>();

        for (Template template : businessRuleDAO.findById(brid).get().getBrType().getTemplates()){
            if (template.getDatabaseType().getDtid() ==  businessRuleDAO.findById(brid).get().getTable().getTargetDatabase().getDatabaseType().getDtid()) {
                int template1 = template.getTemplateid();
                foundBRTypeTemplatesIDs.add(template1);
            }
        }
        return foundBRTypeTemplatesIDs;
    }


    private void saveTemplateBrCode(String brCode, int brId) {
        for (Integer templateId : getTemplateIdsByBrId(brId)) {
            Template template = templateDAO.findById(templateId).get();
            template.setBrCode(brCode);
            templateDAO.save(template);
        }
    }

    private void generateTemplateARNG(int templateId, int brId){
        BusinessRule businessRule = businessRuleDAO.findById(brId).get();
        Template template = templateDAO.findById(templateId).get();

        String brCodeTemplate = template.getBrCode();
        String brCode = String.format(brCodeTemplate,
                businessRule.getName(),
                businessRule.getTable().getName(),
                businessRule.getAttributeId1().getName(),
                businessRule.getOperator().getOperator(),
                businessRule.getValues().get(0).getValue(),
                businessRule.getValues().get(1).getValue());
        template.setBrCode(brCode);
        templateDAO.save(template);
    }


    private void writeTriggerCode(String triggerCode) throws IOException {
        FileWriter fileWriter = new FileWriter("trigger.sql");
        fileWriter.write(triggerCode);
        fileWriter.close();
    }

//"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pyilmaz52 classicmodels < mysqlcode.sql
    private void writeTriggerToTargetDb(String filename, TargetDatabase targetDatabase) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+targetDatabase.db_db, targetDatabase.db_user, targetDatabase.db_pass);
            Statement stmt = con.createStatement();
            String content = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8");
            stmt.executeUpdate(content);
        } catch (Exception e) {
            System.out.println(e);
        }

//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"+targetDatabase.db_db, targetDatabase.db_user, targetDatabase.db_pass);




            //
//            Runtime.getRuntime().exec("\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -u " + targetDatabase.db_user +
//                    " -p"+targetDatabase.db_pass+
//                    " " + targetDatabase.db_db + " < "
//                    + filename);
//            System.exit(0);
//        }
//        catch (IOException e) {
//            System.out.println("exception happened - here's what I know: ");
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        try {
//            Runtime.getRuntime().exec("DEL " + filename);
//            System.exit(0);
//        }
//        catch (IOException e) {
//            System.out.println("exception happened - here's what I know: ");
//            e.printStackTrace();
//            System.exit(-1);
//        }





            //            Runtime.getRuntime().exec("cmd.exe /c \"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -u " + targetDatabase.db_user +
//                    " -p"+targetDatabase.db_pass+
//                    " " + targetDatabase.db_db + " < "
//                    + filename);
//            System.exit(0);
//        }
//        catch (IOException e) {
//            System.out.println("exception happened - here's what I know: ");
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        try {
//            Runtime.getRuntime().exec("cmd.exe /c DEL " + filename);
//            System.exit(0);
//        }
//        catch (IOException e) {
//            System.out.println("exception happened - here's what I know: ");
//            e.printStackTrace();
//            System.exit(-1);
//        }





//            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -u " + targetDatabase.db_user +
//                    " -p"+targetDatabase.db_pass+
//                    " " + targetDatabase.db_db + " < "
//                    + filename);
//
//            builder.redirectErrorStream(true);
//            Process p = builder.start();
//            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            while (true) {
//                line = r.readLine();
//                if (line == null) {
//                    break;
//                }
//                System.out.println(line);
//            }
//
//            //delete file gelijk erna.
//            ProcessBuilder builder1 = new ProcessBuilder("cmd.exe", "/c", "DEL " + filename);
//            builder1.redirectErrorStream(true);
//            Process p1 = builder1.start();
//            BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
//            String line1;
//            while (true) {
//                line1 = r1.readLine();
//                if (line1 == null) {
//                    break;
//                }
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}



/*


 EXAMPLE OUTPUT:

 return id + " = BRname: " + brName +
                " , BRTypeCode: " + brtCode +
                " , TableName: " + tableName +
                " , Attribute1Name: " + attribute1Name +
                " , Operator: " + operatorName +
                " , Value(s): " + value1 + " & " + value2 +
                " ___________________________________________" +
                " DatabaseType: " + dbTypeName +
                " , Template BRType BrCode: " + brTypeTempIds +
                " , Template DatabaseType BrCode: " + brDTTempIds +
                " *****_____________________________________________*****" +
                " BRTpes voor deze ID: " + foundBRTypeTemplatesIDs +
                " DatabaseTypes voor deze ID: " + foundBRTypeTemplatesIDs +
                " ___________ " + myTemplate;*/

