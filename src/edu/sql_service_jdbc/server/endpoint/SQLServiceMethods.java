package edu.sql_service_jdbc.server.endpoint;



import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by Dmitry on 01.02.2017.
 */
public class SQLServiceMethods {
    private static final String url = "jdbc:mysql://localhost:3306/hospital?autoReconnect=true&verifyServerCertificate=false&useSSL=true";
    private static final String user = "root";
    private static final String password = "";
    private Connection connection;
    private Statement statement;

    public SQLServiceMethods() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("connected to MySQL DB");
        } catch (SQLException e) {
            System.err.println("!!!!!!!!did not connect to MySQL!!!!!!!!!!!");
            e.printStackTrace();
        }
    }

    public boolean registerDoc(String user, String password) {
        try {
            System.out.println(user);
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT dctr_name FROM doctors WHERE dctr_name = \"" + user + "\");");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet.close();
                System.out.println("already registered");
                return false;
            } else {
                statement.executeUpdate("INSERT INTO doctors (dctr_name, dctr_pass) VALUES (\"" + user +"\",\"" + password +"\");");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean registerPatient(String user, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT ptnt_name FROM patients WHERE ptnt_name = \"" + user + "\");");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet.close();
                System.out.println("already registered");
                return false;
            } else {
                statement.executeUpdate("INSERT INTO patients (ptnt_name, ptnt_pass) VALUES (\"" + user + "\",\"" + password + "\");");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean addPatient(String doctor, String patient) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT ptnt_name FROM doc_to_ptnt " +
                    "WHERE (dctr_name,ptnt_name) = (\"" + doctor +"\", \"" + patient +"\"));\n");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                resultSet.close();
                return false;
            } else {
                statement.executeUpdate("INSERT INTO doc_to_ptnt (dctr_name, ptnt_name) VALUES (\"" + doctor + "\", \"" + patient  +"\");");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean checkDocPassword(String user, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT dctr_name FROM doctors WHERE dctr_name = \""+ user +"\");");
            resultSet.next();
            if (!resultSet.getBoolean(1)) {
                resultSet.close();
                System.out.println("not registered");
                return false;
            }
            resultSet = statement.executeQuery("SELECT dctr_pass FROM doctors WHERE dctr_name = \"" + user +"\";");
            resultSet.next();
            String res = resultSet.getString(1);
            resultSet.close();
            return res.equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean checkPatientPassword(String user, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(SELECT ptnt_name FROM patients WHERE ptnt_name = \""+ user +"\");");
            resultSet.next();
            if (!resultSet.getBoolean(1)) {
                resultSet.close();
                System.out.println("not registered");
                return false;
            }
            resultSet = statement.executeQuery("SELECT ptnt_pass FROM patients WHERE ptnt_name = \"" + user +"\";");
            resultSet.next();
            String res = resultSet.getString(1);
            resultSet.close();
            return res.equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean putRecipe(String doc, String patient, String recipe) {
        try {
            java.sql.Timestamp date = new java.sql.Timestamp((new java.util.Date()).getTime());
            statement.executeUpdate(String.format("INSERT INTO recipes (dctr, ptnt, date, recipe) VALUES (\"%s\", \"%s\", \"%s\", \"%s\");", doc, patient,date,recipe));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public ArrayList<String> getPatients(String doc) {
        try {
            HashSet<String> patientsSet = new HashSet<>();
            ResultSet resultSet = statement.executeQuery("SELECT ptnt_name FROM doc_to_ptnt WHERE dctr_name = \"" + doc +"\";");
            while (resultSet.next()) {
                patientsSet.add(resultSet.getNString(1));
            }
            resultSet.close();
            ArrayList<String> res = new ArrayList<>(patientsSet);
            Collections.sort(res);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public ArrayList<String> getRecipes(String patient) {
        try {
            ArrayList<String> res = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT dctr, date, recipe FROM recipes WHERE ptnt = \""+ patient +"\";");
            while (resultSet.next()) {
                res.add(resultSet.getString(1) + " " + resultSet.getDate(2) + " " + resultSet.getTime(2) + " : " + resultSet.getString(3));
            }
            resultSet.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public ArrayList<String> getRecipesDoc(String doc, String patient) {
        try {
            ArrayList<String> res = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT date, recipe FROM recipes WHERE (dctr,ptnt) = (\"" + doc +"\", \""+ patient + "\");");
            while (resultSet.next()) {
                res.add(resultSet.getDate(1).toString() + " " + resultSet.getTime(1) + " : " + resultSet.getString(2));
            }
            resultSet.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public static void main(String[] args) {
        SQLServiceMethods methods = new SQLServiceMethods();
        System.out.println(methods.getPatients("dr1"));
        Vector v = new Vector(methods.getPatients("dr1"));
        System.out.println(v);
    }

}
