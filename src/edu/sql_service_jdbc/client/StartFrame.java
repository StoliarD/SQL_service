package edu.sql_service_jdbc.client;

import edu.sql_service_jdbc.SQLServiceInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Dmitry on 01.02.2017.
 */
public class StartFrame extends JFrame{
    private JButton admin;
    private JButton doc;
    private JButton patient;
    Constants.Rights userRights;

    public StartFrame() {
        super("doctors patients drugs");
        setSize(400, 200);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-400,
                Toolkit.getDefaultToolkit().getScreenSize().height/2-350);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("who are you?");
        label.setPreferredSize(new Dimension(200,100));
        add(label);
        JPanel panel = new JPanel();
        admin = new JButton("admin");
        doc = new JButton("doc");
        patient = new JButton("patient");
        admin.addActionListener(startFrameListener);
        doc.addActionListener(startFrameListener);
        patient.addActionListener(startFrameListener);
        panel.add(admin);
        panel.add(doc);
        panel.add(patient);
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    ActionListener startFrameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button == admin) {
                userRights = Constants.Rights.ADMIN;
            } else if (button == doc) {
                userRights = Constants.Rights.DOC;
            } else {
                userRights = Constants.Rights.PATIENT;
            }
            new PasswordFrame();
            dispose();
        }
    };

    public static void main(String[] args) {
        StartFrame frame = new StartFrame();
    }



    class PasswordFrame extends JFrame {
        JTextField passwordTextField;
        JTextField userTextField;
        JButton button;
        JLabel errorLabel;

        public PasswordFrame() {
            super("doctors patients drugs");
            setLocationRelativeTo(StartFrame.this);
            setSize(400, 200);
            setLayout(new GridLayout(4,1));

            if (!userRights.equals(Constants.Rights.ADMIN)) {
                JPanel userPanel = new JPanel();
                JLabel userNameLabel = new JLabel("user:    ");
                userPanel.add(userNameLabel);
                userTextField = new JTextField(15);
                userPanel.add(userTextField);
                add(userPanel);
            }
            JPanel passwordPanel = new JPanel();
            JLabel passwordLabel = new JLabel("password: ");
            passwordPanel.add(passwordLabel);
            passwordTextField = new JTextField(15);
            passwordPanel.add(passwordTextField);
            add(passwordPanel);
            button = new JButton("OK");
            button.addActionListener(passwordFrameListener);
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(button);
            add(buttonPanel);

            JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            errorLabel = new JLabel();
            errorPanel.add(errorLabel);
            add(errorPanel);


            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setResizable(true);
            setVisible(true);
        }

        private void incorrect() {
            errorLabel.setText("incorrect");
            revalidate();
        }

        ActionListener passwordFrameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                assert button == PasswordFrame.this.button;

                switch (userRights) {
                    case ADMIN:
                        if (passwordTextField.getText().equals(Constants.adminPass)){
                            new AdminFrame();
                            PasswordFrame.this.dispose();
                        } else
                            PasswordFrame.this.incorrect();
                        break;
                    case DOC:
                        String docName = userTextField.getText();
                        if (ServerConnect.getPort().checkDocPassword(docName,passwordTextField.getText())){
                            new DocFrame(docName);
                            PasswordFrame.this.dispose();
                        } else
                            incorrect();
                        break;
                    case PATIENT:
                        String patientName = userTextField.getText();
                        if (ServerConnect.getPort().checkPatientPassword(patientName,passwordTextField.getText())) {
                            new PatientFrame(patientName);
                            PasswordFrame.this.dispose();
                        } else incorrect();
                        break;
                }
            }
        };
    }
}
