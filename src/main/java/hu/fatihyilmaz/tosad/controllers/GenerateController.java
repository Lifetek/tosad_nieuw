package hu.fatihyilmaz.tosad.controllers;

public class GenerateController {
    //    (nieuwe postgres db aanmaken(fabriek)
    //de trigger die in template brcode (met placeholders) in targetDB zetten door eerst de placeholders te vervangen
    //in dit java bestand de placeholder vullen (zoals table, value(s), attributen, operator) door
    //      de brid te pakken


    //voorbeeld voor template (uit tool database):

    //          create or replace trigger [triggernaam]
    //          before ...
    //          on [tabelnaam]
    //          for each row
    //          begin
    //(ARNG)    ... [attribuut1] [operator] [value1] AND [value2]

    //(ACMP)    ... [attribuut1] [operator] [value1]
    //          END

    //in template bij "brcode" is de voorbeeld van hierboven te zien, maar dan ipv van [objecten] zet je vraagtekens

    //in deze java bestand vul je dus de placeholders zoals [attribuut1] en [operator] aan de hand van brid
    //en deze zet je dus in de target database (fabriek)




}
