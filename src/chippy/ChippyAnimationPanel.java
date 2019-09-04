package chippy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ChippyAnimationPanel extends JPanel {
    
    protected Map<String, ChippyAnimation> animations;
    protected Queue<String> animationQueue;
    protected ChippyAnimation currentAnimation;
    protected ImageIcon basePose;
    
    private final Thread animationThread;
    private final ChippyAnimationThreadController chippyAnimationController;
    
    private final int width;
    private final int height;
    
    private final String idleAnimationName;
    
    private JLabel chippyImageLabel;
    
    // Chippy currently runs at a cool 20 fps (minus whatever stupid java thread overhead)
    private final int CHIPPY_FRAME_DELAY = 50;
    
    
    
    
    //
    public ChippyAnimationPanel(ChippyAnimation idleAnimation, ImageIcon basePose, int width, int height) {
        this.basePose = basePose;
        animations = new HashMap();
        idleAnimationName = idleAnimation.getName();
        animations.put(idleAnimationName, idleAnimation);
        
        animationQueue = new LinkedList();
        this.width = width;
        this.height = height;
        
        chippyAnimationController = new ChippyAnimationThreadController();
        animationThread = new Thread(chippyAnimationController);
        
        initView();
    }
    
    
    //
    public void start() {
        animationThread.start();
    }
    
    
    //
    public void stop() {
        chippyAnimationController.stop();
    }
    
    
    //
    public void addAnimation(ChippyAnimation animation) {
        animations.put(animation.getName(), animation);
    }
    
    
    //
    public void clearQueuedAnimations() {
        animationQueue.clear();
    }
    
    
    //
    public void enqueueAnimation(String animationName) {
        if(animations.containsKey(animationName)) {
            animationQueue.add(animationName);
        }
    }
    
    
    //
    private void initView() {
        setOpaque(false);
        
        setPreferredSize(new Dimension(width, height));
        
        chippyImageLabel = new JLabel();
        
        add(chippyImageLabel, BorderLayout.CENTER);
    }
    
    
    // TODO - Maybe make this thread safe but what's the worst that could happen?
    protected String getNextAnimationName() {
        if(animationQueue.isEmpty()) {
            return idleAnimationName;
        } else {
            return animationQueue.remove();
        }
    }
    
    
    //
    protected void replaceImage(ImageIcon image) {
        chippyImageLabel.setIcon(image);
    }
    
    
    //
    private class ChippyAnimationThreadController implements Runnable {
        
        private boolean keepGoing;
        
        
        //
        @Override
        public void run() {
            keepGoing = true;
            
            replaceImage(basePose);
            
            while(keepGoing) {
                
                String nextAnimationName = getNextAnimationName();
                ChippyAnimation animation = animations.get(nextAnimationName);
                animation.setNextFrameToStart();
                
                do {
                    
                    replaceImage(animation.getNextFrame());
                    
                    try {
                        Thread.sleep(CHIPPY_FRAME_DELAY);
                    } catch (InterruptedException ex) {
                        System.exit(1);
                    }
                    
                } while(keepGoing && !animation.nextFrameIsBeginning());
                
            }
            
            replaceImage(basePose);
            
        }
        
        
        //
        public void stop() {
            keepGoing = false;
        }
        
    }
}
