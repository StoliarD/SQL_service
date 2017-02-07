package edu.old.sql_service_jdbc;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

/**
 * Created by Dmitry on 01.02.2017.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SQLServiceInterface {
    @WebMethod
    ArrayList<String> getDocs();

    @WebMethod
    ArrayList<String> getPatients();

    @WebMethod
    boolean registerDoc(String user);

    @WebMethod
    boolean checkDocPass(String user, String password);

    @WebMethod
    boolean checkPatientPass(String user, String password);

    @WebMethod
    void printHello();
}
