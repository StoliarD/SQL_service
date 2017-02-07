package edu.sql_service_jdbc.server.endpoint;


import edu.sql_service_jdbc.SQLServiceInterface;

import javax.jws.WebMethod;
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
    public boolean addPatient(String doctor, String docPass, String patient) {
        if (!methods.checkDocPassword(doctor,docPass))
            throw new Error();
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
    public boolean putRecipe(String doctor, String docPass, String patient, String recipe) {
        if (!methods.checkDocPassword(doctor,docPass))
            throw new Error();
        return methods.putRecipe(doctor,patient,recipe);
    }

    @Override
    public String[] getPatients(String doctor, String docPass) {
        if (!methods.checkDocPassword(doctor,docPass))
            throw new Error();
        return methods.getPatients(doctor).toArray(new String[0]);
    }

    @Override
    public String[] getRecipes(String patient, String patientPass) {
        if (!methods.checkPatientPassword(patient,patientPass))
            throw new Error();
        return methods.getRecipes(patient).toArray(new String[0]);
    }

    @Override
    public String[] getRecipesDoc(String doctor, String docPass, String patient) {
        if (!methods.checkDocPassword(doctor,docPass))
            throw new Error();
        return methods.getRecipesDoc(doctor, patient).toArray(new String[0]);
    }

    @Override
    public void printHello() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        SQLService sqlService = new SQLService();
        for (String i : sqlService.getPatients("dr1", "12345")) {
            System.out.println(i);
        }
    }

}
