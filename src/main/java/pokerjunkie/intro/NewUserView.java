package pokerjunkie.intro;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import user.NewUser;


public class NewUserView extends JFrame {
    
    //
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    //
    private JButton saveButton;
    private JButton cancelButton;
    
    //
    private final int fieldColumns = 20;
    
    private LoginController controller;
    
    
    //
    public NewUserView(LoginController controller) {
        this.controller = controller;
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        initGUI();
    }
    
    
    //
    public void initGUI() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        container.add(initFieldPanel());
        
        container.add(initButtonPanel());
        
        registerActionListeners();
        
        pack();
        
        setResizable(false);
    }
    
    
    //
    public JPanel initFieldPanel() {
        JPanel fieldPanel = new JPanel();
        
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(LEFT_ALIGNMENT);
        fieldPanel.add(usernameLabel);
        
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usernameField = new JTextField(fieldColumns);
        usernamePanel.add(usernameField);
        fieldPanel.add(usernamePanel);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
        fieldPanel.add(passwordLabel);
        
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        passwordField = new JPasswordField(fieldColumns);
        passwordPanel.add(passwordField);
        fieldPanel.add(passwordPanel);
        
        JLabel repeatPasswordLabel = new JLabel("Repeat Password:");
        repeatPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
        fieldPanel.add(repeatPasswordLabel);
        JPanel repeatPasswordPanel = new JPanel();
        repeatPasswordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        repeatPasswordField = new JPasswordField(fieldColumns);
        repeatPasswordPanel.add(repeatPasswordField);
        fieldPanel.add(repeatPasswordPanel);
        
        return fieldPanel;
    }
    
    
    //
    public JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        cancelButton = new JButton("CANCEL");
        buttonPanel.add(cancelButton);
        
        saveButton = new JButton("SAVE");
        buttonPanel.add(saveButton);
        
        return buttonPanel;
    }
    
    
    //
    public void registerActionListeners() {
        //
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSavePressed();
            }
        });
        
        //
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancelPressed();
            }
        });
    }
    
    
    //
    private void onSavePressed() {
        String username = usernameField.getText();
        String password = String.copyValueOf(passwordField.getPassword());
        String repeatPassword = (String.copyValueOf(repeatPasswordField.getPassword()));
        
        NewUser newUser = new NewUser(username, password, repeatPassword);
        
        controller.handleRegisterNewUser(newUser);
    }
    
    
    //
    private void onCancelPressed() {
        controller.handleCloseNewUserView();
    }
    
    
    //
    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    
    //
    public void close() {
        setVisible(false);
        setEnabled(false);
        dispose();
    }
}
