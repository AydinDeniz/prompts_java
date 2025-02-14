// AudioInput.java
import java.util.Random;

public class AudioInput {
    private String[] sampleSentences = {
        "Hello, how are you?", 
        "Good morning, I need coffee.", 
        "Could you help me find this address?",
        "What is the weather like today?",
        "Thank you for your assistance."
    };

    public String captureAudio() {
        Random random = new Random();
        int index = random.nextInt(sampleSentences.length);
        System.out.println("Captured audio: \"" + sampleSentences[index] + "\"");
        return sampleSentences[index];
    }
}

// LanguageTranslator.java
import java.util.HashMap;
import java.util.Map;

public class LanguageTranslator {
    private Map<String, String> translationMap;

    public LanguageTranslator() {
        translationMap = new HashMap<>();
        translationMap.put("Hello, how are you?", "Hola, ¿cómo estás?");
        translationMap.put("Good morning, I need coffee.", "Buenos días, necesito café.");
        translationMap.put("Could you help me find this address?", "¿Podrías ayudarme a encontrar esta dirección?");
        translationMap.put("What is the weather like today?", "¿Cómo está el clima hoy?");
        translationMap.put("Thank you for your assistance.", "Gracias por tu ayuda.");
    }

    public String translate(String input) {
        System.out.println("Translating: \"" + input + "\"");
        return translationMap.getOrDefault(input, "Translation not available.");
    }
}

// ARDisplay.java
public class ARDisplay {
    public void showText(String text) {
        System.out.println("Displaying text in AR: \"" + text + "\"");
    }
}

// SmartGlasses.java
public class SmartGlasses {
    private AudioInput audioInput;
    private LanguageTranslator translator;
    private ARDisplay display;

    public SmartGlasses() {
        audioInput = new AudioInput();
        translator = new LanguageTranslator();
        display = new ARDisplay();
    }

    public void performTranslation() {
        String capturedAudio = audioInput.captureAudio();
        String translation = translator.translate(capturedAudio);
        display.showText(translation);
    }
}

// TranslationGlassesApp.java
public class TranslationGlassesApp {
    public static void main(String[] args) {
        SmartGlasses glasses = new SmartGlasses();
        glasses.performTranslation();
    }
}