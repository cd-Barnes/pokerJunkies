package pokerjunkie.intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pokerjunkie.TwoPanelBackgroundView;
import user.User;


//
public class LoginView extends TwoPanelBackgroundView {
    
    //
    private JList userSelectionList;
    private DefaultListModel userSelectionListModel;
    private final String userSelectionLabelText = "USERS";
    private final float userSelectionLabelFontSize = 18.0f;
    private final Color userSelectionLabelFontColor = Color.white;
    //
    private JPasswordField passwordField;
    private JButton selectUserButton;
    private final String selectUserButtonText = "LOG IN";
    //
    private JButton newUserButton;
    private final String newUserButtonText = "NEW USER";
    
    //
    private final Color userDetailsFontColor = Color.white;
    private final float userDetailsLabelFontSize = 18.0f;
    //
    private JLabel userDetailsNameLabel;
    //
    private JLabel userDetailsBalanceLabel;
    
    //
    private final LoginController controller;
    
    
    //
    public LoginView(LoginController controller) {
        super();
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }
    
    
    //
    private void initGUI() {
        setTitle("$ LOG IN $");
        initUserSelectionPanel();
        initUserDetailsPanel();
        registerActionListeners();
    }
    
    
    //
    private  void initUserSelectionPanel() {
        
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);
        
        JLabel userSelectionLabel = new JLabel(userSelectionLabelText);
        userSelectionLabel.setForeground(userSelectionLabelFontColor);
        userSelectionLabel.setFont(userSelectionLabel.getFont().deriveFont(userSelectionLabelFontSize));
        userSelectionLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        leftPanel.add(userSelectionLabel, BorderLayout.PAGE_START);
        
        userSelectionListModel = new DefaultListModel();
        userSelectionList = new JList(userSelectionListModel);
        userSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userSelectionList.setLayoutOrientation(JList.VERTICAL);
        
        leftPanel.add(userSelectionList, BorderLayout.CENTER);
        
        passwordField = new JPasswordField();
        passwordField.setEchoChar('$');
        
        selectUserButton = new JButton(selectUserButtonText);
        selectUserButton.setEnabled(false);
        
        newUserButton = new JButton(newUserButtonText);
        JPanel additionalButtonsPanel = new JPanel();
        additionalButtonsPanel.setOpaque(false);
        additionalButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        additionalButtonsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        additionalButtonsPanel.add(newUserButton);
        
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(selectUserButton, BorderLayout.LINE_END);
        passwordPanel.add(additionalButtonsPanel, BorderLayout.PAGE_END);
        passwordPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        leftPanel.add(passwordPanel, BorderLayout.PAGE_END);
    }
    
    
    //
    private void initUserDetailsPanel() {
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);
        
        userDetailsNameLabel = new JLabel();
        userDetailsNameLabel.setForeground(userDetailsFontColor);
        userDetailsNameLabel.setFont(userDetailsNameLabel.getFont().deriveFont(userDetailsLabelFontSize));
        userDetailsNameLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        userDetailsNameLabel.setVisible(false);
        rightPanel.add(userDetailsNameLabel, BorderLayout.PAGE_START);
        
        userDetailsBalanceLabel = new JLabel();
        userDetailsBalanceLabel.setForeground(userDetailsFontColor);
        userDetailsBalanceLabel.setFont(userDetailsBalanceLabel.getFont().deriveFont(userDetailsLabelFontSize));
        userDetailsBalanceLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        userDetailsBalanceLabel.setVisible(false);
        
        rightPanel.add(userDetailsBalanceLabel, BorderLayout.CENTER);
    }
    
    
    //
    private void registerActionListeners() {
        
        //
        userSelectionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(!e.getValueIsAdjusting()) {
                    
                    updateSelectUserButton();
                    
                    int selectedIndex = userSelectionList.getSelectedIndex();
                    
                    if(selectedIndex == -1) {
                        hideUserDetails();
                    } else {
                        String selectedName = (String) userSelectionListModel.elementAt(selectedIndex);
                        controller.handleUserSelected(selectedName);
                    }
                }
            }
        });
        
         //
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSelectUserButton();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSelectUserButton();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        
        //
        selectUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = (String) userSelectionList.getSelectedValue();
                String pass = String.copyValueOf(passwordField.getPassword());
                controller.handleTryLogin(user, pass);
            }
        });
        
        //
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleStartNewUserView();
            }
        });
    }
    
    
    // Enables selectUserButton only if certain conditions are met.
    private void updateSelectUserButton() {
        if(userSelectionList.getSelectedIndex() == -1 || passwordField.getPassword().length == 0) {
            selectUserButton.setEnabled(false);
        } else {
            selectUserButton.setEnabled(true);
        }
    }
    
    
    //
    public void setUserDetails(User user) {
        userDetailsNameLabel.setText(user.getName());
        userDetailsNameLabel.setVisible(true);
        String balanceText = "Balance: $" + user.getBalance();
        userDetailsBalanceLabel.setText(balanceText);
        userDetailsBalanceLabel.setVisible(true);
    }
    
    
    //
    public void hideUserDetails() {
        userDetailsNameLabel.setVisible(false);
        userDetailsBalanceLabel.setVisible(false);
    }
    
    
    //
    public void populateUserList(List<String> usernames) {
        userSelectionListModel.removeAllElements();
        
        for(String username : usernames) {
            userSelectionListModel.addElement(username);
        }
    }
    
    
    //
    public void setPlayerSelectionToLast() {
        userSelectionList.setSelectedIndex(userSelectionListModel.getSize() - 1);
    }
    
    
    //
    public void setNewUserButtonEnabled(boolean enable) {
        newUserButton.setEnabled(enable);
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
