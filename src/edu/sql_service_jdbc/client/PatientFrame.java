package edu.sql_service_jdbc.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Dmitry on 07.02.2017.
 */
public class PatientFrame extends AbstractUserFrame {
    String patientName;
    JList<String> recipesList;
    public PatientFrame(String patientName) {
        setSize(600,400);
        this.patientName = patientName;
        setTitle("docs patients drugs: patient " + patientName);
        recipesList = new JList<>(new Vector<>(Arrays.asList(servicePort.getRecipes(patientName))));
        recipesList.addMouseListener(listListener);
        JScrollPane scrollPane = new JScrollPane(recipesList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    MouseListener listListener = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            JOptionPane.showMessageDialog(PatientFrame.this, recipesList.getSelectedValue());
        }
    };


}
