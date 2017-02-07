package edu.sql_service_jdbc;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**
 * Created by Dmitry on 05.02.2017.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SQLServiceInterface {
    @WebMethod
    public boolean registerDoc(String user);

    @WebMethod
    public boolean addPatient(String doctor, String patient);

    @WebMethod
    public boolean checkDocPassword(String user, String password);

    @WebMethod
    public boolean checkPatientPassword(String user, String password);

    @WebMethod
    public boolean putRecipe(String doc, String patient, String recipe);

    @WebMethod
    public String[] getPatients(String doc);

    @WebMethod
    public String[] getRecipes(String patient);

    @WebMethod
    public String[] getRecipesDoc(String doc, String patient);

    @WebMethod
    void printHello();
}