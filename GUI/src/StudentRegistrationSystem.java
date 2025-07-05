import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentRegistrationSystem {
    // Database Configuration
    private static final String DB_URL = "jdbc:ucanaccess://VUE_Exhibition.accdb";
    
    // GUI Components
    private JFrame frame;
    private JTextField txtRegID, txtName, txtFaculty, txtProject, txtContact, txtEmail;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRegistrationSystem().initialize());
    }
    
    public void initialize() {
        createGUI();
    }
    
    private void createGUI() {
        // Main Window Setup
        frame = new JFrame("Victoria University - Exhibition Registration");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Main Panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title Panel
        JLabel titleLabel = new JLabel("STUDENT PROJECT REGISTRATION", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form Panel (GridBagLayout for precise alignment)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form Fields
        String[] labels = {"Registration ID:", "Student Name:", "Faculty:", "Project Title:", "Contact Number:", "Email:"};
        JTextField[] fields = {
            txtRegID = new JTextField(20),
            txtName = new JTextField(20),
            txtFaculty = new JTextField(20),
            txtProject = new JTextField(20),
            txtContact = new JTextField(20),
            txtEmail = new JTextField(20)
        };
        
        // Add Labels and Fields to Form
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
        btnRegister.addActionListener(e -> registerStudent());
        
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearForm());
        
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> editStudent());
        
        // Uniform Button Sizing
        Dimension btnSize = new Dimension(100, 30);
        btnRegister.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnEdit);
        
        // Assemble Main Panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private void registerStudent() {
        if (!validateInputs()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO Participants VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, txtRegID.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtFaculty.getText());
            pstmt.setString(4, txtProject.getText());
            pstmt.setString(5, txtContact.getText());
            pstmt.setString(6, txtEmail.getText());
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Registration Successful!");
            clearForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editStudent() {
        String regID = JOptionPane.showInputDialog(frame, "Enter Registration ID to edit:");
        if (regID == null || regID.trim().isEmpty()) return;
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM Participants WHERE RegID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, regID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                txtRegID.setText(rs.getString("RegID"));
                txtName.setText(rs.getString("StudentName"));
                txtFaculty.setText(rs.getString("Faculty"));
                txtProject.setText(rs.getString("ProjectTitle"));
                txtContact.setText(rs.getString("ContactNumber"));
                txtEmail.setText(rs.getString("Email"));
            } else {
                JOptionPane.showMessageDialog(frame, "No record found with ID: " + regID);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        txtRegID.setText("");
        txtName.setText("");
        txtFaculty.setText("");
        txtProject.setText("");
        txtContact.setText("");
        txtEmail.setText("");
    }
    
    private boolean validateInputs() {
        return !txtRegID.getText().isEmpty() &&
               !txtName.getText().isEmpty() &&
               !txtFaculty.getText().isEmpty() &&
               !txtProject.getText().isEmpty() &&
               !txtContact.getText().isEmpty() &&
               !txtEmail.getText().isEmpty();
    }
}