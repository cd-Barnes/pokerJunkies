package chippy;


import pokerjunkie.Controller;
import pokerjunkie.PokerJunkies;


//
public class ChippyController implements Controller {
    
    //
    private final int defaultPhraseDuration = 3500;
    private ChippyPhraseThreadController phraseThreadController;
    
    // Chippy built-in phrases.
    private final String greeting = "Hey %s, it's your old pal Chippy!";
    private final String dadGreeting = "Hey Dad, welcome back!";
    
    private final String catchPhrase = "Bet large!";
    
    private final String[] randomPhrases = {
            "Here is a time to bet large.",
        
            "Your balance is looking kinda low,\n" +
            "why not toss some more in for good luck?",
            
            "Remember, you can't take it with you,\n" +
            "so you might as well give it to me!" };
    
    private final String[] dragPhrases = {
            "Weeeeeee!",
            
            "This part of the screen is cool too,\n" + 
            "I guess...",
            
            "You can put me anywhere you want,\n" + 
            "but you can't make me go away."};
    
    //
    private final ChippyView view;
    
    
    //
    public ChippyController() {
        view = new ChippyView(this);
    }
    
    
    // Causes Chippy so say a phrase for the duration.
    public void sayPhrase(String phrase, int duration) {
        
        if(phraseThreadController == null || phraseThreadController.isFinished()) {
            
            phraseThreadController = new ChippyPhraseThreadController(phrase, duration);
            Thread phraseThread = new Thread(phraseThreadController);
            
            phraseThread.start();
        }
    }
    
    
    //
    public void sayPhrase(String phrase) {
        sayPhrase(phrase, defaultPhraseDuration);
    }
    
    
    //
    public void sayRandomPhrase() {
        int phraseIndex = (int) (Math.random() * randomPhrases.length);
        
        sayPhrase(randomPhrases[phraseIndex]);
    }
    
    
    //
    public void sayDragPhrase() {
        int phraseIndex = (int) (Math.random() * dragPhrases.length);
        
        sayPhrase(dragPhrases[phraseIndex]);
    }
    
    
    //
    public void sayCatchPhrase() {
        sayPhrase(catchPhrase);
    }
    
    //
    public void makeChippyDissapointed() {
        view.doAnimation("dissapoint");
    }
    
    
    //
    public void greetUser() {
        String username = PokerJunkies.getUserDAO().getActiveUser().getName();
        
        if(username.equalsIgnoreCase("Cole") || username.equalsIgnoreCase("B")) {
            sayPhrase(dadGreeting);
        } else {
            sayPhrase(String.format(greeting, username));
        }
    }
    
    
    //
    private class ChippyPhraseThreadController implements Runnable {
        
        private String phrase;
        private int duration;
        private boolean finished;
        
        
        //
        public ChippyPhraseThreadController(String phrase, int duration) {
            this.phrase = phrase;
            this.duration = duration;
            finished = false;
        }
        
        
        //
        @Override
        public void run() {
            
            view.setSpeechText(phrase);
            view.setSpeechBubbleVisible(true);
            view.pack();
            
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
            
            view.setSpeechBubbleVisible(false);
            view.setSpeechText("");
            view.pack();
            finished = true;
        }
        
        
        //
        public boolean isFinished() {
            return finished;
        }
    }
    
    
    // Chippy never ends.
    @Override
    public void onEnd() {}
    
    
    //
    @Override
    public void onStart() {
        view.setVisible(true);
        view.startAnimation();
        sayCatchPhrase();
    }
}
