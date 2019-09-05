package pokerjunkie;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BackgroundPanel extends JPanel {
        
        private Image backgroundImage;
        
        public BackgroundPanel(String imageUrl) {
            super();
            
            try {
                setBackgroundImage(imageUrl);
            } catch(IOException e) {
                backgroundImage = null;
            }
            
            setLayout(null);
        }
        
        
        public final void setBackgroundImage(String imageUrl) throws IOException {
            File imagePath = new File(imageUrl);
            backgroundImage = ImageIO.read(imagePath);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, null);
            }
        }
    }