package pokerjunkie.intro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import pokerjunkie.TwoPanelBackgroundView;
import user.User;



// TODO - Get the option constraints from some other place.
public class MainMenuView extends TwoPanelBackgroundView {
    
    //
    private final String gameConfigLabelText = "Configure Game";
    private final Color labelFontColor = Color.white;
    private final float gameConfigLabelFontSize = 18.0f;
    //
    private final float optionLabelFontSize = 18.0f;
    //
    private final String numPlayersLabelText = "Players: ";
    private JSpinner numPlayersSpinner;
    private SpinnerNumberModel numPlayersSpinnerModel;
    private final int minPlayers = 2;
    private final int maxPlayers = 4;
    //
    private final String minimumBetLabelText = "Minimum Bet:";
    private JSpinner minimumBetSpinner;
    private SpinnerListModel minimumBetSpinnerModel;
    private final int minimumBetAmounts[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 50, 100, 200, 500, 1000};
    //
    private final Dimension maxSpinnerSize = new Dimension(leftPanelWidth, leftPanelHeight);
    //
    private JButton startGameButton;
    private final String startGameButtonText = "Start Game";

    private JButton tutorialButton;
    private final String tutorialButtonText = "Play Tutorial";
    
    //
    private final Color userDetailsFontColor = Color.white;
    private final float userDetailsLabelFontSize = 18.0f;
    //
    private JLabel userDetailsNameLabel;
    //
    private JLabel userDetailsBalanceLabel;
    
    //
    private final MainMenuController controller;
    
    
    //
    public MainMenuView(MainMenuController controller) {
        super();
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUI();
    }
    
    
    //
    public int getNumPlayers() {
        return numPlayersSpinnerModel.getNumber().intValue();
    }
    
    
    //
    public int getMinimumBet() {
        return (int) minimumBetSpinnerModel.getValue();
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
    private void initGUI() {
        setTitle("$ Main Menu $");
        initGameConfigPanel();
        initUserDetailsPanel();
        registerActionListeners();
    }
    
    
    //
    private void initGameConfigPanel() {
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);
        
        JLabel gameConfigLabel = new JLabel(gameConfigLabelText);
        gameConfigLabel.setForeground(labelFontColor);
        gameConfigLabel.setFont(gameConfigLabel.getFont().deriveFont(gameConfigLabelFontSize));
        gameConfigLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        
        leftPanel.add(gameConfigLabel, BorderLayout.PAGE_START);
        
        JPanel gameOptionsPanel = new JPanel();
        gameOptionsPanel.setOpaque(false);
        gameOptionsPanel.setLayout(new BoxLayout(gameOptionsPanel, BoxLayout.Y_AXIS));
        
        JPanel numPlayersPanel = createGameOptionPanel(numPlayersLabelText);
        numPlayersSpinnerModel = new SpinnerNumberModel(minPlayers, minPlayers, maxPlayers, 1);
        numPlayersSpinner = new JSpinner(numPlayersSpinnerModel);
        numPlayersSpinner.setMaximumSize(maxSpinnerSize);
        ((DefaultEditor) numPlayersSpinner.getEditor()).getTextField().setEditable(false);
        numPlayersPanel.add(numPlayersSpinner);
        gameOptionsPanel.add(numPlayersPanel);
        
        JPanel minimumBetPanel = createGameOptionPanel(minimumBetLabelText);
        List<Integer> minimumBetList = new ArrayList();
        for(int bet : minimumBetAmounts) {
            minimumBetList.add(bet);
        }
        minimumBetSpinnerModel = new SpinnerListModel(minimumBetList);
        minimumBetSpinner = new JSpinner(minimumBetSpinnerModel);
        minimumBetSpinner.setMaximumSize(maxSpinnerSize);
        minimumBetSpinner.setMinimumSize(new Dimension(900, 30));
        ((DefaultEditor) minimumBetSpinner.getEditor()).getTextField().setEditable(false);
        minimumBetPanel.add(minimumBetSpinner);
        gameOptionsPanel.add(minimumBetPanel);
        
        gameOptionsPanel.add(Box.createVerticalGlue());
        leftPanel.add(gameOptionsPanel, BorderLayout.CENTER);
        
        startGameButton = new JButton(startGameButtonText);
        leftPanel.add(startGameButton, BorderLayout.PAGE_END);
    }
    
    
    // Crates a panel with a label that will place an added component to the right of that label.
    private JPanel createGameOptionPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        JLabel label = new JLabel(labelText);
        label.setForeground(labelFontColor);
        label.setFont(label.getFont().deriveFont(optionLabelFontSize));
        label.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(label);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        return panel;
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

        //added the tutty
        tutorialButton = new JButton(tutorialButtonText);
        rightPanel.add(tutorialButton, BorderLayout.PAGE_END);
    }
    
    
    //
    private void registerActionListeners() {
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleStartGame();
            }
        });

        tutorialButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleStartTutorial();
            }
        });
    }
    
    
    //
    public void close() {
        setVisible(false);
        setEnabled(false);
        dispose();
    }
}
