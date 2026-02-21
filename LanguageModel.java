import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
    String window = "";
    char c;
    In in = new In(fileName);
    for (int i = 0; i < windowLength; i++) {
        char tempChar = in.readChar();
        window += tempChar;
    }
    while (!in.isEmpty()) {
        c = in.readChar();
        List probs = CharDataMap.get(window);
        if (probs == null) {
            probs = new List();
            CharDataMap.put(window, probs);
        }
        probs.update(c);
        window = window.substring(1) + c;
    }
    for (List probs : CharDataMap.values()) {
        calculateProbabilities(probs);
    }
}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	void calculateProbabilities(List probs) {	
        Node start = probs.getNodeF();			
		ListIterator itr = new ListIterator(start);
        int total = 0;
        while (itr.hasNext()) {
            CharData cd = itr.next();
            total += cd.count;
        }
        ListIterator itr2 = new ListIterator(start);
        double cp = 0.0;
        while (itr2.hasNext()) {
            CharData cc = itr2.next();
            cc.p =( ((double)(1.0/total))* cc.count);
            cc.cp = cc.p +cp;
            cp = cc.cp;
        }


	}

    // Returns a random character from the given probabilities list.
	char getRandomChar(List probs) {
	
        double r = this.randomGenerator.nextDouble();
        ListIterator itr = new ListIterator(probs.getNodeF());
        while (itr.hasNext()) {
            CharData cd =itr.next();
            if (cd.cp >= r){return cd.chr;}
        }
		return 0;
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
		if (initialText.length() < this.windowLength) {
        return initialText;
    }

    StringBuilder generatedText = new StringBuilder(initialText);
    
    String currentWindow = initialText.substring(initialText.length() - this.windowLength);

    
    for (int i = 0; i < textLength; i++) {
        List probs = CharDataMap.get(currentWindow);
        
        
        if (probs == null) {
            break; 
        }
        
        char nextChar = getRandomChar(probs);
        generatedText.append(nextChar);
        
        currentWindow = currentWindow.substring(1) + nextChar;
    }

    return generatedText.toString();
}
       
	

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}
    }
  
