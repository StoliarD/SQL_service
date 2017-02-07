package edu.sql_service_jdbc.client;


import edu.sql_service_jdbc.SQLServiceInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dmitry on 01.02.2017.
 */
public class AdminFrame extends AbstractUserFrame {
    private JTextField userTextField;
    private JButton registerButton;
    private JButton deleteDocButton;

    public AdminFrame() {
        servicePort.printHello();
        setSize(400,100);
        setTitle("docs patients drugs: admin");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        userTextField = new JTextField(15);
        registerButton = new JButton("reg doc");
        registerButton.addActionListener(adminFrameListener);
//        deleteDocButton = new JButton("fire doc");
//        deleteDocButton.addActionListener(adminFrameListener);
        add(userTextField); add(registerButton); // add(deleteDocButton);
    }

    void addDoc(String name, String password) {

    }

    void deleteDoc(String name) {

    }

    ActionListener adminFrameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button == registerButton) {
                System.out.println(userTextField.getText());
                if (userTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(AdminFrame.this, "input name");
                } else if (servicePort.registerDoc(userTextField.getText())) {
                    userTextField.setText("");
                    JOptionPane.showMessageDialog(AdminFrame.this, "success!!!");
                } else {
                    JOptionPane.showMessageDialog(AdminFrame.this,"already exists");
                }
            }
        }
    };

}
