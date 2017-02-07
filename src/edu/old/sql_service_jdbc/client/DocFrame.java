package edu.old.sql_service_jdbc.client;

import edu.old.sql_service_jdbc.SQLServiceInterface;
import edu.sql_service_jdbc.AbstractUserFrame;

/**
 * Created by Dmitry on 02.02.2017.
 */
public class DocFrame extends AbstractUserFrame {
    public DocFrame(SQLServiceInterface sqlService, String userName) {
        super(sqlService);
        setTitle("docs patients drugs: dr " + userName);
    }
}
