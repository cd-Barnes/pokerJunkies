package chippy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;


//
public class ChippyView extends JFrame {
    
    //
    private final ChippyController controller;
    
    //
    private ChippyAnimationPanel animationPanel;
    private SpeechBubble speechBubble;
    
    
    //
    public ChippyView(ChippyController controller) {
        
        this.controller = controller;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            initGUI();
        } catch(IOException e) {
            System.exit(1);
        }
    }
    
    
    //
    public void startAnimation() {
        animationPanel.start();
    }
    
    
    //
    public void doAnimation(String animationName) {
        animationPanel.enqueueAnimation(animationName);
    }
    
    
    //
    public void setSpeechBubbleVisible(boolean visible) {
        speechBubble.setVisible(visible);
    }
    
    
    //
    public void setSpeechText(String text) {
        speechBubble.setText(text);
    }
    
    
    //
    private void initGUI() throws IOException {
        configWindow();
        
        JPanel contentPane = new DraggableFramePanel(this);
        contentPane.setLayout(new BorderLayout());
        contentPane.setOpaque(false);
        
        initChippyAnimationPanel();
        contentPane.add(animationPanel, BorderLayout.CENTER);
        
        speechBubble = new SpeechBubble();
        
        contentPane.add(speechBubble, BorderLayout.LINE_END);
        
        this.setContentPane(contentPane);
        this.pack();
    }
    
    
    //
    private void initChippyAnimationPanel() throws IOException {
        
        ImageIcon basePose = new ImageIcon("res/chippy/base_pose.png");
        ChippyAnimation idleAnimation = new ChippyAnimation("idle", "res/chippy/idle/", 32);
        
        animationPanel = new ChippyAnimationPanel(idleAnimation, basePose, 500, 500);
        
        ChippyAnimation dissapointAnimation = new ChippyAnimation("dissapoint", "res/chippy/dissapoint/", 32);
        animationPanel.addAnimation(dissapointAnimation);
    }
    
    
    // Makes the window undecorated, invisible, and sets it to be always on top of other windows.
    private void configWindow() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
    }

    //this class allows an undecorated frame to be moved
    private class DraggableFramePanel extends JPanel {
        
        private Point lastPress;
        private final JFrame parentFrame;
        private boolean dragging;

        //
        public DraggableFramePanel(final JFrame parentFrame) {
            this.parentFrame = parentFrame;
            initMouseListeners();
        }
        
        //
        private void initMouseListeners() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    lastPress = e.getPoint();
                    dragging = false;
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(dragging) {
                        controller.sayDragPhrase();
                    } else {
                        controller.sayRandomPhrase();
                    }
                }
            });
            addMouseMotionListener(new MouseInputAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int xMoved = e.getX() - lastPress.x;
                    int yMoved = e.getY() - lastPress.y;
                    moveFrame(xMoved, yMoved);
                    dragging = true;
                }
            });
        }
        
        //
        private void moveFrame(int xMoved, int yMoved) {
            int x = parentFrame.getLocation().x + xMoved;
            int y = parentFrame.getLocation().y + yMoved;
            parentFrame.setLocation(x, y);
        }
    }

    
    //
    private class SpeechBubble extends JPanel {
        
        // Drawing style constants.
        private final int outlineThickness = 3;
        private final int padding = outlineThickness * 3;
        private final Color bubbleFill = new Color(0.5f, 0.8f, 1f);
        private final Color bubbleOutline = Color.black;
        
        // Text constants.
        private final float fontSize = 18.0f;
        private final Color fontColor = Color.black;
        
        // Text components.
        private JTextArea textLabel;
        
        
        //
        public SpeechBubble() {
            setLayout(new GridBagLayout());
            initTextLabel();
            
            setOpaque(false);
            setVisible(false);
        }
        
        
        //
        private void initTextLabel() {
            textLabel = new JTextArea();
            textLabel.setEditable(false);
            textLabel.setOpaque(false);
            textLabel.setForeground(fontColor);
            textLabel.setFont(textLabel.getFont().deriveFont(fontSize));
            textLabel.setBorder(new EmptyBorder(padding, padding, padding, padding));
            
            JPanel textPanel = new JPanel();
            textPanel.setBackground(bubbleFill);
            textPanel.setBorder(BorderFactory.createLineBorder(bubbleOutline, outlineThickness, true));
            textPanel.setLayout(new GridBagLayout());
            textPanel.add(textLabel);
            
            add(textPanel);
        }
        
        
        //
        public void setText(String phrase) {
            textLabel.setText(phrase);
        }
    }
}
