
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ExhibitionRegistration extends JFrame {
    private JTextField txtRegID, txtName, txtFaculty, txtProject, txtContact, txtEmail;
    
    public ExhibitionRegistration() {
        setTitle("Victoria University - Exhibition Registration");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        
        // Main panel 
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title Panel 
        JLabel titleLabel = new JLabel("STUDENT PROJECT REGISTRATION", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form Panel (Center) 
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST;
        
        // Labels and Text Fields
        String[] labels = {"Registration ID:", "Student Name:", "Faculty:", "Project Title:", "Contact Number:", "Email:"};
        JTextField[] fields = {
            txtRegID = new JTextField(20),
            txtName = new JTextField(20),
            txtFaculty = new JTextField(20),
            txtProject = new JTextField(20),
            txtContact = new JTextField(20),
            txtEmail = new JTextField(20)
        };
        
        //  labels and fields 
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            formPanel.add(new JLabel(labels[i]), gbc);
            
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegister = new JButton("Register");
        JButton btnClear = new JButton("Clear");
        JButton btnEdit = new JButton("Edit");
        
        // Style buttons uniformly
        Dimension btnSize = new Dimension(100, 30);
        btnRegister.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnEdit);
        
        // Add panels to main frame
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExhibitionRegistration());
    }
}
