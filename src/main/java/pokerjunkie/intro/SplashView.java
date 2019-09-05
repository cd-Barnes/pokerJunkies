package pokerjunkie.intro;

import pokerjunkie.BackgroundPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class SplashView extends JFrame {
    
    private final String splashImageUrl = "src/main/resources/img/splash.png";
    private final String playButtonImageUrl = "src/main/resources/img/play_button.png";
    
    private final int splashWidth = 628;
    private final int splashHeight = 468;
    
    private JLabel playButton;
    private final int playButtonX = 80;
    private final int playButtonY = 320;
    private final int playButtonWidth = 160;
    private final int playButtonHeight = 75;
    
    private JLabel loadingMessage;
    private final int messageX = 50;
    private final int messageY = 330;
    private final int messageWidth = 320;
    private final int messageHeight = 60;
    private final float messageFontSize = 18.0f;
    
    private SplashController controller;
    
    
    //
    public SplashView(SplashController controller) {
        
        this.controller = controller;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            initGUI();
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
    
    // TODO - break into smaller functions.
    private void initGUI() throws IOException {
        
        setTitle("$ POKER JUNKIES $");
        
        BackgroundPanel backgroundPanel = initBackgroundPanel();
        
        playButton = initPlayButton();
        backgroundPanel.add(playButton);
        
        loadingMessage = initLoadingMessage();
        backgroundPanel.add(loadingMessage);
        
        pack();
        setResizable(false);
    }
    
    
    //
    private BackgroundPanel initBackgroundPanel() throws IOException {
        
        BackgroundPanel panel = new BackgroundPanel(splashImageUrl);
        
        panel.setPreferredSize(new Dimension(splashWidth, splashHeight));
        Container container = getContentPane();
        container.add(panel);
        
        return panel;
    }
    
    
    //
    private JLabel initPlayButton() {
        ImageIcon playButtonIcon = new ImageIcon(playButtonImageUrl);
        
        playButton = new JLabel(playButtonIcon);
        
        playButton.setBounds(playButtonX, playButtonY, playButtonWidth, playButtonHeight);
        playButton.setVisible(false);
        
        playButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(playButton.isVisible()) {
                    controller.onPlay();
                }
            }
            // Unused mouse events.
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        return playButton;
    }
    
    
    //
    private JLabel initLoadingMessage() {
        loadingMessage = new JLabel();
        
        loadingMessage.setBounds(messageX, messageY, messageWidth, messageHeight);
        loadingMessage.setForeground(Color.white);
        loadingMessage.setFont(loadingMessage.getFont().deriveFont(messageFontSize));
        
        return loadingMessage;
    }
    
    
    // Sets the loading message text.
    public void setLoadingMessage(String message) {
       loadingMessage.setText(message);
    }
    
    
    // Shows the loading message if it is enabled.
    public void setLoadingMessageEnabled(boolean enable) {
        loadingMessage.setVisible(enable);
    }
    
    
    // Shows the play button if it is enabled.
    public void setPlayButtonEnabled(boolean enable) {
        playButton.setVisible(enable);
    }
    
    
    //
    public void close() {
        setVisible(false);
        setEnabled(false);
        dispose();
    }
    
}
