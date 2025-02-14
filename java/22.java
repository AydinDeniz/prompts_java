import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;
import java.util.Scanner;

public class VoiceCommandInterface {

    private Synthesizer synthesizer;

    public VoiceCommandInterface() {
        try {
            // Setting up the Synthesizer
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speak(String text) {
        try {
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String command) {
        switch (command.toLowerCase()) {
            case "greet":
                speak("Hello! Hope you are having a great day!");
                break;
            case "status":
                speak("All systems are running smoothly.");
                break;
            case "goodbye":
                speak("Goodbye! Have a nice day!");
                break;
            default:
                speak("Sorry, I did not understand the command.");
                break;
        }
    }

    public void shutdown() {
        try {
            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VoiceCommandInterface vci = new VoiceCommandInterface();
        Scanner scanner = new Scanner(System.in);
        String command = "";

        vci.speak("Welcome to the voice command interface. Please enter a command.");

        while (!command.equalsIgnoreCase("exit")) {
            System.out.print("Enter command (greet, status, goodbye, exit): ");
            command = scanner.nextLine();
            vci.executeCommand(command);
            if (command.equalsIgnoreCase("exit")) {
                vci.speak("Exiting the application.");
            }
        }

        vci.shutdown();
        scanner.close();
    }
}