package edu.old.sql_service_jdbc.client;

import edu.old.sql_service_jdbc.SQLServiceInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dmitry on 03.02.2017.
 */
public class ServerConnect {
    private static URL url;
    private static QName qName;
    private static Service service;
    private static SQLServiceInterface servicePort;

    private ServerConnect() {}

    public static SQLServiceInterface getServicePort() {
        if (servicePort != null) {
            return servicePort;
        } else {
            try {
                url = new URL("http://localhost:1986/wss/sql_service?wsdl");
            } catch (MalformedURLException e) {
                System.err.println(e);
                throw new Error();
            }
            qName = new QName("http://endpoint.server.sql_service_jdbc.edu/", "SQLServiceService");
            service = Service.create(url,qName);
            servicePort = service.getPort(SQLServiceInterface.class);
            return servicePort;
        }
    }
}
