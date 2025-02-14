// TextSegment.java
public class TextSegment {
    private String language;
    private String content;

    public TextSegment(String language, String content) {
        this.language = language;
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "[" + language.toUpperCase() + "] " + content;
    }
}

// Translator.java
import java.util.HashMap;
import java.util.Map;

public class Translator {
    private Map<String, String> languageMap;

    public Translator() {
        this.languageMap = new HashMap<>();
        languageMap.put("Hello", "Hola"); // English to Spanish
        languageMap.put("world", "mundo");
        languageMap.put("Hello", "Bonjour"); // English to French
        languageMap.put("world", "monde");
    }

    public String translate(TextSegment segment, String targetLanguage) {
        StringBuilder translatedContent = new StringBuilder();
        String[] words = segment.getContent().split(" ");
        for (String word : words) {
            String translatedWord = languageMap.getOrDefault(word, word);
            translatedContent.append(translatedWord).append(" ");
        }
        return translatedContent.toString().trim();
    }
}

// Transcriber.java
public class Transcriber {
    public String transcribe(String content) {
        // Simulating transcription by returning content in uppercase
        return content.toUpperCase();
    }
}

// LanguageServiceSystem.java
import java.util.ArrayList;
import java.util.List;

public class LanguageServiceSystem {
    private Translator translator;
    private Transcriber transcriber;

    public LanguageServiceSystem() {
        this.translator = new Translator();
        this.transcriber = new Transcriber();
    }

    public void processSegments(List<TextSegment> segments, String targetLanguage) {
        for (TextSegment segment : segments) {
            System.out.println("Original: " + segment);
            String translated = translator.translate(segment, targetLanguage);
            System.out.println("Translated (" + targetLanguage + "): " + translated);
            String transcribed = transcriber.transcribe(segment.getContent());
            System.out.println("Transcribed: " + transcribed);
            System.out.println();
        }
    }
}

// LanguageTranslationApp.java
import java.util.Arrays;

public class LanguageTranslationApp {
    public static void main(String[] args) {
        LanguageServiceSystem serviceSystem = new LanguageServiceSystem();

        TextSegment segment1 = new TextSegment("English", "Hello world");
        TextSegment segment2 = new TextSegment("English", "Good morning");

        serviceSystem.processSegments(Arrays.asList(segment1, segment2), "Spanish");
    }
}