// LegalDocument.java
import java.util.List;

public class LegalDocument {
    private String title;
    private List<String> content;

    public LegalDocument(String title, List<String> content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "LegalDocument[Title: " + title + "]";
    }
}

// TermExtractor.java
import java.util.*;
import java.util.regex.*;

public class TermExtractor {
    private static final Pattern TERM_PATTERN = Pattern.compile("\\b[A-Z][a-z]*\\b");

    public List<String> extractTerms(LegalDocument document) {
        Set<String> uniqueTerms = new HashSet<>();
        for (String line : document.getContent()) {
            Matcher matcher = TERM_PATTERN.matcher(line);
            while (matcher.find()) {
                uniqueTerms.add(matcher.group());
            }
        }
        return new ArrayList<>(uniqueTerms);
    }
}

// RiskAssessment.java
import java.util.*;

public class RiskAssessment {
    private static final Set<String> RISK_TERMS = Set.of("liability", "penalty", "breach", "fine", "litigation");

    public double assessRisk(LegalDocument document) {
        double riskScore = 0.0;
        for (String line : document.getContent()) {
            for (String riskTerm : RISK_TERMS) {
                if (line.toLowerCase().contains(riskTerm)) {
                    riskScore += 1.0;
                }
            }
        }
        return riskScore;
    }
}

// ComplianceChecker.java
import java.util.*;

public class ComplianceChecker {
    private static final Set<String> COMPLIANCE_TERMS = Set.of("must", "shall", "require", "obligation");

    public double checkCompliance(LegalDocument document) {
        double complianceScore = 0.0;
        for (String line : document.getContent()) {
            for (String term : COMPLIANCE_TERMS) {
                if (line.toLowerCase().contains(term)) {
                    complianceScore += 1.0;
                }
            }
        }
        return complianceScore;
    }
}

// NLPProcessor.java
public class NLPProcessor {
    private TermExtractor termExtractor;
    private RiskAssessment riskAssessment;
    private ComplianceChecker complianceChecker;

    public NLPProcessor() {
        this.termExtractor = new TermExtractor();
        this.riskAssessment = new RiskAssessment();
        this.complianceChecker = new ComplianceChecker();
    }

    public void analyze(LegalDocument document) {
        List<String> extractedTerms = termExtractor.extractTerms(document);
        double riskScore = riskAssessment.assessRisk(document);
        double complianceScore = complianceChecker.checkCompliance(document);

        System.out.println("Document Analysis Summary for: " + document.getTitle());
        System.out.println("Extracted Terms: " + extractedTerms);
        System.out.println("Risk Score: " + riskScore);
        System.out.println("Compliance Score: " + complianceScore);
    }
}

// LegalDocumentAnalyzerApp.java
import java.util.Arrays;

public class LegalDocumentAnalyzerApp {
    public static void main(String[] args) {
        LegalDocument document = new LegalDocument("Sample Contract", 
            Arrays.asList(
                "This Agreement is entered into the day of , 2023 by and between Company A and Company B.",
                "Company A shall deliver the goods mentioned in Annexure A, and Company B shall make payments accordingly.",
                "The liability for breach of contract shall include penalties.",
                "All obligations described herein must be fulfilled by both parties.",
                "Non-compliance will result in litigation, penalties, or fines."
            ));

        NLPProcessor processor = new NLPProcessor();
        processor.analyze(document);
    }
}