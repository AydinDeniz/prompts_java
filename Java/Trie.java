
import java.util.*;

// Trie Node class
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

// Trie class with auto-complete functionality
public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    // Check if the word is in the Trie
    public boolean search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isEndOfWord;
    }

    // Get all words that start with the given prefix
    public List<String> autoComplete(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return results; // Empty list if prefix not found
            }
        }
        findAllWords(current, new StringBuilder(prefix), results);
        return results;
    }

    // Recursive helper method to find all words starting from a given node
    private void findAllWords(TrieNode node, StringBuilder prefix, List<String> results) {
        if (node.isEndOfWord) {
            results.add(prefix.toString());
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            findAllWords(entry.getValue(), prefix.append(entry.getKey()), results);
            prefix.deleteCharAt(prefix.length() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("apricot");
        trie.insert("banana");
        trie.insert("berry");
        trie.insert("blueberry");

        System.out.println("Search 'apple': " + trie.search("apple"));
        System.out.println("Search 'app': " + trie.search("app"));
        System.out.println("Auto-complete for 'ap': " + trie.autoComplete("ap"));
        System.out.println("Auto-complete for 'b': " + trie.autoComplete("b"));
    }
}
