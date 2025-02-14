// DNASequence.java
public class DNASequence {
    private String sequence;

    public DNASequence(String sequence) {
        this.sequence = sequence.toUpperCase();
    }

    public String getSequence() {
        return sequence;
    }

    public int length() {
        return sequence.length();
    }

    public char nucleotideAt(int position) {
        return sequence.charAt(position);
    }

    @Override
    public String toString() {
        return sequence;
    }
}

// SequenceAlignment.java
public class SequenceAlignment {
    public static String alignSequences(DNASequence seq1, DNASequence seq2) {
        StringBuilder aligned1 = new StringBuilder();
        StringBuilder aligned2 = new StringBuilder();

        int m = seq1.length();
        int n = seq2.length();
        int i = 0, j = 0;

        while (i < m && j < n) {
            if (seq1.nucleotideAt(i) == seq2.nucleotideAt(j)) {
                aligned1.append(seq1.nucleotideAt(i));
                aligned2.append(seq2.nucleotideAt(j));
                i++;
                j++;
            } else {
                aligned1.append(seq1.nucleotideAt(i));
                aligned2.append('-');
                i++;
            }
        }

        while (i < m) {
            aligned1.append(seq1.nucleotideAt(i));
            aligned2.append('-');
            i++;
        }

        while (j < n) {
            aligned1.append('-');
            aligned2.append(seq2.nucleotideAt(j));
            j++;
        }

        return aligned1.toString() + "\n" + aligned2.toString();
    }
}

// SequenceAnalyzer.java
import java.util.*;

public class SequenceAnalyzer {
    public static Map<Character, Integer> nucleotideFrequency(DNASequence sequence) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char nucleotide : sequence.getSequence().toCharArray()) {
            frequencyMap.put(nucleotide, frequencyMap.getOrDefault(nucleotide, 0) + 1);
        }
        return frequencyMap;
    }

    public static int gcContentPercentage(DNASequence sequence) {
        int gcCount = 0;
        for (char nucleotide : sequence.getSequence().toCharArray()) {
            if (nucleotide == 'G' || nucleotide == 'C') {
                gcCount++;
            }
        }
        return (gcCount * 100) / sequence.length();
    }
}

// GenomicDataDriver.java
public class GenomicDataDriver {
    public static void main(String[] args) {
        DNASequence seq1 = new DNASequence("ACTGATT");
        DNASequence seq2 = new DNASequence("ACCGTTA");

        System.out.println("Sequence 1: " + seq1);
        System.out.println("Sequence 2: " + seq2);

        System.out.println("Aligned Sequences:");
        System.out.println(SequenceAlignment.alignSequences(seq1, seq2));

        System.out.println("Nucleotide Frequency for Sequence 1:");
        Map<Character, Integer> frequencyMap = SequenceAnalyzer.nucleotideFrequency(seq1);
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("GC Content for Sequence 1: " + SequenceAnalyzer.gcContentPercentage(seq1) + "%");
    }
}