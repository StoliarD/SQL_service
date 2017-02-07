package edu.sql_service_jdbc.client;

import edu.sql_service_jdbc.SQLServiceInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dmitry on 01.02.2017.
 */
abstract class AbstractUserFrame extends JFrame{
    SQLServiceInterface servicePort;
    public AbstractUserFrame() {
        super("docs patients drugs: abstract");
        servicePort = ServerConnect.getPort();
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-400,
                Toolkit.getDefaultToolkit().getScreenSize().height/2-350);
//        setLayout(new BorderLayout());
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
