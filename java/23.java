import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Parser {
    private Scanner scanner;
    private Map<String, Integer> variables;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.variables = new HashMap<>();
    }

    public void parse() {
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (token.equals("print")) {
                printStatement();
            } else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                assignmentStatement(token);
            } else {
                error("Unexpected token: " + token);
            }
        }
    }

    private void printStatement() {
        if (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            System.out.println(value);
        } else {
            String variable = scanner.next();
            System.out.println(variables.getOrDefault(variable, 0));
        }
    }

    private void assignmentStatement(String varName) {
        if (scanner.hasNext("=") && scanner.next().equals("=")) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                variables.put(varName, value);
            } else {
                error("Expected an integer value for " + varName);
            }
        } else {
            error("Expected '=' after variable name");
        }
    }

    private void error(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }
}

public class SimpleCompiler {
    public static void main(String[] args) {
        String program = "x = 10\n"
                       + "y = 20\n"
                       + "print x\n"
                       + "print y\n"
                       + "print 100\n";

        Scanner scanner = new Scanner(program);
        Parser parser = new Parser(scanner);
        parser.parse();
        scanner.close();
    }
}