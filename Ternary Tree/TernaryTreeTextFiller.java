package main.textfiller;

// import java.io.UncheckedIOException;
import java.util.*;

// import javax.lang.model.util.ElementScanner14;
// import javax.validation.constraints.NotNull;

/**
 * A ternary-search-tree implementation of a text-autocompletion
 * trie, a simplified version of some autocomplete software.
 * 
 * @author Natalie Lau
 */
public class TernaryTreeTextFiller implements TextFiller {

    // Fields
    // -----------------------------------------------------------
    private TTNode root;
    private int size;
    
    // Constructor
    // -----------------------------------------------------------
    public TernaryTreeTextFiller() {
        this.root = null;
        this.size = 0;
    }
    
    
    // Methods
    // -----------------------------------------------------------
    
    /**
     * Returns the number of words stored inside TernaryTreeTextFiller.
     * 
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if tree has no complete words, false otherwise
     * 
     * @return true or false
     */
    public boolean empty() {
        return size == 0;
    }
    
    /**
     * Calls private helper method to add new term to tree
     * 
     * @param toAdd the new term to add to the tree
     */
    public void add(String toAdd) {
        if (!this.contains(normalizeTerm(toAdd))) {
            this.root = this.add(this.root, normalizeTerm(toAdd), 0);
        }
    }
    
    /**
     * Calls private helper method to see if the incoming term exists
     * 
     * @param query the term to search for
     * 
     * @return true if query is in the tree, false otherise
     */
    public boolean contains(String query) {
        return this.contains(this.root, normalizeTerm(query));
    }
    
    /**
     * Calls private helper method to autocomplete the incoming term
     * 
     * @param query the prefix to search for
     * 
     * @return a String that contains query as a prefix, null if no such String exists in the tree
     */
    public String textFill(String query) {
        return this.textFill(this.root, normalizeTerm(query), "");
    }
    
    /**
     * Calls private helper method to create alphabetically ordered list of all terms in tree
     * 
     * @return an alphabetically sorted ArrayList that contains all terms in tree
     */
    public List<String> getSortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (this.empty()) {
            return sortedList;
        }
        this.getSortedList(this.root, "", sortedList);
        return sortedList;
    }
    
    
    // Private Helper Methods
    // -----------------------------------------------------------
    
    /**
     * Normalizes a term to either add or search for in the tree,
     * since we do not want to allow the addition of either null or
     * empty strings within, including empty spaces at the beginning
     * or end of the string (spaces in the middle are fine, as they
     * allow our tree to also store multi-word phrases).
     * 
     * @param s The string to sanitize
     * 
     * @return The sanitized version of s
     */
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /**
     * Given two characters, c1 and c2, determines whether c1 is
     * alphabetically less than, greater than, or equal to c2
     * 
     * @param c1 The first character
     * @param c2 The second character
     * 
     * @return
     *   - some int less than 0 if c1 is alphabetically less than c2
     *   - 0 if c1 is equal to c2
     *   - some int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!

    /**
     * Searches the tree recursively to see if the incoming term exists
     * 
     * @param n the current node
     * @param query the term to search for
     * 
     * @return true if the term is in the tree, false otherwise
     */
    private boolean contains(TTNode n, String query) {
        if (n == null) {
            return false;
        }
        int compare = compareChars(query.charAt(0), n.letter);
        if (compare == 0) {
            if (query.length() == 1) {
                return n.wordEnd;
            }
            return contains(n.mid, query.substring(1));
        } else if (compare > 0) {
            return contains(n.right, query);
        } else {
            return contains(n.left, query);
        }
    }
    
    /**
     * Recursively searches the tree for a String containing the incoming term as a prefix
     * 
     * @param n the current node
     * @param query the prefix to search for
     * @param str a String that contains the already collected letters
     * 
     * @return a String that contains query as a prefix, null if no such String exists in the tree
     */
    private String textFill(TTNode n, String query, String str) {
        if (n == null) {
            return null;
        }
        int compare = compareChars(query.charAt(0), n.letter);
        if (compare == 0) {
            if (query.length() == 1) {
                return collectSuffix(n, str);
            }
            str += n.letter;
            return textFill(n.mid, query.substring(1), str);
        } else if (compare > 0) {
            return textFill(n.right, query, str);
        } else {
            return textFill(n.left, query, str);
        }
    }

    /**
     * Recursively traverses tree to add all terms to an ArrayList in alphabetical order
     * 
     * @param n the current node
     * @param str the current word being collected
     * @param list the current list of collected words
     * 
     * @return an alphabetically sorted ArrayList that contains all terms in tree
     */
    private void getSortedList(TTNode n, String str, ArrayList<String> list) {
        if (n.left != null) {
            getSortedList(n.left, str, list);
        }
        if (n.wordEnd) {
            list.add(str + n.letter);
        }
        if (n.mid != null) {
            getSortedList(n.mid, str + n.letter, list);
        }
        if (n.right != null) {
            getSortedList(n.right, str, list);
        }
    }

    /**
     * Recursively traverses the middle path from the incoming node to collect a suffix
     * 
     * @param n the starting node, will never be null
     * @param str the current String that contains already collected letters
     * 
     * @return a String that contains all letters from n to a word_end
     */
    private String collectSuffix(TTNode n, String str) {
        str += n.letter;
        return n.wordEnd ? str : collectSuffix(n.mid, str);
    }

    // Extra Methods
    // -----------------------------------------------------------
    
    /**
     * Calls private helper method to add new term with given priority to tree
     * 
     * @param toAdd the new term to add to the tree
     * @param priority the priority level of the new term
     */
    public void add(String toAdd, int priority) {
        toAdd = normalizeTerm(toAdd);
        if (!this.contains(toAdd)) {
            this.root = this.add(this.root, toAdd, priority);
        }
    }
    
    public String textFillPremium(String query) {
        return this.textFillPremium(this.root, normalizeTerm(query), "");
    }

    // Extra Private Methods
    // -----------------------------------------------------------

    /**
     * Recursively adds the given term with given priority to the tree
     * 
     * @param n the current node
     * @param toAdd the new term
     * @param priority the priority level of the new term
     * 
     * @return the updated node n
     */
    private TTNode add(TTNode n, String toAdd, int priority) {
        if (n == null) {
            return addSuffix(n, toAdd, priority);
        }
        int compare = compareChars(toAdd.charAt(0), n.letter);
        if (compare == 0) {
            if (n.priority < priority) {
                n.priority = priority;
            }
            if (toAdd.length() == 1 && !n.wordEnd) {
                this.size++;
                n.wordEnd = true;
                return n;
            }
            n.mid = add(n.mid, toAdd.substring(1), priority);
        } else if (compare > 0) {
            n.right = add(n.right, toAdd, priority);
        } else {
            n.left = add(n.left, toAdd, priority);
        }
        return n;
    }

    /**
     * Recursively adds the suffix with given priority to the middle path of given node
     * 
     * @param n the current node
     * @param suffix the suffix to add
     * @param priority the priority of the suffix
     * 
     * @return the updated node n
     */
    private TTNode addSuffix(TTNode n, String suffix, int priority) {
        if (suffix.length() <= 0) {
            return null;
        }
        n = new TTNode(suffix.charAt(0), suffix.length() == 1, priority);
        if (suffix.length() == 1) {
            this.size++;
        } else {
            n.mid = addSuffix(n.mid, suffix.substring(1), priority);
        }
        return n;
    }

    /**
     * Recursively searches the tree for the highest priority String
     * that contains the incoming term as a prefix
     * 
     * @param n the current node
     * @param query the prefix to search for
     * @param str the current String that contains the already collected letters
     * 
     * @return the highest priority String that contains query as a prefix, null if no such term exists
     */
    private String textFillPremium(TTNode n, String query, String str) {
        if (n == null) {
            return null;
        }
        int compare = compareChars(query.charAt(0), n.letter);
        if (compare < 0) {
            return textFillPremium(n.left, query, str);
        } else if (compare > 0) {
            return textFillPremium(n.right, query, str);
        } else {
            str += n.letter;
            if (query.length() == 1) {
                return n.mid == null ? str : collectSuffixPremium(n.mid, str, n.priority);
            }
            return textFillPremium(n.mid, query.substring(1), str);
        }
    }

    /**
     * Recursively collects suffix with highest priority starting at incoming node
     * 
     * @param n the current node
     * @param str the current String that contains already collected letters
     * @param priority the highest priority encountered so far
     * 
     * @return a String that contains term with highest priority
     */
    private String collectSuffixPremium(TTNode n, String str, int priority) {
        if (n.priority > priority) {
            priority = n.priority;
        }
        if (n.left != null) {
            str = collectSuffixPremium(n.left, str, priority);
            if (n.left.priority >= priority) {
                return str;
            }
        }
        if (n.right != null) {
            str = collectSuffixPremium(n.right, str, priority);
            if (n.right.priority >= priority) {
                return str;
            }
        }
        if (n.priority < priority) {
            return str;
        }
        if (n.mid != null) {
            return collectSuffixPremium(n.mid, str + n.letter, priority);
        }
        return str + n.letter;
    }
    
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /**
     * Internal storage of textfiller search terms
     * as represented using a Ternary Tree (TT) with TTNodes
     * [!] Note: these are currently implemented for the base-assignment;
     *     those endeavoring the extra-credit may need to make changes
     *     below (primarily to the fields and constructor)
     */
    private class TTNode {
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        int priority;

        /**
         * Constructs a new TTNode containing the given character
         * and priority, which can then be added to the existing tree.
         * 
         * @param letter char to store at this node
         * @param wordEnd whether or not this is a word-ending letter
         * @param priority priority of this char
         */
        TTNode(char letter, boolean wordEnd, int priority) {
            this.letter = letter;
            this.wordEnd = wordEnd;
            this.priority = priority;
        }

    }
    
}
