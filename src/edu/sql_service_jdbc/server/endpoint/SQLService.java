package edu.sql_service_jdbc.server.endpoint;


import edu.sql_service_jdbc.SQLServiceInterface;

import javax.jws.WebService;

/**
 * Created by Dmitry on 01.02.2017.
 */

@WebService(endpointInterface = "edu.sql_service_jdbc.SQLServiceInterface")
public class SQLService implements SQLServiceInterface {
    SQLServiceMethods methods;

    public SQLService() {
        super();
        methods = new SQLServiceMethods();
    }

    @Override
    public boolean registerDoc(String user) {
        return methods.registerDoc(user, "12345");//(new BigInteger(130,new SecureRandom())).toString(5));
    }


    public boolean registerPatient(String user) {
        return methods.registerPatient(user,"p123");
    }

    @Override
    public boolean addPatient(String doctor, String patient) {
        methods.registerPatient(patient,"p123");
        return methods.addPatient(doctor,patient);
    }

    @Override
    public boolean checkDocPassword(String user, String password) {
        return methods.checkDocPassword(user, password);
    }

    @Override
    public boolean checkPatientPassword(String user, String password) {
        return methods.checkPatientPassword(user, password);
    }

    @Override
    public boolean putRecipe(String doc, String patient, String recipe) {
        return methods.putRecipe(doc,patient,recipe);
    }

    @Override
    public String[] getPatients(String doc) {
        return methods.getPatients(doc).toArray(new String[0]);
    }

    @Override
    public String[] getRecipes(String patient) {
        return methods.getRecipes(patient).toArray(new String[0]);
    }

    @Override
    public String[] getRecipesDoc(String doc, String patient) {
        return methods.getRecipesDoc(doc, patient).toArray(new String[0]);
    }

    @Override
    public void printHello() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        SQLService sqlService = new SQLService();
        for (String i : sqlService.getPatients("dr1")) {
            System.out.println(i);
        }
    }

}
