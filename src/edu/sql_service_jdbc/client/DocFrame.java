package edu.sql_service_jdbc.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Dmitry on 06.02.2017.
 */
public class DocFrame extends AbstractUserFrame{
    String docName;
    String docPass;
    String patientName;
    JList patientsList;
    DefaultListModel<String> recipesDFM;
    DefaultListModel<String> patientsDFM;
    JList recipesList;
    JButton addPatientButton;
    JButton addRecipeButton;

    public DocFrame(String docName, String docPass) {
        servicePort.printHello();
        this.docName = docName;
        this.docPass = docPass;
        patientName = "";
        setLayout(new BorderLayout());
        setSize(800,800);
        setTitle("docs patients drugs: dr." + docName);
        add(Box.createHorizontalStrut(10),BorderLayout.WEST);
        initTopButtonMenu();
        prepareLists();
        revalidate();
    }

    void initTopButtonMenu() {
        addRecipeButton = new JButton("add recipe");
        addRecipeButton.setPreferredSize(new Dimension(150,25));
        addRecipeButton.addActionListener(docFrameListener);
        addPatientButton = new JButton("add patient");
        addPatientButton.setPreferredSize(new Dimension(150,25));
        addPatientButton.addActionListener(docFrameListener);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(addRecipeButton); panel.add(addPatientButton);
        add(panel,BorderLayout.NORTH);
    }

    void prepareLists() {
        JPanel panel = new JPanel(new BorderLayout());
        patientsDFM = new DefaultListModel<>();
        for (String s : servicePort.getPatients(docName, docPass)) {
            patientsDFM.addElement(s);
        }
        patientsList = new JList<>(patientsDFM);
        JScrollPane patientsScrollPane = new JScrollPane(patientsList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        recipesDFM = new DefaultListModel<>();
        String[] ss = {"",};
        prepareRecipes(ss);
        recipesList = new JList<>(recipesDFM);
        JScrollPane recipesScrollPane = new JScrollPane(recipesList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        patientsScrollPane.setPreferredSize(new Dimension(200,700));

        patientsList.addMouseListener(listListener);
        recipesList.addMouseListener(listListener);

        panel.add(patientsScrollPane,BorderLayout.WEST); panel.add(recipesScrollPane,BorderLayout.CENTER);
        add(panel,BorderLayout.CENTER);
    }

    void prepareRecipes(String[] recipes) {
        recipesDFM.removeAllElements();
        for (String s : recipes)
            recipesDFM.addElement(s);
        revalidate();
    }


    ActionListener docFrameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                if (source == addPatientButton) {
                    recipesList.removeAll();
                    String patient = JOptionPane.showInputDialog("input patient name");
                    if (patient!=null && !patient.trim().equals("")) {
                        if (!servicePort.addPatient(docName,docPass,patient))
                            JOptionPane.showMessageDialog(DocFrame.this, "already in your list");
                        else
                            patientsDFM.addElement(patient);
                    } else JOptionPane.showMessageDialog(DocFrame.this, "try again with input");
                    revalidate();
                } else if (source == addRecipeButton){
                    if (patientName == null) {
                        JOptionPane.showMessageDialog(DocFrame.this, "choose patient");
                    } else {
                        String recipe = JOptionPane.showInputDialog("input recipe");
                        if (recipe != null) {
                            servicePort.putRecipe(docName, docPass, patientName, recipe);
                            recipesDFM.addElement("NOW:   " + recipe);
                        }
                    }
                }
            } else if (source instanceof JList) {
                System.out.println("!!!!!!!WARNING!!!!!!!!");
            }
        }
    };

    MouseListener listListener = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            Object source = e.getSource();
            if (source == patientsList) {
                String value = (String) patientsList.getSelectedValue();
                System.out.println(value);
                if (patientName.equals(value)) {
                    JOptionPane.showMessageDialog(DocFrame.this, "there may be a phone number");
                } else {
                    patientName = value;
                    prepareRecipes(servicePort.getRecipesDoc(docName,docPass,patientName));
                }
            } else if (source == recipesList) {
                JOptionPane.showMessageDialog(DocFrame.this, recipesList.getSelectedValue());
            }
        }
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            if (e.getClickCount()>=2) {
//                if (e.getSource() == patientsList) {
//                    JOptionPane.showMessageDialog(DocFrame.this, "there may be a phone number");
//                }
//            }
//        }
    };



}
