package pokerjunkie.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import pokerjunkie.BackgroundPanel;


public class GameView extends JFrame {
    
    //
    private final String backgroundImagePath = "res/table.png";
    private final int backgroundWidth = 1188;
    private final int backgroundHeight = 738;
    private BackgroundPanel backgroundPanel;
    
    //
    private ImageIcon[] cardImage;
    private final String cardImagePath = "res/cards/%s_%s.png";
    private ImageIcon cardSpotImage;
    private final String cardSpotImagePath = "res/card_spot.png";
    private ImageIcon deckImage;
    private final String deckImagePath = "res/decks/Deck_Red.png";
    private ImageIcon deckStackImage;
    private final String deckStackImagePath = "res/decks/Stack_Red.png";
    private ImageIcon dealerImage;
    private final String dealerImagePath = "res/chips/dealer_chip.png";
    private ImageIcon bigBlindImage;
    private final String bigBlindImagePath = "res/chips/big_blind_chip.png";
    private ImageIcon littleBlindImage;
    private final String littleBlindImagePath = "res/chips/little_blind_chip.png";
    private ImageIcon chipSpotImage;
    private final String chipSpotImagePath = "res/chip_spot.png";
    
    //
    private JLabel[] tableCard;
    private JLabel tableDeck;
    
    //
    private JLabel userNameLabel;
    private JLabel userChip;
    private JLabel[] userCard;
    private JLabel userBalance;
    //
    private JButton betButton;
    private final String betButtonImagePath = "res/bet_button.png";
    private JSpinner betSpinner;
    private SpinnerNumberModel betSpinnerModel;
    private final float betSpinnerFontSize = 24.0f;
    //
    private JButton foldButton;
    private final String foldButtonImagePath = "res/fold_button.png";
    //
    private JButton dealButton;
    private final String dealButtonImagePath = "res/deal_button.png";
    //
    private JLabel potBalanceLabel;
    
    // TODO - Make button graphics
    
    //
    private JPanel[] playerHandPanel;
    private JLabel[] playerNameLabel;
    private JLabel[] playerChip;
    private JLabel[][] playerCard;
    private JLabel[] playerBalance;
    
    //
    private final Color nameLabelFontColor = new Color(255, 255, 255, 179);
    private final Color activeTurnFontColor = new Color(255, 37, 37, 255);
    private final float nameLabelFontSize = 32.0f;
    private final float balanceLabelFontSize = 24.0f;
    
    //
    private final GameController controller;
    
    
    //
    public GameView(GameController controller) {
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            loadAssets();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        
        initGUI();
        registerActionListeners();
    }
    
    
    // Sets the player name and truncates it to 8 chars so that it doesn't mess up the layout.
    public void setPlayerName(int playerIndex, String name) {
        if(name.length() > 8) {
            String truncatedName = name.substring(0, 5) + "...";
            playerNameLabel[playerIndex].setText(truncatedName);
        } else {
            playerNameLabel[playerIndex].setText(name);
        }
    }
    
    
    // Sets the user name. Unlike setPlayerName, name is not truncated.
    public void setUserName(String name) {
        userNameLabel.setText(name);
    }
    
    
    //
    public void setPlayerBalance(int playerIndex, int balance) {
        playerBalance[playerIndex].setText("$" + balance);
    }
    
    
    //
    public void setUserBalance(int balance) {
        userBalance.setText("$" + balance);
    }
    
    
    // Sets the card image to the given card index. If hidden is true, then the deck image is shown instead. 
    public void setPlayerCard(int playerIndex, int cardPosition, Card card, boolean hidden) {
        ImageIcon image;
        
        if(hidden) {
            image = deckImage;
        } else {
            image = cardImage[card.getIndex()];
        }
        
        playerCard[playerIndex][cardPosition].setIcon(image);
    }
    
    
    //
    public void clearPlayerCards(int playerIndex) {
        for(int i = 0; i < playerCard[playerIndex].length; i++) {
            playerCard[playerIndex][i].setIcon(cardSpotImage);
        }
        
    }
    
    
    //
    public void setUserCard(int cardPosition, Card card) {
        userCard[cardPosition].setIcon(cardImage[card.getIndex()]);
    }
    
    
    //
    public void clearUserCards() {
        for(int i = 0; i < userCard.length; i++) {
            userCard[i].setIcon(cardSpotImage);
        }
    }
    
    
    //
    public void setTableCard(int cardPosition, Card card) {
        tableCard[cardPosition].setIcon(cardImage[card.getIndex()]);
    }
    
    
    //
    public void clearTableCards() {
        for(int i = 0; i < tableCard.length; i++) {
            tableCard[i].setIcon(cardSpotImage);
        }
    }
    
    
    // Gives the dealer chip to the selected player. If playerIndex is -1, then user is dealer. 
    public void setDealer(int playerIndex) {
        if(playerIndex == -1) {
            userChip.setIcon(dealerImage);
        } else {
            playerChip[playerIndex].setIcon(dealerImage);
        }
    }
    
    
    // Gives the big blind chip to the selected player. If playerIndex is -1, then user is big blind.
    public void setBigBlind(int playerIndex) {
        if(playerIndex == -1) {
            userChip.setIcon(bigBlindImage);
        } else {
            playerChip[playerIndex].setIcon(bigBlindImage);
        }
    }
    
    
    // Gives the little blind chip to the selected player. If playerIndex is -1, then user is little blind. 
    public void setLittleBlind(int playerIndex) {
        if(playerIndex == -1) {
            userChip.setIcon(littleBlindImage);
        } else {
            playerChip[playerIndex].setIcon(littleBlindImage);
        }
    }
    
    
    //
    public void setActivePlayer(int playerIndex) {
        if(playerIndex == -1) {
            userNameLabel.setForeground(activeTurnFontColor);
            
            for(int i = 0; i < playerNameLabel.length; i++) {
                playerNameLabel[i].setForeground(nameLabelFontColor);
            }
        } else {
            userNameLabel.setForeground(nameLabelFontColor);
            for(int i = 0; i < playerNameLabel.length; i++) {
                playerNameLabel[i].setForeground(nameLabelFontColor);
            }
            playerNameLabel[playerIndex].setForeground(activeTurnFontColor);
        }
    }
    
    
    //
    public void clearChips() {
        userChip.setIcon(chipSpotImage);
        
        for(int i = 0; i < playerChip.length; i++) {
            playerChip[i].setIcon(chipSpotImage);
        }
    }
    
    
    //
    public void clearTable() {
        for(int i = 0; i < playerCard.length; i++) {
            clearPlayerCards(i);
        }
        clearUserCards();
        clearChips();
        clearTableCards();
        setPotBalance(0);
    }
    
    
    //
    public void setNumComputerPlayers(int num) {
        for(int i = 0; i < playerHandPanel.length; i++) {
            playerHandPanel[i].setVisible(i < num);
        }
    }
    
    
    //
    public void setPotBalance(int balance) {
        potBalanceLabel.setText("$" + balance);
    }
    
    
    //
    public void setBettingAmounts(int minimum, int maximum, int increment) {
        betSpinnerModel = new SpinnerNumberModel(minimum, minimum, maximum, increment);
        betSpinner.setModel(betSpinnerModel);
        configSpinnerEditor();
    }
    
    
    //
    public int getUserBet() {
        return betSpinnerModel.getNumber().intValue();
    }
    
    
    //
    private void loadAssets() throws IOException {
        loadCardImages();
        loadChipImages();
    }
    
    
    //
    private void loadCardImages() {
        
        // Establishes ordering for cards.
        char suits[] = {'H', 'D', 'S', 'C'};
        char ranks[] = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
        
        cardImage = new ImageIcon[suits.length * ranks.length];
        
        // Load cards for all suits and ranks.
        for(int s = 0; s < suits.length; s++) {
            for(int r = 0; r < ranks.length; r++) {
                
                String path = String.format(cardImagePath, suits[s], ranks[r]);
                
                int cardIndex = (s * ranks.length) + r;
                
                cardImage[cardIndex] = new ImageIcon(path);
            }
        }
        
        deckImage = new ImageIcon(deckImagePath);
        deckStackImage = new ImageIcon(deckStackImagePath);
        cardSpotImage = new ImageIcon(cardSpotImagePath);
    }
    
    
    //
    private void loadChipImages() {
        dealerImage = new ImageIcon(dealerImagePath);
        bigBlindImage = new ImageIcon(bigBlindImagePath);
        littleBlindImage = new ImageIcon(littleBlindImagePath);
        chipSpotImage = new ImageIcon(chipSpotImagePath);
    }
    
    
    //
    private void initGUI() {
        setTitle("$ PLAWING GAMZ $");
        
        backgroundPanel = initBackgroundPanel();
        
        backgroundPanel.add(initTablePanel(), BorderLayout.CENTER);
        backgroundPanel.add(initUserPanel(), BorderLayout.PAGE_END);
        
        playerHandPanel = new JPanel[3];
        playerNameLabel = new JLabel[3];
        playerCard = new JLabel[3][2];
        playerChip = new JLabel[3];
        playerBalance = new JLabel[3];
        
        JPanel playerHandPanelHolder = new JPanel();
        playerHandPanelHolder.setOpaque(false);
        playerHandPanelHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        for(int i = 0; i < playerHandPanel.length; i++) {
            playerHandPanel[i] = initPlayerHandPanel(i);
            playerHandPanelHolder.add(playerHandPanel[i]);
        }
        
        backgroundPanel.add(playerHandPanelHolder, BorderLayout.PAGE_START);
        
        pack();
        setResizable(false);
    }
    
    
    //
    private BackgroundPanel initBackgroundPanel() {
        BackgroundPanel panel = new BackgroundPanel(backgroundImagePath);
        
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(backgroundWidth, backgroundHeight));
        Container container = getContentPane();
        container.add(panel);
        
        return panel;
    }
    
    
    //
    private JPanel initTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.setOpaque(false);
        
        tablePanel.add(Box.createHorizontalGlue());
        
        JPanel potPanel = new JPanel();
        potPanel.setLayout(new BoxLayout(potPanel, BoxLayout.Y_AXIS));
        potPanel.setOpaque(false);
        potPanel.setBorder(new EmptyBorder(0, 0, 0, 75));
        
        // Made extra long so that potBalanceLabel size doesn't shift cards.
        JLabel potLabel = new JLabel("Pot:     ");
        potLabel.setFont(potLabel.getFont().deriveFont(balanceLabelFontSize));
        potLabel.setForeground(nameLabelFontColor);
        potPanel.add(potLabel);
        
        potBalanceLabel = new JLabel("$-----");
        potBalanceLabel.setFont(potBalanceLabel.getFont().deriveFont(nameLabelFontSize));
        potBalanceLabel.setForeground(nameLabelFontColor);
        potPanel.add(potBalanceLabel);
        
        tablePanel.add(potPanel);
        
        tableCard = new JLabel[5];
        for(int i = 0; i < tableCard.length; i++) {
            tableCard[i] = new JLabel(cardSpotImage);
            tableCard[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            tablePanel.add(tableCard[i]);
        }
        
        tableDeck = new JLabel(deckStackImage);
        tableDeck.setBorder(new EmptyBorder(5, 75, 5, 5));
        tablePanel.add(tableDeck);
        
        tablePanel.add(Box.createHorizontalGlue());
        
        return tablePanel;
    }
    
    
    // User hand panel holds the user's cards and controls.
    private JPanel initUserPanel() {
        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userPanel.setOpaque(false);
        userPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        JPanel handPanel = new JPanel();
        handPanel.setLayout(new BorderLayout());
        handPanel.setOpaque(false);
        
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userNamePanel.setOpaque(false);
        
        userNameLabel = new JLabel("USER");
        userNameLabel.setForeground(nameLabelFontColor);
        userNameLabel.setFont(userNameLabel.getFont().deriveFont(nameLabelFontSize));
        userNamePanel.add(userNameLabel);
        
        userChip = new JLabel(chipSpotImage);
        userNamePanel.add(userChip);
        
        userBalance = new JLabel("$-----");
        userBalance.setForeground(nameLabelFontColor);
        userBalance.setFont(userBalance.getFont().deriveFont(balanceLabelFontSize));
        userNamePanel.add(userBalance);
        
        handPanel.add(userNamePanel, BorderLayout.PAGE_START);
        
        JPanel userCardPanel = new JPanel();
        userCardPanel.setLayout(new BoxLayout(userCardPanel, BoxLayout.X_AXIS));
        userCardPanel.setOpaque(false);
        
        userCardPanel.add(Box.createHorizontalGlue());
        
        userCard = new JLabel[2];
        for(int i = 0; i < userCard.length; i++) {
            userCard[i] = new JLabel(cardSpotImage);
            userCard[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            userCardPanel.add(userCard[i]);
        }
        
        userCardPanel.add(Box.createHorizontalGlue());
        handPanel.add(userCardPanel, BorderLayout.CENTER);
        
        userPanel.add(handPanel);
        
        JPanel betPanel = new JPanel();
        betPanel.setOpaque(false);
        betPanel.setLayout(new BorderLayout());
        betPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        betSpinner = new JSpinner();
        betSpinner.setOpaque(false);
        configSpinnerEditor();
        betSpinner.setMaximumSize(new Dimension(140, 40));
        betSpinner.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        betPanel.add(betSpinner, BorderLayout.PAGE_END);
        
        JPanel betButtonPanel = new JPanel();
        betButtonPanel.setOpaque(false);
        betButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        betButton = new JButton(new ImageIcon(betButtonImagePath)); // TODO - replace with image.
        betButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        betButtonPanel.add(betButton);
        foldButton = new JButton(new ImageIcon(foldButtonImagePath));
        foldButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        betButtonPanel.add(foldButton);
        betPanel.add(betButtonPanel, BorderLayout.CENTER);
        
        JPanel dealButtonPanel = new JPanel();
        dealButtonPanel.setOpaque(false);
        dealButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dealButton = new JButton(new ImageIcon(dealButtonImagePath));
        dealButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        dealButtonPanel.add(dealButton);
        betPanel.add(dealButtonPanel, BorderLayout.PAGE_START);
        
        userPanel.add(betPanel);
        
        return userPanel;
    }
    
    
    //
    private void registerActionListeners() {
        
        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleUserBet();
            }
        });
        
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleUserFold();
            }
        });
        
        dealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleStartRound();
            }
        });
    }
    
    
    //
    private void configSpinnerEditor() {
        Font spinnerFont = ((DefaultEditor) betSpinner.getEditor()).getTextField().getFont();
        spinnerFont = spinnerFont.deriveFont(betSpinnerFontSize);
        ((DefaultEditor) betSpinner.getEditor()).getTextField().setFont(spinnerFont);
        ((DefaultEditor) betSpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) betSpinner.getEditor()).getTextField().setColumns(9);
        ((DefaultEditor) betSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        ((DefaultEditor) betSpinner.getEditor()).getTextField().setForeground(Color.black);
    }
    
    
    //
    private JPanel initPlayerHandPanel(int playerIndex) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setOpaque(false);
        playerPanel.setBorder(new EmptyBorder(30, 40, 0, 40));
        
        JPanel playerNamePanel = new JPanel();
        playerNamePanel.setOpaque(false);
        playerNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        playerNameLabel[playerIndex] = new JLabel("CPU_" + playerIndex);
        playerNameLabel[playerIndex].setForeground(nameLabelFontColor);
        playerNameLabel[playerIndex].setFont(playerNameLabel[playerIndex].getFont().deriveFont(nameLabelFontSize));
        playerNamePanel.add(playerNameLabel[playerIndex]);
        
        playerChip[playerIndex] = new JLabel(chipSpotImage);
        playerNamePanel.add(playerChip[playerIndex]);
        
        playerBalance[playerIndex] = new JLabel("$-----");
        playerBalance[playerIndex].setForeground(nameLabelFontColor);
        playerBalance[playerIndex].setFont(playerBalance[playerIndex].getFont().deriveFont(balanceLabelFontSize));
        playerNamePanel.add(playerBalance[playerIndex]);
        
        playerPanel.add(playerNamePanel, BorderLayout.PAGE_END);
        
        JPanel playerCardPanel = new JPanel();
        playerCardPanel.setLayout(new BoxLayout(playerCardPanel, BoxLayout.X_AXIS));
        
        playerCardPanel.setOpaque(false);
        playerCardPanel.add(Box.createHorizontalGlue());
        for(int i = 0; i < playerCard[playerIndex].length; i++) {
            playerCard[playerIndex][i] = new JLabel(cardSpotImage);
            playerCard[playerIndex][i].setBorder(new EmptyBorder(5, 5, 5, 5));
            playerCardPanel.add(playerCard[playerIndex][i]);
        }
        playerCardPanel.add(Box.createHorizontalGlue());
        
        playerPanel.add(playerCardPanel, BorderLayout.CENTER);
        
        return playerPanel;
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
