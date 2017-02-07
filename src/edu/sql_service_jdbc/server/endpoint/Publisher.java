package edu.sql_service_jdbc.server.endpoint;

import javax.xml.ws.Endpoint;

/**
 * Created by Dmitry on 01.02.2017.
 */
public class Publisher {
    public static void main(String... args) {
        Endpoint.publish("http://localhost:9000/wss/sql_service", new SQLService());
    }
}
