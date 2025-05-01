package tn.esprit.test;

import tn.esprit.util.BadWordsFilter;

public class BadWordsFilterTest {

    // This method will only run if you explicitly call it.
    public static void runTest() {
        String[] tests = {"Hello", "damn it!", "c0n", "Ceci est normal", "mot interdit"};

        System.out.println("=== TEST DU FILTRE DE MOTS ===");
        for (String test : tests) {
            boolean result = BadWordsFilter.containsBadWords(test);
            // Display whether the word is blocked or allowed
            System.out.printf("%-20s → %s%n",
                    test,
                    result ? "🔴 BLOQUÉ" : "🟢 AUTORISÉ");
        }
    }

    public static void main(String[] args) {
        runTest(); // Calling the test
    }
}
