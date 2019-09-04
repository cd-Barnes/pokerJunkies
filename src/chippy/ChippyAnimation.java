package chippy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;



public class ChippyAnimation {
    
    
    private final String name;
    
    private final List<ImageIcon> frames;
    private int nextFrame;
    
    //
    public ChippyAnimation(String name, String basePath, int frameCount) throws IOException {
        this.name = name;
        nextFrame = 0;
        
        this.frames = new ArrayList();
        
        String leadingZeroes[] = {"", "0", "00", "000"};
        
        for(int i = 0; i < frameCount; i++) {

            int zeroes = 1;
            if(i < 10) {
                zeroes = 3;
            }
            else if(i < 100) {
                zeroes = 2;
            }

            String imagePath = String.format("%s%s%s.png", basePath, leadingZeroes[zeroes], Integer.toString(i));
            ImageIcon frame = new ImageIcon(imagePath);
            frames.add(frame);
        }
    }
    
    
    //
    public String getName() {
        return name;
    }
    
    
    //
    public ImageIcon getNextFrame() {
        ImageIcon frame = frames.get(nextFrame);
        
        nextFrame++;
        nextFrame %= frames.size();
        
        return frame;
    }
    
    
    //
    public void setNextFrameToStart() {
        nextFrame = 0;
    }
    
    
    //
    public boolean nextFrameIsBeginning() {
        return nextFrame == 0;
    }
}
