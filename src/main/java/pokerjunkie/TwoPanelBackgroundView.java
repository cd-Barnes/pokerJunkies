package pokerjunkie;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TwoPanelBackgroundView extends JFrame {
    
    //
    protected BackgroundPanel backgroundPanel;
    protected String backgroundImageUrl = "src/main/resources/img/menu_background.png";
    protected int backgroundWidth = 628;
    protected int backgroundHeight = 468;
    //
    protected JPanel leftPanel;
    protected JPanel rightPanel;
    //
    protected final int leftPanelX = 50;
    protected final int leftPanelY = 20;
    protected final int leftPanelWidth = 225;
    protected final int leftPanelHeight = 400;
    //
    protected final int rightPanelX = 320;
    protected final int rightPanelY = 58;
    protected final int rightPanelHeight = 195;
    protected final int rightPanelWidth = 275;
    
    
    //
    public TwoPanelBackgroundView() {
        initPanels();
        
        pack();
        
        setResizable(false);
    }
    
    
    //
    protected void initPanels() {
        backgroundPanel = initBackgroundPanel();
        rightPanel = initContentPanel(rightPanelX, rightPanelY, rightPanelWidth, rightPanelHeight);
        leftPanel = initContentPanel(leftPanelX, leftPanelY, leftPanelWidth, leftPanelHeight);
        
        backgroundPanel.add(leftPanel);
        backgroundPanel.add(rightPanel);
    }
    
    
    //
    protected BackgroundPanel initBackgroundPanel() {
        BackgroundPanel panel = new BackgroundPanel(backgroundImageUrl);
        
        panel.setPreferredSize(new Dimension(backgroundWidth, backgroundHeight));
        Container container = getContentPane();
        container.add(panel);
        
        return panel;
    }
    
    
    //
    protected JPanel initContentPanel(int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        
        panel.setBounds(x, y, width, height);
        
        return panel;
    }
}
