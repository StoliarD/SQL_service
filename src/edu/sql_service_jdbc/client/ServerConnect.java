package edu.sql_service_jdbc.client;

import edu.sql_service_jdbc.SQLServiceInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dmitry on 05.02.2017.
 */
public class ServerConnect {
    private static URL url;
    private static QName qName;
    private static Service service;
    private static SQLServiceInterface servicePort;
    private static void connect() {
        try {
            url = new URL("http://localhost:9000/wss/sql_service?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        qName = new QName("http://endpoint.server.sql_service_jdbc.edu/", "SQLServiceService");
        service = Service.create(url, qName);
        servicePort = service.getPort(SQLServiceInterface.class);
    }

    public static SQLServiceInterface getPort() {
        if (servicePort == null)
            connect();
        return servicePort;
    }
}
