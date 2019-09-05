package pokerjunkie.intro;

import pokerjunkie.Controller;
import pokerjunkie.PokerJunkies;



public class SplashController implements Controller {

    
    private SplashView view;
    
    
    //
    public SplashController() {
        view  = new SplashView(this);
    }
    
    
    //
    public void setLoadingMessage(String message) {
        view.setLoadingMessage(message);
    }
    
    
    //
    public void onLoadingFinished() {
        view.setLoadingMessageEnabled(false);
        view.setPlayButtonEnabled(true);
    }
    
    //
    public void onPlay() {
        PokerJunkies.launchLogin();
    }
    
    
    @Override
    public void onStart() {
        view.setVisible(true);
    }
    
    
    @Override
    public void onEnd() {
        view.close();
        view = null;
    }
    
}
