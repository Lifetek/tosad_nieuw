package hu.fatihyilmaz.tosad.controllers;

import hu.fatihyilmaz.tosad.model.generator.Template;
import hu.fatihyilmaz.tosad.model.rule.BRType;
import hu.fatihyilmaz.tosad.model.rule.BusinessRule;
import hu.fatihyilmaz.tosad.model.rule.Operator;
import hu.fatihyilmaz.tosad.model.rule.Value;
import hu.fatihyilmaz.tosad.model.targetschema.Attribute;
import hu.fatihyilmaz.tosad.model.targetschema.DatabaseType;
import hu.fatihyilmaz.tosad.model.targetschema.TargetTable;
import hu.fatihyilmaz.tosad.persistence.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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



//    @RequestMapping("/{id}")
//    public int index(@PathVariable("id") String id) {
//        int brid = Integer.parseInt(id);
//
////
////        BusinessRule foundBR = businessRuleDAO.findById(brid).get();
////
////        String dbType = foundBR.getTable().getTargetDatabase().getDatabaseType().getName();
////
////        List<Template> brTemplates = foundBR.getTable().getTargetDatabase().getDatabaseType().getTemplates();
////
////        for(Template template : brTemplates) {
////            template.setBrCode("codecode");
////        }
//
//
//        //define
//        TargetTable targetTable = new TargetTable("my tb3");
//        TargetTable savedTargetTable = targetTableDAO.save(targetTable);
//
//        BusinessRule businessRule = new BusinessRule();
//        businessRule.setTable(savedTargetTable);
//        businessRule.setDescription("My descccc3");
//        businessRule.setName("Oh my god!!!");
//
//        BusinessRule savedBusinessRule = businessRuleDAO.save(businessRule);
//
//
//        //   savedBusinessRule.getTable().getTargetDatabase().getDatabaseType().getTemplates()
//
////
////        savedBusinessRule.getTable().getTargetDatabase().getDatabaseType().getName();
////
////
////        businessRuleDAO.findById(1).get().
//
//        return brid;
//    }


    //stap 1: input BusinessRule ID
    @RequestMapping("/{id}")
    public String infoBR(@PathVariable("id") String id) {
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

    //stap4: Lees Template
//        List<Template> templates = foundBR.getBrType().getTemplates();
//        String template1 = templates.get(0).getBrCode();




        //alles
        List<String> brTypeTempIds = new ArrayList<>();
        List<String> brDTTempIds = new ArrayList<>();
        for(BusinessRule businessRule : businessRuleDAO.findAll()) {
            for (Template template : businessRule.getBrType().getTemplates()) {
                brTypeTempIds.add(template.getBrCode());
            }
        }

        for(BusinessRule businessRule : businessRuleDAO.findAll()) {
            for (Template template : businessRule.getTable().getTargetDatabase().getDatabaseType().getTemplates()) {
                brDTTempIds.add(template.getBrCode());
            }
        }

        //voor specifieke ID
        List<String> foundBRTypeTemplatesID = new ArrayList<>();
        for (Template template : foundBR.getBrType().getTemplates()){
            String template1 = template.getBrCode();
            foundBRTypeTemplatesID.add(template1);
        }

        List<String> foundDBTypeTemplatesID = new ArrayList<>();
        for (Template template : foundBR.getTable().getTargetDatabase().getDatabaseType().getTemplates()){
            String template1 = template.getBrCode();
            foundDBTypeTemplatesID.add(template1);
        }

//                .get(0).getBrCode();

//        List<Template> templates = foundBR.getBrType().getTemplates();

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
                " BRTpes voor deze ID: " + foundBRTypeTemplatesID +
                " DatabaseTypes voor deze ID: " + foundDBTypeTemplatesID;
    }
}